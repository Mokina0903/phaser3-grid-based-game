package at.ac.tuwien.foop.server.service;


import at.ac.tuwien.foop.server.dto.LoginResponse;
import at.ac.tuwien.foop.server.dto.PlayerRequest;
import at.ac.tuwien.foop.server.exception.BadRequestException;
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

    public LoginResponse createPlayer(String name) {
        prepareStateActive();
        Player player = playerRepository.createPlayer(name);
        String token = tokenService.createNewToken(player.getId());
        return LoginResponse.builder()
                .token(token)
                .player(player)
                .build();
    }

    public Player updatePlayer(PlayerRequest playerRequest) {
        prepareStateActive();
        Player player = playerRequest.getPlayer();
        if(player.getCurrentEnvironment() != null || player.getKnownState() != null || player.getPosition() != null) {
            throw new BadRequestException();
        }
        return playerRepository.updatePlayer(player);
    }

    public Player setPlayerReady(Long id) {
        prepareStateActive();
        // TODO
        return null;
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
