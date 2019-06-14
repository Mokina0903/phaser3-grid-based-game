package at.ac.tuwien.foop.server.service;

import at.ac.tuwien.foop.server.dto.EmptyRequest;
import at.ac.tuwien.foop.server.dto.MovementRequest;
import at.ac.tuwien.foop.server.game.GameMaster;
import at.ac.tuwien.foop.server.game.player.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GameService {

    private final GameMaster gameMaster;

    public void prepareMovement(MovementRequest movementRequest) {
        Player player = getPlayerForToken(movementRequest.getToken());
        gameMaster.getCurrentGameState().prepareMovement(player, movementRequest.toPosition());
    }

    public void confirmMovement(EmptyRequest emptyRequest) {
        Player player = getPlayerForToken(emptyRequest.getToken());
        gameMaster.getCurrentGameState().confirmMovement(player);
        gameMaster.endRoundIfAllPlayersHaveConfirmedMovement();
    }

    private static Player getPlayerForToken(String token) {
        // TODO Dummy method. Replace with real method, as soon as Robin is done
        return null;
    }
}
