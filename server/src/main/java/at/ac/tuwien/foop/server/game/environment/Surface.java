package at.ac.tuwien.foop.server.game.environment;

import at.ac.tuwien.foop.server.exception.InvalidTransitionPositionsException;
import at.ac.tuwien.foop.server.game.GameMaster;
import at.ac.tuwien.foop.server.game.GameState;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

import java.util.Collection;

public class Surface implements GameEnvironment {

    private final GameMaster gameMaster;

    /**
     * data structure to manage subscribed players
     */
    Collection<Player> subscribedPlayers;

    /**
     * All global points of the game field this environment uses
     */
    Collection<Position> positions;

    /**
     * All entries to tunnels. The Surface (or the players on the surface) do not know, how the tunnels are connected
     */
    Collection<Position> tunnelEntries;

    public Surface(GameMaster gameMaster) {
        this.gameMaster = gameMaster;
    }

    @Override
    public void enterEnvironment(Player player) {
        // TODO
    }

    @Override
    public void leaveEnvironment(Player player) {
        // TODO
    }

    @Override
    public void setGameState(GameState gameState) {
        // TODO
    }

    @Override
    public GameState getGameState() {
        // TODO
        return null;
    }

    @Override
    public void notifyStateChanges() {
        // TODO
    }

    @Override
    public Collection<Player> getPresentPlayers() {
        // TODO
        return null;
    }

    @Override
    public Collection<Position> getEnvironmentTransitionPositions() {
        return tunnelEntries;
    }

    @Override
    public void setupEnvironment(Collection<Position> environmentArea, Collection<Position> environmentTransitionPositions) {
        environmentTransitionPositions.stream().filter(position -> !environmentArea.contains(position)).findFirst().ifPresentOrElse(
                invalidPosition -> {
                    throw new InvalidTransitionPositionsException(invalidPosition, environmentArea);
                },
                () -> {
                    this.positions = environmentArea;
                    this.tunnelEntries = environmentTransitionPositions;
                }
        );
    }

    @Override
    public GameEnvironment getAdjacentEnvironment(Position position) {
        return gameMaster.getTunnelForPosition(position);
    }
}
