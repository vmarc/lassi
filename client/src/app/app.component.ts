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


<div class="footerWrap">
    <div class="footer">
      <div class="footerContent">
       <small>&copy; Copyright 2019-2020, Matthias Somay & Kenneth Van Den Borne</small>
      </div>
    </div>
</div>
  `
})
export class AppComponent {
}
