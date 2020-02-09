package lighting.server.scene;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SceneController {

	private final SceneService sceneService;

	public SceneController(SceneService sceneService) {
		this.sceneService = sceneService;
	}

	@GetMapping(value = "/api/record/{sceneId}")
	public Reply recordScene(@PathVariable Integer sceneId) {
		return sceneService.recordScene(sceneId);
	}

	@GetMapping(value = "/api/goto/{sceneId}")
	public Reply gotoScene(@PathVariable Integer sceneId) {
		return sceneService.gotoScene(sceneId);
	}

	@GetMapping(value = "/api/scene/{sceneId}")
	public Scene scene(@PathVariable Integer sceneId) {
		return sceneService.getScene(sceneId);
	}

	@GetMapping(value = "/api/scenes")
	public Scenes scenes() {
		return sceneService.getScenes();
	}

	@PostMapping(value = "/api/setup")
	public void setup(@RequestBody Setup setup) {
		sceneService.printSetup(setup);
	}

	@MessageMapping("/status")
	@SendTo("/topic/scenes")
	public SceneStatusesMessage sceneStatuses(SceneStatusesRequest request) {
		return new SceneStatusesMessage(sceneService.getCurrentScene().getId());
	}
}
