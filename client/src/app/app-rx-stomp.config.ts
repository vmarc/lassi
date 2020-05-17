import { InjectableRxStompConfig } from '@stomp/ng2-stompjs';

export var ipAddress;

export const appRxStompConfig: InjectableRxStompConfig = {

  brokerURL: 'ws://localhost:8080/stomp/websocket',

  heartbeatIncoming: 0,
  heartbeatOutgoing: 20000,
  reconnectDelay: 5000,

  debug: (msg: string): void => {
    console.log(new Date().toLocaleString('nl'), msg);
  }
}

