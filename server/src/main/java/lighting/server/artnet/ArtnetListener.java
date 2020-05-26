package lighting.server.artnet;

import ch.bildspur.artnet.ArtNetClient;
import ch.bildspur.artnet.events.ArtNetServerEventAdapter;
import ch.bildspur.artnet.packets.ArtDmxPacket;
import ch.bildspur.artnet.packets.ArtNetPacket;
import lighting.server.IO.IIOService;
import lighting.server.frame.Frame;
import lighting.server.scene.Scene;
import lighting.server.settings.Settings;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Component
public class ArtnetListener {

    private ArtNetClient artNetClient = new ArtNetClient();
    private final IIOService iioService;
    private Scene scene = new Scene();
    private boolean framesAdded = false;
    private int numberOfFrames = 1;
    private Settings settings;
    private Instant time;
    private Instant timePrev;
    private long timeElapsed;
    private HashMap<Integer, Frame> currentFrames = new HashMap<>();

    public ArtnetListener(IIOService iioService) {
        this.iioService = iioService;

    }

    public void setCurrentFrames(HashMap<Integer, Frame> currentFrames) {
        this.currentFrames = currentFrames;
    }

    public HashMap<Integer, Frame> getCurrentFrames() {
        return currentFrames;
    }

    public Scene getScene() {
        return scene;
    }

    public ArtNetClient getArtNetClient() {
        return artNetClient;
    }

    public boolean isFramesAdded() {
        return framesAdded;
    }

    public void setFramesAdded(boolean framesAdded) {
        this.framesAdded = framesAdded;
    }

    public void setNumberOfFrames(int numberOfFrames) {
        this.numberOfFrames = numberOfFrames;
    }

    public void recordData(int button_id) throws IOException {
        //artNetClient = new ArtNetClient();

        this.settings = this.iioService.getSettingsFromDisk();
        scene = new Scene();
        scene.setFadeTime(settings.getFadeTimeInSeconds());
        scene.setButtonId(button_id);
        scene.setCreatedOn(LocalDateTime.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        scene.setName("Recording of " + scene.getCreatedOn().format(formatter));

        artNetClient.getArtNetServer().addListener(
                new ArtNetServerEventAdapter() {
                    @Override public void artNetPacketReceived(ArtNetPacket packet) {

                        ArtDmxPacket dmxPacket = (ArtDmxPacket) packet;

                        time = Instant.now();
                        if (timePrev != null){
                            timeElapsed = Duration.between(timePrev, time).toMillis();
                        }
                        else {timeElapsed = 0;}
                        timePrev = time;

                        Frame frame = new Frame(byteArrayToIntArray(dmxPacket.getDmxData()), timeElapsed, dmxPacket.getUniverseID());
                        scene.getFrames().add(frame);
                        framesAdded = true;
                        if (scene.getFrames().size() >= numberOfFrames){
                            try {
                                iioService.saveSceneToDisk(scene);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            artNetClient.stop();
                        }
                    }


                });
        artNetClient.start();
    }

    public void captureData() {
        artNetClient.getArtNetServer().addListener(
                new ArtNetServerEventAdapter() {
                    @Override public void artNetPacketReceived(ArtNetPacket packet) {

                        ArtDmxPacket dmxPacket = (ArtDmxPacket)packet;
                        Frame frame = new Frame(byteArrayToIntArray(dmxPacket.getDmxData()), 0, dmxPacket.getUniverseID());
                        currentFrames.put(frame.getUniverse(), frame);
                        System.out.println("frame: " + frame.getDmxValues()[0]);
                        System.out.println("getFramescurrent: " + currentFrames.get(1).getDmxValues()[0]);
                    }


                });

        artNetClient.start();
    }

    public boolean stopRecording(){
        numberOfFrames = scene.getFrames().size();
        try {
            if (numberOfFrames > 0){
                iioService.saveSceneToDisk(scene);
                this.getArtNetClient().stop();
                return true;
            }
            else {
                this.getArtNetClient().stop();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int[] byteArrayToIntArray(byte[] src) {
        int size = src.length;
        int[] intArray = new int[512];
        //System.out.println("element size => " + size);

        for (int i = 0; i < 512; i++) {
            if (i < size){
                int x = src[i] & 0xFF;
                intArray[i] = x;
/*                System.out.println("element: " + i);
                System.out.println(x);
                System.out.println(src[i]);*/
            }
            else{
                intArray[i] = 0;
            }
        }
        return intArray;
    }
}
