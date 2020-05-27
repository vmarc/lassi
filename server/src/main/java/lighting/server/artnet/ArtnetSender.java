package lighting.server.artnet;

import ch.bildspur.artnet.ArtNetClient;
import lighting.server.IO.IIOService;
import lighting.server.frame.Frame;
import lighting.server.scene.Scene;
import lighting.server.scene.SceneFader;
import lighting.server.settings.Settings;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.time.Duration;
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
    private String ipAddress = "192.168.0.255";


    public ArtnetSender(IIOService iOService) {
        this.iOService = iOService;
        this.ipAddress = getIp();
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

        List <Frame> frames = sceneToPlay.getFrames();
        frames.remove(0);
        for (Frame frame : frames) {

            if (!stop) {

                while (pause) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                renewLastFrames(frame);

                byte[] dmxData = intArrayToByteArray(frame.getDmxValues());
                if (!artNetClient.isRunning()) {
                    artNetClient.start();
                }
                System.out.println(frame.getStartTime());

                artNetClient.unicastDmx(ipAddress, 0, frame.getUniverse(), dmxData);
                System.out.println("U: " + frame.getUniverse());

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
        artNetClient.unicastDmx(ipAddress,0, universe, dmxData);
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
                sf.setPause(true);
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

        for (SceneFader sf:activeSceneFaders
        ) {
            renewLastFrames(new Frame(sf.getDmxValues(), 0, sf.getEndFrame().getUniverse()));
            sf.setTotalFrames(0);
            System.out.println("Setting 0");
        }

        lastFrames.forEach((integer, frame) ->{
            Frame emptyFrame = createEmptyFrame(frame);
            SceneFader sceneFader = new SceneFader(settings.getFramesPerSecond(), settings.getFadeTimeInSeconds(), frame, emptyFrame,0);
            activeSceneFaders.add(sceneFader);
            sceneFader.fadeFrame(this);
            renewLastFrames(emptyFrame);

        });
        iOService.writeToLog(0, "Stopped fading");

    }

    public void pause(boolean bool) {
        for (SceneFader sf:activeSceneFaders
        ) {
            sf.setPause(bool);
        }
        pause = bool;
    }

    public Frame createEmptyFrame(Frame frame){
        int[] emptyArray = IntStream.generate(() -> new Random().nextInt(1)).limit(512).toArray();
        return new Frame(emptyArray, 0, frame.getUniverse());
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
            long startTime = Duration.between(list.get(0).getCreatedOn(),f.getCreatedOn()).toMillis();
            System.out.println("Wait time for fading: " + startTime);
            if (startFrame == null){
                startFrame = createEmptyFrame(f);
            }
            if (!Arrays.equals(f.getDmxValues(), startFrame.getDmxValues())){
                SceneFader sceneFader = new SceneFader(settings.getFramesPerSecond(), sceneToPlay.getFadeTime(), startFrame, f, startTime);
                activeSceneFaders.add(sceneFader);
                sceneFader.fadeFrame(this);
            }
            renewLastFrames(f);
        }

    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public void removeSceneFader(SceneFader sceneFader){
        activeSceneFaders.remove(sceneFader);
    }

    public String getIp(){
        String ipAdress = "192.168.0.255";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements())
            {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback())
                    continue;
                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses())
                {
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if (broadcast == null)
                        continue;

                    ipAdress = broadcast.toString().substring(1);
                    System.out.println(ipAdress);
                }
            }

            System.out.println(ipAdress);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipAdress;
    }


}


