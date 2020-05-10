package lighting.server;

import lighting.server.artnet.ArtnetSender;
import lighting.server.frame.Frame;
import lighting.server.sceneX.Scene;

import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ArtnetSender a = new ArtnetSender();
        for (int i = 0; i < 1000; i++) {
            Scene scene = new Scene();
            int[] dmxValues = IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray();
            Frame frame = new Frame(dmxValues, 10);
            scene.setUniverse(2);
            scene.getFrames().add(frame);
            a.setSceneToPlay(scene);
            a.sendData();
            Thread.sleep(1000);
            System.out.println("send" + i);
        }


        //SceneFader sf = new SceneFader(10,5,frame1,frame2);
        //sf.fadeFrame();
    }




}
