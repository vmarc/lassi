import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Scene} from '../scene/scene';
import {ScenesService} from '../scene/scenes.service';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatSort} from '@angular/material/sort';
import {SceneDetailsDialogComponent} from "./scene-details-dialog.component";
import {ConfirmDeleteDialogComponent} from "./confirm-delete-dialog.component";
import {EditSavedSceneDialogComponent} from "./edit-scene-dialog.component";

@Component({
  selector: 'app-list-saved-scenes',
  template: `
    <h1>List of Scenes</h1>
    <div class="container">
      <div class="table">
        <mat-table #table class="center" [dataSource]="dataSource" matSort>
          <ng-container matColumnDef="name">
            <mat-header-cell *matHeaderCellDef mat-sort-header> Name</mat-header-cell>
            <mat-cell *matCellDef="let scenes"> {{scenes.name}} </mat-cell>
          </ng-container>
          <ng-container matColumnDef="buttonId">
            <mat-header-cell *matHeaderCellDef mat-sort-header> Button</mat-header-cell>
            <mat-cell *matCellDef="let scenes"> {{scenes.buttonId}} </mat-cell>
          </ng-container>
          <ng-container matColumnDef="createdOn">
            <mat-header-cell *matHeaderCellDef mat-sort-header> Created/Edited On</mat-header-cell>
            <mat-cell *matCellDef="let scenes"> {{scenes.createdOn | date:'d/LL/yyyy, HH:mm'}} </mat-cell>
          </ng-container>

          <ng-container matColumnDef="actions">
            <mat-header-cell *matHeaderCellDef> Actions</mat-header-cell>
            <mat-cell *matCellDef="let row">
              <button [ngClass]="customCss" mat-icon-button matTooltip="Play/Pause Scene" (click)="playPause(row)">
              </button>
              <button class="fas fa-stop-circle" mat-icon-button matTooltip="Stop Scene" [disabled]="!playingScene" (click)="stop(row)">
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

          <mat-header-row *matHeaderRowDef="displayedColumns; sticky: true"></mat-header-row>
          <mat-row *matRowDef="let row; columns: displayedColumns;"></mat-row>
        </mat-table>

        <mat-paginator [pageSizeOptions]="[10, 25, 100]"></mat-paginator>
      </div>
    </div>
  `,
  styles: [`
    .mat-table.center {
      width: 100%;
      margin-left: auto;
      margin-right: auto;
      margin-bottom: auto;
    }

    .container {
      text-align: center
    }

    .table {
      display: inline-block;
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
      text-align: center;
      justify-content: center;
    }

    .dmx {
      white-space: pre-wrap;
    }

    .mat-icon-button {
      font-size: 20px;
    }

    h1 {
      text-align: center;
      padding-bottom: 20px;
    }

  `]
})
export class ListSavedScenesComponent implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  dataSource: MatTableDataSource<Scene> = new MatTableDataSource<Scene>();
  displayedColumns = ['name', 'buttonId', 'createdOn', 'actions'];
  playingScene: boolean = false;
  pause: boolean = false;
  stopped: boolean = false;

  constructor(private scenesService: ScenesService,
              private router: Router,
              private dialog: MatDialog,
              private snackbar: MatSnackBar) {
  }

  get customCss() {
    if (this.playingScene && this.pause == false) {
      return 'fas fa-pause-circle'
    } else if (this.playingScene == false || this.pause == true) {
      return 'fas fa-play-circle'
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

  play(row) {
    this.stopped = false;
    this.snackbar.open('Playing Scene...', 'Close', {
      duration: 3000
    });
    this.scenesService.play(row['id']).subscribe(donePlaying => {
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

  playPause(row) {
    if (this.playingScene == false) {
      this.playingScene = !this.playingScene;
      this.play(row);
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

  stop(row) {
    this.stopped = true;
    this.scenesService.stopPlaying();
    this.snackbar.open('Stopping Scene...', 'Close', {
      duration: 3000
    });
    this.playingScene = !this.playingScene;
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
    this.scenesService.download(row['id']).subscribe(blob => {
      var newBlob = new Blob([blob], {type: "application/json"});

      const data = window.URL.createObjectURL(newBlob);

      var link = document.createElement('a');
      link.href = data;
      link.download = row['id'] + ".json";
      link.dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));

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

