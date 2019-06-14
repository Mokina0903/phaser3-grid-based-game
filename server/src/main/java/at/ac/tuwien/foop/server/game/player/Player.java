package at.ac.tuwien.foop.server.game.player;

import at.ac.tuwien.foop.server.game.GameView;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.environment.GameEnvironment;
import at.ac.tuwien.foop.server.game.movement.MovementStrategy;
import at.ac.tuwien.foop.server.game.player.state.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    private final PlayerState waitingState = new PlayerStateWaiting();
    private final PlayerState deadState = new PlayerStateDead();
    private final PlayerState preparingMovementState = new PlayerStatePreparingMovement(this);
    private final PlayerState wonState = new PlayerStateWon();
    private final PlayerState lostState = new PlayerStateLost();

    private String name;
    private Long id;
    private Position position;
    private GameView knownStatus;
    private MovementStrategy movementStrategy;
    private GameEnvironment currentEnvironment;
    private PlayerState currentState;

    public void prepareMovement(Position targetLocation) {
        currentState.prepareMovement(targetLocation);
    }

    public void confirmMovement() {
        currentState.confirmMovement();
    }

    public void setWaiting() {
        this.currentState = waitingState;
    }

    public void setDead() {
        this.currentState = deadState;
    }

    public void setPreparingMovement() {
        this.currentState = preparingMovementState;
    }

    public void setMovementPrepared(Position targetLocation) {
        currentState = new PlayerStateMovementPrepared(this, targetLocation);
    }

    public void setMovementConfirmed(Position targetLocation) {
        currentState = new PlayerStateMovementConfirmed(this, targetLocation);
    }

    public void setWon() {
        currentState = wonState;
    }

    public void setLost() {
        currentState = lostState;
    }

    public boolean isReady() {
        return currentState.isReady();
    }

    public boolean isDead() {
        return currentState.isDead();
    }

    public boolean isCat() {
        return movementStrategy.isCat();
    }

    public boolean isMouse() {
        return movementStrategy.isMouse();
    }

    public void move() {
        currentState.move();
    }

    public void leaveEnvironment() {
        currentEnvironment.leaveEnvironment(this);
    }

    public void enterEnvironment(GameEnvironment environment) {
        environment.enterEnvironment(this);
        currentEnvironment = environment;
    }
}
