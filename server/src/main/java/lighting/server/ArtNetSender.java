package lighting.server;

import ch.bildspur.artnet.ArtNetClient;

public class ArtNetSender {

    ArtNetClient artnet = new ArtNetClient();

    public void sendData(){
        artnet.start();
        byte[] data = artnet.readDmxData(0, 0);
        System.out.println("First Byte: " + data[0] + 0xFF);
        artnet.stop();
    }

}
