package tsxdk.io;

import com.google.inject.Inject;
import common.defaults.SystemDescriptors;
import tsxdk.base.TSX;
import tsxdk.model.TSServerConnectionModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 15. of Mai
 *  2016
 *  20:44
 */
public class SocketConnectionImpl extends TSX implements SocketConnection {
    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    @Inject
    public SocketConnectionImpl(TSServerConnectionModel serverHandle) {
        super(SystemDescriptors.IO, SocketConnection.class);
        Objects.requireNonNull(serverHandle);
        log.info("Creating SocketConnection - host: {}, port: {}", serverHandle.getHost(), serverHandle.getPort());
        socket = createSocket(serverHandle.getHost(), serverHandle.getPort());
        reader = createInput();
        writer = createOutput();
        log.info("SocketConnection created - socket: {}, reader: {}, writer: {}", socket, reader, writer);
    }

    private Socket createSocket(String host, int port) {
        try {
            return new Socket(host,port);
        } catch (IOException e) {
            throwFatal("Could not create socket", e);
        }
        return null;
    }

    private PrintWriter createOutput() {
        try {
            return new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throwFatal("Could not initialize output", e);
        }
        return null;
    }

    private BufferedReader createInput() {
        try {
            return new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        } catch (NullPointerException | IOException e) {
            throwFatal("Could not initialize input", e);
        }
        return null;
    }

    @Override
    public boolean isClosed() {
        return socket.isClosed();
    }

    @Override
    public BufferedReader getReader() {
        return reader;
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }
}
