package at.ac.tuwien.foop.server.service;

import at.ac.tuwien.foop.server.dto.EmptyRequest;
import at.ac.tuwien.foop.server.dto.MovementRequest;
import at.ac.tuwien.foop.server.game.GameMaster;
import at.ac.tuwien.foop.server.game.player.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GameService {

    private final GameMaster gameMaster;
    private final TokenService tokenService;

    public void prepareMovement(MovementRequest movementRequest) {
        Player player = tokenService.getPlayer(movementRequest.getToken());
        gameMaster.prepareMovement(player, movementRequest.toPosition());
    }

    public void confirmMovement(EmptyRequest emptyRequest) {
        Player player = tokenService.getPlayer(emptyRequest.getToken());
        gameMaster.confirmMovement(player);
    }
}
