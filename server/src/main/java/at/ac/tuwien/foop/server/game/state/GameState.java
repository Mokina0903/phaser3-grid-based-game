package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.dto.LoginResponse;
import at.ac.tuwien.foop.server.dto.PlayerRequest;
import at.ac.tuwien.foop.server.exception.IllegalOperationForGameStateException;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

/**
 * Represents the state the game is currently in. Actions lead to an error, if the concrete state does not allow that action
 */
public interface GameState {

    /**
     * Creates a new player, if the state allows it
     *
     * @param name the name of the player
     * @return the created player object and a token used for authentication
     */
    default LoginResponse createPlayer(String name) {
        throw new IllegalOperationForGameStateException("create player", getGameState());
    }

    /**
     * updates a player, if the state allows it
     *
     * @param playerRequest the 'should-be' data of the player object
     * @return the updated player
     */
    default Player updatePlayer(PlayerRequest playerRequest) {
        throw new IllegalOperationForGameStateException("update player", getGameState());
    }

    /**
     * transitions the player to the state 'ReadyForGame', if the state allows it
     *
     * @param player the player that should be set ready
     * @return the player
     */
    default Player setPlayerReadyForGame(Player player) {
        throw new IllegalOperationForGameStateException("setting player ready", getGameState());
    }

    /**
     * Prepares movement for a player, if the game state and the player state allow it
     *
     * @param player the player that wants to move
     * @param targetLocation the location the player wants to move to
     */
    default void prepareMovement(Player player, Position targetLocation) {
        throw new IllegalOperationForGameStateException("prepare movement", getGameState());
    }

    /**
     * confirms the previously prepared movement, if the game state and the player state allow it
     *
     * @param player the player that wants to confirm their movement
     */
    default void confirmMovement(Player player) {
        throw new IllegalOperationForGameStateException("confirm movement", getGameState());
    }

    String getGameState();
}
