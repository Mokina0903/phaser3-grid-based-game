package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerStatePreparingMovement implements PlayerState {

    private static final String GAME_STATE = "DEAD";

    private final Player player;

    @Override
    public void prepareMovement(Position position) {
        player.getMovementStrategy().validatePrepareMovement(player, position);
        player.setMovementPrepared(position);
    }

    @Override
    public String getGameState() {
        return GAME_STATE;
    }
}
