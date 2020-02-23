import { Component, OnInit } from '@angular/core';
import { Scenes } from '../scene/scenes';
import { ScenesService } from './scenes.service';

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
      <mat-cell *matCellDef="let scenes"> {{scenes.createdOn}} </mat-cell>
    </ng-container>

    <ng-container matColumnDef="actions">
  <mat-header-cell  *matHeaderCellDef > Actions </mat-header-cell>
  <mat-cell *matCellDef="let row" >
       <button mat-icon-button (click)="play()" >
       <mat-icon>play_arrow</mat-icon>
       </button>

       <button mat-icon-button (click)="edit()" >
       <mat-icon>edit</mat-icon>
       </button>

       <button mat-icon-button (click)="delete()">
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

  dataSource: Array<Scenes> = [];
  displayedColumns = ['id', 'name', 'duration', 'buttonId', 'createdOn', 'actions'];

  constructor(private scenesService: ScenesService) { }

  ngOnInit(): void {
    this.scenesService.findAll().subscribe(data => {
      this.dataSource = data;
    })
  }

  play() {

  }

  delete() {


  }

  edit() {

  }

}
