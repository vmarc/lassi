package lighting.server.artnet;

import ch.bildspur.artnet.ArtNetClient;
import ch.bildspur.artnet.events.ArtNetServerEventAdapter;
import ch.bildspur.artnet.packets.ArtDmxPacket;
import ch.bildspur.artnet.packets.ArtNetPacket;
import lighting.server.IO.IIOService;
import lighting.server.frame.Frame;
import lighting.server.sceneX.Scene;
import lighting.server.settings.Settings;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ArtnetListener {

    private final ArtNetClient artNetClient = new ArtNetClient();
    private final IIOService iioService;
    private Scene scene = new Scene();
    private boolean framesAdded = false;
    private Frame currentFrame;
    private Settings settings;

    public ArtnetListener(IIOService iioService) {
        this.iioService = iioService;

    }


    public Scene getScene() {
        return scene;
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

        scene.setFadeTime(settings.getFadeTimeInSeconds());
        scene.setButtonId(button_id);
        scene.setCreatedOn(LocalDateTime.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        scene.setName("Recording of " + scene.getCreatedOn().format(formatter));

        artNetClient.getArtNetServer().addListener(

                new ArtNetServerEventAdapter() {
                    @Override
                    public void artNetPacketReceived(ArtNetPacket packet) {

                        ArtDmxPacket dmxPacket = (ArtDmxPacket) packet;
                        scene.setUniverse(dmxPacket.getUniverseID());
                        Frame frame = new Frame(byteArrayToIntArray(dmxPacket.getDmxData()), 100);
                        scene.getFrames().add(frame);
                        framesAdded = true;
                        try {
                            iioService.saveSceneToDisk(scene);
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
