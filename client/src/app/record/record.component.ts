import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-record',
  template: `

  <button mat-flat-button color="warn">Record</button>
  <button mat-flat-button color="primary">Stop</button>

  `,
  styles: []
})
export class RecordComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
