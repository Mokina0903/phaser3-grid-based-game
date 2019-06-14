package at.ac.tuwien.foop.server.exception;

import at.ac.tuwien.foop.server.game.player.Player;

import static at.ac.tuwien.foop.server.exception.ErrorIds.CANNOT_MOVE_SO_FAR;

public class CannotMoveSoFarException extends GameException {

    public CannotMoveSoFarException(Player player, int distance) {
        super(CANNOT_MOVE_SO_FAR, String.format("Player: %d cannot confirmMovement so far (%d squares)", player.getId(), distance));
    }
}
