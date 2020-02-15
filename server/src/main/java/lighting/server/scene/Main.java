package lighting.server.scene;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
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

    }
}
