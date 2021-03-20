package lassi.server.simulator

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SimulatorController(simulatorService: SimulatorService) {

  @GetMapping(value = Array("/scala-api/simulator/{buttonId}/{value}"))
  def simulate(@PathVariable buttonId: String, @PathVariable value: Boolean): Unit = {
    simulatorService.simulate(buttonId, value)
  }

}
