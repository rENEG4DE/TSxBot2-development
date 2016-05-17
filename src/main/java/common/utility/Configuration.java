package common.utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 15. of Mai
 *  2016
 *  20:44
 */
public class Configuration {
    private static final org.apache.commons.configuration.Configuration cfg = ConfigAccess.get();

    public static final String SYSTEM_SERVERLABEL = cfg.getString("system.serverlabel");
    public static final String TSSERVER_HOST = cfg.getString("tsserver.host");
    public static final Integer TSSERVER_PORT = cfg.getInt("tsserver.port");
    public static final String TSSERVER_LOGIN = cfg.getString("tsserver.login");
    public static final String TSSERVER_PASSWORD = cfg.getString("tsserver.password");

    public Map<String, String> getEnvironment() {
        final Map<String, String> result = new HashMap<>();
        final Iterator<String> cfgKeyIterator = cfg.getKeys();

        String current;
        while(cfgKeyIterator.hasNext()) {
            current = cfgKeyIterator.next();
            result.put(current, cfg.getString(current));
        }

        return result;
    }
}
