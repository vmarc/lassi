package lighting.server.scene;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lighting.server.IO.IOService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SceneController {

	private static final Logger log = LogManager.getLogger(SceneController.class);

	private final SceneService sceneService;
	private final IOService iOService;

	public SceneController(final SceneService sceneService, final IOService iOService) {
		this.sceneService = sceneService;
		this.iOService = iOService;
	}

	@GetMapping(value = "/api/recordSceneSingleFrame/{button_id}")
	public boolean recordSceneSingleFrame(@PathVariable int button_id) {
		log.info("Recorded a scene with a single frame to button " + button_id);
		return sceneService.recordScene(button_id);
	}

	@GetMapping(value = "/api/recordSceneMultipleFrames/{button_id}")
	public boolean recordSceneMultipleFrames(@PathVariable int button_id) {
		log.info("Recorded a scene with multiple frames to button " + button_id);
		return sceneService.recordSceneMultipleFrames(button_id);
	}

	@GetMapping(value = "/api/stopRecording")
	public boolean stopRecording() {
		log.info("Stopped recording");
		return sceneService.stopRecording();
	}

	@PutMapping(value = "/api/savescene/")
	public void saveScene(@RequestBody Scene scene) {
		try {
			iOService.updateSceneFromDisk(scene);
			log.info("Saved/updated scene to disk with ID: " + scene.getId());
		} catch (IOException e) {
			log.error("Could not save scene to disk with ID: " + scene.getId(), e);
		}
	}

	@GetMapping(value = "api/downloadscene/{scene_id}")
	public String downloadScene(@PathVariable String scene_id) {
		try {
			log.info("Downloaded scene with ID: " + scene_id);
			return iOService.downloadScene(scene_id);
		} catch (IOException e) {
			log.error("Could not download scene with ID: " + scene_id, e);
		}
		return null;
	}

	@GetMapping(value = "/api/playscene/{button_id}")
	public boolean playSceneFromButton(@PathVariable int button_id) {
		try {
			log.info("Playing scene from button " + button_id);
			return sceneService.playSceneFromButton(button_id);
		} catch (IOException e) {
			log.error("Could not play scene from button " + button_id, e);
			return false;
		}
	}

	@GetMapping(value = "/api/playscenefromid/{id}")
	public boolean playSceneFromId(@PathVariable String id) {
		try {
			log.info("Playing scene from ID " + id);
			return sceneService.playSceneFromId(id);
		} catch (IOException e) {
			log.error("Could not play scene from ID " + id, e);
			return false;
		}
	}

	@GetMapping(value = "/api/stop")
	public void stop() {
		sceneService.stop();
		log.info("Stopped playing a scene");
	}

	@GetMapping(value = "/api/pause/{bool}")
	public void pause(@PathVariable boolean bool) {
		sceneService.pause(bool);
		log.info("Paused a playing scene");
	}

	@GetMapping(value = "/api/sceneslist")
	public List<Scene> getScenes() {
		try {
			log.info("Retrieved all scenes from disk");
			return iOService.getAllScenesFromDisk();
		} catch (IOException e) {
			log.error("Could not retrieve all scenes from disk", e);
		}
		return null;
	}

	@GetMapping(value = "/api/deletescene/{scene_id}")
	public void deleteScene(@PathVariable String scene_id) {
		try {
			iOService.deleteSceneFromDisk(scene_id);
			log.info("Scene deleted with ID: " + scene_id);
		} catch (IOException e) {
			log.error("Could not delete scene with ID: " + scene_id, e);
		}
	}

	@GetMapping(value = "/api/getscene/{scene_id}")
	public Scene getSceneById(@PathVariable String scene_id) {
		try {
			log.info("Retrieved scene from disk with ID: " + scene_id);
			return iOService.getSceneFromDisk(scene_id);
		} catch (IOException e) {
			log.error("Could not retrieved scene from disk with ID: " + scene_id, e);
		}
		return null;
	}

	@GetMapping(value = "api/getbuttons")
	public List<Boolean> getButtons() {
		try {
			log.info("Retrieved buttons status");
			return iOService.getButtons();
		} catch (IOException e) {
			log.error("Could not retrieve buttons status", e);
		}
		return null;
	}

	@GetMapping(value = "api/getPages")
	public int getPages() {
		try {
			log.info("Retrieved page size");
			return iOService.getSettingsFromDisk().getButtonPages();
		} catch (IOException e) {
			log.error("Could not retrieve page size", e);
		}
		return 0;
	}
}
