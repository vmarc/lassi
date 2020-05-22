package lighting.server.settings;

import lighting.server.IO.IIOService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SettingsController {

    private final ISettingsService settingsService;
    private final IIOService iOService;

    public SettingsController(ISettingsService settingsService, IIOService iOService) {
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
            iOService.writeToLog(0, "Saved settings to disk: Fade time in seconds:  " + settings.getFadeTimeInSeconds() + " - Frames per seconds: " + settings.getFramesPerSecond());
        } catch (IOException e) {
            iOService.writeToLog(-1, "Could not save settings to disk: Fade time in seconds:  " + settings.getFadeTimeInSeconds() + " - Frames per seconds: " + settings.getFramesPerSecond());
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

    @GetMapping(value = "/api/gethostip")
    public String getHostIpAddress() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
