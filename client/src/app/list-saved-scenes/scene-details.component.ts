import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import * as moment from 'moment';
import {first} from 'rxjs/operators';
import {Scene} from '../scene/scene';
import {ScenesService} from '../scene/scenes.service';

@Component({
  selector: 'app-scene-edit',
  template: `
    <h1>Scene</h1>
    <div>
      <h4>ID:</h4>
      <p>{{scene?.id}}</p>
      <h4>Name:</h4>
      <p>{{scene?.name}}</p>
      <h4>Duration:</h4>
      <p>{{scene?.duration}}</p>
      <h4>Button:</h4>
      <p>{{scene?.buttonId}}</p>
      <h4>Fade Time:</h4>
      <p>{{scene?.fadeTime}}</p>
      <h4>Created/Edited On:</h4>
      <p>{{date}}</p>
      <h4>Frames:</h4>
      <ul>
        <li *ngFor="let frame of scene?.frames">
          Universe: {{frame.universe}}<br> Created On: {{frame.createdOn | date:'d/LL/yyyy, HH:mm'}} <br> {{frame.dmxValues}}
        </li>
      </ul>
    </div>
  `
})
export class SceneDetailsComponent implements OnInit {

  scene: Scene;
  date: string;

  constructor(private sceneService: ScenesService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    moment.locale('nl-be');
    this.activatedRoute.params.pipe(first()).subscribe(params => {
      const id = params['sceneId'];
      this.sceneService.get(id).subscribe(x => {
        this.scene = x;
        this.date = moment(this.scene.createdOn).format('LLLL');
      });
    });
  }

}
