package lassi.tools

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioPinDigitalInput
import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.pi4j.io.gpio.PinPullResistance
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin

class PiConfig(gpio: GpioController) {

  val ledPlay: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(/* 11 */ RaspiPin.GPIO_14, "ledPlay", PinState.LOW)
  val ledStop: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(/* 25 */ RaspiPin.GPIO_06, "ledStop", PinState.LOW)
  val ledRecord: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(/* 9 */ RaspiPin.GPIO_13, "ledRecord", PinState.LOW)
  val led1: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(/* 10 */ RaspiPin.GPIO_12, "led1", PinState.LOW)
  val led2: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(/* 24 */ RaspiPin.GPIO_05, "led2", PinState.LOW)
  val led3: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(/* 23 */ RaspiPin.GPIO_04, "led3", PinState.LOW)
  val led4: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(/* 22 */ RaspiPin.GPIO_03, "led4", PinState.LOW)
  val led5: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(/* 27 */ RaspiPin.GPIO_02, "led5", PinState.LOW)
  val led6: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(/* 18 */ RaspiPin.GPIO_01, "led6", PinState.LOW)
  val led7: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(/* 17 */ RaspiPin.GPIO_00, "led7", PinState.LOW)
  val led8: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(/* 15 */ RaspiPin.GPIO_16, "led8", PinState.LOW)
  val led9: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(/* 14 */ RaspiPin.GPIO_15, "led9", PinState.LOW)
  // val ledMain = gpio.provisionDigitalOutputPin(/* +4 */ RaspiPin.GPIO_07, "ledMain", PinState.LOW)
  val fan: GpioPinDigitalOutput = gpio.provisionDigitalOutputPin(/* 2 */ RaspiPin.GPIO_08, "fan", PinState.LOW)

  val leds: Seq[GpioPinDigitalOutput] = Seq(
    ledPlay,
    ledStop,
    ledRecord,
    led1,
    led2,
    led3,
    led4,
    led5,
    led6,
    led7,
    led8,
    led9,
    fan
  )

  val buttonPlay: GpioPinDigitalInput = gpio.provisionDigitalInputPin(/* 21 */ RaspiPin.GPIO_29, "buttonPlay", PinPullResistance.PULL_DOWN)
  val buttonStop: GpioPinDigitalInput = gpio.provisionDigitalInputPin(/* 20 */ RaspiPin.GPIO_28, "buttonStop", PinPullResistance.PULL_DOWN)
  val buttonRecord: GpioPinDigitalInput = gpio.provisionDigitalInputPin(/* 26 */ RaspiPin.GPIO_25, "buttonRecord", PinPullResistance.PULL_DOWN)
  val button1: GpioPinDigitalInput = gpio.provisionDigitalInputPin(/* 16 */ RaspiPin.GPIO_27, "button1", PinPullResistance.PULL_DOWN)
  val button2: GpioPinDigitalInput = gpio.provisionDigitalInputPin(/* 19 */ RaspiPin.GPIO_24, "button2", PinPullResistance.PULL_DOWN)
  val button3: GpioPinDigitalInput = gpio.provisionDigitalInputPin(/* 13 */ RaspiPin.GPIO_23, "button3", PinPullResistance.PULL_DOWN)
  val button4: GpioPinDigitalInput = gpio.provisionDigitalInputPin(/* 12 */ RaspiPin.GPIO_26, "button4", PinPullResistance.PULL_DOWN)
  val button5: GpioPinDigitalInput = gpio.provisionDigitalInputPin(/* 6 */ RaspiPin.GPIO_22, "button5", PinPullResistance.PULL_DOWN)
  val button6: GpioPinDigitalInput = gpio.provisionDigitalInputPin(/* 5 */ RaspiPin.GPIO_21, "button6", PinPullResistance.PULL_DOWN)
  val button7: GpioPinDigitalInput = gpio.provisionDigitalInputPin(/* 8 */ RaspiPin.GPIO_10, "button7", PinPullResistance.PULL_DOWN)
  val button8: GpioPinDigitalInput = gpio.provisionDigitalInputPin(/* 0 */ RaspiPin.GPIO_30, "button8")
  val button9: GpioPinDigitalInput = gpio.provisionDigitalInputPin(/* 7 */ RaspiPin.GPIO_11, "button9", PinPullResistance.PULL_DOWN)

  val buttons: Seq[GpioPinDigitalInput] = Seq(
    buttonPlay,
    buttonStop,
    buttonRecord,
    button1,
    button2,
    button3,
    button4,
    button5,
    button6,
    button7,
    button8,
    button9
  )
}
