package common.utility;

import com.google.inject.Provider;
import com.google.inject.Provides;
import tsxdk.model.TSServerConnectionModel;

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
public class Configuration implements Provider<TSServerConnectionModel> {
    private static final org.apache.commons.configuration.Configuration cfg = ConfigAccess.get();

    public static final String SYSTEM_SERVERLABEL = cfg.getString("system.serverlabel");
    public static final String TSSERVER_HOST = cfg.getString("tsserver.host");
    public static final Integer TSSERVER_PORT = cfg.getInt("tsserver.port");
    public static final String TSSERVER_LOGIN = cfg.getString("tsserver.login");
    public static final String TSSERVER_PASSWORD = cfg.getString("tsserver.password");
    public static final String TSSERVER_PATH = cfg.getString("tsserver.path");
    public static final String TSSERVER_EXE = cfg.getString("tsserver.exe");
    public static final String TSSERVER_PARM = cfg.getString("tsserver.parm");
    public static final Integer TSSERVER_STARTDELAY = cfg.getInt("tsserver.startDelay");
    public static final Boolean TSSERVER_PINGENEBALED = cfg.getBoolean("tsserver.pingEnabled");
    public static final Integer TSSERVER_PINGPERIOD = cfg.getInt("tsserver.pingPeriod");

    public static final Integer QUERY_RECVPERIOD = cfg.getInt("query.receiverPeriod");
    public static final Integer QUERY_COMPLETEPERIOD = cfg.getInt("query.completionPeriod");
    public static final Integer QUERY_PERSEC = cfg.getInt("query.perSecond");
    public static final Integer QUERY_COMPLETIONTICKS = cfg.getInt("query.completionTicks");

    @Provides
    public TSServerConnectionModel get() {
        return new TSServerConnectionModel(Configuration.TSSERVER_HOST, Configuration.TSSERVER_PORT);
    }

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
