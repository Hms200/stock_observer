import * as stomp from '@stomp/stompjs'

const stompClient = new stomp.Client({
    brokerURL: 'ws://localhost:8085/ws',
    reconnectDelay: 500,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
})

export default stompClient

export type MessageCommend =
    | 'START_GET_PRICE'
    | 'STOP_GET_PRICE'
    | 'START_GET_ASK_PRICE'
    | 'STOP_GET_ASK_PRICE'

export interface StompMessage<T> {
    code: string
    command?: MessageCommend
    data?: T
}
