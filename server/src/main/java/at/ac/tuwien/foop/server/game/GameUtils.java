package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.game.environment.GameEnvironment;
import at.ac.tuwien.foop.server.game.environment.Surface;
import at.ac.tuwien.foop.server.game.environment.Tunnel;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class GameUtils {

    private GameUtils() {
    }

    public static int getTotalDistance(Position position1, Position position2) {
        return Math.abs(position1.getXPosition() - position2.getXPosition())
                +
                Math.abs(position1.getYPosition() - position2.getYPosition());
    }

    static Pair<Surface, Collection<Tunnel>> getGameField() {

        int xDimensions = 10;
        int yDimensions = 10;

        Collection<Position> allGlobalPositions = new LinkedList<>();

        for (int i = 0; i < xDimensions; i++) {
            for (int j = 0; j < yDimensions; j++) {
                allGlobalPositions.add(new Position(i, j));
            }
        }

        Collection<Position> tunnel1Area = new LinkedList<>();
        for (int i = 0; i < xDimensions; i++) {
            tunnel1Area.add(Position.builder().xPosition(i).yPosition(0).build());
        }
        Collection<Position> tunnel1AreaTransitionPositions =
                List.of(
                        Position.builder().xPosition(0).yPosition(0).build(),
                        Position.builder().xPosition(9).yPosition(0).build()
                );



        Tunnel tunnel1 = new Tunnel();
        Surface surface = new Surface();

        Map<Position, GameEnvironment> tunnelExits = new HashMap<>();
        Map<Position, GameEnvironment> tunnelEntries = new HashMap<>();

        tunnel1AreaTransitionPositions.forEach(position ->  {
            tunnelExits.put(position, surface);
            tunnelEntries.put(position, tunnel1);
        });

        tunnel1.setupEnvironment(tunnel1Area, tunnelExits);
        surface.setupEnvironment(allGlobalPositions, tunnelEntries);

        return Pair.of(surface, Set.of(tunnel1));
    }
}
