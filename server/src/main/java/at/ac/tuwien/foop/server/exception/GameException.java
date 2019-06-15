package at.ac.tuwien.foop.server.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GameException extends RuntimeException {

    private final long errorId;
    private final String message;

    GameException(String message) {
        this.message = message;
        this.errorId = ErrorIds.UNCATEGORIZED_SERVER;
    }

    GameException(long errorId, String message) {
        this.errorId = errorId;
        this.message = message;
    }
}
