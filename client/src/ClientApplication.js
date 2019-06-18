export default class ClientApplication {


    constructor() {
        this.serverAddress = "http://localhost:8080";

        this.socket = new SockJS(this.serverAddress + "/ws");
        this.stompClient = Stomp.over(this.socket);

        this.stompClient.connect({}, frame => {
                console.log("Connected :- " + frame);
                this.stompClient.subscribe('/topic/player', notifications =>
                    alert(notifications));
                // over websocket
                //this.stompClient.send('/app/addPlayer', "Chuck Norris")

                //over REST
                this.addPlayer()

            }, error =>
                alert(error)
        );
    }

    addPlayer() {
        fetch(this.serverAddress + "/players", {
            mode: 'no-cors',
            headers: {"Content-Type": "application/json; charset=utf-8"},
            method: 'POST',
            body: 'ChuckNorris'
        }).then(response => console.log("added player response: " + response));
    }

    // character is mouse or cat
    setCharacterOfPlayer(character) {
        fetch(this.serverAddress + "/players/char", {
            mode: 'no-cors',
            headers: {"Content-Type": "application/json; charset=utf-8"},
            method: 'PATCH',
            body: character
        }).then(() => console.log("set player chat to: " + character));
    }

    getAllPlayers() {
        fetch(this.serverAddress + '/players', {
            mode: 'no-cors',
            headers: {"Content-Type": "application/json; charset=utf-8"}
        }).then(response => response.json())
            .then(data => {
                console.log("fetching all players in client: " + data);
                return JSON.parse(data);
                ;
            });
    }

    disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }
        this.setConnected(false);
        console.log("Disconnected");
    }

}
