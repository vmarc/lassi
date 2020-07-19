package lighting.server.settings;

import java.io.IOException;

import org.springframework.stereotype.Component;

import lighting.server.IO.IOService;

@Component
public class SettingsServiceImpl implements SettingsService {

	private final IOService iOService;

	public SettingsServiceImpl(final IOService iOService) {
		this.iOService = iOService;
	}

	public void saveSettingsToDisk(Settings settings) throws IOException {
		this.iOService.saveSettingsToDisk(settings);
	}

	public Settings getSettingsFromDisk() throws IOException {
		return this.iOService.getSettingsFromDisk();
	}
}
