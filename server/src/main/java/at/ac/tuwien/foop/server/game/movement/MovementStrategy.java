package at.ac.tuwien.foop.server.game.movement;

import at.ac.tuwien.foop.server.exception.CannotMoveSoFarException;
import at.ac.tuwien.foop.server.game.GameUtils;
import at.ac.tuwien.foop.server.game.Position;
import at.ac.tuwien.foop.server.game.player.Player;

/**
 * Prepares and executes movement for ONE player (every player has their movement strategy)
 * Also validates if movement would be valid
 */
public interface MovementStrategy {

    /**
     * logs in confirmMovement command for player and validates if movement is valid
     * e.g. checks if the given player can confirmMovement that far, or if the player would leave the field, etc..
     *
     * @param player the player that wants to confirmMovement (has reference to their current position)
     * @param targetPosition the Position, where the player wants to go to
     * @throws at.ac.tuwien.foop.server.exception.GameException in case something went wrong or target location is invalid
     */
    void validatePrepareMovement(Player player, Position targetPosition);

    // example implementation --> change if needed

    default void validateMovementPossible(Player player, Position targetPosition) {
        int movementSpeed = getMovementSpeed();

        int distance = GameUtils.getTotalDistance(player.getPosition(), targetPosition);

        if (distance > movementSpeed) {
            throw new CannotMoveSoFarException(player, distance);
        }
    }

    /**
     * @return the movement speed of the player
     */
    int getMovementSpeed();

    default boolean isMouse() {
        return false;
    }

    default boolean isCat() {
        return false;
    }
}
