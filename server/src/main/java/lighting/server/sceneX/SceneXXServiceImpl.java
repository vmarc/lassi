package lighting.server.sceneX;

import lighting.server.IO.IIOService;
import lighting.server.SceneFader;
import lighting.server.artnet.ArtnetListener;
import lighting.server.artnet.ArtnetSender;
import lighting.server.frame.Frame;
import lighting.server.settings.Settings;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SceneXXServiceImpl implements ISceneXService {

    private ArtnetListener artnetListener;
    private ArtnetSender artnetSender;
    private SceneX currentPlayingScene = new SceneX();
    private Settings settings;

    private final IIOService iOService;

    public SceneXXServiceImpl(IIOService iOService) {
        this.iOService = iOService;
        try {
            this.settings = this.iOService.getSettingsFromDisk();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean recordScene(int button_id) {
        artnetListener = new ArtnetListener(iOService);
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
                SceneFader sceneFader = new SceneFader(settings.getFramesPerSecond(), settings.getFadeTimeInSeconds(), currentPlayingScene.getFrames().get(0), scene.getFrames().get(0));
                try {
                    this.artnetSender.setToPlay(sceneFader.fadeFrame());
                    this.artnetSender.sendFrame();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.currentPlayingScene = scene;
                System.out.println("playing scene from button");
            }

        }

    }

    @Override
    public Frame getLiveData() {
        this.artnetListener.captureData();
        return this.artnetListener.getCurrentFrame();
    }


}
