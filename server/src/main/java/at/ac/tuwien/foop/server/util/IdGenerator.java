package at.ac.tuwien.foop.server.util;


import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private static final AtomicLong counter = new AtomicLong(1L);

    public static Long getId() {
        return counter.getAndIncrement();
    }
}
