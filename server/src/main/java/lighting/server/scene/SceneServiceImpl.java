package lighting.server.scene;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import lighting.server.IO.IOService;
import lighting.server.artnet.OldArtnetListener;
import lighting.server.artnet.ArtnetSender;

@Component
public class SceneServiceImpl implements SceneService {

	private static final Logger log = LogManager.getLogger(SceneServiceImpl.class);

	private final OldArtnetListener artnetListener;
	private final ArtnetSender artnetSender;
	private final IOService iOService;

	public SceneServiceImpl(final IOService iOService) {
		this.iOService = iOService;
		this.artnetListener = new OldArtnetListener(iOService);
		this.artnetSender = new ArtnetSender(iOService);
	}

	public boolean recordScene(int buttonId) {
		try {
			List<Scene> scenesOnDisk = iOService.getAllScenesFromDisk();
			for (Scene s : scenesOnDisk) {
				if (buttonId != 0 && s.getButtonId() == buttonId) {
					s.setButtonId(0);
					iOService.updateSceneFromDisk(s);
				}
			}
		} catch (IOException e) {
			log.error("Could not read scenes from disk", e);
		}

		try {
			artnetListener.setFramesAdded(false);
			artnetListener.setNumberOfFrames(1);
			artnetListener.recordData(buttonId);
		} catch (IOException e) {
			log.error("Could not record a scene with a single frames to button " + buttonId, e);
		}

		try {
			Thread.sleep(3000);
			artnetListener.getArtNetClient().stop();
		} catch (InterruptedException e) {
			log.error("Could not record a scene with a single frames to button " + buttonId, e);
		}
		return artnetListener.isFramesAdded();
	}

	public boolean recordSceneMultipleFrames(int buttonId) {
		try {
			List<Scene> scenesOnDisk = iOService.getAllScenesFromDisk();
			for (Scene s : scenesOnDisk) {
				if (buttonId != 0 && s.getButtonId() == buttonId) {
					s.setButtonId(0);
					iOService.updateSceneFromDisk(s);
				}
			}
		} catch (IOException e) {
			log.error("Could dot read all scenes from disk", e);
		}

		try {
			artnetListener.setFramesAdded(false);
			artnetListener.setNumberOfFrames(20000);
			artnetListener.recordData(buttonId);
			return true;
		} catch (IOException e) {
			log.error("Could not record a scene with multiple frames to button " + buttonId, e);
			return false;
		}
	}

	public boolean stopRecording() {
		return artnetListener.stopRecording();
	}

	public boolean playSceneFromButton(int buttonId) throws IOException {
		List<Scene> scenes = iOService.getAllScenesFromDisk();
		for (Scene scene : scenes) {
			if (scene.getButtonId() == buttonId) {
				return playScene(scene);
			}
		}
		return false;
	}

	public boolean playSceneFromId(String sceneId) throws IOException {
		List<Scene> scenes = iOService.getAllScenesFromDisk();

		for (Scene scene : scenes) {
			if (scene.getId().equals(sceneId)) {
				return playScene(scene);
			}
		}
		return false;
	}

	public boolean playScene(Scene scene) {
		artnetSender.setSceneToPlay(scene);
		artnetSender.sendData();
		return true;
	}

	public void stop() {
		artnetSender.stop();
	}

	public void pause(boolean bool) {
		artnetSender.pause(bool);
	}
}
