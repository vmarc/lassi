package lassi.server.simulator

case class SimulatorStatus(
  play: Boolean = false,
  stop: Boolean = false,
  record: Boolean = false,
  control1: Boolean = false,
  control2: Boolean = false,
  control3: Boolean = false,
  control4: Boolean = false,
  control5: Boolean = false,
  control6: Boolean = false,
  control7: Boolean = false,
  control8: Boolean = false,
  control9: Boolean = false
) {

  def controlCount: Int = 12

  def put(controlId: Int, value: Boolean): SimulatorStatus = {
    controlId match {
      case 0 => copy(play = value)
      case 1 => copy(stop = value)
      case 2 => copy(record = value)
      case 3 => copy(control1 = value)
      case 4 => copy(control2 = value)
      case 5 => copy(control3 = value)
      case 6 => copy(control4 = value)
      case 7 => copy(control5 = value)
      case 8 => copy(control6 = value)
      case 9 => copy(control7 = value)
      case 10 => copy(control8 = value)
      case 11 => copy(control9 = value)
      case _ => this
    }
  }

}
