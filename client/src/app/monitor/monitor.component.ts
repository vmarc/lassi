import {Component, OnDestroy, OnInit, Inject} from '@angular/core';
import {Subscription} from 'rxjs';
import {RxStompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import {Frame} from '../scene/frame';
import { ScenesService } from '../scene/scenes.service';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import {map, retry, catchError} from 'rxjs/operators';
import {MatSnackBar} from '@angular/material/snack-bar';


@Component({
  selector: 'app-monitor',
  template: `
<h1>Monitor</h1>
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
  <button class="buttons" mat-flat-button color="warn" (click)="record()" [disabled]="recordButton">
  <i class="fas fa-record-vinyl"></i> Record</button>
  <button class="buttons" mat-flat-button color="primary" (click)="stop()" [disabled]="stopButton">
  <i class="fas fa-stop-circle"></i> Stop</button>
 </div>
 </div>
`,
  styleUrls: ['./monitor.component.css']
})
export class MonitorComponent implements OnInit, OnDestroy {

  frame: Frame = Frame.empty();
  recordButton: boolean = true;
  recordSingleFrame: boolean = false;
  recordMultipleFrames: boolean = false;
  stopButton: boolean = true;

  private topicSubscription: Subscription;

  constructor(private rxStompService: RxStompService,
              private sceneService: ScenesService,
              private router: Router,
              private dialog: MatDialog,
              private snackbar: MatSnackBar) {

  }

  ngOnInit() {
    this.topicSubscription = this.rxStompService.watch('/topic/output').subscribe((message: Message) => {
      this.frame = Frame.fromJSON(JSON.parse(message.body));
    });
  }

  toggleSingleFrameRecord() {
    this.recordSingleFrame = !this.recordSingleFrame;
    this.recordButton = !this.recordButton;
    if (this.stopButton == false) {
      this.stopButton = true;
    }
    if (this.recordSingleFrame = true) {
      this.recordMultipleFrames = false;
    }


  }

  toggleMultipleFramesRecord() {
    this.recordMultipleFrames = !this.recordMultipleFrames;
    this.recordButton = !this.recordButton;
    if (this.recordMultipleFrames = true) {
      this.recordSingleFrame = false;
    }

  }

  record() {
    if (this.recordSingleFrame == true) {
      this.sceneService.recordSingleFrame(0).subscribe(data => {
        this.snackbar.open('Recording Single Frame...', 'Close', {
          duration: 3000
        });
        if (data) {
          this.snackbar.open('Recording Single Frame Done...', 'Close', {
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
        this.stopButton = !this.stopButton;
      });
    }
  }


  stop() {
    this.sceneService.stopRecording().subscribe( data => {
      if (data) {
        this.snackbar.open('Recording Multiple Frames Done...', 'Close', {
          duration: 3000
        });
        this.stopButton = !this.stopButton;
      }
    });
  }

  ngOnDestroy() {
    this.topicSubscription.unsubscribe();
  }

}

