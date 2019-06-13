package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.game.player.Player;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * The game master represents the state of the game
 * It knows at all times, where every player is and which state the game has
 *
 * The GameMaster is essentially the 'persistence' layer of our application. Only 'Services' e.g.
 * MovementStrategy communicate with the GameMaster. The Endpoint never communicates directly with the GameMaster
 *
 * The GameMaster expects already validated client data. Therefore the validations should be done in the 'Services'
 */
@Component
public class GameMaster {

    Collection<Player> loggedInPlayers;

    /**
     * The state of the game that contains all environments and all the data about all the players in these environments
     */
    GameState gameState;

    /**
     * Registers player to the current game
     *
     * @param player
     */
    void registerPlayer(Player player) {
        // TODO
    }

    /**
     * Prepares movement for the player
     *
     * @param player the player that should be moved
     * @param targetPosition the position the player wants to go to
     */
    void preparedMovementForPlayer(Player player, Position targetPosition) {
        // TODO
    }

    /**
     * moves player to the targetLocation, checks if an environment change is about to happen and puts player in the correct environment
     *
     * @param player The player that should be moved
     */
    void movePlayer(Player player) {
        // TODO
    }

    /**
     * builds game environments i.e. builds the surface level and all tunnels
     */
    void buildGame() {
        // TODO
    }

    /**
     * starts the game
     */
    void start() {
        // TODO
    }
}
