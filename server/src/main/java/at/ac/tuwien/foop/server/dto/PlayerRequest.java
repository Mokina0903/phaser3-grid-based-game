package at.ac.tuwien.foop.server.dto;


import at.ac.tuwien.foop.server.game.player.Player;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class PlayerRequest extends EmptyRequest {

    @NotNull
    @Valid
    @EqualsAndHashCode.Exclude
    private Player player;
}
