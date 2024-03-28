import { ref, inject } from "vue";
import { DEFAULT_RECONNECT_TIME, STATE_CONNECTING, STATE_OPEN, STATE_CLOSING, STATE_CLOSED, EVENT_OPEN, EVENT_MESSAGE, EVENT_CLOSE, EVENT_ERROR } from "./constants";

export const socket = ref(null);

const openCallbacks = [];
const messageCallbacks = [];
const closeCallbacks = [];
const errorCallbacks = [];

const connectionString = ref("your_websocket_url_here");
let debug = true

export const setConnectionString = (newConnectionString) => {
    connectionString.value = newConnectionString;
};

export const connect = () => {
//export function connect() {
    socket.value = new WebSocket(connectionString.value);
    socket.value.addEventListener(EVENT_OPEN, () => {
        console.log("Connection opened");
        openCallbacks.forEach(callback => socket.value.addEventListener(EVENT_OPEN, callback));
        messageCallbacks.forEach(callback => socket.value.addEventListener(EVENT_MESSAGE, callback));
        closeCallbacks.forEach(callback => socket.value.addEventListener(EVENT_CLOSE, callback));
        errorCallbacks.forEach(callback => socket.value.addEventListener(EVENT_ERROR, callback));
    });
    socket.value.addEventListener(EVENT_CLOSE, event => {
        console.log("Connection closed", event);
    });
    if (debug) {
        socket.value.addEventListener(EVENT_MESSAGE, message => {
            console.log("Received message:", message.data);
        });
        socket.value.addEventListener(EVENT_ERROR, error => {
            console.error("Error:", error);
        });
    }
};

export const onOpen = callback => {
    socket.value.addEventListener(EVENT_OPEN, callback);
    openCallbacks.push(callback);
};

export const onMessage = callback => {
    socket.value.addEventListener(EVENT_MESSAGE, callback);
    messageCallbacks.push(callback);
};

export const onClose = callback => {
    socket.value.addEventListener(EVENT_CLOSE, callback);
    closeCallbacks.push(callback);
};

export const onError = callback => {
    socket.value.addEventListener(EVENT_ERROR, callback);
    errorCallbacks.push(callback);
};

export const closeConnection = callback => {
console.log(socket.value);
console.log(socket.value.readyState);
console.log(WebSocket.OPEN);
console.log(socket.value && socket.value.readyState === WebSocket.OPEN)
    if (socket.value && socket.value.readyState === WebSocket.OPEN) {
        socket.value.close();
    }
};

export default {
    connect,
    setConnectionString,
    socket,    
    onOpen,
    onMessage,
    onClose,
    onError
};