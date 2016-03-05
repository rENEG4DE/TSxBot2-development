package tsxdk.io;

import common.base.SystemDescriptor;
import common.base.TSX;
import tsxdk.model.TSServerHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Ulli Gerhard on 29.09.2015.
 */
public class SocketConnectionImpl extends TSX implements SocketConnection {
    private final TSServerHandle serverHandle;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public SocketConnectionImpl(TSServerHandle serverHandle) {
        super(SystemDescriptor.IO, SocketConnection.class);
        log.info("Creating SocketConnection - serverhandle: {}", serverHandle);
        this.serverHandle = serverHandle;
        initSocket();
        initInput();
        initOutput();
        log.info("SocketConnection created - host: {}, port: {}", serverHandle.getHost(), serverHandle.getPort());
    }

    private void initSocket() {
        final String host = serverHandle.getHost();
        final int port = serverHandle.getPort();
        try {
            socket = new Socket(host,port);
        } catch (IOException e) {
            throwFatal("Could not create socket", e);
        }
    }

    private void initOutput() {
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throwFatal("Could not initialize output", e);
        }
    }

    private void initInput() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        } catch (NullPointerException | IOException e) {
            throwFatal("Could not initialize input", e);
        }
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
