import {Component, OnDestroy, OnInit, Inject} from '@angular/core';
import {Subscription} from 'rxjs';
import {RxStompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import {Frame} from '../scene/frame';
import { ScenesService } from '../scene/scenes.service';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-monitor',
  template: `
<h1>Monitor</h1>
<div class="dmx-levels">
  <div *ngFor="let dmxValue of frame.dmxValues" class="dmx-level">
    {{dmxValue}}
  </div>
</div>
<div class="buttons">
  <button mat-flat-button color="warn" (click)="record()" [disabled]="recordingDone">Record</button>
  <button mat-flat-button color="primary" (click)="stop()">Stop</button>
 </div>
`,
  styleUrls: ['./monitor.component.css']
})
export class MonitorComponent implements OnInit, OnDestroy {

  frame: Frame = Frame.empty();
  recordingDone: boolean = false;
  private topicSubscription: Subscription;

  constructor(private rxStompService: RxStompService, private sceneService: ScenesService, private router: Router, private dialog: MatDialog) {

  }

  ngOnInit() {
    this.topicSubscription = this.rxStompService.watch('/topic/output').subscribe((message: Message) => {
      this.frame = Frame.fromJSON(JSON.parse(message.body));
    });
  }

  record() {
    this.sceneService.record(0).subscribe(data => {
      this.recordingDone = false;
      this.openRecordingDoneDialog(data);
    });
    this.recordingDone = true;
  }

  stop() {

  }

  openRecordingDoneDialog(recordingDone: boolean) {

    const dialogRef = this.dialog.open(RecordingDoneDialogComponent, {
        width: '750px',
        data: {
          record: recordingDone
        }
      });



    dialogRef.afterClosed().subscribe(result => this.reloadComponent());

  }

  reloadComponent() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/monitor']);
  }

  ngOnDestroy() {
    this.topicSubscription.unsubscribe();
  }

}


@Component({
  selector: 'recording-done-dialog',
  template: `<h1 mat-dialog-title>Information</h1>
<div mat-dialog-content>
  <p>Recording {{status}}</p>
</div>
<div mat-dialog-actions>
 <button mat-button [mat-dialog-close]="true">OK</button>
</div>
`
})
export class RecordingDoneDialogComponent {

  status: string;

  constructor(
    public dialogRef: MatDialogRef<RecordingDoneDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {record: boolean}) {
    console.log(data);
    if (data.record == true) {
      this.status = "finished.";
    } else {
      this.status = "failed."
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }


}
