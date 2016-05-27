package tsxdk.model;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 15. of Mai
 * 2016
 * 20:44
 */
public class TSServerConnectionModel {
    private final String host;
    private final int port;

    public TSServerConnectionModel(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "TSServerConnectionModel{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
