package at.ac.tuwien.foop.server.service;


import at.ac.tuwien.foop.server.exception.GameException;
import at.ac.tuwien.foop.server.game.player.Player;
import at.ac.tuwien.foop.server.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PrepareService {

    private final PlayerRepository playerRepository;
    private final TokenService tokenService;

    public PrepareService(PlayerRepository playerRepository, TokenService tokenService) {
        this.playerRepository = playerRepository;
        this.tokenService = tokenService;
    }

    public Player login(String name) {
        prepareStateActive();
        Player player = playerRepository.createNewPlayer(name);

    }

    /**
     * Checks if the server is in state PREPARE
     *
     * @throws GameException if the server is not in state PREPARE
     */
    private void prepareStateActive() throws GameException {
        // TODO
    }
}
