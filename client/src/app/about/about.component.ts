import {Component} from '@angular/core';

@Component({
  selector: 'app-about',
  template: `
    <div class="title">
      About
    </div>
    <div class="about">
      <p>
        bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla
      </p>
      <p>
        bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla
      </p>
      <p>
        bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla
      </p>
      <p>
        bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla
      </p>
      <p>
        bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla
      </p>
    </div>
  `,
  styles: [`
    .about {
      margin-left: 1em;
      margin-right: 1em;
    }
  `]
})
export class AboutComponent {
}
