package lighting.server.IO;

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

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lighting.server.scene.Scene;
import lighting.server.settings.Settings;

@Component
public class IOServiceImpl implements IOService {

	private static final Logger log = LogManager.getLogger(IOServiceImpl.class);

	private final Path parentDir = Paths.get(System.getProperty("user.dir"));
	private final Path scenesDir = Paths.get(parentDir + "/scenes/");
	private final Path settingsDir = Paths.get(parentDir + "/settings/");

	public IOServiceImpl() {
		createDirectories();
	}

	//Check if directories are created, if not creates them
	public void createDirectories() {
		File scenes = new File(String.valueOf(scenesDir));
		if (!scenes.exists()) {
			if (scenes.mkdir()) {
				log.info("Scenes directory is created!");
			} else {
				log.info("Failed to create Scenes directory!");
			}
		}
		File settings = new File(String.valueOf(settingsDir));
		if (!settings.exists()) {
			if (settings.mkdir()) {
				log.info("Settings directory is created!");
				createDefaultSettings();
			} else {
				log.info("Failed to create Settings directory!");
			}
		}
	}

	public void createDefaultSettings() {
		//default settings
		Settings settings = new Settings(40, 5, 1);
		try {
			saveSettingsToDisk(settings);
		} catch (IOException e) {
			log.error("Could not save settings to disk", e);
		}
	}

	//maps Scene object to .json and saves it to disk
	public void saveSceneToDisk(Scene scene) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
		objectMapper.writeValue(new File((scenesDir) + "/scene_" + scene.getId() + ".json"), scene);
	}

	//Returns String representation of a Scene .json file
	public String downloadScene(String scene_id) throws IOException {
		Path filePath = Paths.get(scenesDir + "/scene_" + scene_id + ".json");
		return FileUtils.readFileToString(filePath.toFile(), "UTF-8");
	}

	public List<Scene> getAllScenesFromDisk() throws IOException {
		List<String> result;
		List<Scene> scenesList = new ArrayList<>();

		Stream<Path> walk = Files.walk(Paths.get(String.valueOf(scenesDir)));

		result = walk.filter(Files::isRegularFile).map(x -> x.toString()).filter(f -> f.endsWith(".json")).collect(Collectors.toList());

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

	//gets .json file from disk by scene_id and maps it to a Scene object
	public Scene getSceneFromDisk(String scene_id) throws IOException {
		Path filePath = Paths.get(scenesDir + "/scene_" + scene_id + ".json");
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
		return objectMapper.readValue(filePath.toFile(), Scene.class);
	}

	//Overwrites Scene from disk
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

	//gets the status of all buttons
	public List<Boolean> getButtons() throws IOException {
		List<Boolean> buttons = new ArrayList<>(
				Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
						true, true, true, true));

		List<Scene> scenes = getAllScenesFromDisk();

		for (Scene scene : scenes) {
			if (scene.getButtonId() != 0) {
				buttons.set((scene.getButtonId() - 1), false);
			}
		}
		return buttons;
	}

	//maps a Settings object to .json file and saves it on disk
	public void saveSettingsToDisk(Settings settings) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(new File((settingsDir) + "/settings.json"), settings);
	}

	//gets .json file from disk and maps it to a Settings object
	public Settings getSettingsFromDisk() throws IOException {
		Stream<Path> walk = Files.walk(Paths.get(String.valueOf(settingsDir)));
		String result = walk.filter(Files::isRegularFile).map(x -> x.toString()).filter(f -> f.endsWith(".json")).collect(Collectors.joining());
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(new File(result), Settings.class);
	}
}
