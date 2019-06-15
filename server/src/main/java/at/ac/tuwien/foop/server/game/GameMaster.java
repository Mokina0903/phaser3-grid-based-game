package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.dto.LoginResponse;
import at.ac.tuwien.foop.server.dto.PlayerRequest;
import at.ac.tuwien.foop.server.game.environment.Surface;
import at.ac.tuwien.foop.server.game.environment.Tunnel;
import at.ac.tuwien.foop.server.game.player.Player;
import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.game.state.GameStateInGame;
import at.ac.tuwien.foop.server.game.state.GameStatePostGame;
import at.ac.tuwien.foop.server.game.state.GameStatePreGame;
import at.ac.tuwien.foop.server.repository.PlayerRepository;
import at.ac.tuwien.foop.server.service.TokenService;
import org.apache.commons.lang3.tuple.Pair;
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
public class GameMaster implements GameState {

    private final TokenService tokenService;
    private final PlayerRepository playerRepository;

    /**
     * The state of the game that contains all environments and all the data about all the players in these environments
     */
    private GameModel gameModel;
    private Surface surface;
    private Collection<Tunnel> tunnels;
    private Turn currentTurn;
    private Tunnel targetTunnel;

    private GameState currentGameState;

    public GameMaster(TokenService tokenService, PlayerRepository playerRepository) {
        this.tokenService = tokenService;
        this.playerRepository = playerRepository;
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

    private void endRoundIfAllPlayersHaveConfirmedMovement() {
        if (allPlayersInThisRoundReady()) {
            switchTurn();

            if (currentTurn.equals(Turn.CAT_TURN)) {
                getCatStream().forEach(Player::move);
                getMiceStream().forEach(Player::setPreparingMovement);
            } else if (currentTurn.equals(Turn.MOUSE_TURN)) {
                getMiceStream().forEach(Player::move);
                getCatStream().forEach(Player::setPreparingMovement);
            }

            getKilledPlayers().forEach(Player::die);

            if (allMiceDead()) {
                getMiceStream().forEach(Player::setLost);
                getCatStream().forEach(Player::setWon);

                currentGameState = new GameStatePostGame();
            } else if (allLivingMiceInTargetTunnel()) {
                getMiceStream().forEach(Player::setWon);
                getCatStream().forEach(Player::setLost);

                currentGameState = new GameStatePostGame();
            }
        }
    }

    private void buildAndStartGameIfAllAreReady() {
        if (allPlayers().allMatch(Player::isReadyForGame)) {
            SecureRandom random = new SecureRandom();
            int randomNumber = random.nextInt(2);

            if (randomNumber == 0) {
                // Cats start
                allPlayers().filter(Player::isCat).forEach(Player::setPreparingMovement);
                allPlayers().filter(Player::isMouse).forEach(Player::setWaiting);
            }
            else {
                // Mice start
                allPlayers().filter(Player::isMouse).forEach(Player::setPreparingMovement);
                allPlayers().filter(Player::isCat).forEach(Player::setWaiting);
            }

            currentGameState = new GameStateInGame();
        }
    }

    private boolean allPlayersInThisRoundReady() {
        if (currentTurn.equals(Turn.CAT_TURN)) {
            return allPlayers()
                    .filter(Player::isCat)
                    .allMatch(Player::isReadyForTurn);
        } else if (currentTurn.equals(Turn.MOUSE_TURN)) {
            return allPlayers()
                    .filter(Player::isMouse)
                    .allMatch(Player::isReadyForTurn);
        } else throw new IllegalStateException();
    }

    private void broadcastGameState() {
        // TODO
    }

    private void switchTurn() {
        if (currentTurn.equals(Turn.CAT_TURN)) {
            currentTurn = Turn.MOUSE_TURN;
        } else if (currentTurn.equals(Turn.MOUSE_TURN)) {
            currentTurn = Turn.CAT_TURN;
        } else throw new IllegalStateException();
    }

    private Stream<Player> getMiceStream() {
        return allPlayers()
                .filter(Player::isMouse);
    }

    private Stream<Player> getCatStream() {
        return allPlayers()
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

    private Stream<Player> allPlayers() {
        return playerRepository.findAll().stream();
    }

    @Override
    public LoginResponse createPlayer(String name) {
        return currentGameState.createPlayer(name);
    }

    @Override
    public Player updatePlayer(PlayerRequest playerRequest) {
        return currentGameState.updatePlayer(playerRequest);
    }

    @Override
    public Player setPlayerReadyForGame(Player player) {
        Player readyPlayer = currentGameState.setPlayerReadyForGame(player);
        buildAndStartGameIfAllAreReady();
        return readyPlayer;
    }

    @Override
    public void prepareMovement(Player player, Position targetLocation) {
        currentGameState.prepareMovement(player, targetLocation);
    }

    @Override
    public void confirmMovement(Player player) {
        currentGameState.confirmMovement(player);
        endRoundIfAllPlayersHaveConfirmedMovement();
    }

    @Override
    public String getGameState() {
        return currentGameState.getGameState();
    }

    @PostConstruct
    void initialize() {
        currentGameState = new GameStatePreGame(playerRepository, tokenService);
    }

    enum Turn {
        CAT_TURN,
        MOUSE_TURN
    }
}
