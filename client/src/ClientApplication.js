export default class ClientApplication {


    constructor() {
        this.serverAddress = "http://localhost:8080";

        this.socket = new SockJS(this.serverAddress + "/ws");
        this.stompClient = Stomp.over(this.socket);

        this.stompClient.connect({}, frame => {
                console.log("Connected :- " + frame);
                this.stompClient.subscribe('/topic/addPlayer', notifications =>
                    alert(notifications));
                this.addPlayer();
            }, error =>
                alert(error)
        );
    }

    addPlayer() {
        fetch(this.serverAddress + "/players", {mode: 'no-cors',
            headers: {"Content-Type": "application/json; charset=utf-8"},
            method: 'POST',
            body: JSON.stringify({
                username: 'Elon Musk',
            })
        }).then(response => console.log("added player response: " + response));
    }

    getAllPlayers() {
        fetch(this.serverAddress + '/players', {mode: 'no-cors'})
            .then(response => response.json())
            .then(data => console.log("fetching all players in client: " + data));
        return data;
    }

    disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }
        this.setConnected(false);
        console.log("Disconnected");
    }

}
