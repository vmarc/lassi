package lighting.server.sceneX;

import lighting.server.scene.Reply;

import java.io.IOException;
import java.util.List;

public interface ISceneXService {
    Reply recordScene();
    void playScene() throws IOException;
    void saveScenesToJSON(SceneX sceneX) throws IOException;
    List<SceneX> getAllScenesFromDisk() throws IOException;
    void deleteSceneFromDisk(String scene_id) throws IOException;
    SceneX getSceneFromDisk(String scene_id) throws IOException;

}
