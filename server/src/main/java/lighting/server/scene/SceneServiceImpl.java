package lighting.server.scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SceneServiceImpl implements SceneService {

	private final Logger logger = LoggerFactory.getLogger(SceneServiceImpl.class);
	private final Scenes scenes = new Scenes();
	private Scene currentScene = null;
	private SimpMessagingTemplate template;

	public SceneServiceImpl(SimpMessagingTemplate template) {
		this.template = template;
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
