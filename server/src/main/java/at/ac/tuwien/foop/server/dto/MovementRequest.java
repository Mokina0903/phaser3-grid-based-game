package at.ac.tuwien.foop.server.dto;

import at.ac.tuwien.foop.server.game.Position;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MovementRequest extends EmptyRequest {

    private Integer xPosition;
    private Integer yPosition;

    public Position toPosition() {
        return Position
                .builder()
                .xPosition(xPosition)
                .yPosition(yPosition)
                .build();
    }
}
