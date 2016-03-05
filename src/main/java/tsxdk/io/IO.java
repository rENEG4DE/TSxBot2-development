package tsxdk.io;

import java.util.Optional;

/**
 * Interface for Teamspeak-IO
 */
public interface IO {
    Optional<String> in();

    void out(String out);
}
