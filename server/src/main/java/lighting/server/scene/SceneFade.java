package lighting.server.scene;

import lighting.server.frame.Frame;

public class SceneFade {

    private final int fadeTimeInSeconds;
    private final Frame startFrame;
    private final Frame endFrame;
    private final long startDelay;

    public SceneFade(final int fadeTimeInSeconds, final Frame startFrame, final Frame endFrame, final long startTime) {
        this.fadeTimeInSeconds = fadeTimeInSeconds;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
        this.startDelay = startTime;
    }

    public Frame frameAt(final int milliseconds) {
        long fadeTime = 1000L * fadeTimeInSeconds;
        if (milliseconds < startDelay) {
            return startFrame;
        }
        if (milliseconds >= fadeTime) {
            return endFrame;
        }

        double progress = (double) (milliseconds - startDelay) / (fadeTime - startDelay);

        int[] startDmxValues = startFrame.getDmxValues();
        int[] endDmxValues = endFrame.getDmxValues();
        int[] dmxValues = new int[512];

        for (int i = 0; i < 512; i++) {
            int startDmxValue = startDmxValues[i];
            int endDmxValue = endDmxValues[i];
            int difference = endDmxValue - startDmxValue;
            int newValue = startDmxValue + (int) (Math.round(progress * difference));
            dmxValues[i] = newValue;
        }
        return new Frame(dmxValues, 0L /* TODO ??? */, startFrame.getUniverse());
    }
}
