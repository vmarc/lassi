import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Scenes } from '../scene/scenes';
import { Observable } from 'rxjs';
import { Reply } from '../scene/reply';
import {map} from 'rxjs/operators';
import {Sceneslist} from './sceneslist';

@Injectable()
export class ScenesService {


  private scenesUrl: string;
  private deleteUrl: string;
  private getUrl: string;

  constructor(private http: HttpClient) {
    this.scenesUrl = "http://localhost:8080/api/sceneslist";
    this.deleteUrl = "http://localhost:8080/api/deletescene/";
    this.getUrl = "http://localhost:8080/api/getscene/";
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

  public play(scene_id: string): void {
    this.http.get('/api/playscene/' + scene_id);
  }

}
