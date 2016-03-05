package common.base;

import common.utility.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ulli Gerhard on 27.02.2016.
 */
public abstract class TSX {
    protected final Logger log;
    protected final Configuration cfg;

    public TSX(SystemDescriptor subSystem, Class<?> clazz) {
        log = LoggerFactory.getLogger(createLoggerDescriptor(subSystem, clazz));
        cfg = new Configuration ();
    }

    public void throwFatal (String message, Throwable t) {
        log.error("Fatal: " + message, t);
        System.exit(-1);
    }

    public static String createLoggerDescriptor(SystemDescriptor subSystem, Class<?> clazz) {
        return subSystem.name() + "::" + clazz.getSimpleName();
    }
}
