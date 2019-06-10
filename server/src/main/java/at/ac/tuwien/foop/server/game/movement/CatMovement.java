package at.ac.tuwien.foop.server.game.movement;

import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CatMovement implements MovementStrategy {

    /**
     * Movement speed of the cat (in squares/round)
     */
    private static final int MOVEMENT_SPEED = 2;

    private final Player player;

    @Override
    public void prepareMovement(Player player, Position targetPosition) {
        // TODO
    }

    @Override
    public void move(Player player) {
        // TODO
    }

    @Override
    public int getMovementSpeed() {
        return MOVEMENT_SPEED;
    }
}
