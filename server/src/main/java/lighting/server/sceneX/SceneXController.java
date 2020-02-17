package lighting.server.sceneX;

import lighting.server.scene.Reply;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SceneXController {

    private final ISceneXService sceneService;

    public SceneXController(ISceneXService sceneService) {
        this.sceneService = sceneService;
    }

    @GetMapping(value = "/api/recordscenebis")
    public Reply recordScene() {
        return sceneService.recordScene();
    }

}
