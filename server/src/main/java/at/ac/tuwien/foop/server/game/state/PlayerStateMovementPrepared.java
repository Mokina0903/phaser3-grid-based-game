package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerStateMovementPrepared implements PlayerState {

    private static final String PLAYER_STATE = "DEAD";

    private final Player player;
    private Position targetPosition;

    @Override
    public void prepareMovement(Position position) {
        player.getMovementStrategy().validatePrepareMovement(player, position);
        if (targetPosition.equals(player.getPosition())) {
            player.setPreparingMovement();
            targetPosition = null;
        }
        else {
            targetPosition = position;
        }
    }

    @Override
    public void confirmMovement() {
        player.setMovementConfirmed(targetPosition);
    }

    @Override
    public String getGameState() {
        return PLAYER_STATE;
    }
}
