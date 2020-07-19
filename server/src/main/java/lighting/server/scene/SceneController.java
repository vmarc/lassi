package lighting.server.scene;

import java.io.IOException;
import java.util.List;

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

	private final SceneService sceneService;
	private final IOService iOService;

	public SceneController(final SceneService sceneService, final IOService iOService) {
		this.sceneService = sceneService;
		this.iOService = iOService;
	}

	@GetMapping(value = "/api/recordSceneSingleFrame/{button_id}")
	public boolean recordSceneSingleFrame(@PathVariable int button_id) {
		iOService.writeToLog(0, "Recorded a scene with a single frame to button " + button_id);
		return this.sceneService.recordScene(button_id);
	}

	@GetMapping(value = "/api/recordSceneMultipleFrames/{button_id}")
	public boolean recordSceneMultipleFrames(@PathVariable int button_id) {
		iOService.writeToLog(0, "Recorded a scene with multiple frames to button " + button_id);
		return this.sceneService.recordSceneMultipleFrames(button_id);
	}

	@GetMapping(value = "/api/stopRecording")
	public boolean stopRecording() {
		iOService.writeToLog(0, "Stopped recording");
		return this.sceneService.stopRecording();
	}

	@PutMapping(value = "/api/savescene/")
	public void saveScene(@RequestBody Scene scene) {
		try {
			this.iOService.updateSceneFromDisk(scene);
			iOService.writeToLog(0, "Saved/updated scene to disk with ID: " + scene.getId());
		} catch (IOException e) {
			iOService.writeToLog(-1, "Could not save scene to disk with ID: " + scene.getId());
			e.printStackTrace();
		}
	}

	@GetMapping(value = "api/downloadscene/{scene_id}")
	public String downloadScene(@PathVariable String scene_id) {
		try {
			iOService.writeToLog(0, "Downloaded scene with ID: " + scene_id);
			return this.iOService.downloadScene(scene_id);
		} catch (IOException e) {
			iOService.writeToLog(-1, "Could not download scene with ID: " + scene_id);
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/api/playscene/{button_id}")
	public boolean playSceneFromButton(@PathVariable int button_id) {
		try {
			iOService.writeToLog(0, "Playing scene from button " + button_id);
			return this.sceneService.playSceneFromButton(button_id);
		} catch (IOException e) {
			iOService.writeToLog(-1, "Could not play scene from button " + button_id);
			e.printStackTrace();
			return false;
		}
	}

	@GetMapping(value = "/api/playscenefromid/{id}")
	public boolean playSceneFromId(@PathVariable String id) {
		try {
			iOService.writeToLog(0, "Playing scene from ID " + id);
			return this.sceneService.playSceneFromId(id);
		} catch (IOException e) {
			iOService.writeToLog(-1, "Could not play scene from ID " + id);
			e.printStackTrace();
			return false;
		}
	}

	@GetMapping(value = "/api/stop")
	public void stop() {
		this.sceneService.stop();
		iOService.writeToLog(0, "Stopped playing a scene");
	}

	@GetMapping(value = "/api/pause/{bool}")
	public void pause(@PathVariable boolean bool) {
		this.sceneService.pause(bool);
		iOService.writeToLog(0, "Paused a playing scene");
	}

	@GetMapping(value = "/api/sceneslist")
	public List<Scene> getScenes() {
		try {
			iOService.writeToLog(0, "Retrieved all scenes from disk");
			return iOService.getAllScenesFromDisk();
		} catch (IOException e) {
			iOService.writeToLog(-1, "Could not retrieve all scenes from disk");
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/api/deletescene/{scene_id}")
	public void deleteScene(@PathVariable String scene_id) {
		try {
			this.iOService.deleteSceneFromDisk(scene_id);
			iOService.writeToLog(0, "Scene deleted with ID: " + scene_id);
		} catch (IOException e) {
			iOService.writeToLog(-1, "Could not delete scene with ID: " + scene_id);
			e.printStackTrace();
		}
	}

	@GetMapping(value = "/api/getscene/{scene_id}")
	public Scene getSceneById(@PathVariable String scene_id) {
		try {
			iOService.writeToLog(0, "Retrieved scene from disk with ID: " + scene_id);
			return this.iOService.getSceneFromDisk(scene_id);
		} catch (IOException e) {
			iOService.writeToLog(-1, "Could not retrieved scene from disk with ID: " + scene_id);
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "api/getbuttons")
	public List<Boolean> getButtons() {
		try {
			iOService.writeToLog(0, "Retrieved buttons status");
			return this.iOService.getButtons();
		} catch (IOException e) {
			iOService.writeToLog(-1, "Could not retrieve buttons status");
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "api/getPages")
	public int getPages() {
		try {
			iOService.writeToLog(0, "Retrieved page size");
			return this.iOService.getSettingsFromDisk().getButtonPages();
		} catch (IOException e) {
			iOService.writeToLog(-1, "Could not retrieve page size");
			e.printStackTrace();
		}
		return 0;
	}
}
