import {Component, Input, OnInit} from '@angular/core';
import { ScenesService } from '../scene/scenes.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-simulator-control',
  template: `
    <div class="control">
      <app-simulator-led [sceneId]="sceneId" [playable]="playable"></app-simulator-led>
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
  @Input() recordSingleFrame: boolean;
  @Input() recordMultipleFrames: boolean;
  @Input() playable: string;


  constructor(private sceneService: ScenesService, private snackbar: MatSnackBar) {
  }

  ngOnInit(): void {

  }



  buttonClicked(): void {
    if (this.recordSingleFrame) {
      this.sceneService.recordSingleFrame(this.sceneId);
      this.snackbar.open('Recording Single Frame...', 'Close', {
        duration: 3000
      });
    }

    if (this.recordMultipleFrames) {
      this.sceneService.recordMultipleFrames(this.sceneId);
      this.snackbar.open('Recording Multiple Frames...', 'Close', {
        duration: 3000
      });
    }

      if (!this.recordSingleFrame && !this.recordMultipleFrames) {
        if (this.playable == 'green') {
          this.sceneService.playFromButton(this.sceneId);
          this.snackbar.open('Playing Scene...', 'Close', {
            duration: 3000
          });
        } else {
          this.snackbar.open('This button does not have a Scene...', 'Close', {
            duration: 3000
          });
        }

      }

    }



}
