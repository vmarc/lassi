import {Component} from '@angular/core';

@Component({
  selector: 'app-simulator',
  template: `
    <div class="title">Simulator</div>
    <div class="buttons">
      <div>
        <div class="button-row">
          <app-simulator-control [record]="record" [sceneId]="1"></app-simulator-control>
          <app-simulator-control [record]="record" [sceneId]="2"></app-simulator-control>
          <app-simulator-control [record]="record" [sceneId]="3"></app-simulator-control>
        </div>
        <div class="button-row">
          <app-simulator-control [record]="record" [sceneId]="4"></app-simulator-control>
          <app-simulator-control [record]="record" [sceneId]="5"></app-simulator-control>
          <app-simulator-control [record]="record" [sceneId]="6"></app-simulator-control>
        </div>
        <div class="button-row">
          <app-simulator-control [record]="record" [sceneId]="7"></app-simulator-control>
          <app-simulator-control [record]="record" [sceneId]="8"></app-simulator-control>
          <app-simulator-control [record]="record" [sceneId]="9"></app-simulator-control>
        </div>
      </div>
      <div class="record">
        <mat-slide-toggle [checked]="record" (change)="toggleRecord()">Record mode</mat-slide-toggle>
      </div>
    </div>
  `,
  styles: [`
    .buttons {
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    .button-row {
      padding-bottom: 1em;
    }

    .record button {
      margin-top: 2em;
      font-size: 1.2em;
    }
  `]
})
export class SimulatorComponent {

  record = false;

  toggleRecord() {
    this.record = !this.record;
  }

}
