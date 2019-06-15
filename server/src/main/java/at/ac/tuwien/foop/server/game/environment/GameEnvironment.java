package at.ac.tuwien.foop.server.game.environment;

import at.ac.tuwien.foop.server.exception.PositionNotInEnvironmentException;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;
import at.ac.tuwien.foop.server.game.player.state.PlayerState;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a environment in which the players can be => Tunnel/Surface
 * Basically is an observable (I know there are Java Observables, but they are shit)
 */
public interface GameEnvironment {

    /**
     * subscribes a player for all gameState changes visible to this environment
     *
     * @param player the player that wants to be notified of all changes to this environment (e.g. a player that is in this tunnel, or on this surface)
     */
    default void enterEnvironment(Player player) {
        getPresentPlayers().add(player);
        getPresentPlayers().forEach(subscribedPlayer -> {
            player.updateKnownPlayerLocation(subscribedPlayer, this);
            subscribedPlayer.updateKnownPlayerLocation(player, this);
        });
    }

    /**
     * unsubscribes a player. (e.g. a mouse leaves this environment (surface) to go to another environment (tunnel)
     *
     * @param player             the player to be unsubscribed
     * @param environmentEntered the environment, where the player went to
     */
    default void leaveEnvironment(Player player, GameEnvironment environmentEntered) {
        getPresentPlayers().remove(player);
        getPresentPlayers().forEach(subscribedPlayer -> subscribedPlayer.updateKnownPlayerLocation(player, environmentEntered));
    }

    default void move(Player player, Position targetLocation) {
        validatePositionInEnvironment(targetLocation);

        if (isLeavingEnvironment(targetLocation)) {
            GameEnvironment environmentEntered = getAdjacentEnvironment(targetLocation);
            leaveEnvironment(player, environmentEntered);
            environmentEntered.enterEnvironment(player);
        }
        player.setPosition(targetLocation);
        getPresentPlayers().forEach(presentPlayer -> presentPlayer.updateKnownPlayerLocation(player, targetLocation));

        player.setWaiting();
    }

    default void validatePositionInEnvironment(Position position) {
        if (getEnvironmentArea().stream().noneMatch(position::equals)) {
            throw new PositionNotInEnvironmentException();
        }
    }

    default void playerStateChanged(Player player, PlayerState playerState) {
        getPresentPlayers().forEach(presentPlayer -> presentPlayer.updateKnownPlayerState(player, playerState));
    }

    Collection<Position> getEnvironmentArea();

    /**
     * @return all currently present players in this environment i.e. the subscribers of this observable
     */
    Collection<Player> getPresentPlayers();

    Map<Position, GameEnvironment> getEnvironmentTransitionMap();

    void setupEnvironment(Collection<Position> environmentArea, Map<Position, GameEnvironment> environmentTransitionMap);

    default boolean isLeavingEnvironment(Position position) {
        return getEnvironmentTransitionMap().containsKey(position);
    }

    default GameEnvironment getAdjacentEnvironment(Position position) {
        return getEnvironmentTransitionMap().get(position);
    }

    default boolean isPlayerPresent(Player player) {
        return getPresentPlayers().contains(player);
    }
}
