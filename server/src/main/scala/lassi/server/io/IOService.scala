package lassi.server.io

import lassi.domain.Scene
import lassi.server.settings.Settings

trait IOService {

  def updateSceneFromDisk(scene: Scene): Unit

  def saveSceneToDisk(scene: Scene): Unit

  def downloadScene(scene_id: String): String

  def getAllScenesFromDisk: Seq[Scene]

  def deleteSceneFromDisk(sceneId: String): Unit

  def getSceneFromDisk(sceneId: String): Scene

  def getButtons: Seq[Boolean]

  def readSettings: Settings

  def writeSettings(settings: Settings): Unit
}
