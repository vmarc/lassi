package lighting.server;

import ch.bildspur.artnet.ArtNetClient;
import lighting.server.IO.IOServiceImpl;
import lighting.server.artnet.ArtnetSender;
import lighting.server.frame.Frame;
import lighting.server.scene.Scene;

import java.util.ArrayList;
import java.util.List;
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

            //artNetClient.broadcastDmx(0, 1, dmxData);
            //artNetClient.broadcastDmx(0, 2, dmxData);
            artNetClient.unicastDmx("raspberrypi", 0,1, dmxData);



            Thread.sleep(500);
            System.out.println("send" + i);
        }
        //artNetClient.stop();

/*        Frame f = new Frame(IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray(), 1, 1);
        Frame f1 = new Frame(IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray(), 2, 1);
        Frame f2 = new Frame(IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray(), 3, 2);
        Frame f3 = new Frame(IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray(), 4, 3);
        Frame f4 = new Frame(IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray(), 5, 2);
        Frame f5 = new Frame(IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray(), 6, 2);

        List<Frame> list = new ArrayList<>();
        list.add(f);
        list.add(f1);
        list.add(f2);
        list.add(f3);
        list.add(f4);
        list.add(f5);

        ArtnetSender a = new ArtnetSender(new IOServiceImpl());

        a.fade();*/


    }




}
