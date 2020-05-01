package lighting.server;

import ch.bildspur.artnet.ArtNetClient;
import lighting.server.SceneFader;
import lighting.server.artnet.ArtnetListener;
import lighting.server.frame.Frame;
import lighting.server.sceneX.SceneX;
import lighting.server.sceneX.SceneXXServiceImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int[] dmxValues = IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray();
        Frame frame1 = new Frame(dmxValues, 10);
        int[] dmxValues2 = IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray();
        Frame frame2 = new Frame(dmxValues2, 20);

        //SceneFader sf = new SceneFader(10,5,frame1,frame2);
        //sf.fadeFrame();


        ArtNetClient client = new ArtNetClient();
        client.start();
        Random random = new Random();
        byte[] dmx = new byte[512];

        for (int i = 0; i < 10; i++) {
            random.nextBytes(dmx);
            client.broadcastDmx(0,0, dmx);
            Thread.sleep(2000);
            System.out.println("send" + i);
        }







        /*
        List<Frame> frames = new ArrayList<>();
        frames.add(frame1);
        frames.add(frame2);
        LocalDateTime dateTime = LocalDateTime.now();
        SceneX sceneX = new SceneX("Podium", 200L, 1, dateTime, frames);

        SceneXXServiceImpl sceneXXService = new SceneXXServiceImpl();
        try {
            sceneXXService.saveScenesToJSON(sceneX);
        } catch (IOException e) {
            e.printStackTrace();
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