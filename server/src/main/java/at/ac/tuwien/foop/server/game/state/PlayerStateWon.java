package at.ac.tuwien.foop.server.game.state;

public class PlayerStateWon implements PlayerState {

    private static final String PLAYER_STATE = "WON";

    @Override
    public String getGameState() {
        return PLAYER_STATE;
    }
}
