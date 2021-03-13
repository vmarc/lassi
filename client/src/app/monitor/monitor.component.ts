import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {RxStompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import {Frame} from '../scene/frame';
import {ScenesService} from '../scene/scenes.service';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-monitor',
  template: `
    <h1>Monitor</h1>

    <div class="date" [hidden]="disabledDate">
      <strong>Frame created on: {{frame?.createdOn | date:'d/LL/yyyy, HH:mm'}}</strong>
    </div>

    <div class="dmx-levels">
      <div *ngFor="let dmxValue of frame?.dmxValues" class="dmx-level">
        {{dmxValue}}
      </div>
    </div>

    <mat-form-field class="universe" appearance="outline">
      <mat-label>Choose universe to display</mat-label>
      <input matInput placeholder="" [formControl]="universe" required>
      <mat-error *ngIf="universe.invalid">{{getErrorMessage()}}</mat-error>
    </mat-form-field>

    <div class="record">

      <table>
        <tr>
          <mat-slide-toggle [checked]="recordSingleFrame" (change)="toggleSingleFrameRecord()">Record single frame</mat-slide-toggle>
        </tr>
        <tr>
          <mat-slide-toggle [checked]="recordMultipleFrames" (change)="toggleMultipleFramesRecord()">Record multiple frames</mat-slide-toggle>
        </tr>
      </table>

      <div>
        <button class="buttons" mat-flat-button color="warn" (click)="record()" [disabled]="recordButtonDisabled">
          <i class="fas fa-record-vinyl"></i> Record
        </button>
        <button class="buttons" mat-flat-button color="primary" (click)="stop()" [disabled]="stopButtonDisabled">
          <i class="fas fa-stop-circle"></i> Stop
        </button>
      </div>
    </div>
  `,
  styles: [`

    .dmx-levels {
      display: flex;
      flex-wrap: wrap;
      margin-left: 1em;
      margin-right: 1em;
      margin-bottom: 3em;
      border-top: 1px solid lightgray;
      border-left: 1px solid lightgray;
    }

    .dmx-level {
      display: inline-block;
      width: 2.2em;
      height: 1.8em;
      line-height: 1.8em;
      border-right: 1px solid lightgray;
      border-bottom: 1px solid lightgray;
      text-align: center;
    }

    .buttons {
      display: inline-flex;
      flex-wrap: wrap;
      margin: 5px;

    }

    .universe {
      margin-left: 1em;
      margin-bottom: 1em;
    }

    h1 {
      text-align: center;
    }

    .date {
      text-align: center;
      margin-bottom: 2em;
    }

    .record {
      margin-left: 1em;
    }

  `]
})
export class MonitorComponent implements OnInit, OnDestroy {

  formGroup: FormGroup;

  universe = new FormControl('', [Validators.required, Validators.min(0), Validators.max(32768)]);

  frame: Frame;
  disabledDate = false;
  recordButtonDisabled = true;
  recordSingleFrame = false;
  recordMultipleFrames = false;
  stopButtonDisabled = true;
  frames: Frame[] = [];

  private topicSubscription: Subscription;

  constructor(private rxStompService: RxStompService,
              private sceneService: ScenesService,
              private router: Router,
              private snackbar: MatSnackBar,
              private builder: FormBuilder) {
    this.formGroup = this.builder.group({
      universe: ["", []]
    });
  }

  getErrorMessage() {
    if (this.universe.hasError('required')) {
      return 'You must enter a valid value';
    }
    if (this.universe.hasError('min')) {
      return 'You must enter a value above 0';
    }
    if (this.universe.hasError('max')) {
      return 'You must enter a value below 32768';
    }
  }

  ngOnInit() {
    this.topicSubscription = this.rxStompService.watch('/topic/output').subscribe((data: Message) => {
      let map = new Map<string, Frame>();
      let jsonObject = JSON.parse(data.body);
      for (var value in jsonObject) {
        map.set(value, Frame.fromJSON(jsonObject[value]));
      }

      this.frame = map.get(this.universe.value);
      if (this.frame == null) {
        this.frame = Frame.empty();
        this.disabledDate = true;
      } else {
        this.disabledDate = false;
      }

    });
  }

  ngOnDestroy() {
    this.topicSubscription.unsubscribe();
  }

  toggleSingleFrameRecord() {

    this.recordSingleFrame = !this.recordSingleFrame;

    if (this.recordSingleFrame == true) {
      this.recordMultipleFrames = false;
      this.recordButtonDisabled = false;
    } else {
      this.recordButtonDisabled = true;
    }

    if (this.stopButtonDisabled == false) {
      this.stopButtonDisabled = true;
    }
  }

  toggleMultipleFramesRecord() {

    this.recordMultipleFrames = !this.recordMultipleFrames;

    if (this.recordMultipleFrames == true) {
      this.recordButtonDisabled = false;
      this.recordSingleFrame = false;
    } else {
      this.recordButtonDisabled = true;
    }
  }

  record() {

    if (this.recordSingleFrame == true) {

      this.sceneService.recordSingleFrame(0).subscribe(data => {
        this.snackbar.open('Recording Single Frame...', 'Close', {
          duration: 3000
        });
        if (data) {
          this.snackbar.open('Recording Single Frame done', 'Close', {
            duration: 3000
          });
        } else {
          this.snackbar.open('No data to record...', 'Close', {
            duration: 3000
          });
        }
      });
    }
    if (this.recordMultipleFrames == true) {
      this.sceneService.recordMultipleFrames(0).subscribe(data => {
        this.snackbar.open('Recording Multiple Frames...', 'Close', {
          duration: 3000
        });
        this.stopButtonDisabled = !this.stopButtonDisabled;
      });
    }
  }

  stop() {
    this.sceneService.stopRecording().subscribe(recordingDone => {
      if (recordingDone) {
        this.snackbar.open('Recording Multiple Frames done', 'Close', {
          duration: 3000
        });
        this.stopButtonDisabled = !this.stopButtonDisabled;
      } else {
        this.snackbar.open('No data to record...', 'Close', {
          duration: 3000
        });
        this.stopButtonDisabled = !this.stopButtonDisabled;
      }
    });
  }

}
