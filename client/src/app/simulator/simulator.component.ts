import {Component, Input, OnInit} from '@angular/core';
import { ScenesService } from '../scene/scenes.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-simulator',
  template: `<h1 class="title">Simulator</h1>
<div class="buttons">
  <div>
    <div class="button-row">
      <app-simulator-control [recordSingleFrame]="recordSingleFrame" [recordMultipleFrames]="recordMultipleFrames" [sceneId]="1" [playable]="playable1"></app-simulator-control>
      <app-simulator-control [recordSingleFrame]="recordSingleFrame" [recordMultipleFrames]="recordMultipleFrames" [sceneId]="2" [playable]="playable2"></app-simulator-control>
      <app-simulator-control [recordSingleFrame]="recordSingleFrame" [recordMultipleFrames]="recordMultipleFrames" [sceneId]="3" [playable]="playable3"></app-simulator-control>
    </div>
    <div class="button-row">
      <app-simulator-control [recordSingleFrame]="recordSingleFrame" [recordMultipleFrames]="recordMultipleFrames" [sceneId]="4" [playable]="playable4"></app-simulator-control>
      <app-simulator-control [recordSingleFrame]="recordSingleFrame" [recordMultipleFrames]="recordMultipleFrames" [sceneId]="5" [playable]="playable5"></app-simulator-control>
      <app-simulator-control [recordSingleFrame]="recordSingleFrame" [recordMultipleFrames]="recordMultipleFrames" [sceneId]="6" [playable]="playable6"></app-simulator-control>
    </div>
    <div class="button-row">
      <app-simulator-control [recordSingleFrame]="recordSingleFrame" [recordMultipleFrames]="recordMultipleFrames" [sceneId]="7" [playable]="playable7"></app-simulator-control>
      <app-simulator-control [recordSingleFrame]="recordSingleFrame" [recordMultipleFrames]="recordMultipleFrames" [sceneId]="8" [playable]="playable8"></app-simulator-control>
      <app-simulator-control [recordSingleFrame]="recordSingleFrame" [recordMultipleFrames]="recordMultipleFrames" [sceneId]="9" [playable]="playable9"></app-simulator-control>
    </div>
  </div>
   <div class="record">
   <table>
   <tr>
    <mat-slide-toggle [checked]="playMode" (change)="togglePlayMode()">Play mode</mat-slide-toggle>
</tr>
<tr>
   <mat-slide-toggle [checked]="recordSingleFrame" (change)="toggleSingleFrameRecord()">Record single frame</mat-slide-toggle>
</tr>
<tr>
     <mat-slide-toggle [checked]="recordMultipleFrames" (change)="toggleMultipleFramesRecord()">Record multiple frames</mat-slide-toggle>
</tr>

</table>

  </div>
  <button class="record" mat-flat-button color="primary" (click)="stopRecording()" [disabled]="stopButton">
  <i class="fas fa-stop-circle"></i> Stop</button>
</div>
`,
  styleUrls: [ './simulator.component.css' ]
})
export class SimulatorComponent implements OnInit{

  playableButtons: boolean[];
  recordedButtons: boolean[];

  playMode: boolean = false;
  recordSingleFrame: boolean = false;
  recordMultipleFrames: boolean = false;
  stopButton: boolean = true;


  playable1: string = 'gray';
  playable2: string = 'gray';
  playable3: string = 'gray';
  playable4: string = 'gray';
  playable5: string = 'gray';
  playable6: string = 'gray';
  playable7: string = 'gray';
  playable8: string = 'gray';
  playable9: string = 'gray';

  playColor: string;

  constructor(private sceneService: ScenesService,
              private snackbar: MatSnackBar) {
  }

  togglePlayMode() {
    this.playMode = !this.playMode;
    if (this.stopButton == false) {
      this.stopButton = true;
    }
    if (this.playMode == true) {
      this.recordMultipleFrames = !this.playMode;
      this.recordSingleFrame = !this.playMode;
    }

    this.fillInColors();
  }

  toggleSingleFrameRecord() {
    this.recordSingleFrame = !this.recordSingleFrame;
    if (this.stopButton == false) {
      this.stopButton = true;
    }
    if (this.recordSingleFrame = true) {
      this.recordMultipleFrames = false;
      this.playMode = false;
    }

    this.fillInColors();
  }

  toggleMultipleFramesRecord() {
    this.recordMultipleFrames = !this.recordMultipleFrames;
    this.stopButton = !this.stopButton;
    if (this.recordMultipleFrames = true) {
      this.recordSingleFrame = false;
      this.playMode = false;
    }


    this.fillInColors();
  }

  stopRecording() {
    this.sceneService.stopRecording().subscribe(data => {
      if (data) {
        this.snackbar.open('Recording Multiple Frames done...', 'Close', {
          duration: 3000
        });
      }
    });
  }

  ngOnInit(): void {
    this.sceneService.getButtons().subscribe(data => {

      this.recordedButtons = data;

      this.playableButtons = this.inverted(this.recordedButtons);
      console.log(this.playableButtons);
      this.fillInColors();

    });
  }

  inverted(bools: boolean[]): boolean[] {
    return bools.map(function (bool) {
      return !bool;
    })
  }

  fillInColors() {

    if (!this.recordSingleFrame && !this.recordMultipleFrames && !this.playMode) {
      this.playColor = 'gray';
    }
    if (this.playMode) {
      this.playColor = 'green';
    }
    if (this.recordMultipleFrames || this.recordSingleFrame) {
      this.playColor = 'red';
    }

    if (this.playableButtons[0]) {
      this.playable1 = this.playColor;
    }
    if (this.playableButtons[1]) {
      this.playable2 = this.playColor;
    }
    if (this.playableButtons[2]) {
      this.playable3 = this.playColor;
    }
    if (this.playableButtons[3]) {
      this.playable4 = this.playColor;
    }
    if (this.playableButtons[4]) {
      this.playable5 = this.playColor;
    }
    if (this.playableButtons[5]) {
      this.playable6 = this.playColor;
    }
    if (this.playableButtons[6]) {
      this.playable7 = this.playColor;
    }
    if (this.playableButtons[7]) {
      this.playable8 = this.playColor;
    }
    if (this.playableButtons[8]) {
      this.playable9 = this.playColor;
    }
  }

}
