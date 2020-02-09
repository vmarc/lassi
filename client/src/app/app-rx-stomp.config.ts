import { InjectableRxStompConfig } from '@stomp/ng2-stompjs';

export const appRxStompConfig: InjectableRxStompConfig = {

  brokerURL: 'ws://127.0.0.1:8001/stomp/websocket',

  heartbeatIncoming: 0,
  heartbeatOutgoing: 20000,
  reconnectDelay: 5000,

  debug: (msg: string): void => {
    console.log(new Date().toLocaleString('nl'), msg);
  }
};
