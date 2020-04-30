package lighting.server.sceneX;

import ch.bildspur.artnet.ArtNetClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lighting.server.artnet.ArtnetListener;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SceneXXServiceImpl implements ISceneXService {

    private Path parentDir = Paths.get(System.getProperty("user.dir")).getParent();
    private Path scenesDir = Paths.get(parentDir + "/scenes/");

    private ArtnetListener artnetListener;


    public SceneXXServiceImpl() {
        createDirectory(scenesDir);
    }

    public void createDirectory(Path dir) {
        File file = new File(String.valueOf(dir));
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

    public void recordScene(int button_id) {
        artnetListener = new ArtnetListener();
        artnetListener.recordData();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            saveScenesToJSON(artnetListener.getSceneX());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("test made");
    }

    public void playSceneFromButton(int button) throws IOException {
        List<SceneX> scenes = getAllScenesFromDisk();

        for (SceneX scene : scenes) {
            if (scene.getButtonId() == button) {
                //TODO play scene
                System.out.println("playing scene from button");
            }

        }

    }

    public void saveScenesToJSON(SceneX sceneX) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.writeValue(new File((scenesDir) + "/scene_" + sceneX.getId() + ".json" ), sceneX);
    }

    public List<SceneX> getAllScenesFromDisk() throws IOException {
        List<String> result;
        List<SceneX> scenesList = new ArrayList<>();

        Stream<Path> walk = Files.walk(Paths.get(String.valueOf(scenesDir)));

        result = walk.filter(Files::isRegularFile)
                .map(x -> x.toString()).filter(f -> f.endsWith(".json")).collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        for (String j : result) {
            SceneX scene = objectMapper.readValue(new File(j), SceneX.class);
            scenesList.add(scene);
        }

        return scenesList;

    }

    public void deleteSceneFromDisk(String scene_id) throws IOException {
        Path filePath = Paths.get(scenesDir + "/scene_" + scene_id + ".json");
        Files.deleteIfExists(filePath);
    }

    public SceneX getSceneFromDisk(String scene_id) throws IOException {
        Path filePath = Paths.get(scenesDir + "/scene_" + scene_id + ".json");
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        SceneX scene = objectMapper.readValue(filePath.toFile(), SceneX.class);
        return scene;

    }

    public void updateSceneFromDisk(SceneX sceneX) throws IOException {
        List<SceneX> scenesFromDisk = getAllScenesFromDisk();

        //if chosen button is already assigned to an existing scene, the old scene will get value 0
        for (SceneX scene : scenesFromDisk) {
            if (scene.getButtonId() == sceneX.getButtonId()) {
                scene.setButtonId(0);
                deleteSceneFromDisk(scene.getId());
                saveScenesToJSON(scene);
            }

        }
        deleteSceneFromDisk(sceneX.getId());
        saveScenesToJSON(sceneX);

    }

    public List<Integer> getButtonsWithScene() throws IOException {
        List<Integer> buttonsWithScene = new ArrayList<>();

        List<SceneX> scenes = getAllScenesFromDisk();

        for (SceneX scene : scenes) {
            buttonsWithScene.add(scene.getButtonId());

        }
        return buttonsWithScene;
    }

    public List<Integer> getButtonsWithoutScene() throws IOException {
        List<Integer> buttons = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<SceneX> scenes = getAllScenesFromDisk();

        for (SceneX scene : scenes) {
            if (buttons.contains(scene.getButtonId())) {
                buttons.remove(scene.getButtonId());
            }

        }
        return buttons;
    }




}
