import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reply } from '../scene/reply';

@Injectable({
  providedIn: 'root'
})
export class RecordService {

  constructor(private http: HttpClient) {
  }

  record() : void {
    console.log("recording...");
    this.http.get('/api/recordscenebis/' + 0).subscribe();

  }


}
