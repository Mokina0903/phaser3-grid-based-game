package at.ac.tuwien.foop.server.exception;

import at.ac.tuwien.foop.server.game.Position;

import java.util.Collection;

@Deprecated
public class InvalidTransitionPositionsException extends GameException {

    public InvalidTransitionPositionsException(Position position, Collection<Position> positions) {
        super("Position: " + position + " does not occur in list of positions: " + positions.toString());
    }
}
