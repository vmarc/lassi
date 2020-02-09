import {Component, Input} from '@angular/core';
import {SimulatorService} from './simulator.service';

@Component({
  selector: 'app-simulator-control',
  template: `
    <div class="control">
      <app-simulator-led [sceneId]="sceneId"></app-simulator-led>
      <button mat-stroked-button (click)="buttonClicked()">{{sceneId}}</button>
    </div>
  `,
  styles: [`
    .control {
      margin: .2em;
      display: inline-flex;
      flex-direction: column;
      align-items: center;
    }

    .control app-simulator-led {
      display: block;
    }

    .control button {
      width: 2em;
      height: 2em;
      font-size: 2em;
    }
  `]
})
export class SimulatorControlComponent {

  @Input() sceneId: number;
  @Input() record: boolean;

  constructor(private simulatorService: SimulatorService) {
  }

  buttonClicked(): void {
    if (this.record) {
      this.simulatorService.recordScene(this.sceneId).subscribe();
    } else {
      this.simulatorService.gotoScene(this.sceneId).subscribe();
    }
  }

}
