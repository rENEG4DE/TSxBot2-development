package tsxdk.model;

/**
 * Created by Ulli Gerhard on 05.03.2016.
 */
public class TSServerHandle {
    private final String host;
    private final int port;

    public TSServerHandle(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
