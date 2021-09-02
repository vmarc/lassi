package lassi.server.repository

import lassi.domain.Scene
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component

import java.io.File
import java.nio.file.Paths

@Component
class IoServiceImpl(rootDir: String) extends IoService {

  private val log = LogManager.getLogger(classOf[IoServiceImpl])
  private val scenesDir = Paths.get(rootDir + "/scenes/")


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

}
