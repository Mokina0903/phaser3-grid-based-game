package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.exception.IllegalOperationForPlayerStateException;
import at.ac.tuwien.foop.server.game.Position;

public class PlayerStateWaiting implements PlayerState {

    @Override
    public void prepareMovement(Position position) {
        throw new IllegalOperationForPlayerStateException("movement preparation", "waiting");
    }

    @Override
    public void confirmMovement() {
        throw new IllegalOperationForPlayerStateException("movement confirmation", "waiting");
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void move() {
        throw new IllegalOperationForPlayerStateException("move", "waiting");

    }
}
