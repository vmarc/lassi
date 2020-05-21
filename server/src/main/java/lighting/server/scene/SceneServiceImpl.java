package lighting.server.scene;

import lighting.server.IO.IIOService;
import lighting.server.artnet.ArtnetListener;
import lighting.server.artnet.ArtnetSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SceneServiceImpl implements ISceneService {

    private ArtnetListener artnetListener;
    private ArtnetSender artnetSender;



    private final IIOService iOService;

    public SceneServiceImpl(IIOService iOService) {
        this.iOService = iOService;
        this.artnetListener = new ArtnetListener(iOService);
    }

    public boolean recordScene(int button_id) {
        try {
            List<Scene> scenesOnDisk = iOService.getAllScenesFromDisk();
            for (Scene s : scenesOnDisk) {
                if (button_id != 0 && s.getButtonId() == button_id) {
                    s.setButtonId(0);
                    iOService.updateSceneFromDisk(s);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            artnetListener.setFramesAdded(false);
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
                if (button_id != 0 && s.getButtonId() == button_id) {
                    s.setButtonId(0);
                    iOService.updateSceneFromDisk(s);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            artnetListener.setFramesAdded(false);
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

    public boolean playSceneFromButton(int button) throws IOException {
        artnetSender = new ArtnetSender(iOService);
        List<Scene> scenes = iOService.getAllScenesFromDisk();

        for (Scene scene : scenes) {
            if (scene.getButtonId() == button) {
                return playScene(scene);
            }
        }
        return false;
    }

    public boolean playSceneFromId(String id) throws IOException
    {
        artnetSender = new ArtnetSender(iOService);
        List<Scene> scenes = iOService.getAllScenesFromDisk();

        for (Scene scene : scenes) {
            if (scene.getId().equals(id)) {
                return playScene(scene);
            }
        }
        return false;
    }

    public boolean playScene(Scene scene) {

        artnetSender.setSceneToPlay(scene);
        artnetSender.sendData();
        artnetSender.setCurrentPlayingScene(scene);

        System.out.println("playing scene");
        return true;
    }

    public void stop(){
        artnetSender.stop();
    }

    public void pause(boolean bool) {
        artnetSender.pause(bool);
    }


}
