package lighting.server;

import ch.bildspur.artnet.ArtNetBuffer;
import ch.bildspur.artnet.ArtNetClient;
import ch.bildspur.artnet.events.ArtNetServerEventAdapter;
import ch.bildspur.artnet.packets.ArtNetPacket;

import java.util.ArrayList;
import java.util.List;

public class ArtNetReceiver {

    ArtNetClient artNetClient;
    List<ArtNetPacket> receivedPackets;

    public ArtNetReceiver(ArtNetClient artNetClient) {
        this.artNetClient = artNetClient;
    }

    public ArtNetReceiver() {
        this.artNetClient = new ArtNetClient(new ArtNetBuffer(), 8080, 4200);
        this.receivedPackets = new ArrayList<>();
    }

    public void listenForPackets() {
        artNetClient.getArtNetServer().addListener(
                new ArtNetServerEventAdapter() {
                    @Override
                    public void artNetPacketReceived(ArtNetPacket packet) {
                        receivedPackets.add(packet);


                    }
                }
        );
    }
}
