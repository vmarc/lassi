package lighting.server.artnet;

import ch.bildspur.artnet.ArtNetClient;
import lighting.server.frame.Frame;
import lighting.server.scene.Scene;

import java.util.List;


public class ArtnetSender {

    private ArtNetClient artNetClient = new ArtNetClient();
    private Scene sceneToPlay;

    public ArtnetSender() {
    }

    public void setSceneToPlay(Scene sceneToPlay) {
        this.sceneToPlay = sceneToPlay;
    }

    public void sendData() {
        List <Frame> frames = sceneToPlay.getFrames();
        frames.remove(0);
        for (Frame frame : frames) {
            byte[] dmxData = intArrayToByteArray(frame.getDmxValues());
            if (!artNetClient.isRunning()) {
                artNetClient.start();
            }

            artNetClient.broadcastDmx(0, sceneToPlay.getUniverse(), dmxData);
            try {
                Thread.sleep(frame.getStartTime());
                System.out.println(frame.getStartTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
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

    public void stop(){
        artNetClient.stop();
        System.out.println("Stop everything");
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


