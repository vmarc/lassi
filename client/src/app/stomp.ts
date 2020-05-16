import { SettingsService } from './settings/settings.service';
import { OnInit } from '@angular/core';
import { InjectableRxStompConfig } from '@stomp/ng2-stompjs';

export class Stomp {

  public brokerURL = 'ws://localhost:8080/stomp/websocket';
  public heartbeatIncoming: 0; // Typical value 0 - disabled
  public heartbeatOutgoing: 20000; // Typical value 20000 - every 20 seconds
  public reconnectDelay: 5000;

  constructor(private settingsService: SettingsService) {
    console.log(this.settingsService.getHostIp());
    this.settingsService.getHostIp().subscribe(ip => this.brokerURL = 'ws://' + ip + ':8080/stomp/websocket');
  }

  debug(msg: string): void {
    console.log(new Date().toLocaleString('nl'), msg);
  }



}



