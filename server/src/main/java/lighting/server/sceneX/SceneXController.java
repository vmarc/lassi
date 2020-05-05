package lighting.server.sceneX;

import lighting.server.IO.IIOService;
import lighting.server.frame.Frame;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SceneXController {

    private final ISceneXService sceneService;
    private final IIOService iioService;

    public SceneXController(ISceneXService sceneService, IIOService iioService) {
        this.sceneService = sceneService;
        this.iioService = iioService;
    }

    @GetMapping(value = "/api/recordscenebis/{button_id}")
    public void recordScene(@PathVariable int button_id) {
        System.out.println("recording...");

        this.sceneService.recordScene(button_id);
    }

    @PutMapping(value = "/api/savescene/")
    public void saveScene(@RequestBody SceneX sceneX) {
        try {
            this.iioService.updateSceneFromDisk(sceneX);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    public SceneX getScene(@PathVariable String scene_id) {
        try {
            return this.iioService.getSceneFromDisk(scene_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "api/getbuttons")
    public List<Boolean> getButtons() {
        System.out.println("sending buttons");
        try {
            return this.iioService.getButtons();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "api/livedata")
    public Frame getLiveData() {
        //return this.sceneService.getLiveData();
        return null;
    }

    @GetMapping(value = "api/donerecording")
    public boolean recordingDone() {
        return this.sceneService.isRecordingDone();
    }

}
