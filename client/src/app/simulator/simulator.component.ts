import {Component} from '@angular/core';

@Component({
  selector: 'app-simulator',
  templateUrl: './simulator.component.html',
  styleUrls: [ './simulator.component.css' ]
})
export class SimulatorComponent {

  record = false;

  toggleRecord() {
    this.record = !this.record;
  }

}
