package lighting.server.IO;

import lighting.server.scene.Scene;
import lighting.server.settings.Settings;

import java.io.IOException;
import java.util.List;

public interface IIOService {
    void updateSceneFromDisk(Scene scene) throws IOException;
    void saveSceneToDisk(Scene scene) throws IOException;
    String downloadScene(String scene_id) throws IOException;
    List<Scene> getAllScenesFromDisk() throws IOException;
    void deleteSceneFromDisk(String scene_id) throws IOException;
    Scene getSceneFromDisk(String scene_id) throws IOException;
    List<Boolean> getButtons() throws IOException;
    void saveSettingsToDisk(Settings settings) throws IOException;
    Settings getSettingsFromDisk() throws IOException;
}
