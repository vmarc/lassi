package lighting.server.artnet;

import ch.bildspur.artnet.ArtNetClient;
import lighting.server.IO.IIOService;
import lighting.server.frame.Frame;
import lighting.server.scene.Scene;
import lighting.server.scene.SceneFader;
import lighting.server.settings.Settings;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ArtnetSender {

    private final IIOService iOService;
    private ArtNetClient artNetClient = new ArtNetClient();
    private Settings settings;
    private Scene sceneToPlay;
    private boolean stop = false;
    private boolean pause = false;
    private HashMap<Integer, Frame> lastFrames = new HashMap<>();
    List<SceneFader> activeSceneFaders = new ArrayList<>();


    public ArtnetSender(IIOService iOService) {
        this.iOService = iOService;
    }

    public void setSceneToPlay(Scene sceneToPlay) {
        this.sceneToPlay = sceneToPlay;
    }

    public void sendData() {
        stop = false;
        pause = false;

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

                renewLastFrames(frame);

                byte[] dmxData = intArrayToByteArray(frame.getDmxValues());
                if (!artNetClient.isRunning()) {
                    artNetClient.start();
                }
                System.out.println(frame.getStartTime());

                artNetClient.broadcastDmx(0, frame.getUniverse(), dmxData);

                try {
                    Thread.sleep(frame.getStartTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //artNetClient.stop();
    }

    public void sendFrame(int[] dmxvalues, int universe) {
        if (!artNetClient.isRunning()) {
            artNetClient.start();
        }
        byte[] dmxData = intArrayToByteArray(dmxvalues);
        artNetClient.broadcastDmx(0, universe, dmxData);
        iOService.writeToLog(0, "Frame sent");
        //artNetClient.stop();
    }

/*    public void fade(){
        if (currentPlayingScene == null) {
            Frame emptyFrame = createEmptyFrame();
            sceneFader = new SceneFader(settings.getFramesPerSecond(), sceneToPlay.getFadeTime(), emptyFrame, sceneToPlay.getFrames().get(0));
            iOService.writeToLog(0, "Fading from empty frame to frame");
        }
        else {
            sceneFader = new SceneFader(settings.getFramesPerSecond(), sceneToPlay.getFadeTime(), currentPlayingScene.getFrames().get(0), sceneToPlay.getFrames().get(0));
            iOService.writeToLog(0, "Fading from frame to frame");

        }
        sceneFader.fadeFrame(this);
    }*/

    public void stop() {
        if (!stop){
            for (SceneFader sf:activeSceneFaders
                 ) {
                sf.setTotalFrames(0);
            }
            stop = true;
            fadeStop();
        }
    }

    public void fadeStop(){
        try {
            this.settings = this.iOService.getSettingsFromDisk();
        } catch (IOException e) {
            e.printStackTrace();
        }

        lastFrames.forEach((integer, frame) ->{
            SceneFader sceneFader = new SceneFader(settings.getFramesPerSecond(), settings.getFadeTimeInSeconds(), frame, createEmptyFrame());
            sceneFader.fadeFrame(this);
            activeSceneFaders.add(sceneFader);
        });

/*        Frame emptyFrame = createEmptyFrame();
        Frame stoppedFrame = new Frame(sceneFader.getDmxValues());
        Scene scene = new Scene();
        scene.getFrames().add(emptyFrame);
        sceneFader = new SceneFader(settings.getFramesPerSecond(), settings.getFadeTimeInSeconds(), stoppedFrame , emptyFrame);
        sceneFader.fadeFrame(this);*/
        iOService.writeToLog(0, "Stopped fading");

    }

    public void pause(boolean bool) {
        for (SceneFader sf:activeSceneFaders
        ) {
            sf.setPause(bool);
        }
        pause = bool;
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

    public void renewLastFrames(Frame frame){
        lastFrames.put(frame.getUniverse(), frame);
    }

    public void fade(){
        try {
            this.settings = this.iOService.getSettingsFromDisk();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Frame> list = sceneToPlay.getFrames().stream().filter(distinctByKey(Frame::getUniverse)).collect(Collectors.toList());

        for (Frame f: list
             ) {
            Frame startFrame = lastFrames.get(f.getUniverse());
            if (startFrame == null){
                startFrame = createEmptyFrame();
            }
            if (!Arrays.equals(f.getDmxValues(), startFrame.getDmxValues())){
                SceneFader sceneFader = new SceneFader(settings.getFramesPerSecond(), settings.getFadeTimeInSeconds(), startFrame, f);
                sceneFader.fadeFrame(this);
                activeSceneFaders.add(sceneFader);
            }
        }

    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public void removeSceneFader(SceneFader sceneFader){
        activeSceneFaders.remove(sceneFader);
    }


}


