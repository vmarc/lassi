package lighting.server.scene;

import ch.bildspur.artnet.ArtNetClient;
import lighting.server.IO.SceneSerialization;

import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ArtNetClient client = new ArtNetClient();
        client.start();
        Random random = new Random();
        byte[] dmx = new byte[512];


        for (int i = 0; i < 100; i++) {
            random.nextBytes(dmx);
            client.broadcastDmx(0,0, dmx);
            Thread.sleep(2000);
            System.out.println("send" + i);
        }
/*
        System.out.println("test");
        SceneSerialization serialization = new SceneSerialization();
        try {
            serialization.saveScenesToJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

    }

}
