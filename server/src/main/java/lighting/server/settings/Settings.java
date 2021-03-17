package lighting.server.settings;

// migrated
public class Settings {

    private int framesPerSecond;
    private int fadeTimeInSeconds;
    private int buttonPageCount;

    public Settings() {
    }

    public Settings(final int framesPerSecond, final int fadeTimeInSeconds, final int buttonPageCount) {
        this.framesPerSecond = framesPerSecond;
        this.fadeTimeInSeconds = fadeTimeInSeconds;
        this.buttonPageCount = buttonPageCount;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public void setFramesPerSecond(int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
    }

    public int getFadeTimeInSeconds() {
        return fadeTimeInSeconds;
    }

    public void setFadeTimeInSeconds(int fadeTimeInSeconds) {
        this.fadeTimeInSeconds = fadeTimeInSeconds;
    }

    public int getButtonPageCount() {
        return buttonPageCount;
    }

    public void setButtonPageCount(int buttonPageCount) {
        this.buttonPageCount = buttonPageCount;
    }
}
