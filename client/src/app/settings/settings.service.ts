import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {map} from 'rxjs/operators';
import {catchError, retry} from 'rxjs/operators';
import {Settings} from './settings';

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  constructor(private http: HttpClient) {
  }

  public getSettings(): Observable<Settings> {
    return this.http.get<Settings>('/scala-api/settings').pipe(
      catchError(this.handleError)
    );
  }

  public saveSettings(settings: Settings) {
    this.http.put<Settings>('/scala-api/settings', settings).pipe(
      catchError(this.handleError)
    ).subscribe();
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
