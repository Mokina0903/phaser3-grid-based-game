package at.ac.tuwien.foop.server.game.state;

public class PlayerStateLost implements PlayerState {

    private static final String PLAYER_STATE = "LOST";

    @Override
    public String getGameState() {
        return PLAYER_STATE;
    }
}
