package at.ac.tuwien.foop.server.endpoint;


import at.ac.tuwien.foop.server.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static at.ac.tuwien.foop.server.Utils.PlayerIdCache.getPlayerId;

@Controller
public class PlayerController {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);

    @MessageMapping(value = "/addPlayer")
    @SendTo("/topic/addPlayer")
    public Response addPlayer(String sessionId) {
        Long playerId = getPlayerId(sessionId);
        LOG.info("Adding Player " + playerId);
        return Response.builder()
                .message("Player" + playerId + " joined the Game!")
                .build();
    }

}
