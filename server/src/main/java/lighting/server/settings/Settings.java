package lighting.server.settings;

public class Settings {

    private int framesPerSecond;
    private int fadeTimeInSeconds;
    private int buttonPages;

    public Settings() {
    }

    public Settings(int framesPerSecond, int fadeTimeInSeconds, int buttonPages) {
        this.framesPerSecond = framesPerSecond;
        this.fadeTimeInSeconds = fadeTimeInSeconds;
        this.buttonPages = buttonPages;
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

    public int getButtonPages() {
        return buttonPages;
    }

    public void setButtonPages(int buttonPages) {
        this.buttonPages = buttonPages;
    }
}
