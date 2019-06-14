package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.exception.IllegalOperationForGameStateException;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

public interface GameState {

    default void login() {
        throw new IllegalOperationForGameStateException("login", getGameState());
    }

    default void prepareMovement(Player player, Position targetLocation) {
        throw new IllegalOperationForGameStateException("prepare movement", getGameState());
    }

    default void confirmMovement(Player player) {
        throw new IllegalOperationForGameStateException("confirm movement", getGameState());
    }

    String getGameState();
}
