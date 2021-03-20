package lassi.server.artnet

import ch.bildspur.artnet.ArtNetClient
import ch.bildspur.artnet.events.ArtNetServerEventAdapter
import ch.bildspur.artnet.packets.ArtDmxPacket
import ch.bildspur.artnet.packets.ArtNetPacket
import lighting.server.frame.Frame
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct


@Component
class ArtnetListener() {

  private val artNetClient: ArtNetClient = new ArtNetClient()

  @PostConstruct
  def postConstruct(): Unit = {
//    artNetClient =
//    artNetClient.getArtNetServer.addListener(new ArtNetServerEventAdapter() {
//    override def artNetPacketReceived(packet: ArtNetPacket): Unit = {
//      val dmxPacket = packet.asInstanceOf[ArtDmxPacket]
//      val frame = new Frame(byteArrayToIntArray(dmxPacket.getDmxData), 0, dmxPacket.getUniverseID)
//      currentFrames.put(frame.getUniverse, frame)
//  }
//    }
    artNetClient.start()
  }

  def dmxValues(subset: Int, universe: Int): Array[Byte] = {
    artNetClient.readDmxData(subset, universe)
  }

}
