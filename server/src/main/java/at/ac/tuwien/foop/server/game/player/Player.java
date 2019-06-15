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

import javax.validation.constraints.NotNull;

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
    private final PlayerState readyForGameState = new PlayerStateReadyForGame();

    @NotNull
    private Long id;

    @NotNull
    private String name;

    private Position position;

    @NotNull
    private GameView knownStatus;

    private MovementStrategy movementStrategy;

    private GameEnvironment currentEnvironment;

    private PlayerState currentState;

    // player actions that are dependent on player state

    public void prepareMovement(Position targetLocation) {
        currentState.prepareMovement(targetLocation);
    }

    public void confirmMovement() {
        currentState.confirmMovement();
    }

    public void move() {
        currentState.move();
    }

    // Player state progression

    public void setPlayerReady() {
        currentState.setPlayerReady();
    }

    public void setWaiting() {
        this.currentState = waitingState;
    }

    private void setDead() {
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

    // game view methods

    public void updateKnownPlayerLocation(Player player, GameEnvironment gameEnvironment) {
        knownStatus.updatePlayerLocation(player, gameEnvironment);
    }

    public void updateKnownPlayerLocation(Player player, Position position) {
        knownStatus.updatePlayerLocation(player, position);
    }

    public void updateKnownPlayerState(Player player, PlayerState playerState) {
        knownStatus.updatePlayerState(player, playerState);
    }

    // Helper methods

    public boolean isReadyForGame() {
        return currentState.isReadyForGame();
    }

    public boolean isReadyForTurn() {
        return currentState.isReadyForTurn();
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

    public void die() {
        setDead();
        currentEnvironment.playerStateChanged(this, currentState);
    }
}
