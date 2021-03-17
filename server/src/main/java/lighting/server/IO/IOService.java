package lighting.server.IO;

import java.io.IOException;
import java.util.List;

import lighting.server.scene.Scene;
import lighting.server.settings.Settings;

public interface IOService {

	void updateSceneFromDisk(Scene scene) throws IOException;

	void saveSceneToDisk(Scene scene) throws IOException;

	String downloadScene(String scene_id) throws IOException;

	List<Scene> getAllScenesFromDisk() throws IOException;

	void deleteSceneFromDisk(String scene_id) throws IOException;

	Scene getSceneFromDisk(String scene_id) throws IOException;

	List<Boolean> getButtons() throws IOException;

	// migrated
	void saveSettingsToDisk(Settings settings) throws IOException;

	// migrated
	Settings getSettingsFromDisk() throws IOException;
}
