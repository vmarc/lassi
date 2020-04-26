package lighting.server;

import lighting.server.frame.Frame;
import java.time.Duration;
import java.time.Instant;

public class SceneFader {

    private int framesPerSecond;
    private int fadeTimeInSeconds;
    private Frame startFrame;
    private Frame endFrame;

    public SceneFader() {
    }

    public SceneFader(int framesPerSecond, int fadeTimeInSeconds, Frame startFrame, Frame endFrame) {
        this.framesPerSecond = framesPerSecond;
        this.fadeTimeInSeconds = fadeTimeInSeconds;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
    }

    public void fadeFrame() throws InterruptedException {
        Instant start = Instant.now();

        double totalFrames = framesPerSecond*fadeTimeInSeconds;
        double percentageOnTimeOut = 1-(framesPerSecond/3000.0);
        long timeOut = (long) (((1.0/framesPerSecond)*1000)*percentageOnTimeOut);
        double[] differenceList = new double[512];
        int[] dmxValues = startFrame.getDmxValues().clone();
        int[] originalDmxValues = startFrame.getDmxValues().clone();

        //Calculating the difference between the start value per channel in a frame and the end frame, adding it to the differenceList
        //(endFrame minus startFrame) dividing by the totalFrames
        for (int i = 0; i < 512; i++) {
            differenceList[i] = ((endFrame.getDmxValues()[i] - startFrame.getDmxValues()[i])/totalFrames);
        }

        //For each frame add the rounded difference on the original value
        //original startFrame values + rounded value of (the frame count times the difference)
        for (int i = 0; i < totalFrames; i++) {
            //Logging
            System.out.println();
            System.out.print(i + 1 + "....  ");
            
            for (int j = 0; j < 512; j++) {
                dmxValues[j] = (int) (originalDmxValues[j] + Math.round(i * differenceList[j]));
                //Logging
                System.out.print(dmxValues[j] + " / ");
            }
            //Sleep expected sleep time - timeOut
            Thread.sleep(timeOut);
        }
        Instant finish = Instant.now();

        //Logging
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println();
        System.out.println("Time elapsed: " + timeElapsed +" milliseconds");
    }
}
