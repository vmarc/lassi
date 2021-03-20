package lighting.server.artnet;

import ch.bildspur.artnet.ArtNetClient;
import ch.bildspur.artnet.events.ArtNetServerEventAdapter;
import ch.bildspur.artnet.packets.ArtDmxPacket;
import ch.bildspur.artnet.packets.ArtNetPacket;
import lighting.server.IO.IOService;
import lighting.server.frame.Frame;
import lighting.server.scene.Scene;
import lighting.server.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Component
public class OldArtnetListener {

    private static final Logger log = LogManager.getLogger(OldArtnetListener.class);

    private final IOService iioService;
    private ArtNetClient artNetClient = new ArtNetClient();
    private Scene scene = new Scene();
    private boolean framesAdded = false;
    private int numberOfFrames = 1;
    private Settings settings;
    private Instant time;
    private Instant timePrev;
    private long timeElapsed;
    private HashMap<Integer, Frame> currentFrames = new HashMap<>();

    public OldArtnetListener(final IOService iioService) {
        this.iioService = iioService;
    }

    public void setCurrentFrames(HashMap<Integer, Frame> currentFrames) {
        this.currentFrames = currentFrames;
    }

    public HashMap<Integer, Frame> getCurrentFrames() {
        return currentFrames;
    }

    public Scene getScene() {
        return scene;
    }

    public ArtNetClient getArtNetClient() {
        return artNetClient;
    }

    public boolean isFramesAdded() {
        return framesAdded;
    }

    public void setFramesAdded(boolean framesAdded) {
        this.framesAdded = framesAdded;
    }

    public void setNumberOfFrames(int numberOfFrames) {
        this.numberOfFrames = numberOfFrames;
    }

    public void recordData(int button_id) throws IOException {
        artNetClient = new ArtNetClient();

        settings = iioService.getSettingsFromDisk();
        scene = new Scene();
        scene.setFadeTime(settings.getFadeTimeInSeconds());
        scene.setButtonId(button_id);
        scene.setCreatedOn(LocalDateTime.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        scene.setName("Recording of " + scene.getCreatedOn().format(formatter));

        artNetClient.getArtNetServer().addListener(new ArtNetServerEventAdapter() {
            @Override
            public void artNetPacketReceived(ArtNetPacket packet) {

                ArtDmxPacket dmxPacket = (ArtDmxPacket) packet;

                time = Instant.now();
                if (timePrev != null) {
                    timeElapsed = Duration.between(timePrev, time).toMillis();
                } else {
                    timeElapsed = 0;
                }
                timePrev = time;

                Frame frame = new Frame(byteArrayToIntArray(dmxPacket.getDmxData()), timeElapsed, dmxPacket.getUniverseID());
                scene.getFrames().add(frame);
                framesAdded = true;
                if (scene.getFrames().size() >= numberOfFrames) {
                    try {
                        iioService.saveSceneToDisk(scene);
                    } catch (IOException e) {
                        log.error("Could not save scene to disk", e);
                    }
                    artNetClient.stop();
                }
            }
        });
        artNetClient.start();
    }

    public void captureData() {
        artNetClient.getArtNetServer().addListener(new ArtNetServerEventAdapter() {
            @Override
            public void artNetPacketReceived(ArtNetPacket packet) {
                ArtDmxPacket dmxPacket = (ArtDmxPacket) packet;
                Frame frame = new Frame(byteArrayToIntArray(dmxPacket.getDmxData()), 0, dmxPacket.getUniverseID());
                currentFrames.put(frame.getUniverse(), frame);
            }
        });

        artNetClient.start();
    }

    public boolean stopRecording() {
        numberOfFrames = scene.getFrames().size();
        try {
            if (numberOfFrames > 0) {
                iioService.saveSceneToDisk(scene);
                getArtNetClient().stop();
                return true;
            } else {
                getArtNetClient().stop();
                return false;
            }
        } catch (IOException e) {
            log.error("Error while stop recording", e);
            return false;
        }
    }

    public int[] byteArrayToIntArray(byte[] src) {
        int size = src.length;
        int[] intArray = new int[512];

        for (int i = 0; i < 512; i++) {
            if (i < size) {
                int x = src[i] & 0xFF;
                intArray[i] = x;
            } else {
                intArray[i] = 0;
            }
        }
        return intArray;
    }
}
