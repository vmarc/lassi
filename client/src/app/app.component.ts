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
  `
})
export class AppComponent {
}
