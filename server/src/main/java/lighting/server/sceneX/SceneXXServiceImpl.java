package lighting.server.sceneX;

import lighting.server.IO.IIOService;
import lighting.server.artnet.ArtnetListener;
import lighting.server.artnet.ArtnetSender;
import lighting.server.frame.Frame;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class SceneXXServiceImpl implements ISceneXService {

    private ArtnetListener artnetListener;
    private ArtnetSender artnetSender;

    private final IIOService iOService;

    public SceneXXServiceImpl(IIOService iOService) {
        this.iOService = iOService;
    }

    public boolean recordScene(int button_id) {
        artnetListener = new ArtnetListener(this);
        artnetListener.recordData(button_id);
        try {
            Thread.sleep(3000);
            artnetListener.getArtNetClient().stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(artnetListener.isFramesAdded()){
            System.out.println("Something recorded");
            return true;
        }
        else return false;
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

    @Override
    public Frame getLiveData() {
        return null;
    }


}
