package lighting.server.scene;

import lighting.server.artnet.ArtnetSender;
import lighting.server.frame.Frame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;

public class SceneFader {

    private static final Logger log = LogManager.getLogger(SceneFader.class);

    private final int framesPerSecond;
    private final int fadeTimeInSeconds;
    private final Frame startFrame;
    private final Frame endFrame;
    private final long startTime;

    private int[] dmxValues;
    private double totalFrames;
    private boolean pause = false;

    public SceneFader(final int framesPerSecond, final int fadeTimeInSeconds, final Frame startFrame, final Frame endFrame, final long startTime) {
        this.framesPerSecond = framesPerSecond;
        this.fadeTimeInSeconds = fadeTimeInSeconds;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
        this.startTime = startTime;
    }

    public void setTotalFrames(double totalFrames) {
        this.totalFrames = totalFrames;
    }

    public Frame getEndFrame() {
        return endFrame;
    }

    public int[] getDmxValues() {
        return dmxValues;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void fadeFrame(ArtnetSender artnetSender) {
        try {
            Thread.sleep(startTime);
        } catch (InterruptedException e) {
            // do nothing
        }

        Instant start = Instant.now();
        long pauseTimeLong = 0;

        totalFrames = framesPerSecond * fadeTimeInSeconds;
        double timeOut = 1000.0 / framesPerSecond;

        double[] differenceList = new double[512];
        dmxValues = startFrame.getDmxValues().clone();
        int[] originalDmxValues = startFrame.getDmxValues().clone();

        int universe = endFrame.getUniverse();

        // Calculating the difference between the start value per channel in a frame and the end frame, adding it to the differenceList
        // (endFrame minus startFrame) dividing by the totalFrames
        for (int i = 0; i < 512; i++) {
            differenceList[i] = ((endFrame.getDmxValues()[i] - startFrame.getDmxValues()[i]) / totalFrames);
        }

        // Fading Method
        for (int i = 0; i < totalFrames; i++) {
            for (int j = 0; j < 512; j++) {
                dmxValues[j] = (int) (originalDmxValues[j] + Math.round((i + 1) * differenceList[j]));
            }

            Instant pauseTime = null;
            log.info("Pause: " + pause);
            while (pause) {
                if (pauseTime == null) {
                    pauseTime = Instant.now();
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // do nothing
                }
            }

            Instant now = Instant.now();
            long timeElapsed = Duration.between(start, now).toMillis();

            if (pauseTime != null) {
                pauseTimeLong += Duration.between(pauseTime, Instant.now()).toMillis();
            }
            pauseTime = null;

            artnetSender.sendFrame(dmxValues, universe);

            // Sleep expected sleep time - timeOut

            long sleep = (long) ((timeOut * (i + 1)) - (timeElapsed - pauseTimeLong));

            if (sleep > 0) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
        }

        artnetSender.removeSceneFader(this);
        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();
        log.info("Time elapsed: " + timeElapsed + " milliseconds");
    }
}
