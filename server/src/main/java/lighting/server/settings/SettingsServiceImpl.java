package lighting.server.settings;

import lighting.server.IO.IIOService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SettingsServiceImpl implements ISettingsService {

    private final IIOService iOService;

    public SettingsServiceImpl(IIOService iOService) {
        this.iOService = iOService;
    }

    public void saveSettingsToDisk(Settings settings) throws IOException {
        this.iOService.saveSettingsToDisk(settings);
    }

    public Settings getSettingsFromDisk() throws IOException {
        return this.iOService.getSettingsFromDisk();
    }
}
