import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { SettingsService } from './settings.service';
import { Settings } from './settings';

@Component({
  selector: 'app-settings',
  template: `
<h1>Settings</h1>
<form [formGroup]="settingsForm">
<div class="center">
<div>
<mat-form-field>
  <p>Frames per second</p>
  <mat-select  formControlName="framesPerSecond" (change)="changeFps($event)" [value]="fps">
    <mat-option *ngFor="let frame of framesPSec" [value]="frame" >{{frame}}</mat-option>
  </mat-select>
</mat-form-field>
</div>
<div>
<mat-form-field>
  <p>Fade time in seconds for newly recorded scenes</p>
  <mat-select  formControlName="fadeTimeInSeconds" (change)="changeFade($event)" [value]="fadeSec">
    <mat-option *ngFor="let fade of fadeTimeInSec" [value]="fade" >{{fade}}</mat-option>
  </mat-select>
</mat-form-field>
</div>
<button mat-button (click)="save()"><i class="fas fa-save"></i> Save</button>
</div>
</form>
  `,
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  settingsForm: FormGroup = new FormGroup({
    framesPerSecond: new FormControl(),
    fadeTimeInSeconds: new FormControl()
  })

  settings: Settings;
  newSettings: Settings
  fps: any;
  fadeSec: any;
  framesPSec: any[] = [10, 15, 50, 100, 150, 200, 400, 800];
  fadeTimeInSec: any[] = [1, 5, 10, 20, 30];

  constructor(private settingsService: SettingsService) { }

  ngOnInit(): void {
    this.settingsService.getSettings().subscribe(data => {
      this.settings = data;
      this.fps = this.settings.framesPerSecond;
      this.fadeSec = this.settings.fadeTimeInSeconds;
      this.settingsForm.setValue({
        framesPerSecond: this.settings.framesPerSecond,
        fadeTimeInSeconds: this.settings.fadeTimeInSeconds
      });


    });

  }

  get framesPerSecond() {
    return this.settingsForm.get('framesPerSecond');
  }

  get fadeTimeInSeconds() {
    return this.settingsForm.get('fadeTimeInSeconds');
  }

  changeFps($event) {
    this.framesPerSecond.setValue(this.framesPSec[$event]);
  }

  changeFade($event) {
    this.fadeTimeInSeconds.setValue(this.fadeTimeInSec[$event]);
  }

  save() {

    if (this.settings == null) {
       this.newSettings = new Settings(this.settingsForm.get('framesPerSecond').value, this.settingsForm.get('fadeTimeInSeconds').value );
       this.settingsService.saveSettings(this.newSettings);
    } else {
      this.settings.framesPerSecond = this.settingsForm.get('framesPerSecond').value;
      this.settings.fadeTimeInSeconds = this.settingsForm.get('fadeTimeInSeconds').value;
      this.settingsService.saveSettings(this.settings);
    }


  }

}
