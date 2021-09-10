import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'confirm-delete-dialog',
  template: `<h1 mat-dialog-title>Confirmation</h1>
  <div mat-dialog-content>
    <p>Are you sure you want to delete this scene?</p>
  </div>
  <div mat-dialog-actions>
    <button mat-button [mat-dialog-close]="true">Yes</button>
    <button mat-button (click)="onNoClick()" cdkFocusInitial>No</button>
  </div>
  `
})
export class ConfirmDeleteDialogComponent {

  constructor(public dialogRef: MatDialogRef<ConfirmDeleteDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: String) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
