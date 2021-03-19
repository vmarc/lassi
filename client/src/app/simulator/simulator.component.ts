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
        <app-simulator-control controlId="play" title="play" color="green"></app-simulator-control>
        <app-simulator-control controlId="stop" title="stop" color="red"></app-simulator-control>
        <app-simulator-control controlId="record" title="rec" color="orange"></app-simulator-control>
      </div>
      <div class="button-row">
        <app-simulator-control controlId="control1" title="1" color="blue"></app-simulator-control>
        <app-simulator-control controlId="control2" title="2" color="blue"></app-simulator-control>
        <app-simulator-control controlId="control3" title="3" color="blue"></app-simulator-control>
      </div>
      <div class="button-row">
        <app-simulator-control controlId="control4" title="4" color="blue"></app-simulator-control>
        <app-simulator-control controlId="control5" title="5" color="blue"></app-simulator-control>
        <app-simulator-control controlId="control6" title="6" color="blue"></app-simulator-control>
      </div>
      <div class="button-row">
        <app-simulator-control controlId="control7" title="7" color="blue"></app-simulator-control>
        <app-simulator-control controlId="control8" title="8" color="blue"></app-simulator-control>
        <app-simulator-control controlId="control9" title="9" color="blue"></app-simulator-control>
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
