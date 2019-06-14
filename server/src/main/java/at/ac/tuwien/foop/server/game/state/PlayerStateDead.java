package at.ac.tuwien.foop.server.game.state;

public class PlayerStateDead implements PlayerState {

    private static final String PLAYER_STATE = "DEAD";

    @Override
    public boolean isDead() {
        return true;
    }

    @Override
    public String getGameState() {
        return PLAYER_STATE;
    }
}
