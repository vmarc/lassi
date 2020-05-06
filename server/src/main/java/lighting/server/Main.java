package lighting.server;

import lighting.server.artnet.ArtnetSender;
import lighting.server.frame.Frame;
import lighting.server.sceneX.SceneX;

import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ArtnetSender a = new ArtnetSender();
        for (int i = 0; i < 1000; i++) {
            SceneX sceneX = new SceneX();
            int[] dmxValues = IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray();
            Frame frame = new Frame(dmxValues, 10);
            sceneX.getFrames().add(frame);
            a.setSceneToPlay(sceneX);
            a.sendData();
            Thread.sleep(1000);
            System.out.println("send" + i);
        }


        //SceneFader sf = new SceneFader(10,5,frame1,frame2);
        //sf.fadeFrame();
    }




}
