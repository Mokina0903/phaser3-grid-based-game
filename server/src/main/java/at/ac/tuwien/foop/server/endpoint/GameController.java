package at.ac.tuwien.foop.server.endpoint;


import at.ac.tuwien.foop.server.game.GameState;
import at.ac.tuwien.foop.server.game.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class GameController {

    /**
     * Broadcast topics. Clients will subscribe to this topic
     * The server makes sure every client gets the right kind of game state information.
     * i.e. the server knows, when a player is up top or in a tunnel and send fitting game state information for the player
     */
    private static final String TOPIC_GAME_STATE = "/topic/state";
    private static final String TOPIC_PLAYERS = "/topic/players";

    /**
     * Endpoints. (Pls change, if the names are stupid)
     */
    private static final String MOVEMENT_ENDPOINT = "/move";
    private static final String CONFIRM_MOVEMENT_ENDPOINT = MOVEMENT_ENDPOINT + "/confirm";
    private static final String LOGIN_ENDPOINT = "/login";

    /**
     *
     * Clients call this endpoint, when they want to move. The movement will then be validated.
     * If the movement is possible, the future state of the game (the state the game will have, after the movement is confirmed)
     * will be broadcasted to all connected clients
     *
     * @param player contains the id of the (!logged in!) player and also their desired next location (has to be validated, if possible)
     * @return the future state the game will have, if the movement is confirmed
     * @throws at.ac.tuwien.foop.server.exception.GameException if something went wrong (validation failed, future state could not be validated)
     */

    @MessageMapping(MOVEMENT_ENDPOINT)
    @SendTo(TOPIC_GAME_STATE)
    public ResponseEntity<GameState> move(Player player) {
        log.info("Receiving movement request: {}", player);
        return ResponseEntity.ok(new GameState());
    }

    /**
     * Confirms stored movement requests for the given Player.
     * As soon as the last player of the current turn confirmed their movement, the turn is over, all movements are executed,
     * mice are eaten,
     * if cats jump onto them,
     * new views for the game state are distributed among the players (tunnel view vs top view)
     * and the new game state is distributed
     *
     * @param player the player, who confirms their movement (they must have made a request to '/move' endpoint first)
     * @return the fitting future gameState for the player
     */
    @MessageMapping(CONFIRM_MOVEMENT_ENDPOINT)
    public ResponseEntity<GameState> confirmMovement(Player player) {
        log.info("Receiving confirmation request from player: {}", player);
        return ResponseEntity.ok(new GameState());
    }

    /**
     * Logs in new player
     *
     * @param player the player to be logged in. ID should not be set. IDs are assigned by the server
     * @return the newly logged in player (with ID). The new player will be broadcasted to all clients that subscribe to
     * '/topic/players' aka all players
     */
    @MessageMapping(LOGIN_ENDPOINT)
    @SendTo(TOPIC_PLAYERS)
    public ResponseEntity<Player> login(Player player) {
        log.info("Receiving login request from player: {}", player);
        return ResponseEntity.ok(player);
    }
}
