import {Component, OnDestroy, OnInit, Inject} from '@angular/core';
import {Subscription} from 'rxjs';
import {RxStompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import {Frame} from '../scene/frame';
import { ScenesService } from '../scene/scenes.service';
import { Router } from '@angular/router';
import {map, retry, catchError} from 'rxjs/operators';
import {MatSnackBar} from '@angular/material/snack-bar';
import { FormControl, Validators } from '@angular/forms';


@Component({
  selector: 'app-monitor',
  template: `
<h1>Monitor</h1>
<mat-form-field class="universe" appearance="fill">
    <mat-label>Enter the universe</mat-label>
    <input matInput placeholder="0" [formControl]="universe" required>
    <mat-error *ngIf="universe.invalid">{{getErrorMessage()}}</mat-error>
 </mat-form-field>

<div class="dmx-levels">
  <div *ngFor="let dmxValue of frame.dmxValues" class="dmx-level">
    {{dmxValue}}
  </div>
</div>
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
  <i class="fas fa-record-vinyl"></i> Record</button>
  <button class="buttons" mat-flat-button color="primary" (click)="stop()" [disabled]="stopButtonDisabled">
  <i class="fas fa-stop-circle"></i> Stop</button>
 </div>
 </div>
`,
  styleUrls: ['./monitor.component.css']
})
export class MonitorComponent implements OnInit, OnDestroy {

  universe =  new FormControl('0', [Validators.required, Validators.minLength(0), Validators.maxLength(32768)]);

  frame: Frame;
  recordButtonDisabled: boolean = true;
  recordSingleFrame: boolean = false;
  recordMultipleFrames: boolean = false;
  stopButtonDisabled: boolean = true;
  frames: Frame[] = [];
  map = new Map<string, Frame>();

  private topicSubscription: Subscription;

  constructor(private rxStompService: RxStompService,
              private sceneService: ScenesService,
              private router: Router,
              private snackbar: MatSnackBar) {

  }

  getErrorMessage() {
    if (this.universe.hasError('required')) {
      return 'You must enter a valid value';
    }
    if (this.universe.hasError('minlength')) {
      return 'You must enter a value above 0';
    }
    if (this.universe.hasError('maxlength')) {
      return 'You must enter a value below 32768';
    }
  }

  ngOnInit() {
    this.topicSubscription = this.rxStompService.watch('/topic/output').subscribe((data: Message) => {

      let jsonObject = JSON.parse(data.body);
      for (var value in jsonObject) {
        this.map.set(value, Frame.fromJSON(jsonObject[value]));
      }

      console.log(this.universe.value);
      this.frame = this.map.get(this.universe.value);
      if (this.frame == null) {
        this.frame = Frame.empty();
      }

    });
  }

  ngOnDestroy() {
    this.topicSubscription.unsubscribe();
  }

  toggleSingleFrameRecord() {
    this.map.forEach((value, key) => {
      console.log(key, value);
    });
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
          this.snackbar.open('Recording Single Frame Done', 'Close', {
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
    this.sceneService.stopRecording().subscribe( recordingDone => {
      if (recordingDone) {
        this.snackbar.open('Recording Multiple Frames Done', 'Close', {
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

