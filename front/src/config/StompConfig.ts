import * as stomp from '@stomp/stompjs'
import { IMessage } from '@stomp/stompjs'

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

export const unsubscribePriceInfo = (code: string, command: MessageCommend) => {
    const message: StompMessage<string> = {
        code: code,
        command: command,
    }
    stompClient.publish({
        destination: `/pub/price`,
        body: JSON.stringify(message),
    })
    stompClient.unsubscribe(
        command === 'START_GET_PRICE' ? `/sub/price/${code}` : `/sub/asking_price/${code}`,
    )
}

export const subscribePriceInfo = (
    code: string,
    command: MessageCommend,
    callback: (message: IMessage) => void,
) => {
    const destination = command === 'START_GET_PRICE' ? `/sub/price/${code}` : `/sub/asking_price/${code}`
    const message: StompMessage<string> = {
        code: code,
        command: command,
    }
    stompClient.publish({
        destination: `/pub/price`,
        body: JSON.stringify(message),
    })
    stompClient.subscribe(destination, callback)
}
