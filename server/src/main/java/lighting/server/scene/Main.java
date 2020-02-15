package lighting.server.scene;

import lighting.server.ArtNetSender;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ArtNetSender a = new ArtNetSender();
        a.sendData();
        System.out.println("test");
        /*
        SceneServiceImpl sceneService = new SceneServiceImpl();
        //sceneService.saveSceneToJSON();
        try {
            List<Scene> scenes = sceneService.getAllScenesFromDisk();
            for (Scene s : scenes) {
                System.out.println(s.getId());

            }
        } catch (IOException e) {
            e.printStackTrace();

        }

    }*/
    }
}
