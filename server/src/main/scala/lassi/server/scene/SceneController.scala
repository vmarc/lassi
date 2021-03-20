package lassi.server.scene

import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SceneController(sceneService: SceneService) {

  private val log = LogManager.getLogger(classOf[SceneController])

  @GetMapping(value = Array("/scala-api/scenes/record/{buttonId}"))
  def recordSceneSingleFrame(@PathVariable buttonId: Int): Boolean = {
    log.info("Recorded a scene with a single frame to button " + buttonId)
    sceneService.recordScene(buttonId)
  }

}
