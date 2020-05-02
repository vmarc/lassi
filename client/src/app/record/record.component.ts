import { Component, OnInit } from '@angular/core';
import { RecordService } from './record.service';

@Component({
  selector: 'app-record',
  template: `

  <button mat-flat-button color="warn" (click)="record()">Record</button>
  <button mat-flat-button color="primary" (click)="stop()">Stop</button>

  `,
  styles: []
})
export class RecordComponent implements OnInit {

  constructor(private recordService: RecordService) { }

  ngOnInit(): void {
  }

  record() {
    this.recordService.record();
  }

  stop() {

  }

}
