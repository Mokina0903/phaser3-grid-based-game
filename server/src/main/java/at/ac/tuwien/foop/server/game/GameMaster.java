package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.exception.NoMovementPreparedException;
import at.ac.tuwien.foop.server.game.environment.Surface;
import at.ac.tuwien.foop.server.game.environment.Tunnel;
import at.ac.tuwien.foop.server.game.movement.CatMovement;
import at.ac.tuwien.foop.server.game.movement.MouseMovement;
import at.ac.tuwien.foop.server.game.player.Player;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The game master represents the state of the game
 * It knows at all times, where every player is and which state the game has
 *
 * The GameMaster is essentially the 'persistence' layer of our application. Only 'Services' e.g.
 * MovementStrategy communicate with the GameMaster. The Endpoint never communicates directly with the GameMaster
 *
 * The GameMaster expects already validated client data. Therefore the validations should be done in the 'Services'
 */
@Component
public class GameMaster {

    private Surface surface;

    private Collection<Tunnel> tunnels;

    Collection<Player> loggedInPlayers;

    private final Map<Player, Position> targetLocationMap = new ConcurrentHashMap<>();

    private final Map<Player, Player.PlayerState> playerStateMap = new ConcurrentHashMap<>();

    private Turn currentTurn;

    /**
     * The state of the game that contains all environments and all the data about all the players in these environments
     */
    GameState gameState;

    /**
     * Registers player to the current game
     *
     * @param player
     */
    void registerPlayer(Player player) {
        // TODO
    }

    /**
     * Prepares movement for the player
     *
     * @param player the player that should be moved
     * @param targetPosition the position the player wants to go to
     */
    public void prepareMovementForPlayer(Player player, Position targetPosition) {
        targetLocationMap.putIfAbsent(player, targetPosition);
    }

    /**
     * moves player to the targetLocation, checks if an environment change is about to happen and puts player in the correct environment
     *
     * @param player The player that should be moved
     */
    public void confirmMovement(Player player) {
        if (targetLocationMap.get(player) == null) {
            throw new NoMovementPreparedException(player.getId());
        }
        else {
            playerStateMap.put(player, Player.PlayerState.WAITING);
            if (allPlayersInThisRoundReady()) {
                targetLocationMap
                        .keySet()
                        .forEach(movingPlayer -> movingPlayer.move(targetLocationMap.get(movingPlayer)));

                switchTurn();
            }
        }
    }

    /**
     * builds game environments i.e. builds the surface level and all tunnels
     */
    void buildGame() {
        // TODO
    }

    public Surface getSurface() {
        return surface;
    }

    public Tunnel getTunnelForPosition(Position position) {
        return tunnels.stream()
                .filter(tunnel -> tunnel.getEnvironmentTransitionPositions().contains(position))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public Position getPreparedMovementForPlayer(Player player) {
        return targetLocationMap.get(player);
    }

    public Player.PlayerState getPlayerStateForPlayer(Player player) {
        return playerStateMap.get(player);
    }

    private boolean allPlayersInThisRoundReady() {
        if (currentTurn.equals(Turn.CAT_TURN)) {
            return loggedInPlayers.stream()
                    .filter(player -> player.getMovementStrategy() instanceof CatMovement)
                    .allMatch(player -> playerStateMap.get(player).equals(Player.PlayerState.WAITING));
        }
        else if (currentTurn.equals(Turn.MOUSE_TURN)) {
            return loggedInPlayers.stream()
                    .filter(player -> player.getMovementStrategy() instanceof MouseMovement)
                    .allMatch(player -> playerStateMap.get(player).equals(Player.PlayerState.WAITING));
        }
        else throw new IllegalStateException();
    }

    private void broadcastGameState() {
        // TODO
    }

    private Environment findTunnelForPosition(Position position) {
        // TODO

        return null;
    }

    private void switchTurn() {
        if (currentTurn.equals(Turn.CAT_TURN)) {
            currentTurn = Turn.MOUSE_TURN;
        }
        else if (currentTurn.equals(Turn.MOUSE_TURN)) {
            currentTurn = Turn.CAT_TURN;
        }
        else throw new IllegalStateException();
    }

    enum Turn {
        CAT_TURN,
        MOUSE_TURN
    }
}
