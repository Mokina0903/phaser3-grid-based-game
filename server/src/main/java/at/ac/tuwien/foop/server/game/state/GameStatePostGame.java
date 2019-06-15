package at.ac.tuwien.foop.server.game.state;

public class GameStatePostGame implements GameState {

    private static final String GAME_STATE = "POST_GAME";

    @Override
    public String getGameState() {
        return GAME_STATE;
    }
}
