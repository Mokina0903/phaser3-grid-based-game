package at.ac.tuwien.foop.server.exception;

import at.ac.tuwien.foop.server.game.player.Player;

public class InvalidPlayerStateException extends GameException {

    public InvalidPlayerStateException(Player.PlayerState expected, Player.PlayerState actual) {
        super(ErrorIds.INVALID_PLAYER_STATE, String.format("Expected player state %s, but got %s", expected, actual));
    }
}
