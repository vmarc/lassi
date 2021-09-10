import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Scene} from '../domain/scene';

@Injectable()
export class SceneService {

  buttons: Observable<boolean[]>;

  constructor(private http: HttpClient) {
    this.buttons = this.getButtons();
  }

  scenes(): Observable<Scene[]> {
    return this.http.get<Scene[]>('/api/scenes').pipe(catchError(this.handleError));
  }

  get(sceneId: string): Observable<Scene> {
    return this.http.get<Scene>(`/api/scenes/${sceneId}`).pipe(catchError(this.handleError));
  }

  delete(sceneId: string): void {
    this.http.delete(`/api/scenes/${sceneId}`).pipe(catchError(this.handleError)).subscribe();
  }

  play(id: string): Observable<boolean> {
    return this.http.get<boolean>('/api/playscenefromid/' + id).pipe(catchError(this.handleError));
  }

  pause(bool: boolean): void {
    this.http.get('/api/pause/' + bool).pipe(catchError(this.handleError)).subscribe();
  }

  playFromButton(button: number): Observable<boolean> {
    return this.http.get<boolean>('/api/playscene/' + button).pipe(catchError(this.handleError));
  }

  recordSingleFrame(button: number): Observable<boolean> {
    return this.http.get<boolean>(`/api/record-scene/${button}`).pipe(catchError(this.handleError));
  }

  recordMultipleFrames(button: number): Observable<boolean> {
    return this.http.get<boolean>('/api/recordSceneMultipleFrames/' + button).pipe(catchError(this.handleError));
  }

  stopPlaying(): void {
    this.http.get('/api/stop').pipe(catchError(this.handleError)).subscribe();
  }

  stopRecording(): Observable<boolean> {
    return this.http.get<boolean>('/api/stopRecording').pipe(catchError(this.handleError));
  }

  save(scene: Scene): Observable<Scene> {
    return this.http.put<Scene>('/api/savescene/', scene).pipe(catchError(this.handleError));
  }

  getButtons(): Observable<boolean[]> {
    return this.http.get<boolean[]>('api/getbuttons').pipe(catchError(this.handleError));
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
