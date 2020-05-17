import { Component, OnInit, Inject, ViewChild, AfterViewInit } from '@angular/core';
import { Scenes } from '../scene/scenes';
import { ScenesService } from '../scene/scenes.service';
import { NavigationEnd, Router, ActivatedRoute } from '@angular/router';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { FormGroup, FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import {MatSnackBar} from '@angular/material/snack-bar';
import * as moment from 'moment';

@Component({
  selector: 'app-list-saved-scenes',
  template: `
<h1>List of Scenes</h1>
<div class="container">
  <div class="table">
  <mat-table  #table class="center" [dataSource]="dataSource">
    <ng-container matColumnDef="name">
      <mat-header-cell *matHeaderCellDef> Name </mat-header-cell>
      <mat-cell *matCellDef="let scenes"> {{scenes.name}} </mat-cell>
    </ng-container>
    <ng-container matColumnDef="buttonId">
      <mat-header-cell *matHeaderCellDef> Button </mat-header-cell>>
      <mat-cell *matCellDef="let scenes"> {{scenes.buttonId}} </mat-cell>>
    </ng-container>
     <ng-container matColumnDef="universe">
      <mat-header-cell *matHeaderCellDef> Universe </mat-header-cell>>
      <mat-cell *matCellDef="let scenes"> {{scenes.universe}} </mat-cell>>
    </ng-container>

<ng-container matColumnDef="actions">
  <mat-header-cell *matHeaderCellDef > Actions </mat-header-cell>>
  <mat-cell *matCellDef="let row">
       <button class="fas fa-play-circle" mat-icon-button matTooltip="Play Scene" (click)="play(row)">
       </button>

       <button class="fas fa-info-circle" mat-icon-button matTooltip="View details of Scene" (click)="openDetailsDialog(row)">
       </button>

       <button class="fas fa-edit" mat-icon-button matTooltip="Edit Scene" (click)="openEditDialog(row)">
       </button>

       <button class="fas fa-trash-alt" mat-icon-button matTooltip="Delete Scene" (click)="openDeleteDialog(row)">
       </button>

       <button class="fas fa-download" mat-icon-button matTooltip="Download Scene" (click)="download(row)">
       </button>
  </mat-cell>
</ng-container>

    <mat-header-row *matHeaderRowDef="displayedColumns; sticky: true" ></mat-header-row>>
    <mat-row *matRowDef="let row; columns: displayedColumns;"></mat-row>>
  </mat-table>

  <mat-paginator [pageSizeOptions]="[10, 25, 100]"></mat-paginator>
</div>
</div>

  `,
  styleUrls: ['./list-saved-scenes.component.css']
})
export class ListSavedScenesComponent implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;

  dataSource: MatTableDataSource<Scenes> = new MatTableDataSource<Scenes>();
  displayedColumns = ['name', 'buttonId', 'universe', 'actions'];

  constructor(private scenesService: ScenesService,
              private router: Router,
              private dialog: MatDialog,
              private snackbar: MatSnackBar) {

  }

  ngOnInit(): void {
    this.scenesService.findAll().subscribe(data => {
      this.dataSource.data = data;
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;

  }

  play(row) {
    this.scenesService.play(row['id']);
    this.snackbar.open('Playing Scene...', 'Close', {
      duration: 3000
    });

  }

  delete(row) {
    this.scenesService.delete(row['id']);
    this.reloadComponent();
    this.snackbar.open('Scene deleted', 'Close', {
      duration: 3000
    });

  }

  openDetailsDialog(row) {
    const dialogRef = this.dialog.open(SceneDetailsDialogComponent, {
      width: '750px',
      data: {
        id: row['id']
      }
    });
  }

  openEditDialog(row) {
    const dialogRef = this.dialog.open(EditSavedSceneDialogComponent, {
      width: '750px',
      data: {
        id: row['id']
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.reloadComponent();
      this.snackbar.open('Scene edited', 'Close', {
        duration: 3000
      });


    });
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

  download(row) {
    this.scenesService.download(row['id']).subscribe( x => {
      var newBlob = new Blob([x], {type: "application/json"});

      const data = window.URL.createObjectURL(newBlob);

      var link = document.createElement('a');
      link.href = data;
      link.download = row['id'] + ".json";
      link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

      this.snackbar.open('Scene downloaded', 'Close', {
        duration: 3000
      });

    });
  }

  reloadComponent() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/sceneslist']);
  }



}


@Component({
  selector: 'scene-details-dialog',
  template: `<h1 mat-dialog-title>Scene Details</h1>
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
  <h4>Universe:</h4>
  <p>{{scene?.universe}}</p>
  <h4>Created/Edited On:</h4>
  <p>{{date}}</p>
  <h4>Frames:</h4>
  <ul>
    <li *ngFor="let frame of scene?.frames">
    {{frame.dmxValues}}
    </li>
</ul>

</div>
<div mat-dialog-actions>
  <button mat-button (click)="onNoClick()"><i class="fas fa-window-close"></i> Close</button>

</div>
`
})
export class SceneDetailsDialogComponent implements OnInit{

  scene: Scenes;
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
          this.scene = x
          this.date = moment(this.scene.createdOn).format('LLLL')

        });
    }

  onNoClick(): void {
    this.dialogRef.close();
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
  <h4>Name:</h4>
  <input matInput formControlName="name">
</mat-form-field>
</div>

<div>
<mat-form-field>
  <h4>Button:</h4>
  <mat-select  formControlName="buttonId" (change)="changeButton($event)" [value]="selectedButton">
    <mat-option *ngFor="let button of buttons" [value]="button" >{{button}}</mat-option>
  </mat-select>
</mat-form-field>
</div>

<div>
<mat-form-field>
  <h4>Fade Time:</h4>
  <mat-select  formControlName="fadeTime" (change)="changeFadeTime($event)" [value]="selectedFadeTime">
    <mat-option *ngFor="let fade of fadeTimes" [value]="fade" >{{fade}}</mat-option>
  </mat-select>
</mat-form-field>
</div>

<div>
<mat-form-field>
  <h4>Universe:</h4>
   <input matInput formControlName="universe">
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
    universe: new FormControl()
  });

  scene: Scenes;
  selectedButton: any;
  selectedFadeTime: any;
  buttons: any[] = [0,1,2,3,4,5,6,7,8,9];
  fadeTimes: any[] = [5,10,15,20,30,60];
  currentDate: Date;

  constructor(
    public dialogRef: MatDialogRef<EditSavedSceneDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: string },
    private scenesService: ScenesService) {}

  ngOnInit(): void {
    this.scenesService.get(this.data.id).subscribe(data => {
      this.scene = data;
      this.selectedButton = this.scene.buttonId;
      this.selectedFadeTime = this.scene.fadeTime;
        this.editForm.setValue({
          name: this.scene.name,
          buttonId: this.scene.buttonId,
          fadeTime: this.scene.fadeTime,
          universe: this.scene.universe
        });
      });

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

  get universe() {
    return this.editForm.get('universe');
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
    this.currentDate =  now;
    this.scene.name = this.name.value;
    this.scene.buttonId = this.buttonId.value;
    this.scene.fadeTime = this.fadeTime.value;
    this.scene.universe = this.universe.value;
    this.scene.createdOn = this.currentDate;

    this.scenesService.save(this.scene);
    this.dialogRef.close();


  }


}
