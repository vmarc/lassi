package lighting.server.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SettingsServiceImpl implements ISettingsService {

    private Path parentDir = Paths.get(System.getProperty("user.dir")).getParent();
    private Path settingsDir = Paths.get(parentDir + "/settings/");

    public SettingsServiceImpl() {
        createDirectory(settingsDir);
        Settings settings = new Settings(50, 5 );
        try {
            saveSettingsToDisk(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDirectory(Path dir) {
        File file = new File(String.valueOf(dir));
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

    public void saveSettingsToDisk(Settings settings) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File((settingsDir) + "/settings.json"), settings);
    }

    public Settings getSettingsFromDisk() throws IOException {
        Stream<Path> walk = Files.walk(Paths.get(String.valueOf(settingsDir)));
        String result = walk.filter(Files::isRegularFile)
                .map(x -> x.toString()).filter(f -> f.endsWith(".json")).collect(Collectors.joining());

        ObjectMapper objectMapper = new ObjectMapper();
        Settings settings = objectMapper.readValue(new File(result), Settings.class);
        return settings;
    }
}
