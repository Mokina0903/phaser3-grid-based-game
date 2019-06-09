package at.ac.tuwien.foop.server.game;

public class GameUtils {

    private GameUtils() {}

    public static int getTotalDistance(Position position1, Position position2) {
        return Math.abs(position1.getXPosition() - position2.getXPosition())
                +
                Math.abs(position1.getYPosition() - position2.getYPosition());
    }
}
