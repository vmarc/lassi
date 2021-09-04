package lassi.server.scene

import lassi.domain.Scene
import lassi.server.repository.SceneRespository
import lassi.server.repository.SettingsRepository
import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import java.io.IOException

@RestController
class SceneController(
  sceneService: SceneService,
  sceneRespository: SceneRespository,
  settingsRepository: SettingsRepository
) {

  private val log = LogManager.getLogger(classOf[SceneController])

  @GetMapping(value = Array("/api/scenes/record/{buttonId}"))
  def recordSceneSingleFrame(@PathVariable buttonId: Int): Boolean = {
    log.info(s"Record scene with a single frame to button $buttonId")
    sceneService.recordScene(buttonId)
  }

  @GetMapping(value = Array("/api/recordSceneMultipleFrames/{buttonId}"))
  def recordSceneMultipleFrames(@PathVariable buttonId: Int): Boolean = {
    log.info(s"Record scene with multiple frames to button $buttonId")
    sceneService.recordSceneMultipleFrames(buttonId)
  }

  @GetMapping(value = Array("/api/stopRecording"))
  def stopRecording: Boolean = {
    log.info("Stop recording")
    sceneService.stopRecording
  }

  @PutMapping(value = Array("/api/savescene/"))
  def saveScene(@RequestBody scene: Scene): Unit = {
    try {
      sceneRespository.updateSceneFromDisk(scene)
      log.info(s"Save scene ${scene.id}")
    } catch {
      case e: IOException =>
        log.error(s"Could not save scene ${scene.id}", e)
    }
  }

  @GetMapping(value = Array("/api/playscene/{buttonId}"))
  def playSceneFromButton(@PathVariable buttonId: Int): Boolean = try {
    log.info(s"Play scene from button $buttonId")
    sceneService.playSceneFromButton(buttonId)
  } catch {
    case e: IOException =>
      log.error(s"Could not play scene from button $buttonId", e)
      false
  }

  @GetMapping(value = Array("/api/playscenefromid/{sceneId}"))
  def playSceneFromId(@PathVariable sceneId: String): Boolean = try {
    log.info(s"Playing scene $sceneId")
    sceneService.playSceneFromId(sceneId)
  } catch {
    case e: IOException =>
      log.error("Could not play scene $sceneId", e)
      false
  }

  @GetMapping(value = Array("/api/stop"))
  def stop(): Unit = {
    sceneService.stop()
    log.info("Stop playing scene")
  }

  @GetMapping(value = Array("/api/pause/{bool}"))
  def pause(@PathVariable bool: Boolean): Unit = {
    sceneService.pause(bool)
    log.info("Pause playing scene")
  }

  @GetMapping(value = Array("/api/sceneslist"))
  def getScenes: Seq[Scene] = {
    try {
      log.info("Retrieved all scenes from disk")
      sceneRespository.getAllScenesFromDisk
    } catch {
      case e: IOException =>
        log.error("Could not retrieve all scenes from disk", e)
        Seq.empty
    }
  }

  @GetMapping(value = Array("/api/deletescene/{sceneId}"))
  def deleteScene(@PathVariable sceneId: String): Unit = {
    try {
      sceneRespository.deleteSceneFromDisk(sceneId)
      log.info(s"Deleted scene $sceneId")
    } catch {
      case e: IOException =>
        log.error(s"Could not delete scene $sceneId", e)
    }
  }

  @GetMapping(value = Array("/api/getscene/{sceneId}"))
  def getSceneById(@PathVariable sceneId: String): Scene = {
    try {
      log.info(s"Retrieved scene from disk with ID: " + sceneId)
      sceneRespository.getSceneFromDisk(sceneId)
    } catch {
      case e: IOException =>
        log.error("Could not retrieved scene from disk with ID: " + sceneId, e)
        null
    }
  }

  @GetMapping(value = Array("api/getbuttons"))
  def getButtons: Seq[Boolean] = {
    try {
      log.info("Retrieved buttons status")
      sceneRespository.getButtons
    } catch {
      case e: IOException =>
        log.error("Could not retrieve buttons status", e)
        Seq.empty
    }
  }

  @GetMapping(value = Array("api/getPages"))
  def getPages: Int = {
    try {
      log.info("Retrieved page size")
      settingsRepository.readSettings.buttonPageCount
    } catch {
      case e: IOException =>
        log.error("Could not retrieve page size", e)
        0
    }
  }
}
