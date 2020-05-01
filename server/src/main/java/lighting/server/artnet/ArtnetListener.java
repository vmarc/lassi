package lighting.server.artnet;

import ch.bildspur.artnet.ArtNetClient;
import ch.bildspur.artnet.events.ArtNetServerEventAdapter;
import ch.bildspur.artnet.packets.ArtDmxPacket;
import ch.bildspur.artnet.packets.ArtNetPacket;
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
    private final ISceneXService service;
    private Scene scene = new Scene();
    private SceneX sceneX = new SceneX();

    public ArtnetListener(ISceneXService service) {
        this.service = service;
        listenData();
    }

    public Scene getScene() {
        return scene;
    }

    public SceneX getSceneX() {
        return sceneX;
    }

    public void listenData(){
        artNetClient.getArtNetServer().addListener(
                new ArtNetServerEventAdapter() {
                    @Override public void artNetPacketReceived(ArtNetPacket packet) {
                        ArtDmxPacket dmxPacket = (ArtDmxPacket)packet;
                        scene = new Scene(1,"test",byteArrayToIntArray(dmxPacket.getDmxData()));
                    }
                });
        artNetClient.start();
    }

    public void recordData(int button_id){
       sceneX.setButtonId(button_id);
       sceneX.setCreatedOn(LocalDateTime.now());
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
       sceneX.setName("Recording of " + sceneX.getCreatedOn().format(formatter));

       artNetClient.getArtNetServer().addListener(
                new ArtNetServerEventAdapter() {
                    @Override public void artNetPacketReceived(ArtNetPacket packet) {

                            ArtDmxPacket dmxPacket = (ArtDmxPacket)packet;
                            Frame frame = new Frame(byteArrayToIntArray(dmxPacket.getDmxData()), 100);
                            sceneX.getFrames().add(frame);

                            try {
                                service.saveScenesToJSON(sceneX);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            artNetClient.stop();
                        }


                });

        artNetClient.start();

    }


    public byte[] intArrayToByteArray(int[] intArray) {
        int arrayLength = intArray.length;
        byte[] byteArray = new byte[arrayLength << 2];

        for (int i = 0; i < arrayLength; i++) {
            int x = intArray[i];
            int j = i << 2;
            byteArray[j++] = (byte) ((x >>> 0) & 0xff);
            byteArray[j++] = (byte) ((x >>> 8) & 0xff);
            byteArray[j++] = (byte) ((x >>> 16) & 0xff);
            byteArray[j++] = (byte) ((x >>> 24) & 0xff);
        }
        return byteArray;
    }


    public int[] byteArrayToIntArray(byte[] byteArray){
        int[] intArray = new int[512];
        int x = 0;
        for (byte b: byteArray
        ) {
            byte i = (byte) (b & 0xFF);
            intArray[x] = i;
            x++;
        }
        return intArray;
    }
}
