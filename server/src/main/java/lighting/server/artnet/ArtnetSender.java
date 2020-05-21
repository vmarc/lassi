package lighting.server.artnet;

import ch.bildspur.artnet.ArtNetClient;
import lighting.server.IO.IIOService;
import lighting.server.frame.Frame;
import lighting.server.scene.Scene;
import lighting.server.scene.SceneFader;
import lighting.server.settings.Settings;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;


public class ArtnetSender {

    private final IIOService iOService;
    private ArtNetClient artNetClient = new ArtNetClient();
    private Settings settings;
    private Scene currentPlayingScene;
    private Scene sceneToPlay;
    private SceneFader sceneFader;
    private boolean stop = false;
    private boolean pause = false;

    public ArtnetSender(IIOService iOService) {
        this.iOService = iOService;
    }

    public void setSceneToPlay(Scene sceneToPlay) {
        this.sceneToPlay = sceneToPlay;
    }

    public void setCurrentPlayingScene(Scene currentPlayingScene) {
        this.currentPlayingScene = currentPlayingScene;
    }

    public void sendData() {
        stop = false;

        try {
            this.settings = this.iOService.getSettingsFromDisk();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fade();

        if (!stop) {
            while (pause) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e)  {
                    e.printStackTrace();
                }
            }

            List <Frame> frames = sceneToPlay.getFrames();
            frames.remove(0);
            for (Frame frame : frames) {
                byte[] dmxData = intArrayToByteArray(frame.getDmxValues());
                if (!artNetClient.isRunning()) {
                    artNetClient.start();
                }
                System.out.println(frame.getStartTime());

                artNetClient.broadcastDmx(0, sceneToPlay.getUniverse(), dmxData);

                try {
                    Thread.sleep(frame.getStartTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //artNetClient.stop();
    }

    public void sendFrame(int[] dmxvalues) {
        if (!artNetClient.isRunning()) {
            artNetClient.start();
        }
        byte[] dmxData = intArrayToByteArray(dmxvalues);
        artNetClient.broadcastDmx(0, sceneToPlay.getUniverse(), dmxData);

        System.out.println("Frame verstuurd");
        //artNetClient.stop();
    }

    public void fade(){
        if (currentPlayingScene == null) {
            Frame emptyFrame = createEmptyFrame();
            sceneFader = new SceneFader(settings.getFramesPerSecond(), sceneToPlay.getFadeTime(), emptyFrame, sceneToPlay.getFrames().get(0));
        }
        else {
            sceneFader = new SceneFader(settings.getFramesPerSecond(), sceneToPlay.getFadeTime(), currentPlayingScene.getFrames().get(0), sceneToPlay.getFrames().get(0));
        }
        sceneFader.fadeFrame(this);
    }

    public void stop() {
        if (sceneFader != null){
            sceneFader.setTotalFrames(0);
        }
        stop = true;
        fadeStop();
    }

    public void fadeStop(){
        try {
            this.settings = this.iOService.getSettingsFromDisk();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Frame emptyFrame = createEmptyFrame();
        Frame stoppedFrame = new Frame(sceneFader.getDmxValues());
        sceneFader = new SceneFader(settings.getFramesPerSecond(), settings.getFadeTimeInSeconds(), stoppedFrame , emptyFrame);
        sceneFader.fadeFrame(this);
    }

    public void pause(boolean bool) {
        if (sceneFader != null) {
            sceneFader.setPause(bool);
        }
        pause = bool;
        System.out.println("pause : " + bool);
    }

    public Frame createEmptyFrame(){
        int[] emptyArray = IntStream.generate(() -> new Random().nextInt(1)).limit(512).toArray();
        return new Frame(emptyArray);
    }


    public byte[] intArrayToByteArray(int[] intArray) {
        byte[] byteArray = new byte[512];

        for (int i = 0; i < 512; i++) {
            byte b = (byte) (intArray[i] & 0xFF);
            byteArray[i] = b;
        }
        return byteArray;
    }


}


