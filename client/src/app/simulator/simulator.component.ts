import {OnDestroy} from '@angular/core';
import {OnInit} from '@angular/core';
import {Component} from '@angular/core';
import {SimulatorService} from './simulator.service';

@Component({
  selector: 'app-simulator',
  template: `
    <h1>Simulator</h1>
    <div class="buttons">
      <div class="button-row">
        <app-simulator-button buttonId="buttonPlay" title="play" color="green"></app-simulator-button>
        <app-simulator-button buttonId="buttonStop" title="stop" color="red"></app-simulator-button>
        <app-simulator-button buttonId="buttonRecord" title="rec" color="orange"></app-simulator-button>
      </div>
      <div class="button-row">
        <app-simulator-button buttonId="button1" title="1" color="blue"></app-simulator-button>
        <app-simulator-button buttonId="button2" title="2" color="blue"></app-simulator-button>
        <app-simulator-button buttonId="button3" title="3" color="blue"></app-simulator-button>
      </div>
      <div class="button-row">
        <app-simulator-button buttonId="button4" title="4" color="blue"></app-simulator-button>
        <app-simulator-button buttonId="button5" title="5" color="blue"></app-simulator-button>
        <app-simulator-button buttonId="button6" title="6" color="blue"></app-simulator-button>
      </div>
      <div class="button-row">
        <app-simulator-button buttonId="button7" title="7" color="blue"></app-simulator-button>
        <app-simulator-button buttonId="button8" title="8" color="blue"></app-simulator-button>
        <app-simulator-button buttonId="button9" title="9" color="blue"></app-simulator-button>
      </div>
    </div>
  `,
  styles: [`

    .buttons {
      display: flex;
      flex-direction: column;
    }

    .button-row {
      padding-bottom: 1em;
    }

    .record button {
      margin-top: 2em;
      font-size: 1.2em;
    }

  `],
  providers: [SimulatorService]
})
export class SimulatorComponent implements OnInit, OnDestroy {

  constructor(private simulatorService: SimulatorService) {
  }

  ngOnInit(): void {
    this.simulatorService.init();
  }

  ngOnDestroy(): void {
    this.simulatorService.destroy();
  }
}
