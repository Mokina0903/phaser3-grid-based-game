package at.ac.tuwien.foop.server.game.environment;

import at.ac.tuwien.foop.server.exception.InvalidTransitionPositionsException;
import at.ac.tuwien.foop.server.game.GameMaster;
import at.ac.tuwien.foop.server.game.GameState;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

import java.util.Collection;

public class Tunnel implements GameEnvironment {

    private final GameMaster gameMaster;

    /**
     * data structure to manage subscribed players
     */
    private Collection<Player> subscribedPlayers;

    /**
     * All global points of the game field this environment uses
     * <p>
     * This is important for tunnels, because they are underneath the surface game field
     * and will reuse global coordinates
     * <p>
     * e.g. the game field is 2x2, so there are coordinates (Positions) [{0,0}, {0,1}, {1,0}, {1,1}]
     * These are the global coordinates.
     * <p>
     * If a tunnel would lead from the lower left to the upper left square,
     * the tunnel environment instance would have the coordinates (Positions) [{0,0}, {0,1}]
     * So for a player, or the GameMaster its important to know the global position and also the environment a player is in
     */
    private Collection<Position> positions;

    private GameState gameState;

    private Collection<Position> tunnelExits;

    public Tunnel(GameMaster gameMaster) {
        this.gameMaster = gameMaster;
    }

    @Override
    public void enterEnvironment(Player player) {
        subscribedPlayers.add(player);
    }

    @Override
    public void leaveEnvironment(Player player) {
        subscribedPlayers.remove(player);
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        notifyStateChanges();
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void notifyStateChanges() {
        subscribedPlayers.forEach(player -> player.setKnownState(gameState));
    }

    @Override
    public Collection<Player> getPresentPlayers() {
        return subscribedPlayers;
    }

    @Override
    public Collection<Position> getEnvironmentTransitionPositions() {
        return tunnelExits;
    }

    @Override
    public void setupEnvironment(Collection<Position> environmentArea, Collection<Position> environmentTransitionPositions) {
        environmentTransitionPositions.stream().filter(position -> !environmentArea.contains(position)).findFirst().ifPresentOrElse(
                invalidPosition -> {
                    throw new InvalidTransitionPositionsException(invalidPosition, environmentArea);
                },
                () -> {
                    this.positions = environmentArea;
                    this.tunnelExits = environmentTransitionPositions;
                }
        );
    }

    @Override
    public GameEnvironment getAdjacentEnvironment(Position position) {
        return gameMaster.getSurface();
    }
}
