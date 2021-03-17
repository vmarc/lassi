package lassi.tools

import ch.bildspur.artnet.ArtNetClient
import org.apache.logging.log4j.LogManager

import java.util.Random
import java.util.stream.IntStream

object SendRandomData {
  def main(args: Array[String]): Unit = {
    new SendRandomData().send()
  }
}

class SendRandomData() {

  private val log = LogManager.getLogger(classOf[SendRandomData])

  def send(): Unit = {
    val artNetClient = new ArtNetClient
    artNetClient.start()
    for (i <- 0 until 1000) {
      artNetClient.broadcastDmx(0, 1, randomDmxValues())
      artNetClient.broadcastDmx(0, 2, randomDmxValues())
      artNetClient.broadcastDmx(0, 3, randomDmxValues())
      Thread.sleep(1000)
      log.info("send" + i)
    }
  }

  private def randomDmxValues(): Array[Byte] = {
    val dmxValues = IntStream.generate(() => new Random().nextInt(256)).limit(512).toArray
    val dmxData = new Array[Byte](512)
    for (x <- 0 until 512) {
      val b = (dmxValues(x) & 0xFF).toByte
      dmxData(x) = b
    }
    dmxData
  }

}
