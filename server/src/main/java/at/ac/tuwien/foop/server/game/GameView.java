package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.game.environment.GameEnvironment;
import at.ac.tuwien.foop.server.game.player.Player;
import at.ac.tuwien.foop.server.game.player.state.PlayerState;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

/**
 * Represents view on GameModel i.e. the last known positions and state of each player.
 * Every Player has their last known view on how the positions and state of the other players was
 */
public class GameView {

    private final Collection<PlayerView> lastKnownPlayerInformationList = new HashSet<>();

    public void updatePlayerLocation(Player player, GameEnvironment gameEnvironment) {
        findOne(player.getId())
                .ifPresentOrElse(
                        playerView -> playerView.setCurrentEnvironment(gameEnvironment),
                        () -> addNewEntry(player)
                );
    }

    public void updatePlayerLocation(Player player, Position position) {
        findOne(player.getId())
                .ifPresentOrElse(
                        playerView -> playerView.setCurrentPosition(position),
                        () -> addNewEntry(player)
                );
    }

    public void updatePlayerState(Player player, PlayerState playerState) {
        findOne(player.getId())
                .ifPresentOrElse(
                        playerView -> playerView.setCurrentState(playerState),
                        () -> addNewEntry(player)
                );
    }

    private Optional<PlayerView> findOne(Long id) {
        return lastKnownPlayerInformationList.stream()
                .filter(playerView -> playerView.getId().equals(id))
                //should only be one
                .findFirst();
    }

    private void addNewEntry(Player player) {
        lastKnownPlayerInformationList.add(
                PlayerView
                        .builder()
                        .currentState(player.getCurrentState())
                        .currentEnvironment(player.getCurrentEnvironment())
                        .currentPosition(player.getPosition())
                        .id(player.getId())
                        .build()
        );
    }
}
