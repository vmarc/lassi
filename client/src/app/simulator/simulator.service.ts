import {Injectable} from '@angular/core';
import {RxStompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import {Observable} from 'rxjs';
import {BehaviorSubject} from 'rxjs';
import {Subscriptions} from '../util/subscriptions';
import {SimulatorStatus} from './simulator-status';

@Injectable()
export class SimulatorService {

  readonly status$: Observable<SimulatorStatus>;

  private readonly subscriptions = new Subscriptions();
  private readonly _status$ = new BehaviorSubject<SimulatorStatus>({});

  constructor(private rxStompService: RxStompService) {
    this.status$ = this._status$.asObservable();
    console.log('SimulatorService constructor');
  }

  init(): void {
    this.subscriptions.add(this.rxStompService.watch('/topic/simulator/status').subscribe((data: Message) => {
      const s = JSON.parse(data.body);
      this._status$.next(s);
    }));
  }

  destroy(): void {
    this.subscriptions.unsubscribe();
  }

}