import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {RxStompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import {Frame} from '../scene/frame';
import { ScenesService } from '../list-saved-scenes/scenes.service';

@Component({
  selector: 'app-monitor',
  template: `
<h1>Monitor</h1>
<div class="dmx-levels">
  <div *ngFor="let dmxValue of frame.dmxValues" class="dmx-level">
    {{dmxValue}}
  </div>
</div>
<app-record></app-record>
`,
  styleUrls: ['./monitor.component.css']
})
export class MonitorComponent implements OnInit, OnDestroy {

  frame: Frame = Frame.empty();
  private topicSubscription: Subscription;

  constructor(private rxStompService: RxStompService, private sceneService: ScenesService) {
  }

  ngOnInit() {
    this.topicSubscription = this.rxStompService.watch('/topic/output').subscribe((message: Message) => {
      this.frame = Frame.fromJSON(JSON.parse(message.body));
    })
    //this.sceneService.getLiveData().subscribe(data => this.frame = data);
  }

  ngOnDestroy() {
    this.topicSubscription.unsubscribe();
  }

}
