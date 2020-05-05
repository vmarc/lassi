import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Scenes } from '../scene/scenes';
import { Observable } from 'rxjs';
import { Reply } from '../scene/reply';
import {map} from 'rxjs/operators';
import {Sceneslist} from './sceneslist';
import { Frame } from '../scene/frame';

@Injectable()
export class ScenesService {

  buttons: Observable<boolean[]>;

  constructor(private http: HttpClient) {
    this.buttons = this.getButtons();
  }

  public findAll(): Observable<Scenes[]> {
    return this.http.get<Scenes[]>('/api/sceneslist');
  }

  public delete(scene_id: string): void {
    this.http.get('/api/deletescene/' + scene_id).subscribe();
  }

  public get(scene_id: string): Observable<Scenes> {
    return this.http.get<Scenes>('/api/getscene/' + scene_id);
  }

  public play(buttonId: number): void {
    this.http.get('/api/playscene/' + buttonId).subscribe();
  }

  public record(button: number) : void {
    console.log("recording...");
    this.http.get('/api/recordscenebis/' + button).subscribe();

  }

  public save(scene: Scenes) {
    return this.http.put<Scenes>('/api/savescene/', scene).subscribe();
  }

  public getButtons(): Observable<boolean[]> {
    return this.http.get<boolean[]>('api/getbuttons');

  }

  public getLiveData(): Observable<Frame> {
    return this.http.get<Frame>('api/livedata');
  }

  public recordingDone(): Observable<boolean> {
    return this.http.get<boolean>('api/donerecording');
  }

}
