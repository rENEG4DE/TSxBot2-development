package common.Super;

import common.utility.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ulli Gerhard on 27.02.2016.
 */
public abstract class TSX {
    protected final Logger log;
    protected final Configuration cfg;

    public TSX(SystemDesignator subSystem, Class<?> clazz) {
        log = LoggerFactory.getLogger(createLoggerDescriptor(subSystem, clazz));
        cfg = new Configuration ();
    }

    public static String createLoggerDescriptor(SystemDesignator subSystem, Class<?> clazz) {
        return subSystem.name() + "::" + clazz.getSimpleName();
    }
}
