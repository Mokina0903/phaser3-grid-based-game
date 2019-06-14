package at.ac.tuwien.foop.server.game.state;

public class PlayerStateWaiting implements PlayerState {

    private static final String GAME_STATE = "DEAD";

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public String getGameState() {
        return GAME_STATE;
    }
}
