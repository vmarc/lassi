package lighting.server.settings;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lighting.server.IO.IOService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SettingsController {

	private final SettingsService settingsService;
	private final IOService iOService;

	public SettingsController(final SettingsService settingsService, final IOService iOService) {
		this.settingsService = settingsService;
		this.iOService = iOService;
	}

	@GetMapping(value = "/api/getsettings")
	public Settings getSettings() {
		try {
			iOService.writeToLog(0, "Retrieved settings from disk");
			return settingsService.getSettingsFromDisk();
		} catch (IOException e) {
			iOService.writeToLog(-1, "Could not retrieve settings from disk");
			e.printStackTrace();
		}
		return null;
	}

	@PutMapping(value = "/api/savesettings/")
	public void saveSettings(@RequestBody Settings settings) {
		try {
			settingsService.saveSettingsToDisk(settings);
			iOService.writeToLog(0,
					"Saved settings to disk: Fade time in seconds:  " + settings.getFadeTimeInSeconds() + " - Frames per seconds: " + settings.getFramesPerSecond());
		} catch (IOException e) {
			iOService.writeToLog(-1, "Could not save settings to disk: Fade time in seconds:  " + settings.getFadeTimeInSeconds() + " - Frames per seconds: " + settings
					.getFramesPerSecond());
			e.printStackTrace();
		}
	}

	@GetMapping(value = "api/deletelog")
	public void deleteLog() {
		try {
			iOService.deleteLog();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
