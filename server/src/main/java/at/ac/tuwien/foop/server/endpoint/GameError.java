package at.ac.tuwien.foop.server.endpoint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GameError {

    private String message;
    private long id;

    // TODO add what we need
}
