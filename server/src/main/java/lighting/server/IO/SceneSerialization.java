package lighting.server.IO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lighting.server.scene.Scene;
import lighting.server.scene.Scenes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SceneSerialization {

    private Path parentDir = Paths.get(System.getProperty("user.dir")).getParent();
    private Path scenesDir = Paths.get(parentDir + "/scenes/");
    private UUID uuid;

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

    public void saveScenesToJSON() throws IOException {
        int[] dmx = IntStream.generate(() -> new Random().nextInt(512)).limit(512).toArray();
        Scenes scenes = new Scenes("Scene 1");
        Scene scene = new Scene(1, "test3", dmx);
        Scene scene2 = new Scene(2, "test4", dmx);
        scenes.addSceneToScenes(scene);
        scenes.addSceneToScenes(scene2);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.writeValue(new File((scenesDir) + "/scene_" + uuid.randomUUID() + ".json" ), scenes);

    }

    public List<Scenes> getAllScenesFromDisk() throws IOException {
        List<String> result;
        List<Scenes> scenesList = new ArrayList<>();

        Stream<Path> walk = Files.walk(Paths.get(String.valueOf(scenesDir)));

        result = walk.filter(Files::isRegularFile)
                .map(x -> x.toString()).filter(f -> f.endsWith(".json")).collect(Collectors.toList());

        result.forEach(System.out::println);

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        for (String j : result) {
            Scenes scenes = objectMapper.readValue(new File(j), Scenes.class);
            scenesList.add(scenes);
        }

        return scenesList;

    }

}
