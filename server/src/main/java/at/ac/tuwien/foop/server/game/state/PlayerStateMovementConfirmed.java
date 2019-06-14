package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.exception.IllegalOperationForPlayerStateException;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerStateMovementConfirmed implements PlayerState {

    private final Player player;
    private final Position targetLocation;

    @Override
    public void prepareMovement(Position position) {
        throw new IllegalOperationForPlayerStateException("movement preparation", "'movement confirmed'");
    }

    @Override
    public void confirmMovement() {
        throw new IllegalOperationForPlayerStateException("movement confirmation", "'movement confirmed'");
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void move() {
        // TODO move and check if environment changes
        player.setWaiting();
    }
}
