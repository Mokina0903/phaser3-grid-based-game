package at.ac.tuwien.foop.server.game.player.state;

import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerStateMovementConfirmed implements PlayerState {

    private static final String PLAYER_STATE = "DEAD";

    private final Player player;
    private final Position targetLocation;

    @Override
    public boolean isReadyForTurn() {
        return true;
    }

    @Override
    public void move() {
        player.getCurrentEnvironment().move(player, targetLocation);
    }

    @Override
    public String getGameState() {
        return PLAYER_STATE;
    }
}
