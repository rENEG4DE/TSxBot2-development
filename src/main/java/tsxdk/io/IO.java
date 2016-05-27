package tsxdk.io;

import java.util.Optional;

/**
 * Interface for Teamspeak-IO
 */
public interface IO {
    void shutdown();

    Optional<String> in();

    boolean out(String out);
}
