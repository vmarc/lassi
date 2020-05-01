package lighting.server.IO;

import lighting.server.sceneX.SceneX;
import lighting.server.settings.Settings;

import java.io.IOException;
import java.util.List;

public interface IIOService {
    void updateSceneFromDisk(SceneX sceneX) throws IOException;
    void saveScenesToJSON(SceneX sceneX) throws IOException;
    List<SceneX> getAllScenesFromDisk() throws IOException;
    void deleteSceneFromDisk(String scene_id) throws IOException;
    SceneX getSceneFromDisk(String scene_id) throws IOException;
    List<Integer> getButtonsWithScene() throws IOException;
    List<Integer> getButtonsWithoutScene() throws IOException;
    void saveSettingsToDisk(Settings settings) throws IOException;
    Settings getSettingsFromDisk() throws IOException;
}
