package at.ac.tuwien.foop.server.game.environment;

import at.ac.tuwien.foop.server.game.GameState;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

import java.util.Collection;

public class Surface implements GameEnvironment {

    /**
     * data structure to manage subscribed players
     */
    Collection<Player> subscribedPlayers;

    /**
     * All global points of the game field this environment uses
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
