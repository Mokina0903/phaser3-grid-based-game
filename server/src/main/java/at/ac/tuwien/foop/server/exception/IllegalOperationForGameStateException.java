package at.ac.tuwien.foop.server.exception;

public class IllegalOperationForGameStateException extends GameException {

    public IllegalOperationForGameStateException(String operation, String gameState) {
        super(ErrorIds.ILLEGAL_OPERATION_FOR_GAME_STATE, String.format("Operation %s is not supported in current game state: %s", operation, gameState));
    }
}
