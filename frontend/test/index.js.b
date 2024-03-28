import { ref, inject } from "vue";
import { DEFAULT_RECONNECT_TIME, STATE_CONNECTING, STATE_OPEN, STATE_CLOSING, STATE_CLOSED, EVENT_OPEN, EVENT_MESSAGE, EVENT_CLOSE, EVENT_ERROR } from "./constants";

const webSocket = {
    install: (app, data = null) => {
        if (!data) {
            throw "You must provide websocket data (url string or options object)";
        }
        const socket = ref();
        let connectionString;
        const readyState = ref(0);
        const openCallbacks = ref([]);
        const messageCallbacks = ref([]);
        const closeCallbacks = ref([]);
        const errorCallbacks = ref([]);
        let protocols = [];
        let reconnect = true;
        let reconnectTime = DEFAULT_RECONNECT_TIME;
        app.mixin({
            beforeUnmount() {
                openCallbacks.value.forEach(callback => {
                    socket.value && socket.value.removeEventListener(EVENT_OPEN, callback);
                });
                messageCallbacks.value.forEach(callback => {
                    socket.value && socket.value.removeEventListener(EVENT_MESSAGE, callback);
                });
                closeCallbacks.value.forEach(callback => {
                    socket.value && socket.value.removeEventListener(EVENT_CLOSE, callback);
                });
                errorCallbacks.value.forEach(callback => {
                    socket.value && socket.value.removeEventListener(EVENT_ERROR, callback);
                });
                openCallbacks.value = [];
                messageCallbacks.value = [];
                closeCallbacks.value = [];
                errorCallbacks.value = [];
            }
        });
        let debug = true;
        if (typeof data === "string") {
            connectionString = data;
        }
        else {
            const { secured, host } = data;
            if (typeof data.debug === "boolean") {
                debug = data.debug;
            }
            if (typeof data.reconnect === "boolean") {
                reconnect = data.reconnect;
            }
            if (typeof data.reconnectTime === "number") {
                reconnectTime = data.reconnectTime;
            }
            if (Array.isArray(data.protocols)) {
                protocols = data.protocols;
            }
            connectionString = `${secured ? "wss" : "ws"}://${host}`;
        }
        const connect = () => {
            socket.value = new WebSocket(connectionString, protocols);
            readyState.value = STATE_CONNECTING;
            socket.value.addEventListener(EVENT_OPEN, () => {
                debug && console.log("%c[WebSocket] ", "color: green", "Connection: opened");
                openCallbacks.value.forEach(callback => socket.value && socket.value.addEventListener(EVENT_OPEN, callback));
                messageCallbacks.value.forEach(callback => socket.value && socket.value.addEventListener(EVENT_MESSAGE, callback));
                closeCallbacks.value.forEach(callback => socket.value && socket.value.addEventListener(EVENT_CLOSE, callback));
                errorCallbacks.value.forEach(callback => socket.value && socket.value.addEventListener(EVENT_ERROR, callback));
                readyState.value = STATE_OPEN;
            });
            socket.value.addEventListener(EVENT_CLOSE, event => {
                debug && console.log("%c[WebSocket] ", "color: red", "Connection: closed", event);
                if (reconnect) {
                    setTimeout(() => connect(), reconnectTime);
                }
                readyState.value = STATE_CLOSED;
            });
            if (debug) {
                socket.value.addEventListener(EVENT_MESSAGE, message => {
                    console.log("%c[WebSocket] ", "color: lightblue", "Received message:", message.data);
                });
                socket.value.addEventListener(EVENT_ERROR, error => {
                    console.error("%c[WebSocket] ", "color: red", "Error: ", error);
                });
            }
        };
        connect();
        app.provide("socket", socket);
        app.provide("readyState", readyState);
        app.provide("openCallbacks", openCallbacks);
        app.provide("messageCallbacks", messageCallbacks);
        app.provide("closeCallbacks", closeCallbacks);
        app.provide("errorCallbacks", errorCallbacks);
    }
};

export default webSocket;

export const onOpen = callback => {
    const socket = inject("socket");
    const openCallbacks = inject("openCallbacks");
    if (socket && socket.value && openCallbacks && openCallbacks.value) {
        socket.value.addEventListener(EVENT_OPEN, callback);
        openCallbacks.value.push(callback);
    }
};

export const onMessage = callback => {
    const socket = inject("socket");
    const messageCallbacks = inject("messageCallbacks");
    if (socket && socket.value && messageCallbacks && messageCallbacks.value) {
        socket.value.addEventListener(EVENT_MESSAGE, callback);
        messageCallbacks.value.push(callback);
    }
};

export const onClose = callback => {
    const socket = inject("socket");
    const closeCallbacks = inject("closeCallbacks");
    if (socket && socket.value && closeCallbacks && closeCallbacks.value) {
        socket.value.addEventListener(EVENT_CLOSE, callback);
        closeCallbacks.value.push(callback);
    }
};

export const onError = callback => {
    const socket = inject("socket");
    const errorCallbacks = inject("errorCallbacks");
    if (socket && socket.value && errorCallbacks && errorCallbacks.value) {
        socket.value.addEventListener(EVENT_ERROR, callback);
        errorCallbacks.value.push(callback);
    }
};
