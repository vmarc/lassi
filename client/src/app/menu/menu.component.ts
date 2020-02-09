import {Component} from '@angular/core';

@Component({
  selector: 'app-menu',
  template: `
    <div class='buttons'>
      <button mat-stroked-button routerLink="time">Time</button>
      <button mat-stroked-button routerLink="monitor">Monitor</button>
      <button mat-stroked-button routerLink="setup">Setup</button>
      <button mat-stroked-button routerLink="simulator">Simulator</button>
      <button mat-stroked-button routerLink="about">About</button>
    </div>
  `,
  styles: [`
    .buttons {
      margin-top: 1em;
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    .buttons button {
      margin: 1em;
      width: 20em;
    }
  `]
})
export class MenuComponent {
}
