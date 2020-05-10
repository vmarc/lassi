import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {map, retry, catchError} from 'rxjs/operators';
import { Settings } from './settings';

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  constructor(private http: HttpClient) { }

  public getSettings() : Observable<Settings> {
    return this.http.get<Settings>('/api/getsettings').pipe(retry(1), catchError(this.handleError));
  }

  public saveSettings(settings: Settings) {
    this.http.put<Settings>('/api/savesettings/', settings).pipe(retry(1), catchError(this.handleError)).subscribe();
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
