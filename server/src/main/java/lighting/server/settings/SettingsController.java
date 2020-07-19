package lighting.server.settings;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SettingsController {

	private static final Logger log = LogManager.getLogger(SettingsController.class);

	private final SettingsService settingsService;

	public SettingsController(final SettingsService settingsService) {
		this.settingsService = settingsService;
	}

	@GetMapping(value = "/api/getsettings")
	public Settings getSettings() {
		try {
			log.info("Retrieved settings from disk");
			return settingsService.getSettingsFromDisk();
		} catch (IOException e) {
			log.error("Could not retrieve settings from disk", e);
		}
		return null;
	}

	@PutMapping(value = "/api/savesettings/")
	public void saveSettings(@RequestBody Settings settings) {
		try {
			settingsService.saveSettingsToDisk(settings);
			log.info("Saved settings to disk: Fade time in seconds:  " + settings.getFadeTimeInSeconds() + " - Frames per seconds: " + settings.getFramesPerSecond());
		} catch (IOException e) {
			log.error("Could not save settings to disk: Fade time in seconds:  " + settings.getFadeTimeInSeconds() + " - Frames per seconds: " + settings
					.getFramesPerSecond(), e);
		}
	}
}
