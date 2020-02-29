import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reply } from '../scene/reply';

@Injectable({
  providedIn: 'root'
})
export class RecordService {

  private url: string;

  constructor(private http: HttpClient) {
    this.url = "http://localhost:8080/api/recordscenebis";
  }

  record() : void {
    console.log("recording...");
    this.http.post('/api/recordscenebis', "test").subscribe();

  }


}
