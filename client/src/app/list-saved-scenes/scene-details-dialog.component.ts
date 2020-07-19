import {Component, Inject, OnInit} from "@angular/core";
import {Scene} from "../scene/scene";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ScenesService} from "../scene/scenes.service";
import * as moment from "moment";

@Component({
  selector: 'scene-details-dialog',
  template: `
    <h1 mat-dialog-title>Scene Details</h1>
    <div mat-dialog-content>
      <h4>ID:</h4>
      <p>{{scene?.id}}</p>
      <h4>Name:</h4>
      <p>{{scene?.name}}</p>
      <h4>Duration:</h4>
      <p>{{scene?.duration}}</p>
      <h4>Button:</h4>
      <p>{{scene?.buttonId}}</p>
      <h4>Fade Time:</h4>
      <p>{{scene?.fadeTime}}</p>
      <h4>Created/Edited On:</h4>
      <p>{{date}}</p>
      <h4>Frames:</h4>
      <ul>
        <li *ngFor="let frame of scene?.frames">
          Universe: {{frame.universe}}<br> Created On: {{frame.createdOn | date:'d/LL/yyyy, HH:mm'}} <br> {{frame.dmxValues}}
        </li>
      </ul>
    </div>
    <div mat-dialog-actions>
      <button mat-button (click)="onNoClick()"><i class="fas fa-window-close"></i> Close</button>
    </div>
  `
})
export class SceneDetailsDialogComponent implements OnInit {

  scene: Scene;
  date: string;

  constructor(
    public dialogRef: MatDialogRef<SceneDetailsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: string },
    private sceneService: ScenesService) {
  }

  ngOnInit(): void {
    moment.locale('nl-be')
    const id = this.data.id;
    this.sceneService.get(id).subscribe(x => {
      this.scene = x;
      this.date = moment(this.scene.createdOn).format('LLLL');
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
