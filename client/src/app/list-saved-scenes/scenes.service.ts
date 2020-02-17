import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Scenes } from '../scene/scenes';
import { Observable } from 'rxjs';
import { Reply } from '../scene/reply';
import {map} from 'rxjs/operators';
import {Sceneslist} from './sceneslist'

@Injectable()
export class ScenesService {

  private scenesUrl: string;

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Array<Scenes>> {
    return this.http.get('/api/sceneslist').pipe(
      map(response => {
        // @ts-ignore
        return response.sceneslist.map(scenes => Sceneslist.fromJSON(scenes));
      })
    );
  }

  /*
  public findAll(): Observable<Scenes[]> {
    return this.http.get<Scenes[]>(this.scenesUrl);
  }*/
}
