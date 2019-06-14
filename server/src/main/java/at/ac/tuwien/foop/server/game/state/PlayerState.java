package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.exception.IllegalOperationForPlayerStateException;
import at.ac.tuwien.foop.server.game.Position;

public interface PlayerState {

    default void prepareMovement(Position position) {
        throw new IllegalOperationForPlayerStateException("movement preparation", getGameState());
    }

    default void confirmMovement() {
        throw new IllegalOperationForPlayerStateException("confirm movement", getGameState());
    }

    default void move() {
        throw new IllegalOperationForPlayerStateException("movement", getGameState());
    }

    default boolean isReady() {
        return false;
    }

    default boolean isDead() {
        return false;
    }

    String getGameState();
}
