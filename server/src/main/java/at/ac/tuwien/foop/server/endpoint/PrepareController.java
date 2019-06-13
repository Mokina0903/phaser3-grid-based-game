package at.ac.tuwien.foop.server.endpoint;


import at.ac.tuwien.foop.server.game.player.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static at.ac.tuwien.foop.server.util.Constants.LOGIN_ENDPOINT;
import static at.ac.tuwien.foop.server.util.Constants.TOPIC_PLAYERS;

@Controller
@Slf4j
public class PrepareController {

    /**
     * Logs in new player
     *
     * @param name of the player
     * @return the newly logged in player (with ID). The new player will be broadcasted to all clients that subscribe to
     * '/topic/players' aka all players
     */
    @MessageMapping(LOGIN_ENDPOINT)
    @SendTo(TOPIC_PLAYERS)
    public Player login(String name) {


    }
}
