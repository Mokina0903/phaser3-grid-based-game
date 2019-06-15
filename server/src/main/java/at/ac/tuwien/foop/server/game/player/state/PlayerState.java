package at.ac.tuwien.foop.server.game.player.state;

import at.ac.tuwien.foop.server.exception.IllegalOperationForPlayerStateException;
import at.ac.tuwien.foop.server.game.Position;

/**
 * Represents the state a player is currently in. Actions lead to an error, if the concrete state does not allow that action
 */
public interface PlayerState {

    // player actions that are dependent on concrete player state

    /**
     * validates and prepares movement of a player, if they are in a valid state for this action
     *
     * @param position the location the player wants to go to
     */
    default void prepareMovement(Position position) {
        throw new IllegalOperationForPlayerStateException("movement preparation", getGameState());
    }

    /**
     * confirms the previously prepared movement of a player, if they are in a valid state for this action
     */
    default void confirmMovement() {
        throw new IllegalOperationForPlayerStateException("confirm movement", getGameState());
    }

    /**
     * executes the prepared and confirmed movement, if the player is in a valid state for this action
     */
    default void move() {
        throw new IllegalOperationForPlayerStateException("movement", getGameState());
    }

    // Helper methods

    default boolean isReadyForTurn() {
        return false;
    }

    default boolean isDead() {
        return false;
    }

    default void setPlayerReady() {
        throw new IllegalOperationForPlayerStateException("set ready", getGameState());
    }

    default boolean isReadyForGame() {
        return false;
    }

    String getGameState();
}
