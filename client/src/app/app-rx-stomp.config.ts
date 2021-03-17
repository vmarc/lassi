import { InjectableRxStompConfig } from '@stomp/ng2-stompjs';

export const appRxStompConfig: InjectableRxStompConfig = {

  brokerURL: 'ws://localhost:8081/stomp/websocket',

  heartbeatIncoming: 200,
  heartbeatOutgoing: 20000,
  reconnectDelay: 5000,

  debug: (msg: string): void => {
    console.log(new Date().toLocaleString('nl'), msg);
  }
}

