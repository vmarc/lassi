package lassi.tools

import com.pi4j.io.gpio.PinState
import lassi.pi.Pi

object ButtonTestTool {
  def main(args: Array[String]): Unit = {
    new ButtonTestTool().tryout()
  }
}

class ButtonTestTool {

  def tryout(): Unit = {

    println("ButtonTestTool")

    val pi = Pi()

    val listener = pi.createButtonListener { event =>
      println(event.button.name + " = " + event.state.name() + "" + event.edge.name())
      pi.ledWithId(event.button.id) match {
        case None =>
        case Some(led) =>
          if (event.state == PinState.HIGH) {
            led.output.high()
          }
          else {
            led.output.low()
          }
      }
    }

    pi.buttons.foreach { button =>
      button.input.addListener(listener)
    }

    while ( {
      true
    }) Thread.sleep(500)

    pi.shutdown()
    println("Done")
  }

}
