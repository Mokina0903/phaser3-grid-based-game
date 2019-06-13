package at.ac.tuwien.foop.server.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import static at.ac.tuwien.foop.server.util.IdGenerator.getId;
import static at.ac.tuwien.foop.server.util.PlayerIdCache.addPlayer;


/**
 * called after connection established
 * adds the session id and the player id into the PlayerIdCache
 **/
@Component
@Deprecated
public class StompSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(StompSubscribeEventListener.class);

    @Override
    public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
        /*
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());
        String sessionId = headerAccessor.getSessionId();
        Long id = getId();
        addPlayer(sessionId, id);
        LOG.info("Added player with session " + sessionId + " and player id " + id + " to cache.");
         */
    }
}
