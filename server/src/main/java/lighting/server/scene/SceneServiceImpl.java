package lighting.server.scene;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

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
		Scene scene = new Scene(1, "testScene", dmx);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File("/Users/matthiassomay/Desktop/scenes/test.json"), scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
