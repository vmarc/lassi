import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import { ScenesService } from '../list-saved-scenes/scenes.service';

@Component({
  selector: 'app-simulator-led',
  template: `
    <div class="led {{color}}">
    </div>
  `,
  styles: [`
    .led {
      color: white;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      border-radius: 50%;
      height: 15px;
      width: 15px;
    }

    .red {
      background-color: rgb(239, 83, 80);
    }

    .green {
      background-color: rgb(102, 187, 106);
    }

    .blue {
      background-color: rgb(33, 150, 243);
    }

    .orange {
      background-color: rgb(255, 163, 0);
    }

    .gray {
      background-color: rgb(224, 224, 224);
    }

  `]
})
export class SimulatorLedComponent implements OnInit, OnDestroy {

  @Input() sceneId: number;
  buttons: boolean[];
  color = 'gray';
  private subscription: Subscription;

  constructor(private sceneService: ScenesService) {
  }


  ngOnInit() {
    this.sceneService.buttons.subscribe(data => console.log(data));
      var bool = this.buttons[this.sceneId - 1];
      if (bool) {
        this.color = 'red';
      }

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
