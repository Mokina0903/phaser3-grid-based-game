package at.ac.tuwien.foop.server.Utils;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Maps the session id to an incrementing player id
 * rather primitive for testing
 **/
public class PlayerIdCache {

    private static final ConcurrentHashMap<String, Long> playerCache = new ConcurrentHashMap<>();

    public static void addPlayer(String sessionId, Long id) {
        playerCache.putIfAbsent(sessionId, id);
    }

    public static Long getPlayerId(String sessionId) {
        return playerCache.get(sessionId);
    }
}
