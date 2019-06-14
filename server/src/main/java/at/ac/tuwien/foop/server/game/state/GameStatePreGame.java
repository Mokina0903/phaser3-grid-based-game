package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.exception.IllegalOperationForGameStateException;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

public class GameStatePreGame implements GameState {
    @Override
    public void login() {
        // TODO login
    }

    @Override
    public void prepareMovement(Player player, Position targetLocation) {
        throw new IllegalOperationForGameStateException("movement preparation", "'pre game'");
    }

    @Override
    public void confirmMovement(Player player) {
        throw new IllegalOperationForGameStateException("movement", "'movement preparation'");
    }
}
