package lighting.server.sceneX;

import lighting.server.IO.SceneSerialization;
import lighting.server.artnet.ArtnetListener;
import lighting.server.scene.Reply;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class SceneXXServiceImpl implements ISceneXService {

    ArtnetListener artnetListener;

    @Override
    public Reply recordScene() {
        artnetListener = new ArtnetListener();
        artnetListener.recordData();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SceneSerialization sceneSerialization = new SceneSerialization();
        try {
            sceneSerialization.saveScenesToJSON(artnetListener.getSceneX());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("test made");
        return null;
    }
}
