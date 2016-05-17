package common.utility;

import common.defaults.SystemDescriptors;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import tsxdk.base.TSX;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 15. of Mai
 *  2016
 *  20:44
 */
class ConfigAccess {
    private static final String DEFAULT_PROPERTIES = "default.properties";
    private static final String CONFIG_PROPERTIES = "config.properties";
    @SuppressWarnings("CanBeFinal")
    private static Configuration cfg;

    static {
        try {
            cfg = new PropertiesConfiguration(DEFAULT_PROPERTIES);
            ((PropertiesConfiguration)cfg).copy(new PropertiesConfiguration(CONFIG_PROPERTIES));
        } catch (ConfigurationException e) {
            org.slf4j.LoggerFactory.getLogger(TSX.createLoggerDescriptor(SystemDescriptors.UTILITY, ConfigAccess.class))
                    .error("Unable to read configuration", e);
        }
    }

    public static Configuration get() {
        return cfg;
    }
}
