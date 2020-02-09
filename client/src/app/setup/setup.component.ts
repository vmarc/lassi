import {Component, OnInit} from '@angular/core';
import {SceneService} from '../scene/scene.service';
import {Scene} from '../scene/scene';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {Setup} from './setup';

@Component({
  selector: 'app-setup',
  templateUrl: './setup.component.html',
  styleUrls: [ './setup.component.css']
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
