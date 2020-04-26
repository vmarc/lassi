package lighting.server.sceneX;

import lighting.server.scene.Reply;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SceneXController {

    private final ISceneXService sceneService;

    public SceneXController(ISceneXService sceneService) {
        this.sceneService = sceneService;
    }

    @PostMapping(value = "/api/recordscenebis")
    public Reply recordScene() {
        System.out.println("recording...");
        return sceneService.recordScene();
    }

    @PutMapping(value = "/api/savescene/")
    public void saveScene(@RequestBody SceneX sceneX) {
        try {
            this.sceneService.updateSceneFromDisk(sceneX);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO
    @GetMapping(value = "/api/playscene/{scene_id}")
    public void playScene(@PathVariable String scene_id) {
        try {
            this.sceneService.playScene(scene_id);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Raspberrypi..............");
    }

    @GetMapping(value = "/api/sceneslist")
    public List<SceneX> getScenes() {
        try {
            return sceneService.getAllScenesFromDisk();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/api/deletescene/{scene_id}")
    public void deleteScene(@PathVariable String scene_id) {
        try {
            this.sceneService.deleteSceneFromDisk(scene_id);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @GetMapping(value = "/api/getscene/{scene_id}")
    public SceneX getScene(@PathVariable String scene_id) {
        try {
            return this.sceneService.getSceneFromDisk(scene_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
