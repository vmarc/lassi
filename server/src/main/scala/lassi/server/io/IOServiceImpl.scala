package lassi.server.io

import lassi.domain.Scene
import lassi.server.settings.Settings
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component

import java.io.File
import java.nio.file.Paths

@Component
class IOServiceImpl(rootDir: String) extends IOService {

  private val log = LogManager.getLogger(classOf[IOServiceImpl])
  private val scenesDir = Paths.get(rootDir + "/scenes/")
  private val settingsDir = Paths.get(rootDir + "/settings/")
  private val settingsFile = new File((settingsDir) + "/settings.json")


  override def updateSceneFromDisk(scene: Scene): Unit = ???

  override def saveSceneToDisk(scene: Scene): Unit = {
    val filename = scenesDir + "/scene_" + scene.id + ".json"
    Json.objectMapper.writeValue(new File(filename), scene)
  }

  override def downloadScene(scene_id: String): String = ???

  override def getAllScenesFromDisk: Seq[Scene] = ???

  override def deleteSceneFromDisk(sceneId: String): Unit = ???

  override def getSceneFromDisk(sceneId: String): Scene = ???

  override def getButtons: Seq[Boolean] = ???

  override def readSettings: Settings = {
    Json.objectMapper.readValue(settingsFile, classOf[Settings])
  }

  override def writeSettings(settings: Settings): Unit = {
    Json.objectMapper.writeValue(settingsFile, settings)
  }

}
