package at.ac.tuwien.foop.server.repository;


import at.ac.tuwien.foop.server.exception.PlayerAlreadyExistsException;
import at.ac.tuwien.foop.server.exception.PlayerNotFoundException;
import at.ac.tuwien.foop.server.game.movement.CatMovement;
import at.ac.tuwien.foop.server.game.movement.MouseMovement;
import at.ac.tuwien.foop.server.game.movement.MovementStrategy;
import at.ac.tuwien.foop.server.game.player.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
@Slf4j
public class PlayerRepository {

    private final AtomicLong currentId;
    private final Map<Long, Player> players;
    private final ReadWriteLock playersLock;
    private final SecureRandom random;

    public PlayerRepository() {
        this.currentId = new AtomicLong(0L);
        this.players = new HashMap<>();
        this.playersLock = new ReentrantReadWriteLock();
        this.random = new SecureRandom();
        this.random.setSeed(System.currentTimeMillis());
    }

    public Optional<Player> findById(Long id) {
        log.debug("Looking up player by id {}", id);
        playersLock.readLock().lock();
        Player player = players.get(id);
        playersLock.readLock().unlock();
        return Optional.ofNullable(player);
    }

    public Player createPlayer(String name) {
        Long id = currentId.getAndIncrement();
        Player newPlayer = Player.builder()
                .id(id)
                .name(name)
                .build();
        MovementStrategy movementStrategy = random.nextBoolean() ? new CatMovement() : new MouseMovement();
        newPlayer.setMovementStrategy(movementStrategy);
        playersLock.writeLock().lock();
        Player previousPlayer = players.putIfAbsent(id, newPlayer);
        playersLock.writeLock().unlock();
        if(previousPlayer != null) {
            throw new PlayerAlreadyExistsException();
        }
        log.info("Created new player with id {}", id);
        return newPlayer;
    }

    public Player updatePlayer(Player player) {
        playersLock.writeLock().lock();
        Player updatedPlayer = players.computeIfPresent(player.getId(), (id, oldPlayer) -> player);
        playersLock.writeLock().unlock();
        if(updatedPlayer == null) {
            throw new PlayerNotFoundException(player.getId());
        }
        log.info("Updated player with id {}", player.getId());
        return updatedPlayer;
    }

    public Collection<Player> findAll() {
        playersLock.readLock().lock();
        Collection<Player> playerList = players.values();
        playersLock.readLock().unlock();
        return playerList;
    }
}
