package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.game.environment.GameEnvironment;
import at.ac.tuwien.foop.server.game.player.state.PlayerState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
class PlayerView {

    private Long id;
    private Position currentPosition;
    private GameEnvironment currentEnvironment;
    private PlayerState currentState;
}
