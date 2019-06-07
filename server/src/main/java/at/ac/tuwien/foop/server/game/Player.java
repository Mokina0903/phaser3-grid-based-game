package at.ac.tuwien.foop.server.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    private String name;
    private Long id;
    private Position position;
    private GameState knownState;
}
