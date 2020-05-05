package lighting.server.artnet;

import ch.bildspur.artnet.ArtNetClient;
import ch.bildspur.artnet.events.ArtNetServerEventAdapter;
import ch.bildspur.artnet.packets.ArtDmxPacket;
import ch.bildspur.artnet.packets.ArtNetPacket;
import lighting.server.IO.IIOService;
import lighting.server.frame.Frame;
import lighting.server.scene.Scene;
import lighting.server.sceneX.ISceneXService;
import lighting.server.sceneX.SceneX;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ArtnetListener {

    private final ArtNetClient artNetClient = new ArtNetClient();
    private final IIOService iioService;
    private Scene scene = new Scene();
    private SceneX sceneX = new SceneX();
    private boolean framesAdded = false;
    private Frame currentFrame;

    public ArtnetListener(IIOService iioService) {
        this.iioService = iioService;
    }

    public Scene getScene() {
        return scene;
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

    public void recordData(int button_id) {
        sceneX.setButtonId(button_id);
        sceneX.setCreatedOn(LocalDateTime.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        sceneX.setName("Recording of " + sceneX.getCreatedOn().format(formatter));

        artNetClient.getArtNetServer().addListener(

                new ArtNetServerEventAdapter() {
                    @Override
                    public void artNetPacketReceived(ArtNetPacket packet) {

                        ArtDmxPacket dmxPacket = (ArtDmxPacket) packet;
                        Frame frame = new Frame(byteArrayToIntArray(dmxPacket.getDmxData()), 100);
                        sceneX.getFrames().add(frame);
                        framesAdded = true;
                        try {
                            iioService.saveScenesToJSON(sceneX);
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
                        artNetClient.stop();
                    }


                });

        artNetClient.start();
    }

    public int[] byteArrayToIntArray(byte[]src) {
        int dstLength = src.length >>> 2;
        int[]dst = new int[dstLength];

        for (int i=0; i<dstLength; i++) {
            int j = i << 2;
            int x = 0;
            x += (src[j++] & 0xff) << 0;
            x += (src[j++] & 0xff) << 8;
            x += (src[j++] & 0xff) << 16;
            x += (src[j++] & 0xff) << 24;
            dst[i] = x;
        }
        return dst;
    }
}
