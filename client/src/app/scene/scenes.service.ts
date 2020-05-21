import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Scenes } from './scenes';
import { Observable, throwError, Subject, BehaviorSubject } from 'rxjs';
import {map, retry, catchError} from 'rxjs/operators';
import { Frame } from './frame';

@Injectable()
export class ScenesService {

  buttons: Observable<boolean[]>;

  constructor(private http: HttpClient) {
    this.buttons = this.getButtons();
  }


  public findAll(): Observable<Scenes[]> {
    return this.http.get<Scenes[]>('/api/sceneslist').pipe(retry(1), catchError(this.handleError));
  }

  public delete(scene_id: string): void {
    this.http.get('/api/deletescene/' + scene_id).pipe(retry(1), catchError(this.handleError)).subscribe();
  }

  public get(scene_id: string): Observable<Scenes> {
    return this.http.get<Scenes>('/api/getscene/' + scene_id).pipe(retry(1), catchError(this.handleError));
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

  public playFromButton(button: number): void {
    this.http.get('/api/playscene/' + button).pipe(retry(1), catchError(this.handleError)).subscribe();
  }


  public recordSingleFrame(button: number) : Observable<boolean> {
    return this.http.get<boolean>('/api/recordSceneSingleFrame/' + button).pipe(retry(1), catchError(this.handleError));

  }

  public recordMultipleFrames(button: number) : Observable<boolean> {
    return this.http.get<boolean>('/api/recordSceneMultipleFrames/' + button).pipe(retry(1), catchError(this.handleError));

  }

  public stopPlaying(): void {
    this.http.get('/api/stop').pipe(retry(1), catchError(this.handleError));
  }

  public stopRecording(): Observable<boolean> {
    return this.http.get<boolean>('/api/stopRecording').pipe(retry(1), catchError(this.handleError));
  }

  public save(scene: Scenes) {
    return this.http.put<Scenes>('/api/savescene/', scene).pipe(retry(1), catchError(this.handleError)).subscribe();
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
