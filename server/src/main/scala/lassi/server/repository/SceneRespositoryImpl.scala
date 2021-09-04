package lassi.server.repository

import lassi.domain.Scene
import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Component

import java.io.File

@Component
class SceneRespositoryImpl(rootDir: String) extends SceneRespository {

  private val scenesDir = rootDir + "/scenes"

  new File(scenesDir).mkdirs()

  override def updateSceneFromDisk(scene: Scene): Unit = {
    resetButtonIdOfScenesOtherThan(scene)
    saveSceneToDisk(scene)
  }

  override def saveSceneToDisk(scene: Scene): Unit = {
    val filename = scenesDir + "/scene_" + scene.id + ".json"
    Json.objectMapper.writeValue(new File(filename), scene)
  }

  override def getAllScenesFromDisk: Seq[Scene] = {
    val sceneFiles = new File(scenesDir).listFiles().toSeq
    sceneFiles.map(file => Json.objectMapper.readValue(file, classOf[Scene]))
  }

  override def deleteSceneFromDisk(sceneId: String): Unit = {
    sceneFile(sceneId).delete()
  }

  override def getSceneFromDisk(sceneId: String): Scene = {
    Json.objectMapper.readValue(sceneFile(sceneId), classOf[Scene])
  }

  override def getButtons: Seq[Boolean] = {
    val scenes = getAllScenesFromDisk
    1 to 27 map { buttonId =>
      scenes.exists(_.buttonId == buttonId)
    }
  }

  private def resetButtonIdOfScenesOtherThan(scene: Scene): Unit = {
    getAllScenesFromDisk.filterNot(_.id == scene.id).filter(_.buttonId == scene.buttonId).foreach { s =>
      saveSceneToDisk(s.copy(buttonId = 0))
    }
  }

  private def sceneFile(sceneId: String): File = {
    new File(scenesDir + "/scene_" + sceneId + ".json")
  }
}
