package lighting.server;

import ch.bildspur.artnet.ArtNetClient;
import lighting.server.artnet.ArtnetSender;
import lighting.server.frame.Frame;
import lighting.server.scene.Scene;

import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ArtNetClient artNetClient = new ArtNetClient();
        artNetClient.start();

        for (int i = 0; i < 1000; i++) {
            int[] dmxValues = IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray();
            byte[] dmxData = new byte[512];

            for (int x = 0; x < 512; x++) {
                byte b = (byte) (dmxValues[x] & 0xFF);
                dmxData[x] = b;
            }

            artNetClient.unicastDmx("192.168.0.114",0, 2, dmxData);

            Thread.sleep(1000);
            System.out.println("send" + i);
        }
        //artNetClient.stop();

    }




}
