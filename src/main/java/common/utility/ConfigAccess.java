package common.utility;

import common.Super.SystemDesignator;
import common.Super.TSX;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ulli Gerhard on 21.02.2016.
 */
class ConfigAccess {
    private static final String DEFAULT_PROPERTIES = "default.properties";
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static Configuration cfg;

    static {
        try {
            PropertiesConfiguration temp = new PropertiesConfiguration(DEFAULT_PROPERTIES);
            temp.copy(new PropertiesConfiguration(CONFIG_PROPERTIES));
            cfg = temp;
        } catch (ConfigurationException e) {
            org.slf4j.LoggerFactory.getLogger(TSX.createLoggerDescriptor(SystemDesignator.UTILIY, ConfigAccess.class))
                    .error("Unable to read configuration - {}", e.getMessage());
        }
    }

    public static Configuration get() {
        return cfg;
    }
}
