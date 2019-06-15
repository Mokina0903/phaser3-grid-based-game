package at.ac.tuwien.foop.server.exception;


import static at.ac.tuwien.foop.server.exception.ErrorIds.BAD_REQUEST;

public class BadRequestException extends GameException {

    public BadRequestException() {
        super(BAD_REQUEST, "Bad request");
    }
}
