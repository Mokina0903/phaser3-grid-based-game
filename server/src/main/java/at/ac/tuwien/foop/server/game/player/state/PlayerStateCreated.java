package at.ac.tuwien.foop.server.game.player.state;

import at.ac.tuwien.foop.server.game.player.Player;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerStateCreated implements PlayerState {

    private static final String PLAYER_STATE = "CREATED";

    private final Player player;

    @Override
    public void setPlayerReady() {
        player.setPlayerReady();
    }

    @Override
    public String getGameState() {
        return PLAYER_STATE;
    }
}
