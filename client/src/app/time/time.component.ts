import {Component, OnDestroy, OnInit} from '@angular/core';
import {RxStompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import {TimeMessage} from './time-message';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-time',
  template: `
    <div class="title">Time</div>
    <div class="time">
      {{time}}
    </div>
  `,
  styles: [`
    .time {
      display: flex;
      flex-direction: column;
      align-items: center;
      font-size: 3em;
    }
  `]
})
export class TimeComponent implements OnInit, OnDestroy {

  time = '';

  private topicSubscription: Subscription;

  constructor(private rxStompService: RxStompService) {
  }

  ngOnInit() {
    this.topicSubscription = this.rxStompService.watch('/topic/time').subscribe((message: Message) => {
      const timeMessage = TimeMessage.fromJSON(JSON.parse(message.body));
      this.time = timeMessage.time;
    });
  }

  ngOnDestroy() {
    this.topicSubscription.unsubscribe();
  }
}
