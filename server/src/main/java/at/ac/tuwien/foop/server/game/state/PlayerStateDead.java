package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.exception.IllegalOperationForPlayerStateException;
import at.ac.tuwien.foop.server.game.Position;

public class PlayerStateDead implements PlayerState {

    @Override
    public void prepareMovement(Position position) {
        throw new IllegalOperationForPlayerStateException("movement preparation", "dead");
    }

    @Override
    public void confirmMovement() {
        throw new IllegalOperationForPlayerStateException("movement confirmation", "dead");
    }

    @Override
    public boolean isDead() {
        return true;
    }

    @Override
    public void move() {
        throw new IllegalOperationForPlayerStateException("move", "dead");
    }
}
