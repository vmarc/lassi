package lassi.server.repository

import lassi.domain.Scene

trait SceneRespository {

  def updateSceneFromDisk(scene: Scene): Unit

  def saveSceneToDisk(scene: Scene): Unit

  def getAllScenesFromDisk: Seq[Scene]

  def deleteSceneFromDisk(sceneId: String): Unit

  def getSceneFromDisk(sceneId: String): Scene

  def getButtons: Seq[Boolean]

}
