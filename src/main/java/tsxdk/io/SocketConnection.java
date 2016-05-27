package tsxdk.io;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 15. of Mai
 *  2016
 *  20:44
 */
public interface SocketConnection {
    boolean isClosed();

    BufferedReader getReader();

    PrintWriter getWriter();

    void shutdown();
}
