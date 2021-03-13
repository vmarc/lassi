import {InjectableRxStompConfig} from '@stomp/ng2-stompjs';
import {SettingsService} from './settings/settings.service';

export class Stomp extends InjectableRxStompConfig {

  constructor(private settingsService: SettingsService) {
    super();
    //this.settingsService.getHostIp().subscribe(ip => this.brokerURL = 'ws://' + ip + ':8080/stomp/websocket')
    this.brokerURL = null;
    this.heartbeatIncoming = 0;
    this.heartbeatOutgoing = 20000;
    this.reconnectDelay = 5000;

    this.debug = (msg: string): void => {
      console.log(new Date().toLocaleString('nl'), msg);
    };

  }
}
