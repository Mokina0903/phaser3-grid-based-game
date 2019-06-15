package at.ac.tuwien.foop.server.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@Builder
public class EmptyRequest {

    @NotNull
    private String token;
}
