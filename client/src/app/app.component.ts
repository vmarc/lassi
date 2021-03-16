import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <header>
      <mat-toolbar>
        <button mat-button routerLink="/"><h1>Lighting controller</h1></button>
        <span class="toolbar-spacer"></span>
      </mat-toolbar>
    </header>
    <main>
      <router-outlet></router-outlet>
    </main>
  `,
  styles: [`
    main {
      padding-left: 2em;
      padding-right: 1em;
    }
  `]
})
export class AppComponent {
}
