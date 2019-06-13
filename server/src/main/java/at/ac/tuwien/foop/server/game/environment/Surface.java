package at.ac.tuwien.foop.server.game.environment;

import at.ac.tuwien.foop.server.exception.InvalidTransitionPositionsException;
import at.ac.tuwien.foop.server.game.GameMaster;
import at.ac.tuwien.foop.server.game.GameModel;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

import java.util.Collection;

public class Surface implements GameEnvironment {

    private final GameMaster gameMaster;

    private GameModel gameModel;

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
        subscribedPlayers.add(player);
    }

    @Override
    public void leaveEnvironment(Player player) {
        subscribedPlayers.remove(player);
    }

    @Override
    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public GameModel getGameModel() {
        return gameModel;
    }

    @Override
    public void notifyStateChanges() {
        subscribedPlayers.forEach(player -> player.setKnownState(gameModel));
    }

    @Override
    public Collection<Player> getPresentPlayers() {
        return subscribedPlayers;
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
