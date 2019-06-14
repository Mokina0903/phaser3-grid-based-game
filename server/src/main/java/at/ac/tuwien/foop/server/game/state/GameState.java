package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

public interface GameState {

    void login();

    void prepareMovement(Player player, Position targetLocation);

    void confirmMovement(Player player);
}
