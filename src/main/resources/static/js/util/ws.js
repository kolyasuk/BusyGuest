import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'


var stompClient = null
const handlers = []

export function connect() {
    const socket = new SockJS('/gs-guide-websocket')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, frame => {
        console.log('Connected: ' + frame)
        stompClient.subscribe('/topic/activity', bookedTable => {
            handlers.forEach(handler => handler(JSON.parse(bookedTable.body)))
        })
    })
}

export function addHandler(handler) {
    handlers.push(handler)
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
    console.log("Disconnected")
}
