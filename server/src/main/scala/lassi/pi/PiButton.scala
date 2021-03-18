package lassi.pi

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioPinDigitalInput
import com.pi4j.io.gpio.Pin
import com.pi4j.io.gpio.PinPullResistance

object PiButton {

  def apply(gpio: GpioController, id: String, pin: Pin, name: String): PiButton = {
    val input = gpio.provisionDigitalInputPin(pin, name, PinPullResistance.PULL_DOWN)
    input.setProperty("id", id)
    new PiButton(input)
  }

  def build(gpio: GpioController, id: String, pin: Pin, name: String): PiButton = {
    val input = gpio.provisionDigitalInputPin(pin, name)
    input.setProperty("id", id)
    new PiButton(input)
  }
}

class PiButton(val input: GpioPinDigitalInput) {

  def id: String = input.getProperty("id")

  def name: String = input.getName

}
