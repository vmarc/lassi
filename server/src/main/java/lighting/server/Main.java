package lighting.server;

import ch.bildspur.artnet.ArtNetClient;
import lighting.server.IO.IOServiceImpl;
import lighting.server.SceneFader;
import lighting.server.artnet.ArtnetListener;
import lighting.server.artnet.ArtnetSender;
import lighting.server.frame.Frame;
import lighting.server.sceneX.ISceneXService;
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

        ArtnetSender a = new ArtnetSender();
        for (int i = 0; i < 100; i++) {
            SceneX sceneX = new SceneX();
            int[] dmxValues = IntStream.generate(() -> new Random().nextInt(256)).limit(128).toArray();
            Frame frame = new Frame(dmxValues, 10);
            sceneX.getFrames().add(frame);
            a.setSceneToPlay(sceneX);
            a.sendData();
            Thread.sleep(1000);
        }


        //SceneFader sf = new SceneFader(10,5,frame1,frame2);
        //sf.fadeFrame();
    }




}
