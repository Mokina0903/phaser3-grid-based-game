package at.ac.tuwien.foop.server.exception;


import static at.ac.tuwien.foop.server.exception.ErrorIds.INVALID_TOKEN;

public class InvalidTokenException extends GameException {

    public InvalidTokenException() {
        super(INVALID_TOKEN, "The token signature is not valid");
    }
}
