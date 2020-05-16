package lighting.server.scene;

import lighting.server.artnet.ArtnetSender;
import lighting.server.frame.Frame;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SceneFader {

    private int framesPerSecond;
    private int fadeTimeInSeconds;
    private Frame startFrame;
    private Frame endFrame;

    public SceneFader(int framesPerSecond, int fadeTimeInSeconds, Frame startFrame, Frame endFrame) {
        this.framesPerSecond = framesPerSecond;
        this.fadeTimeInSeconds = fadeTimeInSeconds;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
    }

    public List<int[]> fadeFrame() throws InterruptedException {
        Instant start = Instant.now();

        double totalFrames = framesPerSecond*fadeTimeInSeconds;
        double timeOut = 1000.0/framesPerSecond;

        double[] differenceList = new double[128];
        int[] dmxValues = startFrame.getDmxValues().clone();
        int[] originalDmxValues = startFrame.getDmxValues().clone();
        List<int[]> listToFill = new ArrayList<>();

        //Calculating the difference between the start value per channel in a frame and the end frame, adding it to the differenceList
        //(endFrame minus startFrame) dividing by the totalFrames
        for (int i = 0; i < 128; i++) {
            differenceList[i] = ((endFrame.getDmxValues()[i] - startFrame.getDmxValues()[i])/totalFrames);
        }

        //Fading Method
        for (int i = 0; i < totalFrames; i++) {
            //Logging
            System.out.println();
            System.out.print(i + 1 + "....  ");

            Instant now = Instant.now();
            long timeElapsed = Duration.between(start, now).toMillis();
            System.out.println("time elapsed: " + timeElapsed);

            for (int j = 0; j < 128; j++) {
                dmxValues[j] = (int) (originalDmxValues[j] + Math.round((i + 1) * differenceList[j]));
                //dmxValues[j] = (int) (Math.round(differenceList[j] * (timeElapsed/1000)));
                //Logging
                System.out.print(dmxValues[j] + " / ");
            }
            listToFill.add(dmxValues);

            //Sleep expected sleep time - timeOut
            long x = (long) ((timeOut*(i+1)) - timeElapsed);
            System.out.println();
            System.out.println(x);
            if (x > 0){
                Thread.sleep(x);
            }
        }

        Instant finish = Instant.now();

        //Logging
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println();
        System.out.println("Time elapsed: " + timeElapsed +" milliseconds");
        return listToFill;
    }
}
