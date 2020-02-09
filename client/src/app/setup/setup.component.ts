import {Component, OnInit} from '@angular/core';
import {SceneService} from '../scene/scene.service';
import {Scene} from '../scene/scene';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {Setup} from './setup';

@Component({
  selector: 'app-setup',
  template: `
    <div class="title">Setup</div>
    <div class="form-example">
      <form [formGroup]="form">

        <mat-form-field>
          <mat-label>Scene</mat-label>
          <mat-select formControlName="scene">
            <mat-option *ngFor="let scene of scenes" [value]="scene.name">
              {{scene.name}}
            </mat-option>
          </mat-select>
        </mat-form-field>

        <div class="group">
          <mat-checkbox formControlName="option1">Option 1</mat-checkbox>
          <mat-checkbox formControlName="option2">Option 2</mat-checkbox>
        </div>

        <div class="group">
          <mat-radio-group formControlName="selection">
            <mat-radio-button value="one">Option one</mat-radio-button>
            <mat-radio-button value="two">Option two</mat-radio-button>
            <mat-radio-button value="three">Option three</mat-radio-button>
          </mat-radio-group>
        </div>

        <div class="group">
          <mat-slider formControlName="level1"></mat-slider>
          <mat-slider formControlName="level2"></mat-slider>
        </div>

        <button mat-raised-button color="primary" (click)="submit()">Submit</button>
      </form>
    </div>
  `,
  styles: [`

    .form-example {
      margin-left: 1em;
      margin-right: 1em;
    }

    .group {
      margin-top: 1em;
      margin-bottom: 1em;
    }

    .group mat-checkbox {
      display: block;
      margin-top: .5em;
      margin-bottom: .5em;
    }

    .group mat-radio-button {
      display: block;
      margin-top: .5em;
      margin-bottom: .5em;
    }

    .group mat-slider {
      display: block;
      margin-top: .5em;
      margin-bottom: .5em;
    }

  `]
})
export class SetupComponent implements OnInit {

  scenes: Array<Scene> = [];

  readonly form: FormGroup;
  readonly scene = new FormControl();
  readonly option1 = new FormControl(false);
  readonly option2 = new FormControl(false);
  readonly selection = new FormControl('none');
  readonly level1 = new FormControl();
  readonly level2 = new FormControl();

  constructor(private sceneService: SceneService, private fb: FormBuilder) {
    this.form = this.buildForm();
  }

  ngOnInit() {
    this.sceneService.scenes().subscribe(scenes => this.scenes = scenes);
  }

  submit() {
    const setup = new Setup(
      this.scene.value,
      this.option1.value,
      this.option2.value,
      this.selection.value,
      this.level1.value,
      this.level2.value
    );
    console.log('SEND ' + JSON.stringify(setup));
    this.sceneService.setup(setup);
  }

  private buildForm(): FormGroup {
    return this.fb.group(
      {
        scene: this.scene,
        option1: this.option1,
        option2: this.option2,
        selection: this.selection,
        level1: this.level1,
        level2: this.level2
      });
  }

}
