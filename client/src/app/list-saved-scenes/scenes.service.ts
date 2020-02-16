import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Scenes } from '../scene/scenes';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScenesService {

  private scenesUrl: string;

  constructor(private http: HttpClient) {
    this.scenesUrl = 'http://localhost:8080/api/sceneslist';
  }

  public findAll(): Observable<Scenes[]> {
    return this.http.get<Scenes[]>(this.scenesUrl);
  }
}
