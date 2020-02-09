import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {RxStompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import {Scene} from '../scene/scene';

@Component({
  selector: 'app-monitor',
  templateUrl: './monitor.component.html',
  styleUrls: ['./monitor.component.css']
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
