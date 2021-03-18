package lassi.tools

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent
import com.pi4j.io.gpio.event.GpioPinListenerDigital

object ButtonTestTool {
  def main(args: Array[String]): Unit = {
    new ButtonTestTool().tryout()
  }
}

class ButtonTestTool {

  def tryout(): Unit = {

    println("ButtonTestTool")

    val gpio: GpioController = GpioFactory.getInstance
    val config = new PiConfig(gpio)

    config.buttons.foreach { button =>
      button.setShutdownOptions(true)
    }

    val listener = new GpioPinListenerDigital() {
      override def handleGpioPinDigitalStateChangeEvent(event: GpioPinDigitalStateChangeEvent): Unit = { // display pin state on console
        println(event.getPin.getName + " = " + event.getState.name())
      }
    }

    config.buttons.foreach { button =>
      button.setShutdownOptions(true)
      button.addListener(listener)
    }

    while ( {
      true
    }) Thread.sleep(500)

    gpio.shutdown()
    println("Done")
  }

}
