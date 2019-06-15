package at.ac.tuwien.foop.server.exception;

import static at.ac.tuwien.foop.server.exception.ErrorIds.NO_MOVEMENT_PREPARED;

@Deprecated
public class NoMovementPreparedException extends GameException {

    public NoMovementPreparedException(long playerId) {
        super(NO_MOVEMENT_PREPARED, String.format("Player %d did not prepare any movement", playerId));
    }
}
