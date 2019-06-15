package at.ac.tuwien.foop.server.exception;


import static at.ac.tuwien.foop.server.exception.ErrorIds.PLAYER_ALREADY_EXISTS;

public class PlayerAlreadyExistsException extends GameException {

    public PlayerAlreadyExistsException() {
        super(PLAYER_ALREADY_EXISTS, "This player id is already in use, oops");
    }
}
