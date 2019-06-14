package at.ac.tuwien.foop.server.dto;


import at.ac.tuwien.foop.server.game.player.Player;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class LoginResponse {

    @NotNull
    private String token;

    @NotNull
    private Player player;
}
