package at.ac.tuwien.foop.server.game.player;

import at.ac.tuwien.foop.server.game.GameState;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.environment.GameEnvironment;
import at.ac.tuwien.foop.server.game.movement.MovementStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    private Position position;

    private GameState knownState;

    @NotNull
    private MovementStrategy movementStrategy;

    private GameEnvironment currentEnvironment;

    public void prepareMovement(Position targetLocation) {
        movementStrategy.prepareMovement(this, targetLocation);
    }

    public void move() {
        movementStrategy.move(this);
    }

    public void move(Position targetLocation) {
        if (currentEnvironment.isLeavingEnvironment(targetLocation)) {
            GameEnvironment nextEnvironment = currentEnvironment.getAdjacentEnvironment(targetLocation);

            currentEnvironment.leaveEnvironment(this);
            nextEnvironment.enterEnvironment(this);
        }
        position = targetLocation;
    }
}
