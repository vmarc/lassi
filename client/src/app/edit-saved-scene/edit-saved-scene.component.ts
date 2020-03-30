import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Scenes } from '../scene/scenes';
import { ScenesService } from '../list-saved-scenes/scenes.service';
import { Frame } from '../scene/frame';
import { Builder } from 'protractor';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-edit-saved-scene',
  template: `

 <h1>Edit Scene</h1>
 {{scene.id}}
<div class="container" *ngIf="!post; else forminfo" novalidate>
  <form [formGroup]="editForm" (ngSubmit)="onSubmit(formGroup.value)" class="form">

    <mat-form-field class="form-element">
      <label>Name</label>
      <input matInput formControlName="name">
    </mat-form-field>

    <mat-form-field class="form-element">
      <label>Button ID</label>
      <input matInput formControlName="buttonId">
    </mat-form-field>

    <mat-form-field class="form-element">
      <label>Frames</label>
      <textarea matInput matTextareaAutosize matAutosizeMinRows="2" matAutosizeMaxRows="5" formControlName="frames"></textarea>

    </mat-form-field>


    <div class="form-element">
            <button mat-raised-button color="primary" type="submit" class="button" [disabled]="!formGroup.valid">Save</button>
            <button mat-raised-button color="primary" class="button" (click)="goBack()">Go Back</button>
    </div>


  </form>
</div>

  `,
  styles: ['./edit-saved-scene.component.css']
})
export class EditSavedSceneComponent implements OnInit {

  editForm: FormGroup;

  scene: Scenes;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private scenesService: ScenesService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.scenesService.get(params.get('id')).subscribe(data => this.scene = data);
    })

    this.editForm = new FormGroup({
      name: new FormControl(),
      buttonId: new FormControl()
    })

    this.editForm.setValue({
      name: this.scene.name,
      buttonId: this.scene.buttonId
    });

  }

  goBack() {
    this.router.navigate(['sceneslist']);
  }

}
