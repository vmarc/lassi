import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Scenes } from '../scene/scenes';
import { Observable } from 'rxjs';
import { Reply } from '../scene/reply';
import {map} from 'rxjs/operators';
import {Sceneslist} from './sceneslist';

@Injectable()
export class ScenesService {

  constructor(private http: HttpClient) {
  }

  /*public findAll(): Observable<Array<Scenes>> {
    return this.http.get('/api/sceneslist').pipe(
      map(response => {

        return response.sceneslist.map(scenes => Sceneslist.fromJSON(scenes));
      })
    );
  }*/

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

  public save(scene: Scenes) {
    return this.http.put<Scenes>('/api/savescene/', scene).subscribe();
  }

}
