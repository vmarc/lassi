package lighting.server.artnet;

import ch.bildspur.artnet.ArtNetClient;
import lighting.server.frame.Frame;
import lighting.server.sceneX.SceneX;

public class ArtnetSender {

    private final ArtNetClient artNetClient = new ArtNetClient();
    private SceneX sceneToPlay;

    public ArtnetSender(SceneX scene) {
        this.sceneToPlay = scene;
    }

    public void setSceneToPlay(SceneX sceneToPlay) {
        this.sceneToPlay = sceneToPlay;
    }

    public void sendData() {

        for (Frame frame : sceneToPlay.getFrames()) {
            byte[] dmxData = intArrayToByteArray(frame.getDmxValues());

            artNetClient.start();

            artNetClient.broadcastDmx(0, 0, dmxData);

        }

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
