package at.ac.tuwien.foop.server.game.environment;

import at.ac.tuwien.foop.server.game.GameModel;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class Surface implements GameEnvironment {

    private GameModel gameModel;

    /**
     * data structure to manage subscribed players
     */
    private Collection<Player> subscribedPlayers = new HashSet<>();

    /**
     * All global points of the game field this environment uses
     */
    private Collection<Position> positions;

    /**
     * All entries to tunnels. The Surface (or the players on the surface) do not know, how the tunnels are connected
     */
    private Map<Position, GameEnvironment> tunnelEntries;

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
    public Map<Position, GameEnvironment> getEnvironmentTransitionMap() {
        return tunnelEntries;
    }

    @Override
    public void setupEnvironment(Collection<Position> environmentArea, Map<Position, GameEnvironment> environmentTransitionMap) {
        positions = environmentArea;
        tunnelEntries = environmentTransitionMap;
    }
}
