package lassi.server.monitor

import lassi.domain.Frame
import lassi.server.artnet.ArtnetListener
import org.apache.logging.log4j.LogManager
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import java.time.LocalDateTime

@Component
class MonitorService(
  messagingTemplate: SimpMessagingTemplate,
  artnetListener: ArtnetListener
) {

  private val log = LogManager.getLogger(classOf[MonitorService])

  @Scheduled(fixedDelay = 200)
  def simulateOutputUpdate(): Unit = {

    val dmxValues = artnetListener.dmxValues(0, 1).map(_.toInt)

    val frame = Frame(
      dmxValues,
      startTime = 0,
      universe = 1,
      createdOn = LocalDateTime.now
    )

    val frames = Map(1 -> frame)

    // log.info("send " + frames)
    this.messagingTemplate.convertAndSend("/topic/output", frames)
  }
}
