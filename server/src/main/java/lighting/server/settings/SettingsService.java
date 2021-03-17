package lighting.server.settings;

import java.io.IOException;

// migrated
public interface SettingsService {

	void saveSettingsToDisk(Settings settings) throws IOException;

	Settings getSettingsFromDisk() throws IOException;
}
