package lighting.server.sceneX;

import java.io.IOException;
import java.util.List;

public interface ISceneXService {
    void recordScene(int button_id);
    void playSceneFromButton(int button) throws IOException;
    void updateSceneFromDisk(SceneX sceneX) throws IOException;
    void saveScenesToJSON(SceneX sceneX) throws IOException;
    List<SceneX> getAllScenesFromDisk() throws IOException;
    void deleteSceneFromDisk(String scene_id) throws IOException;
    SceneX getSceneFromDisk(String scene_id) throws IOException;
    List<Integer> getButtonsWithScene() throws IOException;
    List<Integer> getButtonsWithoutScene() throws IOException;
}
