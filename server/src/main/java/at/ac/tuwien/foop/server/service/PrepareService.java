package at.ac.tuwien.foop.server.service;


import at.ac.tuwien.foop.server.dto.LoginResponse;
import at.ac.tuwien.foop.server.dto.PlayerRequest;
import at.ac.tuwien.foop.server.exception.PlayerNotFoundException;
import at.ac.tuwien.foop.server.game.GameMaster;
import at.ac.tuwien.foop.server.game.player.Player;
import at.ac.tuwien.foop.server.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class PrepareService {

    private final GameMaster gameMaster;
    private final PlayerRepository playerRepository;

    public LoginResponse createPlayer(String name) {
        return gameMaster.createPlayer(name);
    }

    public Player updatePlayer(PlayerRequest playerRequest) {
        return gameMaster.updatePlayer(playerRequest);
    }

    public Player setPlayerReady(Long id) {
        return gameMaster.setPlayerReadyForGame(playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id)));
    }
}
