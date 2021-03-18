package lassi.tools

import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.PinState

object LedTestTool {
  def main(args: Array[String]): Unit = {
    new LedTestTool().tryout()
  }
}

class LedTestTool() {

  def tryout(): Unit = {

    println("Led test tool")

    val gpio = GpioFactory.getInstance
    val config = new PiConfig(gpio)

    config.leds.foreach { pin =>
      pin.setShutdownOptions(true, PinState.LOW)
    }

    config.leds.foreach { pin =>
      println(pin.getName)
      pin.pulseSync(1000)
    }
    Thread.sleep(1000)

    gpio.shutdown()

    println("done")
  }

}
