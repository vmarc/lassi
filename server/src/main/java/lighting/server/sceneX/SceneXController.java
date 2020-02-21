package lighting.server.sceneX;

import lighting.server.scene.Reply;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SceneXController {

    private final ISceneXService sceneService;

    public SceneXController(ISceneXService sceneService) {
        this.sceneService = sceneService;
    }

    @PostMapping(value = "/api/recordscenebis")
    //@CrossOrigin(origins = "http://localhost:4200")
    public Reply recordScene() {
        System.out.println("recording...");
        return sceneService.recordScene();
    }

}
