package lighting.server.scene;

import ch.bildspur.artnet.ArtNetClient;
import lighting.server.IO.SceneSerialization;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        byte[] dmxData = new byte[512];
        ArtNetClient client = new ArtNetClient();
        client.start();
        client.broadcastDmx(0,0, dmxData);

        for (int i = 0; i < 100; i++) {
            dmxData[0] = (byte) i;
            dmxData[1] = (byte) i;
            dmxData[2] = (byte) i;
            dmxData[3] = (byte) i;
            client.broadcastDmx(0,0, dmxData);
            Thread.sleep(5000);
            System.out.println("send" + i);
        }

        System.out.println("test");
        SceneSerialization serialization = new SceneSerialization();
        try {
            serialization.saveScenesToJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
