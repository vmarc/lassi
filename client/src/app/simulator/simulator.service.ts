import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {Message} from '@stomp/stompjs';
import {RxStompService} from '@stomp/ng2-stompjs';
import {SceneStatusesMessage} from '../scene/scene-statuses-message';
import {SceneService} from '../scene/scene.service';
import {Reply} from '../scene/reply';

@Injectable()
export class SimulatorService {

  private readonly sceneCount = 9;
  private readonly statuses: Array<BehaviorSubject<boolean>>;

  constructor(private rxStompService: RxStompService, private sceneService: SceneService) {
    this.statuses = new Array<BehaviorSubject<boolean>>();
    for (let i = 0; i < this.sceneCount; i++) {
      this.statuses.push(new BehaviorSubject<boolean>(false));
    }
    this.rxStompService.watch('/topic/scenes').subscribe((message: Message) => {
      const sceneStatussesMessage = SceneStatusesMessage.fromJSON(JSON.parse(message.body));
      for (let i = 0; i < this.sceneCount; i++) {
        this.statuses[i].next(sceneStatussesMessage.statusses[i]);
      }
    });
  }

  status(sceneId: number): Observable<boolean> {
    return this.statuses[sceneId];
  }

  recordScene(sceneId: number): Observable<Reply> {
    return this.sceneService.recordScene(sceneId);
  }

  gotoScene(sceneId: number): Observable<Reply> {
    return this.sceneService.gotoScene(sceneId);
  }

}
