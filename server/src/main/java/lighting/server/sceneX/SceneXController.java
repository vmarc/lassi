package lighting.server.sceneX;

import lighting.server.IO.SceneSerialization;
import lighting.server.scene.Reply;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class SceneXController {

    private final ISceneXService sceneService;
    private final SceneSerialization sceneSerialization = new SceneSerialization();

    public SceneXController(ISceneXService sceneService) {
        this.sceneService = sceneService;
    }

    @PostMapping(value = "/api/recordscenebis")
    public Reply recordScene() {
        System.out.println("recording...");
        return sceneService.recordScene();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/api/sceneslist")
    public List<SceneX> getScenes() throws IOException {
        return sceneSerialization.getAllScenesFromDisk();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/api/deletescene/{scene_id}")
    public void deleteScene(@PathVariable String scene_id) {
        try {
            this.sceneSerialization.deleteSceneFromDisk(scene_id);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/api/getscene/{scene_id}")
    public SceneX getScene(@PathVariable String scene_id) {
        try {
            return this.sceneSerialization.getSceneFromDisk(scene_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
