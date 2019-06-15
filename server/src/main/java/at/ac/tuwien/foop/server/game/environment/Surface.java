package at.ac.tuwien.foop.server.game.environment;

import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class Surface implements GameEnvironment {

    /**
     * data structure to manage subscribed players
     */
    private final Collection<Player> subscribedPlayers = new HashSet<>();

    /**
     * All global points of the game field this environment uses
     */
    private Collection<Position> positions = new HashSet<>();

    /**
     * All entries to tunnels. The Surface (or the players on the surface) do not know, how the tunnels are connected
     */
    private Map<Position, GameEnvironment> tunnelEntries;

    @Override
    public Collection<Position> getEnvironmentArea() {
        return positions;
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
