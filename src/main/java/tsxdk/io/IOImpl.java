package tsxdk.io;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import common.defaults.SystemDescriptors;
import tsxdk.base.TSX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class IOImpl extends TSX implements IO {
    private final SocketConnection socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    @Inject
    public IOImpl(SocketConnection conn) {
        super(SystemDescriptors.IO, IO.class);
        log.info("Creating IO - connection {}", conn);
        socket = conn;
        reader = socket.getReader();
        writer = socket.getWriter();
        log.info("IO has been created - reader: {}, writer: {}", reader, writer);
    }

    @Override
    public Optional<String> in() {
        if (socket.isClosed()) {
            log.error("Socket is closed!");
        } else {
            try {
                if (reader.ready()) {
                    final String input = Strings.emptyToNull(reader.readLine());
                    log.debug("<< {}", input != null ? input : "[empty]");
                    return Optional.ofNullable(input);
                }
            } catch (IOException e) {
                throwFatal("Failed to getPopulatedServerConnectionModel input from socket", e);
            }
        }

        return Optional.empty();
    }

    @Override
    public void out(String out) {
        if (socket.isClosed()) {
            log.error("Can not out to socket - is closed");
            return;
        }

        if (Strings.isNullOrEmpty(out)) {
            log.warn("Empty string argument");
        }

        writer.println(out);
        if (writer.checkError()) {
            log.error("Writer suffered an error");
        } else {
            log.debug(">> {}", out);
        }
    }
}
