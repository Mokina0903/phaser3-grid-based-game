package at.ac.tuwien.foop.server.exception;


import static at.ac.tuwien.foop.server.exception.ErrorIds.PLAYER_NOT_FOUND;

public class PlayerNotFoundException extends GameException {

    public PlayerNotFoundException(Long id) {
        super(PLAYER_NOT_FOUND, "Player with id " + id + " not found");
    }
}
