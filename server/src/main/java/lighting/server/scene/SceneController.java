package lighting.server.scene;

import lighting.server.IO.IIOService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SceneController {

    private final ISceneService sceneService;
    private final IIOService iioService;

    public SceneController(ISceneService sceneService, IIOService iioService) {
        this.sceneService = sceneService;
        this.iioService = iioService;
    }

    @GetMapping(value = "/api/recordscenebis/{button_id}")
    public boolean recordScene(@PathVariable int button_id) {
        System.out.println("recording...");
        return this.sceneService.recordScene(button_id);
    }

    @PutMapping(value = "/api/savescene/")
    public void saveScene(@RequestBody Scene scene) {
        try {
            this.iioService.updateSceneFromDisk(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "api/downloadscene/{scene_id}")
    public String downloadScene(@PathVariable String scene_id) {
        try {
            return this.iioService.downloadScene(scene_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/api/playscene/{button_id}")
    public void playSceneFromButton(@PathVariable int button_id) {
        try {
            this.sceneService.playSceneFromButton(button_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/api/playscenefromid/{id}")
    public void playSceneFromId(@PathVariable String id) {
        try {
            this.sceneService.playSceneFromId(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @GetMapping(value = "/api/sceneslist")
    public List<Scene> getScenes() {
        try {
            return iioService.getAllScenesFromDisk();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/api/deletescene/{scene_id}")
    public void deleteScene(@PathVariable String scene_id) {
        try {
            this.iioService.deleteSceneFromDisk(scene_id);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @GetMapping(value = "/api/getscene/{scene_id}")
    public Scene getSceneById(@PathVariable String scene_id) {
        try {
            return this.iioService.getSceneFromDisk(scene_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "api/getbuttons")
    public List<Boolean> getButtons() {
        try {
            return this.iioService.getButtons();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
