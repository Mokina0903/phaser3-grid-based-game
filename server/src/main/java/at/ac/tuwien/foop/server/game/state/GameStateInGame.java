package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.exception.IllegalOperationForGameStateException;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

public class GameStateInGame implements GameState {
    @Override
    public void login() {
        throw new IllegalOperationForGameStateException("login", "'in game'");
    }

    @Override
    public void prepareMovement(Player player, Position targetLocation) {
        player.prepareMovement(targetLocation);
    }

    @Override
    public void confirmMovement(Player player) {
        player.confirmMovement();
    }
}
