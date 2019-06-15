package at.ac.tuwien.foop.server.exception;

public class IllegalOperationForPlayerStateException extends GameException {

    public IllegalOperationForPlayerStateException(String operation, String playerState) {
        super(ErrorIds.ILLEGAL_OPERATION_FOR_PLAYER_STATE, String.format("Operation %s is not supported for player state: %s", operation, playerState));
    }
}
