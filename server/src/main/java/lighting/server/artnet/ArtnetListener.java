package lighting.server.artnet;

import ch.bildspur.artnet.ArtNetClient;
import ch.bildspur.artnet.events.ArtNetServerEventAdapter;
import ch.bildspur.artnet.packets.ArtDmxPacket;
import ch.bildspur.artnet.packets.ArtNetPacket;
import lighting.server.IO.IIOService;
import lighting.server.frame.Frame;
import lighting.server.sceneX.SceneX;
import lighting.server.settings.Settings;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ArtnetListener {

    private final ArtNetClient artNetClient = new ArtNetClient();
    private final IIOService iioService;
    private SceneX sceneX = new SceneX();
    private boolean framesAdded = false;
    private Frame currentFrame;
    private Settings settings;

    public ArtnetListener(IIOService iioService) {
        this.iioService = iioService;

    }


    public SceneX getSceneX() {
        return sceneX;
    }

    public Frame getCurrentFrame() {
        return currentFrame;
    }

    public ArtNetClient getArtNetClient() {
        return artNetClient;
    }

    public boolean isFramesAdded() {
        return framesAdded;
    }

    public void recordData(int button_id) throws IOException {

        this.settings = this.iioService.getSettingsFromDisk();

        sceneX.setFadeTime(settings.getFadeTimeInSeconds());
        sceneX.setButtonId(button_id);
        sceneX.setCreatedOn(LocalDateTime.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        sceneX.setName("Recording of " + sceneX.getCreatedOn().format(formatter));

        artNetClient.getArtNetServer().addListener(

                new ArtNetServerEventAdapter() {
                    @Override
                    public void artNetPacketReceived(ArtNetPacket packet) {

                        ArtDmxPacket dmxPacket = (ArtDmxPacket) packet;
                        sceneX.setUniverse(dmxPacket.getUniverseID());
                        Frame frame = new Frame(byteArrayToIntArray(dmxPacket.getDmxData()), 100);
                        sceneX.getFrames().add(frame);
                        framesAdded = true;
                        try {
                            iioService.saveSceneToDisk(sceneX);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        artNetClient.stop();
                    }


                });
        artNetClient.start();
    }

    public void captureData() {
        artNetClient.getArtNetServer().addListener(
                new ArtNetServerEventAdapter() {
                    @Override public void artNetPacketReceived(ArtNetPacket packet) {

                        ArtDmxPacket dmxPacket = (ArtDmxPacket)packet;
                        Frame frame = new Frame(byteArrayToIntArray(dmxPacket.getDmxData()), 100);
                        currentFrame = frame;
                    }


                });

        artNetClient.start();
    }

    public int[] byteArrayToIntArray(byte[] src) {
        int[] intArray = new int[512];

        for (int i = 0; i < 512; i++) {
            int x = src[i] & 0xFF;
            intArray[i] = x;
        }
        return intArray;

    }
}
