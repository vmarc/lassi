package lighting.server.settings;

public class Settings {

    private int framesPerSecond;
    private int fadeTimeInSeconds;

    public Settings() {
    }

    public Settings(int framesPerSecond, int fadeTimeInSeconds) {
        this.framesPerSecond = framesPerSecond;
        this.fadeTimeInSeconds = fadeTimeInSeconds;
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
}
