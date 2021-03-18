package lassi.pi

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.pi4j.io.gpio.Pin
import com.pi4j.io.gpio.PinState

object PiLed {
  def apply(gpio: GpioController, id: String, pin: Pin, name: String): PiLed = {
    val output = gpio.provisionDigitalOutputPin(pin, name, PinState.LOW)
    output.setProperty("id", id)
    new PiLed(output)
  }
}

class PiLed(val output: GpioPinDigitalOutput) {

  def id: String = output.getProperty("id")

  def name: String = output.getName
}
