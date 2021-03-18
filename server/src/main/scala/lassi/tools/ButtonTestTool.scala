package lassi.tools

import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent
import com.pi4j.io.gpio.event.GpioPinListenerDigital
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

    val listener = new GpioPinListenerDigital() {
      override def handleGpioPinDigitalStateChangeEvent(event: GpioPinDigitalStateChangeEvent): Unit = {
        pi.button(event.getPin.getName) match {
          case None =>
          case Some(button) =>
            println(button.name + " = " + event.getState.name())
            pi.ledWithId(button.id) match {
              case None =>
              case Some(led) =>
                if (event.getState == PinState.HIGH) {
                  led.output.high()
                }
                else {
                  led.output.low()
                }
            }
        }
      }
    }

    pi.buttons.foreach { button =>
      button.input.setShutdownOptions(true)
      button.input.addListener(listener)
    }

    while ( {
      true
    }) Thread.sleep(500)

    pi.shutdown()
    println("Done")
  }

}
