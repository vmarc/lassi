package lighting.server.IO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lighting.server.sceneX.SceneX;
import lighting.server.settings.Settings;
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
public class IOServiceImpl implements IIOService {

    private Path parentDir = Paths.get(System.getProperty("user.dir")).getParent();
    private Path scenesDir = Paths.get(parentDir + "/scenes/");
    private Path settingsDir = Paths.get(parentDir + "/settings/");

    public IOServiceImpl() {
        createDirectories();
        //default settings
        Settings settings = new Settings(50, 5 );
        try {
            saveSettingsToDisk(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDirectories() {
        File scenes = new File(String.valueOf(scenesDir));
        if (!scenes.exists()) {
            if (scenes.mkdir()) {
                System.out.println("Scenes directory is created!");
            } else {
                System.out.println("Failed to create Scenes directory!");
            }
        }
        File settings = new File(String.valueOf(settingsDir));
        if (!settings.exists()) {
            if (settings.mkdir()) {
                System.out.println("Settings directory is created!");
            } else {
                System.out.println("Failed to create Settings directory!");
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
            //Excluding 'button' 0
            if (scene.getButtonId() == 0) {

            } else {
                buttonsWithScene.add(scene.getButtonId());
            }


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

    public void saveSettingsToDisk(Settings settings) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File((settingsDir) + "/settings.json"), settings);
    }

    public Settings getSettingsFromDisk() throws IOException {
        Stream<Path> walk = Files.walk(Paths.get(String.valueOf(settingsDir)));
        String result = walk.filter(Files::isRegularFile)
                .map(x -> x.toString()).filter(f -> f.endsWith(".json")).collect(Collectors.joining());

        ObjectMapper objectMapper = new ObjectMapper();
        Settings settings = objectMapper.readValue(new File(result), Settings.class);
        return settings;
    }

}
