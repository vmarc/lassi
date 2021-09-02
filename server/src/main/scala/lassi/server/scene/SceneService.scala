package lassi.server.scene

trait SceneService {

  def recordScene(buttonId: Int): Boolean

  def playSceneFromButton(buttonId: Int): Boolean

  def playSceneFromId(sceneId: String): Boolean

  def recordSceneMultipleFrames(buttonId: Int): Boolean

  def stopRecording: Boolean

  def stop(): Unit

  def pause(bool: Boolean): Unit
}
