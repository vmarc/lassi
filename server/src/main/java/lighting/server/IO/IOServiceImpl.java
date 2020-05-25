package lighting.server.IO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lighting.server.scene.Scene;
import lighting.server.settings.Settings;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class IOServiceImpl implements IIOService {

    private Path parentDir = Paths.get("/home/pi/DMX-Lighting/");
    private Path scenesDir = Paths.get(parentDir + "/scenes/");
    private Path settingsDir = Paths.get(parentDir + "/settings/");
    private Path logsDir = Paths.get(parentDir + "/lightingLogs/");

    public IOServiceImpl() {
        createDirectories();
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
                createDefaultSettings();
            } else {
                System.out.println("Failed to create Settings directory!");
            }
        }
        File logs = new File(String.valueOf(logsDir));
        if (!logs.exists()) {
            if (logs.mkdir()) {
                System.out.println("Logs directory is created!");
            } else {
                System.out.println("Failed to create Logs directory!");
            }
        }


    }

    public void deleteLog() throws IOException {
        Path filePath = Paths.get(logsDir + "/Lighting.log");
        Files.deleteIfExists(filePath);
        writeToLog(0, "New log file created!");
    }

    public void writeToLog(int level, String message) {

        Logger logger = Logger.getLogger("Lighting logs");
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler(logsDir.toString() + "/Lighting.log", true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            if (level == 0) {
                logger.info(message);
                fh.close();
            } else if (level == -1) {
                logger.warning(message);
                fh.close();
            }


        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createDefaultSettings(){
        //default settings
        Settings settings = new Settings(40, 5 );
        try {
            saveSettingsToDisk(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveSceneToDisk(Scene scene) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.writeValue(new File((scenesDir) + "/scene_" + scene.getId() + ".json" ), scene);

    }

    public String downloadScene(String scene_id) throws IOException {
        Path filePath = Paths.get(scenesDir + "/scene_" + scene_id + ".json");
        String str = FileUtils.readFileToString(filePath.toFile(), "UTF-8");
        return str;
    }

    public List<Scene> getAllScenesFromDisk() throws IOException {
        List<String> result;
        List<Scene> scenesList = new ArrayList<>();

        Stream<Path> walk = Files.walk(Paths.get(String.valueOf(scenesDir)));

        result = walk.filter(Files::isRegularFile)
                .map(x -> x.toString()).filter(f -> f.endsWith(".json")).collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        for (String j : result) {
            Scene scene = objectMapper.readValue(new File(j), Scene.class);
            scenesList.add(scene);
        }


        return scenesList;

    }

    public void deleteSceneFromDisk(String scene_id) throws IOException {
        Path filePath = Paths.get(scenesDir + "/scene_" + scene_id + ".json");
        Files.deleteIfExists(filePath);

    }

    public Scene getSceneFromDisk(String scene_id) throws IOException {
        Path filePath = Paths.get(scenesDir + "/scene_" + scene_id + ".json");
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Scene scene = objectMapper.readValue(filePath.toFile(), Scene.class);
        return scene;

    }

    public void updateSceneFromDisk(Scene sceneX) throws IOException {
        List<Scene> scenesFromDisk = getAllScenesFromDisk();

        //if chosen button is already assigned to an existing scene, the old scene will get value 0
        for (Scene scene : scenesFromDisk) {
            if (scene.getButtonId() == sceneX.getButtonId()) {
                scene.setButtonId(0);
                deleteSceneFromDisk(scene.getId());
                saveSceneToDisk(scene);
            }

        }
        deleteSceneFromDisk(sceneX.getId());
        saveSceneToDisk(sceneX);

    }

    public List<Boolean> getButtons() throws IOException {
        List<Boolean> buttons = new ArrayList<>( Arrays.asList(true, true, true, true, true, true, true, true, true));

        List<Scene> scenes = getAllScenesFromDisk();

        for (Scene scene : scenes) {
            if (scene.getButtonId() != 0) {
                buttons.set((scene.getButtonId() -1), false);
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
