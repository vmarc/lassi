package lighting.server.scene;

import lighting.server.IO.IIOService;
import lighting.server.artnet.ArtnetListener;
import lighting.server.artnet.ArtnetSender;
import lighting.server.frame.Frame;
import lighting.server.settings.Settings;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class SceneServiceImpl implements ISceneService {

    private ArtnetListener artnetListener;
    private ArtnetSender artnetSender;
    private Scene currentPlayingScene = new Scene();
    private Settings settings;

    private final IIOService iOService;

    public SceneServiceImpl(IIOService iOService) {
        this.iOService = iOService;
        try {
            this.settings = this.iOService.getSettingsFromDisk();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.artnetListener = new ArtnetListener(iOService);
    }

    public boolean recordScene(int button_id) {
        try {
            List<Scene> scenesOnDisk = iOService.getAllScenesFromDisk();
            for (Scene s : scenesOnDisk) {
                if (s.getButtonId() == button_id) {
                    s.setButtonId(0);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            artnetListener.setNumberOfFrames(1);
            artnetListener.recordData(button_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public boolean recordSceneMultipleFrames(int button_id) {
        try {
            List<Scene> scenesOnDisk = iOService.getAllScenesFromDisk();
            for (Scene s : scenesOnDisk) {
                //TODO gaat die niet altijd alle scenes aanpassen omdat button nul is?
                // EN doet die dan een update van de scene op de disk ook? of enkel in memory
                if (s.getButtonId() == button_id) {
                    s.setButtonId(0);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            artnetListener.setNumberOfFrames(20000);
            artnetListener.recordData(button_id);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean stopRecording(){
        return artnetListener.stopRecording();
    }

    public void playSceneFromButton(int button) throws IOException {
        artnetSender = new ArtnetSender();
        List<Scene> scenes = iOService.getAllScenesFromDisk();

        for (Scene scene : scenes) {
            if (scene.getButtonId() == button) {
                playScene(scene);
            }

        }

    }

    public void playSceneFromId(String id) throws IOException {
        artnetSender = new ArtnetSender();
        List<Scene> scenes = iOService.getAllScenesFromDisk();

        for (Scene scene : scenes) {
            if (scene.getId().equals(id)) {
                playScene(scene);
            }

        }
    }

    public void playScene(Scene scene) throws IOException {
        this.settings = this.iOService.getSettingsFromDisk();

        this.artnetSender.setSceneToPlay(scene);

        if (currentPlayingScene.getFrames().isEmpty()) {
            int[] emptyArray = IntStream.generate(() -> new Random().nextInt(1)).limit(512).toArray();
            Frame emptyFrame = new Frame(emptyArray);
            SceneFader sceneFader = new SceneFader(settings.getFramesPerSecond(), scene.getFadeTime(), emptyFrame, scene.getFrames().get(0));
            try {
                sceneFader.fadeFrame(artnetSender);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        else {
            SceneFader sceneFader = new SceneFader(settings.getFramesPerSecond(), scene.getFadeTime(), currentPlayingScene.getFrames().get(0), scene.getFrames().get(0));
            try {
                sceneFader.fadeFrame(artnetSender);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.currentPlayingScene = scene;
        artnetSender.sendData();
        System.out.println("playing scene");


    }


}
