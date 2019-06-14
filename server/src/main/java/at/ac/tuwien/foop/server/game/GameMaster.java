package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.game.environment.Surface;
import at.ac.tuwien.foop.server.game.environment.Tunnel;
import at.ac.tuwien.foop.server.game.player.Player;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The game master represents the state of the game
 * It knows at all times, where every player is and which state the game has
 * <p>
 * The GameMaster is essentially the 'persistence' layer of our application. Only 'Services' e.g.
 * MovementStrategy communicate with the GameMaster. The Endpoint never communicates directly with the GameMaster
 * <p>
 * The GameMaster expects already validated client data. Therefore the validations should be done in the 'Services'
 */
@Component
public class GameMaster {

    /**
     * The state of the game that contains all environments and all the data about all the players in these environments
     */
    GameModel gameModel;
    private Surface surface;
    private Collection<Tunnel> tunnels;
    private Collection<Player> loggedInPlayers;
    private Turn currentTurn;

    /**
     * Registers player to the current game
     *
     * @param player
     */
    void registerPlayer(Player player) {
        // TODO
    }

    public void endRoundIfAllPlayersHaveConfirmedMovement() {
        if (allPlayersInThisRoundReady()) {
            switchTurn();

            if (currentTurn.equals(Turn.CAT_TURN)) {
                loggedInPlayers.stream()
                        .filter(Player::isCat)
                        .forEach(Player::move);

                loggedInPlayers.stream()
                        .filter(Player::isMouse)
                        .forEach(Player::setPreparingMovement);
            } else if (currentTurn.equals(Turn.MOUSE_TURN)) {
                loggedInPlayers.stream()
                        .filter(Player::isMouse)
                        .forEach(Player::move);
                loggedInPlayers.stream()
                        .filter(Player::isCat)
                        .forEach(Player::setPreparingMovement);
            }

            getKilledPlayers().forEach(Player::setDead);
        }
    }

    /**
     * builds game environments i.e. builds the surface level and all tunnels
     */
    void buildGame() {
        Pair<Surface, Collection<Tunnel>> gameField = GameUtils.getGameField();
        surface = gameField.getKey();
        tunnels = gameField.getRight();
    }

    private boolean allPlayersInThisRoundReady() {
        if (currentTurn.equals(Turn.CAT_TURN)) {
            return loggedInPlayers.stream()
                    .filter(Player::isCat)
                    .allMatch(Player::isReady);
        } else if (currentTurn.equals(Turn.MOUSE_TURN)) {
            return loggedInPlayers.stream()
                    .filter(Player::isMouse)
                    .allMatch(Player::isReady);
        } else throw new IllegalStateException();
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
        } else if (currentTurn.equals(Turn.MOUSE_TURN)) {
            currentTurn = Turn.CAT_TURN;
        } else throw new IllegalStateException();
    }

    private Collection<Player> getKilledPlayers() {
        Stream<Player> mice = loggedInPlayers.stream()
                .filter(Predicate.not(Player::isDead))
                .filter(Player::isMouse);

        Stream<Player> cats = loggedInPlayers.stream()
                .filter(Predicate.not(Player::isDead))
                .filter(Player::isCat);

        return mice.filter(mouse -> cats.anyMatch(cat -> cat.getPosition().equals(mouse.getPosition()))).collect(Collectors.toList());
    }

    enum Turn {
        CAT_TURN,
        MOUSE_TURN
    }
}
