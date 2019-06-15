package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.dto.LoginResponse;
import at.ac.tuwien.foop.server.dto.PlayerRequest;
import at.ac.tuwien.foop.server.exception.BadRequestException;
import at.ac.tuwien.foop.server.game.player.Player;
import at.ac.tuwien.foop.server.repository.PlayerRepository;
import at.ac.tuwien.foop.server.service.TokenService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GameStatePreGame implements GameState {

    private static final String GAME_STATE = "PRE_GAME";

    private final PlayerRepository playerRepository;
    private final TokenService tokenService;

    @Override
    public LoginResponse createPlayer(String name) {
        Player player = playerRepository.createPlayer(name);
        String token = tokenService.createNewToken(player.getId());
        return LoginResponse.builder()
                .token(token)
                .player(player)
                .build();
    }

    @Override
    public Player updatePlayer(PlayerRequest playerRequest) {
        Player player = playerRequest.getPlayer();
        if(player.getCurrentEnvironment() != null || player.getKnownStatus() != null || player.getPosition() != null) {
            throw new BadRequestException();
        }
        return playerRepository.updatePlayer(player);
    }

    @Override
    public Player setPlayerReadyForGame(Player player) {
        player.setPlayerReady();
        return player;
    }

    @Override
    public String getGameState() {
        return GAME_STATE;
    }
}
