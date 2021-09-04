import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {Router} from '@angular/router';
import {Scene} from '../domain/scene';
import {ConfirmDeleteDialogComponent} from './confirm-delete-dialog.component';
import {ScenesService} from './scenes.service';

@Component({
  selector: 'app-scenes',
  template: `
    <h1>Scenes</h1>
    <mat-paginator [pageSizeOptions]="[10, 25, 100]"></mat-paginator>
    <mat-table #table class="center" [dataSource]="dataSource" matSort>
      <ng-container matColumnDef="name">
        <mat-header-cell *matHeaderCellDef mat-sort-header>Name</mat-header-cell>
        <mat-cell *matCellDef="let scene"> {{scene.name}} </mat-cell>
      </ng-container>
      <ng-container matColumnDef="buttonId">
        <mat-header-cell *matHeaderCellDef mat-sort-header>Button</mat-header-cell>
        <mat-cell *matCellDef="let scene"> {{scene.buttonId}} </mat-cell>
      </ng-container>
      <ng-container matColumnDef="createdOn">
        <mat-header-cell *matHeaderCellDef mat-sort-header>Created/Edited On</mat-header-cell>
        <mat-cell *matCellDef="let scene"> {{scene.createdOn | date:'yyyy-LL-dd HH:mm'}} </mat-cell>
      </ng-container>

      <ng-container matColumnDef="actions">
        <mat-header-cell *matHeaderCellDef>Actions</mat-header-cell>
        <mat-cell *matCellDef="let scene">
          <button [ngClass]="customCss" mat-icon-button matTooltip="Play/Pause Scene" (click)="playPause(scene)"></button>
          <button class="fas fa-stop-circle" mat-icon-button matTooltip="Stop Scene" [disabled]="!playingScene" (click)="stop(scene)"></button>
          <button class="fas fa-info-circle" mat-icon-button matTooltip="View details of Scene" (click)="gotoDetails(scene)"></button>
          <button class="fas fa-edit" mat-icon-button matTooltip="Edit Scene" (click)="openEditDialog(scene)"></button>
          <button class="fas fa-trash-alt" mat-icon-button matTooltip="Delete Scene" (click)="openDeleteDialog(scene)"></button>
        </mat-cell>
      </ng-container>

      <mat-header-row *matHeaderRowDef="displayedColumns; sticky: true"></mat-header-row>
      <mat-row *matRowDef="let row; columns: displayedColumns;"></mat-row>
    </mat-table>
  `,
  styles: [`
    .mat-table.center {
      width: 100%;
      margin-left: auto;
      margin-right: auto;
      margin-bottom: auto;
    }

    .mat-row, .mat-header-row {
      min-width: 1000px;
      width: max-content;
    }

    .mat-header-cell {
      flex-direction: column;
      justify-content: center;
    }

    .mat-cell {
      text-align: left;
      justify-content: center;
    }

    .dmx {
      white-space: pre-wrap;
    }

    .mat-icon-button {
      font-size: 20px;
    }

  `]
})
export class ScenesComponent implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  dataSource: MatTableDataSource<Scene> = new MatTableDataSource<Scene>();
  displayedColumns = ['name', 'buttonId', 'createdOn', 'actions'];
  playingScene = false;
  pause = false;
  stopped = false;

  constructor(private scenesService: ScenesService,
              private router: Router,
              private dialog: MatDialog,
              private snackbar: MatSnackBar) {
  }

  get customCss() {
    if (this.playingScene && this.pause == false) {
      return 'fas fa-pause-circle';
    } else if (this.playingScene == false || this.pause == true) {
      return 'fas fa-play-circle';
    }
  }

  ngOnInit(): void {
    this.scenesService.findAll().subscribe(data => {
      this.dataSource.data = data;
      this.dataSource.sort = this.sort;
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  play(scene: Scene) {
    this.stopped = false;
    this.snackbar.open('Playing Scene...', 'Close', {
      duration: 3000
    });
    this.scenesService.play(scene.id).subscribe(donePlaying => {
      if (donePlaying) {
        if (this.stopped == true) {

        } else {
          this.playingScene = !this.playingScene;
          this.snackbar.open('Done playing Scene...', 'Close', {
            duration: 3000
          });
        }
      }
    });
  }

  playPause(scene: Scene) {
    if (this.playingScene == false) {
      this.playingScene = !this.playingScene;
      this.play(scene);
    } else if (this.playingScene && !this.pause) {
      this.pause = true;
      this.scenesService.pause(true);
      this.snackbar.open('Paused playing Scene...', 'Close', {
        duration: 3000
      });
    } else if (this.playingScene && this.pause) {
      this.pause = false;
      this.scenesService.pause(false);
      this.snackbar.open('Resumed playing Scene...', 'Close', {
        duration: 3000
      });
    }
  }

  stop(scene: Scene) {
    this.stopped = true;
    this.scenesService.stopPlaying();
    this.snackbar.open('Stopping Scene...', 'Close', {
      duration: 3000
    });
    this.playingScene = !this.playingScene;
  }

  delete(scene: Scene) {
    this.scenesService.delete(scene.id);
    this.reloadComponent();
    this.snackbar.open('Scene deleted', 'Close', {
      duration: 3000
    });
  }

  gotoDetails(scene: Scene) {
    this.router.navigate([`/scenes/${scene.id}`]);
  }

  openEditDialog(scene: Scene) {
    this.router.navigate([`/scenes/${scene.id}/edit`]);
  }

  openDeleteDialog(scene: Scene) {
    const dialogRef = this.dialog.open(ConfirmDeleteDialogComponent, {
      width: '750px'
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.delete(scene);
      }
    });
  }

  reloadComponent() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/scenes']);
  }

}
