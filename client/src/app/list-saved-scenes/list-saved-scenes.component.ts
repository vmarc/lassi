import { Component, OnInit, Inject } from '@angular/core';
import { Scenes } from '../scene/scenes';
import { ScenesService } from './scenes.service';
import { NavigationEnd, Router, ActivatedRoute } from '@angular/router';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { FormGroup, FormControl } from '@angular/forms';


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
      <mat-cell *matCellDef="let scenes"> {{scenes.buttonId}} </mat-cell>
    </ng-container>
    <ng-container matColumnDef="createdOn">
      <mat-header-cell *matHeaderCellDef> Created On </mat-header-cell>
      <mat-cell *matCellDef="let scenes"> {{scenes.createdOn  | date:'d/LL/yyyy, HH:mm'}} </mat-cell>
    </ng-container>

<ng-container matColumnDef="actions">
  <mat-header-cell  *matHeaderCellDef > Actions </mat-header-cell>
  <mat-cell *matCellDef="let row" >
       <button mat-icon-button (click)="play(row)" >
       <mat-icon>play_arrow</mat-icon>
       </button>

       <button mat-icon-button (click)="openEditDialog(row)" >
       <mat-icon>edit</mat-icon>
       </button>

       <button mat-icon-button (click)="openDeleteDialog(row)">
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
              private router: Router,
              private dialog: MatDialog) {}

  ngOnInit(): void {
    this.scenesService.findAll().subscribe(data => {
      this.dataSource = data;
    })
  }

  play(row) {
    this.scenesService.play(row['buttonId']);

  }

  delete(row) {
    this.scenesService.delete(row['id']);
    this.reloadComponent();

  }

  openEditDialog(row) {
    const dialogRef = this.dialog.open(EditSavedSceneDialogComponent, {
      width: '750px',
      data: {
        id: row['id']
      }
    });

    dialogRef.afterClosed().subscribe(result => this.reloadComponent());


  }

  reloadComponent() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/sceneslist']);
  }

  openDeleteDialog(row) {
    const dialogRef = this.dialog.open(ConfirmDeleteDialogComponent, {
      width: '750px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.delete(row);
      }
    });
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

@Component({
  selector: 'edit-scene-dialog',
  template: `<h1 mat-dialog-title>Edit Scene</h1>
 <form [formGroup]="editForm">
<div mat-dialog-content>
<div>
<mat-form-field>
  <p>Name</p>
  <input matInput formControlName="name">
</mat-form-field>
</div>

<div>
<mat-form-field>
  <p>Button</p>
  <mat-select  formControlName="buttonId" (change)="changeButton($event)" [value]="selected">
    <mat-option *ngFor="let button of buttons" [value]="button" >{{button}}</mat-option>
  </mat-select>
</mat-form-field>
</div>

</div>
<div mat-dialog-actions>
 <button mat-button (click)="save()">Save</button>
  <button mat-button (click)="onNoClick()" cdkFocusInitial>Cancel</button>
</div>
</form>
`
})
export class EditSavedSceneDialogComponent {

  editForm: FormGroup = new FormGroup({
    name: new FormControl(),
    buttonId: new FormControl(),
  });

  scene: Scenes;
  selected: any;
  buttons: any[] = [1,2,3,4,5,6,7,8,9];

  constructor(
    public dialogRef: MatDialogRef<EditSavedSceneDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: string },
    private scenesService: ScenesService) {}

  ngOnInit(): void {
    this.scenesService.get(this.data.id).subscribe(data => {
        this.scene = data
      this.selected = this.scene.buttonId
        this.editForm.setValue({
          name: this.scene.name,
          buttonId: this.scene.buttonId
        });
      });

  }

  get buttonId() {
    return this.editForm.get('buttonId');
  }

  changeButton($event) {
    this.buttonId.setValue(this.buttons[$event]);

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  save(): void {
    this.scene.name = this.editForm.get('name').value;
    this.scene.buttonId = this.editForm.get('buttonId').value;

    this.scenesService.save(this.scene);
    this.dialogRef.close();


  }


}
