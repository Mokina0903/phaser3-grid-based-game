package at.ac.tuwien.foop.server.service;

import at.ac.tuwien.foop.server.dto.EmptyRequest;
import at.ac.tuwien.foop.server.dto.MovementRequest;
import at.ac.tuwien.foop.server.exception.InvalidPlayerStateException;
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
        validatePlayerState(player);
        player.prepareMovement(movementRequest.toPosition());
    }

    public void confirmMovement(EmptyRequest emptyRequest) {
        Player player = getPlayerForToken(emptyRequest.getToken());
        validatePlayerState(player);
        gameMaster.confirmMovement(player);
    }

    private void validatePlayerState(Player player) {
        Player.PlayerState actualPlayerState = gameMaster.getPlayerStateForPlayer(player);
        if (!actualPlayerState.equals(Player.PlayerState.PLAYING)) {
            throw new InvalidPlayerStateException(Player.PlayerState.PLAYING, actualPlayerState);
        }
    }

    private static Player getPlayerForToken(String token) {
        // TODO Dummy method. Replace with real method, as soon as Robin is done
        return null;
    }
}
