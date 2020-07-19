package lighting.server.scene;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import lighting.server.IO.IOService;
import lighting.server.artnet.ArtnetListener;
import lighting.server.artnet.ArtnetSender;

@Component
public class SceneServiceImpl implements SceneService {

	private static final Logger log = LogManager.getLogger(SceneServiceImpl.class);

	private final ArtnetListener artnetListener;
	private final ArtnetSender artnetSender;
	private final IOService iOService;

	public SceneServiceImpl(final IOService iOService) {
		this.iOService = iOService;
		this.artnetListener = new ArtnetListener(iOService);
		this.artnetSender = new ArtnetSender(iOService);
	}

	public boolean recordScene(int button_id) {
		try {
			List<Scene> scenesOnDisk = iOService.getAllScenesFromDisk();
			for (Scene s : scenesOnDisk) {
				if (button_id != 0 && s.getButtonId() == button_id) {
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
			artnetListener.recordData(button_id);
		} catch (IOException e) {
			log.error("Could not record a scene with a single frames to button " + button_id, e);
		}

		try {
			Thread.sleep(3000);
			artnetListener.getArtNetClient().stop();
		} catch (InterruptedException e) {
			log.error("Could not record a scene with a single frames to button " + button_id, e);
		}
		return artnetListener.isFramesAdded();
	}

	public boolean recordSceneMultipleFrames(int button_id) {
		try {
			List<Scene> scenesOnDisk = iOService.getAllScenesFromDisk();
			for (Scene s : scenesOnDisk) {
				if (button_id != 0 && s.getButtonId() == button_id) {
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
			artnetListener.recordData(button_id);
			return true;
		} catch (IOException e) {
			log.error("Could not record a scene with multiple frames to button " + button_id, e);
			return false;
		}
	}

	public boolean stopRecording() {
		return artnetListener.stopRecording();
	}

	public boolean playSceneFromButton(int button) throws IOException {
		List<Scene> scenes = iOService.getAllScenesFromDisk();
		for (Scene scene : scenes) {
			if (scene.getButtonId() == button) {
				return playScene(scene);
			}
		}
		return false;
	}

	public boolean playSceneFromId(String id) throws IOException {
		List<Scene> scenes = iOService.getAllScenesFromDisk();

		for (Scene scene : scenes) {
			if (scene.getId().equals(id)) {
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
