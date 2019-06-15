package at.ac.tuwien.foop.server.game.player.state;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerStateReadyForGame implements PlayerState {

    private static final String PLAYER_STATE = "READY_FOR_GAME";

    @Override
    public boolean isReadyForGame() {
        return true;
    }

    @Override
    public String getGameState() {
        return PLAYER_STATE;
    }
}
