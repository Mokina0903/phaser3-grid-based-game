package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.exception.IllegalOperationForPlayerStateException;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerStatePreparingMovement implements PlayerState {

    private final Player player;

    @Override
    public void prepareMovement(Position position) {
        player.getMovementStrategy().validatePrepareMovement(player, position);
        player.setMovementPrepared(position);
    }

    @Override
    public void confirmMovement() {
        throw new IllegalOperationForPlayerStateException("movement confirmation", "'movement preparation'");
    }

    @Override
    public void move() {
        throw new IllegalOperationForPlayerStateException("move", "'movement preparation'");
    }
}
