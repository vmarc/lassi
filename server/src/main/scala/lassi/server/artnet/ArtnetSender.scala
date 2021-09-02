package lassi.server.artnet

import lassi.domain.Frame
import lassi.domain.Scene
import lassi.server.scene.SceneFader
import org.springframework.stereotype.Component

@Component
class ArtnetSender {

  def setSceneToPlay(sceneToPlay: Scene): Unit = {
  }

  def sendData(): Unit = {
  }

  def sendFrame(dmxvalues: Array[Int], universe: Int): Unit = {
  }

  def stop(): Unit = {
  }

  def fadeStop(): Unit = {
  }

  def pause(bool: Boolean): Unit = {
  }

  def createEmptyFrame(frame: Frame): Frame = {
    ???
  }

  def intArrayToByteArray(intArray: Array[Int]): Array[Byte] = {
    ???
  }

  def renewLastFrames(frame: Frame): Unit = {
  }

  def fade(): Unit = {
  }

//  def distinctByKey[T](keyExtractor: Function[_ >: T, _]): Predicate[T] = {
//    ???
//  }

  def removeSceneFader(sceneFader: SceneFader): Unit = {
  }

  def getIp: String = {
    ???
  }
}

