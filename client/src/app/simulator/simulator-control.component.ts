import {Component, Input} from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {SimulatorService} from './simulator.service';

@Component({
  selector: 'app-simulator-control',
  template: `
    <div class="control">
      <div>
        <div class="led {{colorClass | async}}">
        </div>
      </div>
      <button mat-stroked-button (click)="buttonClicked()">{{title}}</button>
    </div>
  `,
  styles: [`

    .control {
      margin: .2em;
      display: inline-flex;
      flex-direction: column;
      align-items: center;
    }

    .led {
      color: white;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      border-radius: 50%;
      height: 15px;
      width: 15px;
    }

    .control button {
      width: 3em;
      height: 3em;
      font-size: 1em;
    }

    .red {
      background-color: rgb(239, 83, 80);
    }

    .green {
      background-color: rgb(102, 187, 106);
    }

    .blue {
      background-color: rgb(33, 150, 243);
    }

    .orange {
      background-color: rgb(255, 163, 0);
    }

    .gray {
      background-color: rgb(224, 224, 224);
    }

  `]
})
export class SimulatorControlComponent {

  @Input() controlId: string;
  @Input() title: string;
  @Input() color: string;

  readonly colorClass: Observable<string>;

  constructor(private simulatorService: SimulatorService) {
    this.colorClass = simulatorService.status$.pipe(
      map(simulatorStatus => {
        const on = simulatorStatus[this.controlId];
        if (on === true) {
          return this.color;
        }
        return 'gray';
      })
    );
  }

  buttonClicked(): void {
    console.log('Clicked ' + this.controlId);
  }

}
