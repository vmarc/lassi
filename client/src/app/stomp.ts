import { SettingsService } from './settings/settings.service';
import { OnInit } from '@angular/core';
import { InjectableRxStompConfig } from '@stomp/ng2-stompjs';

export class Stomp extends InjectableRxStompConfig {

  /*public brokerURL = null;
  public heartbeatIncoming: 0; // Typical value 0 - disabled
  public heartbeatOutgoing: 20000; // Typical value 20000 - every 20 seconds
  public reconnectDelay: 5000;*/

  constructor(private settingsService: SettingsService) {
    super();
    //this.settingsService.getHostIp().subscribe(ip => this.brokerURL = 'ws://' + ip + ':8080/stomp/websocket')
    this.brokerURL = null;
    this.heartbeatIncoming = 0;
    this.heartbeatOutgoing = 20000;
    this.reconnectDelay = 5000;

    this.debug = (msg: string): void => {
      console.log(new Date().toLocaleString('nl'), msg);
    }


    /*this.beforeConnect = (stompClient: any) : Promise<void> => {
      return new Promise<void>(((resolve, reject) => {
        this.settingsService.getHostIp().subscribe(ip => {
          stompClient.brokerURL = 'ws://' + ip + ':8080/stomp/websocket';
          resolve();
        });
      }));
    }
    console.log(this.brokerURL);*/
  }



}
