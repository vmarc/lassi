package lighting.server.sceneX;

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

    @GetMapping(value = "/api/recordscenebis/{button_id}")
    public void recordScene(@PathVariable int button_id) {
        System.out.println("recording...");
        this.sceneService.recordScene(button_id);
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
    @GetMapping(value = "/api/playscene/{button_id}")
    public void playSceneFromButton(@PathVariable int button_id) {
        try {
            this.sceneService.playSceneFromButton(button_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @GetMapping(value = "api/getbuttons")
    public List<Boolean> getButtonsWithoutScene() {
        try {
            return this.sceneService.getButtons();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
