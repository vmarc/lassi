import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Settings } from './settings';

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  constructor(private http: HttpClient) { }

  public getSettings() : Observable<Settings> {
    return this.http.get<Settings>('/api/getsettings');
  }

  public saveSettings(settings: Settings) {
    this.http.put<Settings>('/api/savesettings/', settings).subscribe();
  }
}
