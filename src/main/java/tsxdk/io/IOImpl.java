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
    public void shutdown() {
        socket.shutdown();
    }

    @Override
    public Optional<String> in() {
//        for (final Optional<String> res = in0();res.isPresent();) {
//            log.debug("<< {}", s);
//            return res;
//        }
//
//        return Optional.empty();

        final Optional<String> result = in0();
        result.ifPresent(s -> log.debug("<< {}", s));
        return result;
    }

    private Optional<String> in0() {
        if (socket.isClosed()) {
            log.error("Can not read from closed socket");
        } else {
            try {
                if (reader.ready()) {
                    return Optional.ofNullable(Strings.emptyToNull(reader.readLine().trim()));
                }
            } catch (IOException e) {
                throwFatal("Failed to read from socket", e);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean out(String out) {
        if (out0(out)) {
            log.debug(">> {}", out);
            return true;
        } else return false;
    }

    private boolean out0(String out) {
        if (socket.isClosed()) {
            log.error("Can not write to closed socket");
            return false;
        }

        if (Strings.isNullOrEmpty(out)) {
            log.warn("Empty write-argument");
            return false;
        }

        writer.println(out);

        if (writer.checkError()) {
            log.error("Writer suffered an error - output was probably not written - {}", out);
            return false;
        } else return true;
    }
}
