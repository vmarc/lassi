package lassi.server.scene

import lassi.domain.Frame
import lassi.domain.Scene
import lassi.server.artnet.ArtnetListener
import lassi.server.io.IOService
import org.springframework.stereotype.Component

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Component
class SceneServiceImpl(
  ioService: IOService,
  artnetListener: ArtnetListener
) extends SceneService {

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

    val settings = ioService.readSettings
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

}
