package at.ac.tuwien.foop.server.exception;

final class ErrorIds {

    private ErrorIds() {}

    static final int UNCATEGORIZED_SERVER = 1;
    static final int CANNOT_MOVE_SO_FAR = 2;
    static final int NO_MOVEMENT_PREPARED = 3;
    static final int INVALID_PLAYER_STATE = 4;
    static final int ILLEGAL_OPERATION_FOR_PLAYER_STATE = 5;
    static final int ILLEGAL_OPERATION_FOR_GAME_STATE = 6;
}
