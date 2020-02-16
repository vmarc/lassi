package lighting.server.scene;

import ch.bildspur.artnet.ArtNetClient;
import ch.bildspur.artnet.events.ArtNetServerEventAdapter;
import ch.bildspur.artnet.packets.ArtNetPacket;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

       ArtNetClient client = new ArtNetClient();
        List<ArtNetPacket> packets = new ArrayList<>();

        byte[] dmxData = new byte[512];
        client.start();

/*
        client.getArtNetServer().addListener(
                new ArtNetServerEventAdapter() {
                    @Override
                    public void artNetPacketReceived(ArtNetPacket packet) {
                        packets.add(packet);

                        for (byte b:packet.getData()
                             ) {
                            System.out.println(b);

                        }
                    }
                }
        );*/

        for (int i = 0; i < 100; i++) {
            dmxData[0] = (byte) i;
            dmxData[1] = (byte) i;
            dmxData[2] = (byte) i;
            dmxData[3] = (byte) i;
            client.broadcastDmx( 0, 0, dmxData);
            System.out.println("send" + i);
            Thread.sleep(5000);
        }


        System.out.println("test");

        /*
        SceneServiceImpl sceneService = new SceneServiceImpl();
        //sceneService.saveSceneToJSON();
        try {
            List<Scene> scenes = sceneService.getAllScenesFromDisk();
            for (Scene s : scenes) {
                System.out.println(s.getId());

            }
        } catch (IOException e) {
            e.printStackTrace();

        }

    }*/
    }
}
