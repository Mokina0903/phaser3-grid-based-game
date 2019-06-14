package at.ac.tuwien.foop.server.dto;


import at.ac.tuwien.foop.server.game.player.Player;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class PlayerRequest extends EmptyRequest {

    @NotNull
    @Valid
    @EqualsAndHashCode.Exclude
    private Player player;
}
