package at.ac.tuwien.foop.server.game.environment;

import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class Tunnel implements GameEnvironment {

    /**
     * data structure to manage subscribed players
     */
    private Collection<Player> subscribedPlayers = new HashSet<>();

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

    private Map<Position, GameEnvironment> tunnelExitMap;

    @Override
    public void enterEnvironment(Player player) {
        subscribedPlayers.add(player);
        notifyStateChanges();
    }

    @Override
    public void leaveEnvironment(Player player) {
        subscribedPlayers.remove(player);
        notifyStateChanges();
    }


    @Override
    public void notifyStateChanges() {
        // TODO update world view of all present players
    }

    @Override
    public Collection<Player> getPresentPlayers() {
        return subscribedPlayers;
    }

    @Override
    public Map<Position, GameEnvironment> getEnvironmentTransitionMap() {
        return tunnelExitMap;
    }

    @Override
    public void setupEnvironment(Collection<Position> environmentArea, Map<Position, GameEnvironment> environmentTransitionMap) {
        positions = environmentArea;
        tunnelExitMap = environmentTransitionMap;
    }
}
