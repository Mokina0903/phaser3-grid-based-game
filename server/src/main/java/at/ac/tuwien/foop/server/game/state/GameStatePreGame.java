package at.ac.tuwien.foop.server.game.state;

public class GameStatePreGame implements GameState {

    private static final String GAME_STATE = "PRE_GAME";

    @Override
    public void login() {
        // TODO login
    }

    @Override
    public String getGameState() {
        return GAME_STATE;
    }
}
