package lighting.server.artnet;

import ch.bildspur.artnet.ArtNetClient;
import lighting.server.frame.Frame;
import lighting.server.sceneX.SceneX;

import java.util.List;

public class ArtnetSender {

    private final ArtNetClient artNetClient = new ArtNetClient();
    private SceneX sceneToPlay;
    private List<int[]> toPlay;

    public ArtnetSender() {
    }

    public void setSceneToPlay(SceneX sceneToPlay) {
        this.sceneToPlay = sceneToPlay;
    }


    public SceneX getSceneToPlay() {
        return sceneToPlay;
    }

    public void setToPlay(List<int[]> toPlay) {
        this.toPlay = toPlay;
    }

    public List<int[]> getToPlay() {
        return toPlay;
    }

    public void sendData() {

        for (Frame frame : sceneToPlay.getFrames()) {
            byte[] dmxData = intArrayToByteArray(frame.getDmxValues());
            if (!artNetClient.isRunning()) {
                artNetClient.start();
            }

            artNetClient.broadcastDmx(0, 0, dmxData);

        }

        //artNetClient.stop();


    }

    public void sendFrame() {
        if (!artNetClient.isRunning()) {
            artNetClient.start();
        }
        for (int[] frame : toPlay) {
            byte[] dmxData = intArrayToByteArray(frame);
            artNetClient.broadcastDmx(0, 0, dmxData);

        }
        System.out.println("Frame verstuurd");
        artNetClient.stop();
    }

    public byte[] intArrayToByteArray(int[] intArray) {
        int arrayLength = intArray.length;
        byte[] byteArray = new byte[arrayLength << 2];

        for (int i = 0; i < arrayLength; i++) {
            int x = intArray[i];
            int j = i << 2;
            byteArray[j++] = (byte) ((x >>> 0) & 0xff);
            byteArray[j++] = (byte) ((x >>> 8) & 0xff);
            byteArray[j++] = (byte) ((x >>> 16) & 0xff);
            byteArray[j++] = (byte) ((x >>> 24) & 0xff);
        }
        return byteArray;
    }

}


