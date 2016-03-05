package tsxdk.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by Ulli Gerhard on 09.10.2015.
 */
public interface SocketConnection {
    boolean isClosed();

    BufferedReader getReader();

    PrintWriter getWriter();
}
