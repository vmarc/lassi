import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Frame} from './frame';
import {map} from 'rxjs/operators';
import {Reply} from './reply';
import {Setup} from '../setup/setup';

@Injectable()
export class SceneService {

  constructor(private http: HttpClient) {
  }

  public recordScene(sceneId: number): Observable<Reply> {
    const url = '/api/record/' + sceneId;
    return this.http.get(url).pipe(
      map(response => {
        return Reply.fromJSON(response);
      })
    );
  }

  public gotoScene(sceneId: number): Observable<Reply> {
    const url = '/api/goto/' + sceneId;
    return this.http.get(url).pipe(
      map(response => {
        return Reply.fromJSON(response);
      })
    );
  }

  public scenes(): Observable<Array<Frame>> {
    return this.http.get('/api/scenes').pipe(
      map(response => {
        // @ts-ignore
        return response.scenes.map(scene => Frame.fromJSON(scene));
      })
    );
  }

  public setup(setup: Setup): void {
    this.http.post('/api/setup', setup).subscribe();
  }

}
