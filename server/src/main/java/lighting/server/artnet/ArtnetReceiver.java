package lighting.server.artnet;

import ch.bildspur.artnet.ArtNetClient;
import ch.bildspur.artnet.events.ArtNetServerEventAdapter;
import ch.bildspur.artnet.packets.ArtDmxPacket;
import ch.bildspur.artnet.packets.ArtNetPacket;
import lighting.server.scene.Scene;

public class ArtnetReceiver {

    private final ArtNetClient artNetClient = new ArtNetClient();
    private Scene scene = new Scene();

    public ArtnetReceiver() {
        listenData();
    }

    public Scene getScene() {
        return scene;
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

    public int[] byteArrayToIntArray(byte[] byteArray){
        int[] intArray = new int[512];
        int x = 0;
        for (byte b: byteArray
        ) {
            int i = (b & 0xFF);
            intArray[x] = i;
            x++;
        }
        return intArray;
    }
}