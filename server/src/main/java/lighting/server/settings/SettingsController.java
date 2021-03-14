package lighting.server.settings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SettingsController {

    private static final Logger log = LogManager.getLogger(SettingsController.class);

    private final SettingsService settingsService;

    public SettingsController(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping(value = "/api/settings/get")
    public Settings getSettings() {
        try {
            log.info("Retrieved settings from disk");
            return settingsService.getSettingsFromDisk();
        } catch (IOException e) {
            log.error("Could not retrieve settings from disk", e);
        }
        return null;
    }

    @PutMapping(value = "/api/settings/save")
    public void saveSettings(@RequestBody Settings settings) {
        try {
            settingsService.saveSettingsToDisk(settings);
            String message = "Saved settings to disk: fade time in seconds: " + settings.getFadeTimeInSeconds();
            message += ", frames per seconds: " + settings.getFramesPerSecond();
            message += ", button page count: " + settings.getButtonPageCount();
            log.info(message);
        } catch (IOException e) {
            String message = "Could not save settings to disk: fade time in seconds: " + settings.getFadeTimeInSeconds();
            message += ", frames per seconds: " + settings.getFramesPerSecond();
            message += ", button page count: " + settings.getButtonPageCount();
            log.error(message, e);
        }
    }
}
