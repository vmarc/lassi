package lassi.pi

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.PinPullResistance
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent
import com.pi4j.io.gpio.event.GpioPinListener
import com.pi4j.io.gpio.event.GpioPinListenerDigital

object Pi {

  def apply(): Pi = {
    val gpio = GpioFactory.getInstance
    val leds = buildLeds(gpio)
    val buttons = buildButtons(gpio)
    new Pi(gpio, leds, buttons)
  }

  private val ledDefinitions = Seq(
    new PiLedDefinition(RaspiPin.GPIO_14, "ledPlay"), // BCM 11
    new PiLedDefinition(RaspiPin.GPIO_06, "ledStop"), // BCM 25
    new PiLedDefinition(RaspiPin.GPIO_13, "ledRecord"), // BCM 9
    new PiLedDefinition(RaspiPin.GPIO_12, "led1"), // BCM 10
    new PiLedDefinition(RaspiPin.GPIO_05, "led2"), // BCM 24
    new PiLedDefinition(RaspiPin.GPIO_04, "led3"), // BCM 23
    new PiLedDefinition(RaspiPin.GPIO_03, "led4"), // BCM 22
    new PiLedDefinition(RaspiPin.GPIO_02, "led5"), // BCM 27
    new PiLedDefinition(RaspiPin.GPIO_01, "led6"), // BCM 18
    new PiLedDefinition(RaspiPin.GPIO_00, "led7"), // BCM 17
    new PiLedDefinition(RaspiPin.GPIO_16, "led8"), // BCM 15
    new PiLedDefinition(RaspiPin.GPIO_15, "led9"), // BCM 14
    new PiLedDefinition(RaspiPin.GPIO_08, "fan") // BCM 2
  )

  private val buttonDefinitions = Seq(
    new PiButtonDefinition(RaspiPin.GPIO_29, "buttonPlay"), // BCM 21
    new PiButtonDefinition(RaspiPin.GPIO_28, "buttonStop"), // BCM 20
    new PiButtonDefinition(RaspiPin.GPIO_25, "buttonRecord"), // BCM 26
    new PiButtonDefinition(RaspiPin.GPIO_27, "button1"), // BCM 16
    new PiButtonDefinition(RaspiPin.GPIO_24, "button2"), // BCM 19
    new PiButtonDefinition(RaspiPin.GPIO_23, "button3"), // BCM 13
    new PiButtonDefinition(RaspiPin.GPIO_26, "button4"), // BCM 12
    new PiButtonDefinition(RaspiPin.GPIO_22, "button5"), // BCM 6
    new PiButtonDefinition(RaspiPin.GPIO_21, "button6"), // BCM 5
    new PiButtonDefinition(RaspiPin.GPIO_10, "button7"), // BCM 8
    new PiButtonDefinition(RaspiPin.GPIO_30, "button8", pullDown = false), // BCM 0
    new PiButtonDefinition(RaspiPin.GPIO_11, "button9") // BCM 7
  )

  private def buildLeds(gpio: GpioController): Seq[PiLed] = {
    ledDefinitions.zipWithIndex.map { case (ledDefinition, index) =>
      val led = gpio.provisionDigitalOutputPin(ledDefinition.pin, ledDefinition.name, PinState.LOW)
      led.setProperty("id", "" + index)
      led.setShutdownOptions(true, PinState.LOW)
      new PiLed(led)
    }
  }

  private def buildButtons(gpio: GpioController): Seq[PiButton] = {
    buttonDefinitions.zipWithIndex.map { case (buttonDefinition, index) =>
      val button = if (buttonDefinition.pullDown) {
        gpio.provisionDigitalInputPin(buttonDefinition.pin, buttonDefinition.name, PinPullResistance.PULL_DOWN)
      }
      else {
        gpio.provisionDigitalInputPin(buttonDefinition.pin, buttonDefinition.name)
      }
      button.setProperty("id", "" + index)
      button.setShutdownOptions(true)
      new PiButton(button)
    }
  }
}

class Pi(val gpio: GpioController, val leds: Seq[PiLed], val buttons: Seq[PiButton]) {

  def button(name: String): Option[PiButton] = {
    buttons.find(_.name == name)
  }

  def buttonWithId(id: String): Option[PiButton] = {
    buttons.find(_.id == id)
  }

  def led(name: String): Option[PiLed] = {
    leds.find(_.name == name)
  }

  def ledWithId(id: String): Option[PiLed] = {
    leds.find(_.id == id)
  }

  def createButtonListener(listener: (PiButtonEvent) => Unit): GpioPinListener = {
    new GpioPinListenerDigital() {
      override def handleGpioPinDigitalStateChangeEvent(event: GpioPinDigitalStateChangeEvent): Unit = {
        button(event.getPin.getName) match {
          case None =>
          case Some(button) =>
            val buttonEvent = new PiButtonEvent(button, event.getState, event.getEdge)
            listener(buttonEvent)
        }
      }
    }
  }

  def shutdown(): Unit = gpio.shutdown()

}
