package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

public class GameStateInGame implements GameState {

    private static final String GAME_STATE = "IN_GAME";

    @Override
    public void prepareMovement(Player player, Position targetLocation) {
        player.prepareMovement(targetLocation);
    }

    @Override
    public void confirmMovement(Player player) {
        player.confirmMovement();
    }

    @Override
    public String getGameState() {
        return GAME_STATE;
    }
}
