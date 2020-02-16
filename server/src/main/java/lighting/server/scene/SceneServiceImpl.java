package lighting.server.scene;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class SceneServiceImpl implements SceneService {

	private final Logger logger = LoggerFactory.getLogger(SceneServiceImpl.class);

	private final Scenes scenes = new Scenes();
	private Scene currentScene = null;

	private SimpMessagingTemplate template;

	public SceneServiceImpl(SimpMessagingTemplate template) {
		this.template = template;
	}

	public SceneServiceImpl() {
	}

	public Reply recordScene(int sceneId) {
		logger.info(String.format("record scene %d", sceneId));
		currentScene = scenes.getScene(sceneId);
		sendSceneStatuses();
		return new Reply();
	}

	public Reply gotoScene(int sceneId) {
		logger.info(String.format("go to scene %d", sceneId));
		currentScene = scenes.getScene(sceneId);
		sendSceneStatuses();
		return new Reply();
	}

	public void saveSceneToJSON() {
		int[] dmx = IntStream.generate(() -> new Random().nextInt(512)).limit(512).toArray();
		Duration time = Duration.ofSeconds(40);
		Scene scene = new Scene(2, "test",dmx);
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
		try {
			objectMapper.writeValue(new File("/Users/matthiassomay/Desktop/scenes/test2.json"), scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Scene> getAllScenesFromDisk() throws IOException {
		List<String> result;
		List<Scene> sceneList = new ArrayList<>();


		Stream<Path> walk = Files.walk(Paths.get("/Users/matthiassomay/Desktop/scenes"));

		result = walk.filter(Files::isRegularFile)
					.map(x -> x.toString()).filter(f -> f.endsWith(".json")).collect(Collectors.toList());

		result.forEach(System.out::println);

		ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

		for (String j : result) {
			Scene scene = objectMapper.readValue(new File(j), Scene.class);
			sceneList.add(scene);
		}

		return sceneList;

	}

	public Scene getCurrentScene() {
		return currentScene;
	}

	public Scene getScene(int sceneId) {
		return scenes.getScene(sceneId);
	}

	public Scenes getScenes() {
		return scenes;
	}

	public void printSetup(Setup setup) {
		String message = "Scene " + setup.getScene() + "\n";
		message += "  option1=" + setup.isOption1() + "\n";
		message += "  option2=" + setup.isOption2() + "\n";
		message += "  selection=" + setup.getSelection() + "\n";
		message += "  level1=" + setup.getLevel1() + "\n";
		message += "  level2=" + setup.getLevel2();
		logger.info(message);
	}

	private void sendSceneStatuses() {
		if (currentScene != null) {
			this.template.convertAndSend("/topic/scenes", new SceneStatusesMessage(currentScene.getId()));
		}
	}
}
