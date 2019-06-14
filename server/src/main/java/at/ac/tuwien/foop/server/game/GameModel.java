package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.game.environment.GameEnvironment;

import java.util.Collection;

public class GameModel {

    private Collection<GameEnvironment> environments;

    private GameState currentGameState = GameState.PRE_GAME;

    public void startGame() {
        currentGameState = GameState.IN_GAME;
    }

    public void endGame() {
        currentGameState = GameState.POST_GAME;
    }

    enum GameState {
        PRE_GAME,
        IN_GAME,
        POST_GAME
    }
}
