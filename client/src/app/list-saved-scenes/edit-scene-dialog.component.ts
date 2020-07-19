import {Component, Inject} from "@angular/core";
import {FormControl, FormGroup} from "@angular/forms";
import {Scene} from "../scene/scene";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ScenesService} from "../scene/scenes.service";

@Component({
  selector: 'edit-scene-dialog',
  template: `
    <h1 mat-dialog-title>Edit Scene</h1>
    <form [formGroup]="editForm">
      <div mat-dialog-content>
        <div>
          <mat-form-field>
            <h4>Name:</h4>
            <input matInput formControlName="name">
          </mat-form-field>
        </div>

        <div>
          <mat-form-field>
            <h4>Button:</h4>
            <mat-select formControlName="buttonId" (change)="changeButton($event)" [value]="selectedButton">
              <mat-option *ngFor="let button of buttons" [value]="button">{{button}}</mat-option>
            </mat-select>
          </mat-form-field>
        </div>

        <div>
          <mat-form-field>
            <h4>Fade Time:</h4>
            <mat-select formControlName="fadeTime" (change)="changeFadeTime($event)" [value]="selectedFadeTime">
              <mat-option *ngFor="let fade of fadeTimes" [value]="fade">{{fade}}</mat-option>
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
export class EditSavedSceneDialogComponent {

  editForm: FormGroup = new FormGroup({
    name: new FormControl(),
    buttonId: new FormControl(),
    fadeTime: new FormControl(),
  });

  scene: Scene;
  selectedButton: any;
  selectedFadeTime: any;
  buttons: any[] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27];
  fadeTimes: any[] = [5, 10, 15, 20, 30, 60];
  currentDate: Date;

  constructor(
    public dialogRef: MatDialogRef<EditSavedSceneDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: string },
    private scenesService: ScenesService) {
  }

  get name() {
    return this.editForm.get('name');
  }

  get buttonId() {
    return this.editForm.get('buttonId');
  }

  get fadeTime() {
    return this.editForm.get('fadeTime');
  }

  ngOnInit(): void {
    this.scenesService.get(this.data.id).subscribe(data => {
      this.scene = data;
      this.selectedButton = this.scene.buttonId;
      this.selectedFadeTime = this.scene.fadeTime;
      this.editForm.setValue({
        name: this.scene.name,
        buttonId: this.scene.buttonId,
        fadeTime: this.scene.fadeTime,
      });
    });
  }

  changeButton($event) {
    this.buttonId.setValue(this.buttons[$event]);
  }

  changeFadeTime($event) {
    this.fadeTime.setValue(this.fadeTimes[$event]);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  save(): void {
    var now = new Date();
    now.setHours(now.getHours() + 2)
    this.currentDate = now;
    this.scene.name = this.name.value;
    this.scene.buttonId = this.buttonId.value;
    this.scene.fadeTime = this.fadeTime.value;
    this.scene.createdOn = this.currentDate;

    this.scenesService.save(this.scene);
    this.dialogRef.close();
  }
}
