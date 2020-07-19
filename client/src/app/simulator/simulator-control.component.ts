import {Component, Input} from '@angular/core';
import {ScenesService} from '../scene/scenes.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';

@Component({
  selector: 'app-simulator-control',
  template: `
    <div class="control">
      <app-simulator-led [sceneId]="sceneId" [playable]="playable"></app-simulator-led>
      <button mat-stroked-button (click)="buttonClicked()">{{sceneId}}</button>
    </div>
  `,
  styles: [`
    .control {
      margin: .2em;
      display: inline-flex;
      flex-direction: column;
      align-items: center;
    }

    .control app-simulator-led {
      display: block;
    }

    .control button {
      width: 2em;
      height: 2em;
      font-size: 2em;
    }
  `]
})
export class SimulatorControlComponent {

  @Input() sceneId: number;
  @Input() recordSingleFrame: boolean;
  @Input() recordMultipleFrames: boolean;
  @Input() playMode: boolean;
  @Input() playable: string;
  startedMultipleFramesRecord: boolean = false;
  scenePlaying: boolean = false;
  pausedPlaying: boolean = false;

  constructor(private sceneService: ScenesService,
              private snackbar: MatSnackBar,
              private router: Router) {
  }

  reloadComponent() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/simulator']);
  }

  buttonClicked(): void {

    if (this.recordMultipleFrames && this.startedMultipleFramesRecord == true) {
      this.sceneService.stopRecording().subscribe(data => {
        if (data) {
          this.snackbar.open('Recording Multiple Frames done......', 'Close', {
            duration: 3000
          });
          this.reloadComponent();
        }
      });
      this.startedMultipleFramesRecord = false;
    } else if (!this.recordMultipleFrames && !this.recordSingleFrame && !this.playMode) {
      this.snackbar.open('Please choose a mode...', 'Close', {
        duration: 3000
      });
    } else if (this.recordSingleFrame) {
      this.snackbar.open('Recording Single Frame...', 'Close', {
        duration: 3000
      });
      this.sceneService.recordSingleFrame(this.sceneId).subscribe(data => {
        if (data) {
          this.snackbar.open('Recording Single Frame done', 'Close', {
            duration: 3000
          });
          this.reloadComponent();
        } else {
          this.snackbar.open('Could not record Single Frame.', 'Close', {
            duration: 3000
          });
        }
      });

    } else if (this.recordMultipleFrames && this.startedMultipleFramesRecord == false) {
      this.startedMultipleFramesRecord = true;
      this.sceneService.recordMultipleFrames(this.sceneId).subscribe(data => {
        if (data) {
          this.snackbar.open('Recording Multiple Frames...', 'Close', {
            duration: 3000
          });
        } else {
          this.snackbar.open('Could not record Multiple Frames.', 'Close', {
            duration: 3000
          });
        }
      });

    }

    if (this.playMode && this.scenePlaying == false) {
      if (this.playable == 'green') {
        this.scenePlaying = true;
        this.snackbar.open('Playing Scene...', 'Close', {
          duration: 3000
        });
        this.sceneService.playFromButton(this.sceneId).subscribe(donePlaying => {
          if (donePlaying) {
            this.snackbar.open('Done playing Scene...', 'Close', {
              duration: 3000
            });
            this.scenePlaying = false;
          }
        });

      } else {
        this.snackbar.open('This button does not have a Scene...', 'Close', {
          duration: 3000
        });
      }

    } else if (this.playMode && this.scenePlaying == true && this.pausedPlaying == false) {
      this.pausedPlaying = true;
      this.sceneService.pause(true);
      this.snackbar.open('Paused playing Scene...', 'Close', {
        duration: 3000
      });
    } else if (this.playMode && this.scenePlaying == true && this.pausedPlaying == true) {
      console.log("Resume scene");
      console.log("Scene playing:" + this.scenePlaying);
      console.log("Paused Playing: " + this.pausedPlaying);
      this.pausedPlaying = false;
      this.sceneService.pause(false);
      this.snackbar.open('Resumed playing Scene...', 'Close', {
        duration: 3000
      });
    }
  }

}
