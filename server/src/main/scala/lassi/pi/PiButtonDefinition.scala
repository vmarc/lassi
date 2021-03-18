package lassi.pi

import com.pi4j.io.gpio.Pin

class PiButtonDefinition(val pin: Pin, val name: String, val pullDown: Boolean = true)
