package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.game.environment.Surface;
import at.ac.tuwien.foop.server.game.environment.Tunnel;
import at.ac.tuwien.foop.server.game.player.Player;
import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.game.state.GameStatePreGame;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.List;
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
    private GameModel gameModel;
    private Surface surface;
    private Collection<Tunnel> tunnels;
    private Collection<Player> loggedInPlayers;
    private Turn currentTurn;
    private Tunnel targetTunnel;

    private GameState currentGameState = new GameStatePreGame();

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
                getCatStream().forEach(Player::move);
                getMiceStream().forEach(Player::setPreparingMovement);
            } else if (currentTurn.equals(Turn.MOUSE_TURN)) {
                getMiceStream().forEach(Player::move);
                getCatStream().forEach(Player::setPreparingMovement);
            }

            getKilledPlayers().forEach(Player::setDead);

            if (allMiceDead()) {
                getMiceStream().forEach(Player::setLost);
                getCatStream().forEach(Player::setWon);
            }
            else if (allLivingMiceInTargetTunnel()) {
                getMiceStream().forEach(Player::setWon);
                getCatStream().forEach(Player::setLost);
            }
        }
    }

    /**
     * builds game environments i.e. builds the surface level and all tunnels
     */
    void buildGame() {
        Pair<Surface, List<Tunnel>> gameField = GameUtils.getGameField();
        surface = gameField.getKey();
        tunnels = gameField.getRight();

        SecureRandom secureRandom = new SecureRandom();
        targetTunnel = gameField.getRight().get(secureRandom.nextInt(gameField.getRight().size()));
    }

    public GameState getCurrentGameState() {
        return currentGameState;
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

    private Stream<Player> getMiceStream() {
        return loggedInPlayers
                .stream()
                .filter(Player::isMouse);
    }

    private Stream<Player> getCatStream() {
        return loggedInPlayers
                .stream()
                .filter(Player::isCat);
    }

    private Collection<Player> getKilledPlayers() {
        Stream<Player> livingMice = getMiceStream()
                .filter(Predicate.not(Player::isDead));

        Stream<Player> livingCats = getCatStream()
                // There should not be any dead cats anyway
                .filter(Predicate.not(Player::isDead));

        return livingMice.filter(mouse -> livingCats.anyMatch(cat -> cat.getPosition().equals(mouse.getPosition()))).collect(Collectors.toList());
    }

    private boolean allMiceDead() {
        return getMiceStream().allMatch(Player::isDead);
    }

    private boolean allLivingMiceInTargetTunnel() {
        return getMiceStream()
                .filter(Predicate.not(Player::isDead))
                .allMatch(mouse -> targetTunnel.isPlayerPresent(mouse));
    }

    @PostConstruct
    void initialize() {
        currentGameState = new GameStatePreGame();
    }

    enum Turn {
        CAT_TURN,
        MOUSE_TURN
    }
}
