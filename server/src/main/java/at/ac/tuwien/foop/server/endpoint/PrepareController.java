package at.ac.tuwien.foop.server.endpoint;


import at.ac.tuwien.foop.server.dto.EmptyRequest;
import at.ac.tuwien.foop.server.dto.LoginResponse;
import at.ac.tuwien.foop.server.dto.PlayerRequest;
import at.ac.tuwien.foop.server.exception.BadRequestException;
import at.ac.tuwien.foop.server.game.player.Player;
import at.ac.tuwien.foop.server.service.PrepareService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;


@RestController
@Slf4j
@AllArgsConstructor
public class PrepareController {

    private static final Logger LOG = LoggerFactory.getLogger(PrepareController.class);

    private final PrepareService prepareService;

    /**
     * Logs in new player
     *
     * @param name of the player
     * @return the newly logged in player (with ID). The new player will be broadcasted to all clients that subscribe to
     * '/topic/players' aka all players
     */
    @PostMapping(path = "/players")
    public LoginResponse createPlayer(@RequestBody @NotEmpty String name) {
        LOG.info("Creating player " + name);
        return prepareService.createPlayer(name);
    }

    /**
     * Updates the current player
     *
     * @param playerRequest containing the JWT for authentication and the updated player
     * @return the current player containing all settings like id, name and type
     */
    @PutMapping(path = "/players/{id}")
    public Player updatePlayer(@PathVariable("id") Long id, @RequestBody @Valid PlayerRequest playerRequest) {
        if(!playerRequest.getPlayer().getId().equals(id)) {
            throw new BadRequestException();
        }
        return prepareService.updatePlayer(playerRequest);
    }

    /**
     * Confirms that the player is ready
     *
     * @param id
     * @return
     */
    @PutMapping(path = "/players/{id}/ready")
    public Player setPlayerReady(@PathVariable("id") Long id, @RequestBody @Valid EmptyRequest request) {
        return prepareService.setPlayerReady(id);
    }

    /**
     * Retrieves all players
     *
     * @return Collection of all players
     */
    @GetMapping(path = "/players")
    public Collection<Player> getPlayers() {
        LOG.info("retrieving all players...");
        return prepareService.getAllPlayers();
    }
}
