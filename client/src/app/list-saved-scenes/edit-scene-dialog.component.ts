import {OnInit} from '@angular/core';
import {Component, Inject} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Scene} from '../scene/scene';
import {ScenesService} from '../scene/scenes.service';

@Component({
  selector: 'edit-scene-dialog',
  template: `
    <h1 mat-dialog-title>Edit Scene</h1>
    <form [formGroup]="editForm">
      <div mat-dialog-content>
        <div>
          <mat-form-field>
            <h4>Name:</h4>
            <input matInput [formControl]="name">
          </mat-form-field>
        </div>

        <div>
          <mat-form-field>
            <h4>Button:</h4>
            <mat-select [formControl]="buttonId">
              <mat-option *ngFor="let option of buttonOptions" [value]="option">{{option}}</mat-option>
            </mat-select>
          </mat-form-field>
        </div>

        <div>
          <mat-form-field>
            <h4>Fade Time:</h4>
            <mat-select [formControl]="fadeTime">
              <mat-option *ngFor="let option of fadeTimeOptions" [value]="option">{{option}}</mat-option>
            </mat-select>
          </mat-form-field>
        </div>

      </div>
      <div mat-dialog-actions>
        <button mat-button [mat-dialog-close]="true" (click)="save()"><i class="fas fa-save"></i> Save</button>
        <button mat-button (click)="onNoClick()" cdkFocusInitial><i class="fas fa-window-close"></i> Cancel</button>
      </div>
    </form>
  `
})
export class EditSavedSceneDialogComponent implements OnInit {

  readonly name = new FormControl();
  readonly buttonId = new FormControl();
  readonly fadeTime = new FormControl();

  readonly editForm: FormGroup = new FormGroup({
    name: this.name,
    buttonId: this.buttonId,
    fadeTime: this.fadeTime
  });

  readonly buttonOptions = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27];
  readonly fadeTimeOptions = [5, 10, 15, 20, 30, 60];

  scene: Scene;

  constructor(public dialogRef: MatDialogRef<EditSavedSceneDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: { id: string },
              private scenesService: ScenesService) {
  }

  ngOnInit(): void {
    this.scenesService.get(this.data.id).subscribe(scene => {
      this.scene = scene;
      this.editForm.setValue({
        name: scene.name,
        buttonId: scene.buttonId,
        fadeTime: scene.fadeTime,
      });
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  save(): void {
    const now = new Date();
    now.setHours(now.getHours() + 2);
    const scene = new Scene(
      this.scene.id,
      this.name.value,
      this.scene.duration,
      this.buttonId.value,
      this.fadeTime.value,
      now,
      this.scene.frames
    );
    this.scenesService.save(scene);
    this.dialogRef.close();
  }
}
