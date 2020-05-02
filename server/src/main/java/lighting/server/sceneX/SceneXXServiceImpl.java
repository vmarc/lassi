package lighting.server.sceneX;

import lighting.server.IO.IIOService;
import lighting.server.artnet.ArtnetListener;
import lighting.server.artnet.ArtnetSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SceneXXServiceImpl implements ISceneXService {

    private ArtnetListener artnetListener;
    private ArtnetSender artnetSender;

    private final IIOService iOService;

    public SceneXXServiceImpl(IIOService iOService) {
        this.iOService = iOService;
    }

    public void recordScene(int button_id) {
        artnetListener = new ArtnetListener(this.iOService);
        artnetListener.recordData(button_id);
        System.out.println("test made");
    }

    public void playSceneFromButton(int button) throws IOException {
        artnetSender = new ArtnetSender();
        List<SceneX> scenes = iOService.getAllScenesFromDisk();

        for (SceneX scene : scenes) {
            if (scene.getButtonId() == button) {
                this.artnetSender.setSceneToPlay(scene);
                this.artnetSender.sendData();
                System.out.println("playing scene from button");
            }

        }

    }



}
