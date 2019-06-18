package at.ac.tuwien.foop.server.endpoint;


import at.ac.tuwien.foop.server.dto.LoginResponse;
import at.ac.tuwien.foop.server.dto.Response;
import at.ac.tuwien.foop.server.service.PrepareService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class PlayerController {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);

    private final PrepareService prepareService;

    @MessageMapping("/addPlayer")
    @SendTo("/topic/player")
    public LoginResponse addPlayer(String name) {
        LOG.info("Creating player " + name);
        return prepareService.createPlayer(name);
    }

}
