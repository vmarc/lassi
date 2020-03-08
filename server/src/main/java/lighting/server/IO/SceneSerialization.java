package lighting.server.IO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lighting.server.sceneX.SceneX;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SceneSerialization {

    private Path parentDir = Paths.get(System.getProperty("user.dir")).getParent();
    private Path scenesDir = Paths.get(parentDir + "/scenes/");

    public SceneSerialization() {
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

}
