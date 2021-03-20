package lassi.pi

import com.pi4j.io.gpio.PinEdge
import com.pi4j.io.gpio.PinState

class PiButtonEvent(
  val button: PiButton,
  val state: PinState,
  val edge: PinEdge
)