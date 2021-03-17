import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';
import {Scene} from '../domain/scene';

@Injectable()
export class ScenesService {

  buttons: Observable<boolean[]>;

  constructor(private http: HttpClient) {
    this.buttons = this.getButtons();
  }

  public findAll(): Observable<Scene[]> {
    return this.http.get<Scene[]>('/api/sceneslist').pipe(retry(1), catchError(this.handleError));
  }

  public delete(scene_id: string): void {
    this.http.get('/api/deletescene/' + scene_id).pipe(retry(1), catchError(this.handleError)).subscribe();
  }

  public get(scene_id: string): Observable<Scene> {
    return this.http.get<Scene>('/api/getscene/' + scene_id).pipe(retry(1), catchError(this.handleError));
  }

  public download(scene_id: string): Observable<Blob> {
    return this.http.get('/api/downloadscene/' + scene_id, {responseType: 'blob'}).pipe(retry(1), catchError(this.handleError));
  }

  public play(id: string): Observable<boolean> {
    return this.http.get<boolean>('/api/playscenefromid/' + id).pipe(retry(1), catchError(this.handleError));
  }

  public pause(bool: boolean) : void {
    this.http.get('/api/pause/' + bool).pipe(retry(1), catchError(this.handleError)).subscribe();
  }

  public playFromButton(button: number): Observable<boolean> {
    return this.http.get<boolean>('/api/playscene/' + button).pipe(retry(1), catchError(this.handleError));
  }

  public recordSingleFrame(button: number) : Observable<boolean> {
    return this.http.get<boolean>('/scala-api/scenes/record/' + button).pipe(retry(1), catchError(this.handleError));
  }

  public recordMultipleFrames(button: number) : Observable<boolean> {
    return this.http.get<boolean>('/api/recordSceneMultipleFrames/' + button).pipe(retry(1), catchError(this.handleError));
  }

  public stopPlaying(): void {
    this.http.get('/api/stop').pipe(retry(1), catchError(this.handleError)).subscribe();
  }

  public stopRecording(): Observable<boolean> {
    return this.http.get<boolean>('/api/stopRecording').pipe(retry(1), catchError(this.handleError));
  }

  public save(scene: Scene): Observable<Scene> {
    return this.http.put<Scene>('/api/savescene/', scene).pipe(retry(1), catchError(this.handleError));
  }

  public getButtons(): Observable<boolean[]> {
    return this.http.get<boolean[]>('api/getbuttons').pipe(retry(1), catchError(this.handleError));
  }

  handleError(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}\n\nMake sure the Angular front-end is running!`;
    } else {
      // server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}\n\nMake sure the Spring back-end and/or the Raspberry Pi is running!`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }

}
