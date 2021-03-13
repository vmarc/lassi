import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {SettingsService} from './settings.service';
import {Settings} from './settings';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatDialog} from '@angular/material/dialog';

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
              <mat-select formControlName="framesPerSecond" (change)="changeFps($event)" [value]="fps">
                <mat-option *ngFor="let frame of framesPSec" [value]="frame">{{frame}}</mat-option>
              </mat-select>
            </mat-form-field>
          </div>
          <div>
            <mat-form-field>
              <p>Fade time in seconds for newly recorded scenes</p>
              <mat-select formControlName="fadeTimeInSeconds" (change)="changeFade($event)" [value]="fadeSec">
                <mat-option *ngFor="let fade of fadeTimeInSec" [value]="fade">{{fade}}</mat-option>
              </mat-select>
            </mat-form-field>
          </div>
          <div>
            <mat-form-field>
              <p>Number of button pages</p>
              <mat-select formControlName="buttonPages" (change)="changePages($event)" [value]="pages">
                <mat-option *ngFor="let page of buttonsPages" [value]="page">{{page}}</mat-option>
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

  settingsForm: FormGroup = new FormGroup({
    framesPerSecond: new FormControl(),
    fadeTimeInSeconds: new FormControl(),
    buttonPages: new FormControl()
  });

  settings: Settings;
  newSettings: Settings
  fps: number;
  fadeSec: number;
  pages: number;
  framesPSec = [5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 100, 150, 200, 400, 800];
  fadeTimeInSec = [1, 5, 10, 20, 30];
  buttonsPages = [1, 2, 3];

  constructor(private settingsService: SettingsService,
              private dialog: MatDialog,
              private snackbar: MatSnackBar) {
  }

  get framesPerSecond() {
    return this.settingsForm.get('framesPerSecond');
  }

  get fadeTimeInSeconds() {
    return this.settingsForm.get('fadeTimeInSeconds');
  }

  get buttonPages() {
    return this.settingsForm.get('buttonPages');
  }

  ngOnInit(): void {
    this.settingsService.getSettings().subscribe(data => {
      this.settings = data;
      this.fps = this.settings.framesPerSecond;
      this.fadeSec = this.settings.fadeTimeInSeconds;
      this.pages = this.settings.buttonPages;
      this.settingsForm.setValue({
        framesPerSecond: this.settings.framesPerSecond,
        fadeTimeInSeconds: this.settings.fadeTimeInSeconds,
        buttonPages: this.settings.buttonPages
      });
    });
  }

  changeFps($event) {
    this.framesPerSecond.setValue(this.framesPSec[$event]);
  }

  changeFade($event) {
    this.fadeTimeInSeconds.setValue(this.fadeTimeInSec[$event]);
  }

  changePages($event) {
    this.buttonPages.setValue(this.buttonsPages[$event]);
  }

  save() {

    if (this.settings == null) {
      this.newSettings = new Settings(this.settingsForm.get('framesPerSecond').value, this.settingsForm.get('fadeTimeInSeconds').value, this.settingsForm.get('buttonPages').value);
      this.settingsService.saveSettings(this.newSettings);
    } else {
      this.settings.framesPerSecond = this.settingsForm.get('framesPerSecond').value;
      this.settings.fadeTimeInSeconds = this.settingsForm.get('fadeTimeInSeconds').value;
      this.settings.buttonPages = this.settingsForm.get('buttonPages').value;
      this.settingsService.saveSettings(this.settings);
    }

    this.snackbar.open('Settings saved', 'Close', {
      duration: 3000
    });
  }
}
