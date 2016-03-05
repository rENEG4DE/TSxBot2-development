package common.utility;

import common.base.SystemDescriptor;
import common.base.TSX;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Created by Ulli Gerhard on 21.02.2016.
 */
class ConfigAccess {
    private static final String DEFAULT_PROPERTIES = "default.properties";
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static Configuration cfg;

    static {
        try {
            cfg = new PropertiesConfiguration(DEFAULT_PROPERTIES);
            ((PropertiesConfiguration)cfg).copy(new PropertiesConfiguration(CONFIG_PROPERTIES));
        } catch (ConfigurationException e) {
            org.slf4j.LoggerFactory.getLogger(TSX.createLoggerDescriptor(SystemDescriptor.UTILIY, ConfigAccess.class))
                    .error("Unable to read configuration", e);
        }
    }

    public static Configuration get() {
        return cfg;
    }
}
