package at.ac.tuwien.foop.server.game.player.state;

import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.environment.GameEnvironment;
import at.ac.tuwien.foop.server.game.player.Player;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerStateMovementConfirmed implements PlayerState {

    private static final String PLAYER_STATE = "DEAD";

    private final Player player;
    private final Position targetLocation;

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void move() {
        if (player.getCurrentEnvironment().isLeavingEnvironment(targetLocation)) {
            GameEnvironment environmentEntered = player.getCurrentEnvironment().getAdjacentEnvironment(targetLocation);
            player.leaveEnvironment();
            player.enterEnvironment(environmentEntered);
        }
        player.setPosition(targetLocation);
        player.setWaiting();
    }

    @Override
    public String getGameState() {
        return PLAYER_STATE;
    }
}
