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
    private boolean recordingDone = false;

    private final IIOService iOService;

    public SceneXXServiceImpl(IIOService iOService) {
        this.iOService = iOService;
        try {
            this.settings = this.iOService.getSettingsFromDisk();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recordScene(int button_id) {
        artnetListener = new ArtnetListener(this.iOService);
        artnetListener.recordData(button_id);
        this.recordingDone = true;
        System.out.println("test made");
    }

    public void playSceneFromButton(int button) throws IOException {
        artnetSender = new ArtnetSender();
        List<SceneX> scenes = iOService.getAllScenesFromDisk();

        for (SceneX scene : scenes) {
            if (scene.getButtonId() == button) {
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

    public Frame getLiveData() {
        artnetListener.captureData();
        for (int i = 0; i < artnetListener.getSceneX().getFrames().size(); i++) {
            return artnetListener.getSceneX().getFrames().get(i);
        }
       return null;
    }

    public boolean isRecordingDone() {
        return recordingDone;
    }
}
