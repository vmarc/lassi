package lassi.server.scene

import lassi.domain.Scene
import lassi.server.artnet.ArtnetListener
import lassi.server.artnet.ArtnetSender
import lassi.server.repository.SceneRespository
import lassi.server.repository.SettingsRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Component

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Component
class SceneServiceImpl(
  ioService: SceneRespository,
  artnetListener: ArtnetListener,
  artnetSender: ArtnetSender,
  settingsRepository: SettingsRepository
) extends SceneService {

  private val log: Logger = LogManager.getLogger(classOf[SceneServiceImpl])

  override def recordScene(buttonId: Int): Boolean = {

    val dmxValues = artnetListener.dmxValues(0, 1).map(_.toInt)

    val frame = lassi.domain.Frame(
      dmxValues,
      startTime = 0,
      universe = 1,
      createdOn = LocalDateTime.now
    )

    val existingSceneOption: Option[Scene] = None

    //    try {
    //      val scenesOnDisk = ioService.getAllScenesFromDisk
    //      for (s <- scenesOnDisk) {
    //        if (buttonId != 0 && s.buttonId == buttonId) {
    //          s.setButtonId(0)
    //          ioService.updateSceneFromDisk(s)
    //        }
    //      }
    //    } catch {
    //      case e: IOException =>
    //        throw new RuntimeException("Could not read scenes from disk", e)
    //    }

    val settings = settingsRepository.readSettings
    val createdOn: LocalDateTime = LocalDateTime.now
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    val id = existingSceneOption.map(_.id).getOrElse(UUID.randomUUID.toString)
    val name = existingSceneOption.map(_.name).getOrElse("Recording of " + createdOn.format(formatter))
    val duration: Long = 0
    val fadeTime: Int = existingSceneOption.map(_.fadeTime).getOrElse(settings.fadeTimeInSeconds)

    val scene = Scene(
      id,
      name,
      duration,
      fadeTime,
      buttonId,
      createdOn,
      Seq(frame)
    )

    ioService.saveSceneToDisk(scene: Scene)
    true
  }

  override def recordSceneMultipleFrames(buttonId: Int): Boolean = {
    //    try {
    //      val scenesOnDisk = ioService.getAllScenesFromDisk
    //      for (s <- scenesOnDisk) {
    //        if (buttonId != 0 && s.buttonId == buttonId) {
    //          s.setButtonId(0)
    //          ioService.updateSceneFromDisk(s)
    //        }
    //      }
    //    } catch {
    //      case e: IOException =>
    //        log.error("Could dot read all scenes from disk", e)
    //    }
    //    try {
    //      artnetListener.setFramesAdded(false)
    //      artnetListener.setNumberOfFrames(20000)
    //      artnetListener.recordData(buttonId)
    //      true
    //    } catch {
    //      case e: IOException =>
    //        log.error("Could not record a scene with multiple frames to button " + buttonId, e)
    //        false
    //    }
    false
  }

  override def stopRecording: Boolean = {
    //    artnetListener.stopRecording
    true
  }

  override def playSceneFromButton(buttonId: Int): Boolean = {
    val scenes = ioService.getAllScenesFromDisk
    for (scene <- scenes) {
      if (scene.buttonId == buttonId) return playScene(scene)
    }
    false
  }

  override def playSceneFromId(sceneId: String): Boolean = {
    val scenes = ioService.getAllScenesFromDisk
    for (scene <- scenes) {
      if (scene.id == sceneId) return playScene(scene)
    }
    false
  }

  def playScene(scene: Scene): Boolean = {
    artnetSender.setSceneToPlay(scene)
    artnetSender.sendData()
    true
  }

  override def stop(): Unit = {
    artnetSender.stop()
  }

  override def pause(bool: Boolean): Unit = {
    artnetSender.pause(bool)
  }
}
