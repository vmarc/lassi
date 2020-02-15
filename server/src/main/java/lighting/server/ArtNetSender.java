package lighting.server;

import ch.bildspur.artnet.ArtNetClient;

public class ArtNetSender {

    ArtNetClient artnet = new ArtNetClient();

    public void sendData(){
        byte[] dmxData = new byte[512];
        artnet.start();


        dmxData[0] = (byte) 128;

        artnet.unicastDmx("127.0.0.1", 0, 0, dmxData);

        artnet.stop();
        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
