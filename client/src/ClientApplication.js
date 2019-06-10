export default class ClientApplication {

    constructor(){
        this.socket = new SockJS("http://localhost:8080/ws");
        this.stompClient = Stomp.over(this.socket);

        console.log("Tying to connect...");
        this.stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            parent.subscription = this.subscribe('/topic/addPlayer',
                function (addPlayer) {
                    console.log("Suscribed to /topic/addPlayer")
            });
        });

        //not sure how to call method immediately after connection established
        setTimeout(() =>
            this.addPlayer()
        , 2000)
    }

    addPlayer() {
        this.sessionId = /\/([^\/]+)\/websocket/.exec(this.socket._transport.url)[1];
        console.log("client sessionid: " + this.sessionId);
        this.msg = this.stompClient.send("/app/addPlayer", {}, this.sessionId);
        console.log("print send msg: " + this.msg);
    }

    disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }
         this.setConnected(false);
        console.log("Disconnected");
    }

}
