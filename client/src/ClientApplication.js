export default class ClientApplication {

    constructor() {
        this.socket = new SockJS("http://localhost:8080/ws");
        this.stompClient = Stomp.over(this.socket);

        console.log("Tying to connect...");
        this.stompClient.connect({}, this.connectionSuccess()
        );
    }

    connectionSuccess() {
        setTimeout(() => {
            this.stompClient.subscribe('/topic/addPlayer',
                function (addPlayer) {
                    console.log("Suscribed to /topic/addPlayer");
                });

            this.addPlayer();

        }, 3000)
    };

    addPlayer() {

        //old websocket style
        /*this.sessionId = /\/([^\/]+)\/websocket/.exec(this.socket._transport.url)[1];
        console.log("client sessionid: " + this.sessionId);
        this.msg = this.stompClient.send("/app/addPlayer", {}, this.sessionId);
        console.log("print send msg: " + this.msg);*/

        fetch("'http://localhost:8080/players", {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            //make sure to serialize your JSON body
            body: JSON.stringify({
                "name": 'myPlayerName',
            })
        })
            .then( (response) => {
                console.log("add player rest: " + response);
            });
    }

    getAllPlayers() {
        const userAction = async () => {
            const response = await fetch('http://localhost:8080/players');
            const myJson = await response.json(); //extract JSON from the http response
            // do something with myJson
            console.log(myJson);
            return myJson;
        }
    }

    disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }
        this.setConnected(false);
        console.log("Disconnected");
    }

}
