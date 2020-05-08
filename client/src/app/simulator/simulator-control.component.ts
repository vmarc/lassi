import {Component, Input, OnInit} from '@angular/core';
import { ScenesService } from '../list-saved-scenes/scenes.service';

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
export class SimulatorControlComponent implements OnInit {

  @Input() sceneId: number;
  @Input() record: boolean;

  constructor(private sceneService: ScenesService) {
  }

  ngOnInit(): void {

  }



  buttonClicked(): void {
    if (this.record) {
      this.sceneService.record(this.sceneId);
    } else {
      //this.sceneService.play(this.sceneId);
    }
  }

}
