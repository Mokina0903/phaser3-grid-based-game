package at.ac.tuwien.foop.server.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class EmptyRequest {

    @NotNull
    private String token;
}
