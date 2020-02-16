package lighting.server.IO;

import lighting.server.scene.Scenes;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ScenesController {

    private final SceneSerialization sceneSerialization = new SceneSerialization();

    public ScenesController() {
    }

    @GetMapping(value = "/api/sceneslist")
    public List<Scenes> getScenes() throws IOException {
        return sceneSerialization.getAllScenesFromDisk();
    }


}
