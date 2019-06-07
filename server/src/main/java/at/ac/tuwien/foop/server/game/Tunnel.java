package at.ac.tuwien.foop.server.game;

import java.util.Collection;

public class Tunnel implements GameEnvironment {

    /**
     * data structure to manage subscribed players
     */
    Collection<Player> subscribedPlayers;

    @Override
    public void subscribe(Player player) {
        // TODO
    }

    @Override
    public void unsubscribe(Player player) {
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
}
