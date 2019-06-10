package at.ac.tuwien.foop.server.Utils;

public class IdGenerator {

    public static Long counter = 1L;

    public static Long getId() {
        return counter++;
    }
}
