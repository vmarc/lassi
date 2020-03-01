import { Component, OnInit, Inject } from '@angular/core';
import { Scenes } from '../scene/scenes';
import { ScenesService } from './scenes.service';
import { NavigationEnd, Router } from '@angular/router';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';


@Component({
  selector: 'app-list-saved-scenes',
  template: `
<h1>List of Scenes</h1>
  <div>
  <mat-table class="center" [dataSource]="dataSource">
    <ng-container matColumnDef="id">
      <mat-header-cell *matHeaderCellDef> ID </mat-header-cell>
      <mat-cell *matCellDef="let scenes"> {{scenes.id}} </mat-cell>
    </ng-container>
    <ng-container matColumnDef="name">
      <mat-header-cell *matHeaderCellDef> Name </mat-header-cell>
      <mat-cell *matCellDef="let scenes"> {{scenes.name}} </mat-cell>
    </ng-container>
    <ng-container matColumnDef="duration">
      <mat-header-cell *matHeaderCellDef> Duration </mat-header-cell>
      <mat-cell *matCellDef="let scenes"> {{scenes.duration}} </mat-cell>
    </ng-container>
    <ng-container matColumnDef="buttonId">
      <mat-header-cell *matHeaderCellDef> Button </mat-header-cell>
      <mat-cell *matCellDef="let scenes"> {{scenes.buttonID}} </mat-cell>
    </ng-container>
    <ng-container matColumnDef="createdOn">
      <mat-header-cell *matHeaderCellDef> Created On </mat-header-cell>
      <mat-cell *matCellDef="let scenes"> {{scenes.createdOn  | date:'d/LL/yyyy, HH:mm'}} </mat-cell>
    </ng-container>Ò

<ng-container matColumnDef="actions">
  <mat-header-cell  *matHeaderCellDef > Actions </mat-header-cell>
  <mat-cell *matCellDef="let row" >
       <button mat-icon-button (click)="play()" >
       <mat-icon>play_arrow</mat-icon>
       </button>

       <button mat-icon-button (click)="edit()" >
       <mat-icon>edit</mat-icon>
       </button>

       <button mat-icon-button (click)="openDialog(row)">
       <mat-icon>delete</mat-icon>
       </button>
  </mat-cell>
</ng-container>

    <mat-header-row *matHeaderRowDef="displayedColumns"></mat-header-row>
    <mat-row *matRowDef="let row; columns: displayedColumns;"></mat-row>
  </mat-table>
</div>

  `,
  styleUrls: ['./list-saved-scenes.component.css']
})
export class ListSavedScenesComponent implements OnInit {

  rowID;
  dataSource: Array<Scenes> = [];
  displayedColumns = ['id', 'name', 'duration', 'buttonId', 'createdOn', 'actions'];

  constructor(private scenesService: ScenesService,
              private _router: Router,
              private dialog: MatDialog) {}

  ngOnInit(): void {
    this.scenesService.findAll().subscribe(data => {
      this.dataSource = data;
    })
  }

  play() {

  }

  delete(row) {
    this.scenesService.delete(row['id']);
    this.reloadComponent();

  }

  edit() {

  }

  reloadComponent() {
    this._router.routeReuseStrategy.shouldReuseRoute = () => false;
    this._router.onSameUrlNavigation = 'reload';
    this._router.navigate(['/sceneslist']);
  }

  openDialog(row) {
    const dialogRef = this.dialog.open(ConfirmDeleteDialogComponent, {
      width: '750px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.delete(row);
      }
    })
  }



}

@Component({
  selector: 'confirm-delete-dialog',
  template: `<h1 mat-dialog-title>Confirmation</h1>
<div mat-dialog-content>
  <p>Are you sure you want to delete this Scene?</p>
</div>
<div mat-dialog-actions>
 <button mat-button [mat-dialog-close]="true">Yes</button>
  <button mat-button (click)="onNoClick()" cdkFocusInitial>No</button>
</div>
`
})
export class ConfirmDeleteDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<ConfirmDeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: String) {}

  onNoClick(): void {
    this.dialogRef.close();
  }


}
