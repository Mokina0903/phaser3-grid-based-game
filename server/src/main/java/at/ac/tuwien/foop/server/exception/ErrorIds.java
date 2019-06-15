package at.ac.tuwien.foop.server.exception;

final class ErrorIds {

    private ErrorIds() {}

    static final Integer UNCATEGORIZED_SERVER = 1;
    static final Integer CANNOT_MOVE_SO_FAR = 2;
    static final Integer PLAYER_ALREADY_EXISTS = 3;
    static final Integer INVALID_TOKEN = 4;
    static final Integer PLAYER_NOT_FOUND = 5;
    static final Integer NO_MOVEMENT_PREPARED = 6;
    static final Integer BAD_REQUEST = 7;
    @Deprecated
    static final Integer INVALID_PLAYER_STATE = 8;
    static final Integer ILLEGAL_OPERATION_FOR_PLAYER_STATE = 9;
    static final Integer ILLEGAL_OPERATION_FOR_GAME_STATE = 10;
    static final Integer POSITION_NOT_IN_ENVIRONMENT = 11;
    static final Integer PLAYER_NOT_IN_ENVIRONMENT = 12;
}
