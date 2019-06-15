package at.ac.tuwien.foop.server.exception;

import static at.ac.tuwien.foop.server.exception.ErrorIds.POSITION_NOT_IN_ENVIRONMENT;

public class PositionNotInEnvironmentException extends GameException {

    public PositionNotInEnvironmentException() {
        super(POSITION_NOT_IN_ENVIRONMENT, "The requested position is not in this environment");
    }
}
