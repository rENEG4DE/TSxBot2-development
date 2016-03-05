package common.utility;

/**
 * Created by Ulli Gerhard on 22.02.2016.
 */
public class Configuration {
    private static final org.apache.commons.configuration.Configuration cfg = ConfigAccess.get();

    public static final String SYSTEM_SERVERLABEL = cfg.getString("system.serverlabel");
    public static final String TSSERVER_HOST = cfg.getString("tsserver.host");
    public static final Integer TSSERVER_PORT = cfg.getInt("tsserver.port");
    public static final String TSSERVER_LOGIN = cfg.getString("tsserver.login");
    public static final String TSSERVER_PASSWORD = cfg.getString("tsserver.password");
    public static final int TSIO_INPUTBUFFERSIZE = cfg.getInt("tsio.inputbuffersize");
}
