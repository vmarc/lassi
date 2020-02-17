import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RecordService {

  private url: string;

  constructor(private http: HttpClient) {
    this.url = "http://localhost:8080/api/recordscenebis";
  }

  record() {
    console.log("recording...");
    return this.http.post('/api/recordscenebis', null);

  }


}
