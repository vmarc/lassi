package lighting.server.settings;

import java.io.IOException;

import org.springframework.stereotype.Component;

import lighting.server.IO.IOService;

@Component
// migrated
public class SettingsServiceImpl implements SettingsService {

	private final IOService iOService;

	public SettingsServiceImpl(final IOService iOService) {
		this.iOService = iOService;
	}

	public void saveSettingsToDisk(Settings settings) throws IOException {
		iOService.saveSettingsToDisk(settings);
	}

	public Settings getSettingsFromDisk() throws IOException {
		return iOService.getSettingsFromDisk();
	}
}
