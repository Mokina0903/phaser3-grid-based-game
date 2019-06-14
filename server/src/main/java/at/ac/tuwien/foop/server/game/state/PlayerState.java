package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.game.Position;

public interface PlayerState {

    void prepareMovement(Position position);

    void confirmMovement();

    default boolean isReady() {
        return false;
    }

    default boolean isDead() {
        return false;
    }

    void move();
}
