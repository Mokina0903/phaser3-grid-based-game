package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.game.environment.GameEnvironment;

import java.util.Collection;

/**
 * Represents view on GameModel i.e. the last known positions and state of each player.
 * Every Player has their last known view on how the positions and state of the other players was
 */
public class GameView {

    Collection<GameEnvironment> environmentsWithLastKnownPlayerLocations;
}
