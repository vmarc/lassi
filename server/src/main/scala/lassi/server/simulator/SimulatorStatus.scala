package lassi.server.simulator

case class SimulatorStatus(
  buttonPlay: Boolean = false,
  buttonStop: Boolean = false,
  buttonRecord: Boolean = false,
  button1: Boolean = false,
  button2: Boolean = false,
  button3: Boolean = false,
  button4: Boolean = false,
  button5: Boolean = false,
  button6: Boolean = false,
  button7: Boolean = false,
  button8: Boolean = false,
  button9: Boolean = false
) {

  def buttonCount: Int = 12

  def put(controlId: Int, value: Boolean): SimulatorStatus = {
    controlId match {
      case 0 => copy(buttonPlay = value)
      case 1 => copy(buttonStop = value)
      case 2 => copy(buttonRecord = value)
      case 3 => copy(button1 = value)
      case 4 => copy(button2 = value)
      case 5 => copy(button3 = value)
      case 6 => copy(button4 = value)
      case 7 => copy(button5 = value)
      case 8 => copy(button6 = value)
      case 9 => copy(button7 = value)
      case 10 => copy(button8 = value)
      case 11 => copy(button9 = value)
      case _ => this
    }
  }

}
