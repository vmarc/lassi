import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {RxStompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import {Scene} from '../scene/scene';

@Component({
  selector: 'app-monitor',
  template: `
    <div class="title">
      Monitor
    </div>
    <div class="dmx-levels">
      <div *ngFor="let dmxValue of scene.dmxValues" class="dmx-level">
        {{dmxValue}}
      </div>
    </div>
  `,
  styles: [`

    .dmx-levels {
      display: flex;
      flex-wrap: wrap;
      margin-left: 1em;
      margin-right: 1em;
      border-top: 1px solid lightgray;
      border-left: 1px solid lightgray;
    }

    .dmx-level {
      display: inline-block;
      width: 2.2em;
      height: 1.8em;
      line-height: 1.8em;
      border-right: 1px solid lightgray;
      border-bottom: 1px solid lightgray;
      text-align: center;
    }
  `]
})
export class MonitorComponent implements OnInit, OnDestroy {

  scene: Scene = Scene.empty();
  private topicSubscription: Subscription;

  constructor(private rxStompService: RxStompService) {
  }

  ngOnInit() {
    this.topicSubscription = this.rxStompService.watch('/topic/output').subscribe((message: Message) => {
      this.scene = Scene.fromJSON(JSON.parse(message.body));
    });
  }

  ngOnDestroy() {
    this.topicSubscription.unsubscribe();
  }

}
