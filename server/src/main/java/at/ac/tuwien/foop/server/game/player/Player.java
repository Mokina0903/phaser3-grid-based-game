package at.ac.tuwien.foop.server.game.player;

import at.ac.tuwien.foop.server.game.GameState;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.movement.MovementStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    private String name;
    private Long id;
    private Position position;
    private GameState knownState;
    private MovementStrategy movementStrategy;

    public void prepareMovement(Position targetLocation) {
        movementStrategy.prepareMovement(this, targetLocation);
    }

    public void move() {
        movementStrategy.move(this);
    }
}
