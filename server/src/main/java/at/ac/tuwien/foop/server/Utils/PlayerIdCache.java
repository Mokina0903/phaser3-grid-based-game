package at.ac.tuwien.foop.server.Utils;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Maps the session id to an incrementing player id
 * rather primitive for testin
 **/
public class PlayerIdCache {

    public static ConcurrentHashMap<String, Long> playerCache = new ConcurrentHashMap<>();

    public static void addPlayer(String sessionId, Long id) {
        playerCache.putIfAbsent(sessionId, id);
    }

    public static Long getPlayerId(String sessionId) {
        return playerCache.get(sessionId);
    }
}
