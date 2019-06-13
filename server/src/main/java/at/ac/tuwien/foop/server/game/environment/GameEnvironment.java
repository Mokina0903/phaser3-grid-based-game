package at.ac.tuwien.foop.server.game.environment;

import at.ac.tuwien.foop.server.game.GameModel;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

import java.util.Collection;

/**
 * Represents a environment in which the players can be => Tunnel/Surface
 * Basically is an observable (I know there are Java Observables, but they are shit)
 */
public interface GameEnvironment {

    /**
     * subscribes a player for all gameState changes visible to this environment
     * @param player the player that wants to be notified of all changes to this environment (e.g. a player that is in this tunnel, or on this surface)
     */
    void enterEnvironment(Player player);

    /**
     * unsubscribes a player. (e.g. a mouse leaves this environment (surface) to go to another environment (tunnel)
     * @param player the player to be unsubscribed
     */
    void leaveEnvironment(Player player);

    /**
     * changes the state of this environment
     * @param gameModel the new state of the environment
     */
    void setGameModel(GameModel gameModel);

    /**
     * @return the current gameState
     */
    GameModel getGameModel();

    /**
     * Pushes the gameState to all subscribed players. Call this, when the gameState changes
     */
    void notifyStateChanges();

    /**
     * @return all currently present players in this environment i.e. the subscribers of this observable
     */
    Collection<Player> getPresentPlayers();

    Collection<Position> getEnvironmentTransitionPositions();

    void setupEnvironment(Collection<Position> environmentArea, Collection<Position> environmentTransitionPositions);

    default boolean isLeavingEnvironment(Position position) {
        return getEnvironmentTransitionPositions().contains(position);
    }

    GameEnvironment getAdjacentEnvironment(Position position);
}
