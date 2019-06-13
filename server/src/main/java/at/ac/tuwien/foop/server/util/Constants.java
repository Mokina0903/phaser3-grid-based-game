package at.ac.tuwien.foop.server.util;


public final class Constants {

    /**
     * Broadcast topics. Clients will subscribe to this topic
     * The server makes sure every client gets the right kind of game state information.
     * i.e. the server knows, when a player is up top or in a tunnel and send fitting game state information for the player
     */
    public static final String TOPIC_GAME_STATE = "/topic/state";
    public static final String TOPIC_PLAYERS = "/topic/players";

    /**
     * Endpoints. (Pls change, if the names are stupid)
     */
    public static final String MOVEMENT_ENDPOINT = "/move";
    public static final String CONFIRM_MOVEMENT_ENDPOINT = MOVEMENT_ENDPOINT + "/confirm";
    public static final String LOGIN_ENDPOINT = "/login";

    private Constants() { }
}
