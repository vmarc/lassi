package lassi.server.simulator

import org.apache.logging.log4j.LogManager
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SimulatorService(messagingTemplate: SimpMessagingTemplate) {

  private val log = LogManager.getLogger(classOf[SimulatorService])

  private var status = SimulatorStatus()
  private var index = 0

  @Scheduled(fixedDelay = 500)
  def simulate(): Unit = {

    val index1 = index
    val index2 = if (index < status.buttonCount - 1) index + 1 else 0
    index = index2

    status = status.put(index1, value = false)
    status = status.put(index2, value = true)

    this.messagingTemplate.convertAndSend("/topic/simulator/status", status)
  }

  def simulate(buttonId: String, value: Boolean): Unit = {
    log.info(s"$buttonId -> $value")
  }

}
