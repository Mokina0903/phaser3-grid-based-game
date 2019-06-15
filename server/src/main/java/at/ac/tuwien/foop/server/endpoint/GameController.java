package at.ac.tuwien.foop.server.endpoint;


import at.ac.tuwien.foop.server.dto.EmptyRequest;
import at.ac.tuwien.foop.server.dto.MovementRequest;
import at.ac.tuwien.foop.server.exception.GameException;
import at.ac.tuwien.foop.server.game.player.Player;
import at.ac.tuwien.foop.server.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static at.ac.tuwien.foop.server.util.Constants.*;

@Controller
@Slf4j
@AllArgsConstructor
public class GameController {

    private final GameService gameService;

    /**
     *
     * Clients call this endpoint, when they want to confirmMovement. The movement will then be validated.
     * If the movement is possible, the future state of the game (the state the game will have, after the movement is confirmed)
     * will be broadcasted to all connected clients
     *
     * @param player contains the id of the (!logged in!) player and also their desired next location (has to be validated, if possible)
     * @return the future state the game will have, if the movement is confirmed
     * @throws at.ac.tuwien.foop.server.exception.GameException if something went wrong (validation failed, future state could not be validated)
     */
    @MessageMapping(MOVEMENT_ENDPOINT)
    @SendTo(TOPIC_GAME_STATE)
    public void prepareMovement(MovementRequest movementRequest) {
        log.info("Receiving movement request");
        gameService.prepareMovement(movementRequest);
    }

    /**
     * Confirms stored movement requests for the given Player.
     * As soon as the last player of the current turn confirmed their movement, the turn is over, all movements are executed,
     * mice are eaten,
     * if cats jump onto them,
     * new views for the game state are distributed among the players (tunnel view vs top view)
     * and the new game state is distributed
     *
     * @param player the player, who confirms their movement (they must have made a request to '/confirmMovement' endpoint first)
     * @return the fitting future gameState for the player
     */
    @MessageMapping(CONFIRM_MOVEMENT_ENDPOINT)
    public void confirmMovement(EmptyRequest emptyRequest) {
        log.info("Receiving confirmation request");
        gameService.confirmMovement(emptyRequest);
    }

    /**
     * handles all thrown GameExceptions and maps them to a GameError Object, which clients can handle
     *
     * @param gameException the gameException handled. GameExceptions get thrown all around the application
     * @return a GameError instance generated with the values of the given GameException instance
     */
    @MessageExceptionHandler
    public GameError handleGameException(GameException gameException) {
        return GameError
                .builder()
                .id(gameException.getErrorId())
                .message(gameException.getMessage())
                .build();
    }
}
