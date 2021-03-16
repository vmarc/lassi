import {Component} from '@angular/core';

@Component({
  selector: 'app-menu',
  template: `
    <div class='buttons'>
      <button mat-stroked-button routerLink="scenes">Scenes</button>
      <button mat-stroked-button routerLink="simulator">Simulator</button>
      <button mat-stroked-button routerLink="monitor">Monitor</button>
      <button mat-stroked-button routerLink="settings">Settings</button>
      <button mat-stroked-button routerLink="about">About</button>
    </div>
  `,
  styles: [`
    .buttons {
      margin-top: 1em;
      display: flex;
      flex-direction: column;
    }

    .buttons button {
      margin: 1em;
      width: 20em;
    }
  `]
})
export class MenuComponent {
}
