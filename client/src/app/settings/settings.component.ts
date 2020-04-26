import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-settings',
  template: `
    <p>
      settings works!
    </p>
  `,
  styles: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
