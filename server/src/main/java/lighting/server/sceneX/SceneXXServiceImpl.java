package lighting.server.sceneX;

import ch.bildspur.artnet.ArtNetClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lighting.server.artnet.ArtnetListener;
import lighting.server.frame.Frame;
import lighting.server.scene.Reply;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class SceneXXServiceImpl implements ISceneXService {

    private Path parentDir = Paths.get(System.getProperty("user.dir")).getParent();
    private Path scenesDir = Paths.get(parentDir + "/scenes/");

    ArtnetListener artnetListener;

    public SceneXXServiceImpl() {
        createScenesDirectory();
    }

    public void createScenesDirectory() {
        File file = new File(String.valueOf(scenesDir));
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

    public Reply recordScene() {
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
        return null;
    }

    public void playScene(String scene_id) throws IOException {
        SceneX sceneX = getSceneFromDisk(scene_id);
        ArtNetClient artnet = new ArtNetClient();
        artnet.start();

        for (Frame dmx : sceneX.getFrames()) {
            artnet.broadcastDmx(0, 0, dmx.getDmxValues());
        }
        artnet.stop();
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

        //result.forEach(System.out::println);

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
        System.out.println("delete successful");
    }

    public SceneX getSceneFromDisk(String scene_id) throws IOException {
        Path filePath = Paths.get(scenesDir + "/scene_" + scene_id + ".json");
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        SceneX scene = objectMapper.readValue(filePath.toFile(), SceneX.class);
        return scene;

    }


}
