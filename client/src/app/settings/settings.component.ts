import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Settings} from './settings';
import {SettingsService} from './settings.service';

@Component({
  selector: 'app-settings',
  template: `
    <h1>Settings</h1>
    <form [formGroup]="settingsForm">
      <div class="container">
        <div class="center">
          <div>
            <mat-form-field>
              <p>Frames per second</p>
              <mat-select [formControl]="framesPerSecond">
                <mat-option *ngFor="let option of framesPerSecondOptions" [value]="option">{{option}}</mat-option>
              </mat-select>
            </mat-form-field>
          </div>
          <div>
            <mat-form-field>
              <p>Fade time in seconds for newly recorded scenes</p>
              <mat-select [formControl]="fadeTimeInSeconds">
                <mat-option *ngFor="let option of fadeTimeInSecondsOptions" [value]="option">{{option}}</mat-option>
              </mat-select>
            </mat-form-field>
          </div>
          <div>
            <mat-form-field>
              <p>Number of button pages</p>
              <mat-select [formControl]="buttonPageCount">
                <mat-option *ngFor="let option of buttonPageCountOptions" [value]="option">{{option}}</mat-option>
              </mat-select>
            </mat-form-field>
          </div>
          <button mat-button (click)="save()"><i class="fas fa-save"></i> Save</button>
        </div>
      </div>
    </form>
  `,
  styles: [`

    h1 {
      text-align: center;
    }

    .container {
      text-align: center
    }

    .center {
      display: block;
    }

  `]
})
export class SettingsComponent implements OnInit {

  readonly framesPerSecond = new FormControl();
  readonly fadeTimeInSeconds = new FormControl();
  readonly buttonPageCount = new FormControl();

  readonly settingsForm = new FormGroup({
    framesPerSecond: this.framesPerSecond,
    fadeTimeInSeconds: this.fadeTimeInSeconds,
    buttonPageCount: this.buttonPageCount
  });

  readonly framesPerSecondOptions = [5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 100, 150, 200, 400, 800];
  readonly fadeTimeInSecondsOptions = [1, 5, 10, 20, 30];
  readonly buttonPageCountOptions = [1, 2, 3];

  constructor(private settingsService: SettingsService,
              private dialog: MatDialog,
              private snackbar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.settingsService.getSettings().subscribe(settings => {
      this.settingsForm.setValue({
        framesPerSecond: settings.framesPerSecond,
        fadeTimeInSeconds: settings.fadeTimeInSeconds,
        buttonPageCount: settings.buttonPageCount
      });
    });
  }

  save() {
    const settings = new Settings(this.framesPerSecond.value, this.fadeTimeInSeconds.value, this.buttonPageCount.value);
    this.settingsService.saveSettings(settings);
    this.snackbar.open('Settings saved', 'Close', {
      duration: 3000
    });
  }
}
