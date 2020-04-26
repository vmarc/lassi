package lighting.server.settings;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SettingsController {

    private final ISettingsService settingsService;

    public SettingsController(ISettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping(value = "/api/getsettings")
    public Settings getSettings() {
        try {
            return this.settingsService.getSettingsFromDisk();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping(value = "/api/savesettings/")
    public void saveSettings(@RequestBody Settings settings) {
        try {
            this.settingsService.saveSettingsToDisk(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
