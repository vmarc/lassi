import {OnInit} from '@angular/core';
import {Component} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {ActivatedRoute} from '@angular/router';
import {first} from 'rxjs/operators';
import {Scene} from '../domain/scene';
import {SceneService} from './scene.service';

@Component({
  selector: 'app-scene-edit',
  template: `
    <h1>Scene</h1>
    <form [formGroup]="editForm">
      <div mat-dialog-content>
        <div>
          <mat-form-field>
            <h4>Name:</h4>
            <input matInput [formControl]="name">
          </mat-form-field>
        </div>

        <div>
          <mat-form-field>
            <h4>Button:</h4>
            <mat-select [formControl]="buttonId">
              <mat-option *ngFor="let option of buttonOptions" [value]="option">{{option}}</mat-option>
            </mat-select>
          </mat-form-field>
        </div>

        <div>
          <mat-form-field>
            <h4>Fade Time:</h4>
            <mat-select [formControl]="fadeTime">
              <mat-option *ngFor="let option of fadeTimeOptions" [value]="option">{{option}}</mat-option>
            </mat-select>
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
export class SceneEditComponent implements OnInit {

  readonly name = new FormControl();
  readonly buttonId = new FormControl();
  readonly fadeTime = new FormControl();

  readonly editForm: FormGroup = new FormGroup({
    name: this.name,
    buttonId: this.buttonId,
    fadeTime: this.fadeTime
  });

  readonly buttonOptions = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27];
  readonly fadeTimeOptions = [5, 10, 15, 20, 30, 60];

  scene: Scene;

  constructor(private sceneService: SceneService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.params.pipe(first()).subscribe(params => {
      const id = params['sceneId'];
      this.sceneService.get(id).subscribe(scene => {
        this.scene = scene;
        this.editForm.setValue({
          name: scene.name,
          buttonId: scene.buttonId,
          fadeTime: scene.fadeTime,
        });
      });
    });
  }

  onNoClick(): void {
    this.router.navigate(['/scenes']);
  }

  save(): void {
    const now = new Date();
    now.setHours(now.getHours() + 2);
    const scene = new Scene(
      this.scene.id,
      this.name.value,
      this.scene.duration,
      this.buttonId.value,
      this.fadeTime.value,
      now,
      this.scene.frames
    );
    this.sceneService.save(scene).subscribe(() => {
      this.router.navigate(['/scenes']);
    });
  }
}
