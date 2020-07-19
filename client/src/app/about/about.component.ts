import {Component} from '@angular/core';

@Component({
  selector: 'app-about',
  template: `
    <h1>About</h1>
    <div class="container">
      <div class="center">
        <p>
          This application was developed by Matthias and Kenneth on behalf of Marc.
        </p>
        <p>
          It lets you record lighting scenes on the network and save them to the connected Raspberry Pi for later use.
        </p>
        <p>
          The Monitor component shows the live DMX data that is currently going over the network. From here you can quickly record one or multiple frames.
        </p>
        <p>
          The Simulator component simulates the connected Raspberry Pi, so you can quickly play or record a scene as well as see which buttons already contain a scene.
        </p>
        <p>
          You can also view the recorded scenes in the List of Scenes component. Here you can view, play, edit, delete and download a scene saved on the Raspberry Pi.
        </p>
        <p>
          The Settings component lets you choose the frames per second in which a scene gets played, the default fade time for newly created scenes, <br> as well as the
          number of button pages you want to use on the Raspberry Pi.
        </p>
      </div>
    </div>
  `,
  styles: [`
    .container {
      text-align: center
    }

    .center {
      display: inline-block;
    }

    h1 {
      text-align: center;
      padding-bottom: 20px;
    }
  `]
})
export class AboutComponent {
}
