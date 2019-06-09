package at.ac.tuwien.foop.server.game.environment;

import at.ac.tuwien.foop.server.game.GameState;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

import java.util.Collection;

public class Tunnel implements GameEnvironment {

    /**
     * data structure to manage subscribed players
     */
    Collection<Player> subscribedPlayers;

    /**
     * All global points of the game field this environment uses
     *
     * This is important for tunnels, because they are underneath the surface game field
     * and will reuse global coordinates
     *
     * e.g. the game field is 2x2, so there are coordinates (Positions) [{0,0}, {0,1}, {1,0}, {1,1}]
     * These are the global coordinates.
     *
     * If a tunnel would lead from the lower left to the upper left square,
     * the tunnel environment instance would have the coordinates (Positions) [{0,0}, {0,1}]
     * So for a player, or the GameMaster its important to know the global position and also the environment a player is in
     */
    Collection<Position> positions;

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
}
