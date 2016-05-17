package tsxdk.base;

import common.utility.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  TSxBot2
 *  Coded by Ulli Gerhard
 *  on 15. of Mai
 *  2016
 *  12:15
 */
public abstract class TSX {
    protected final Logger log;
    protected final Configuration cfg;

    public TSX(SystemDescriptor subSystem, Class<?> clazz) {
        log = LoggerFactory.getLogger(SystemDescriptor.createLoggerDescriptor(subSystem, clazz));
        cfg = new Configuration ();
    }

    public void throwFatal (String message, Throwable t) {
        log.error("Fatal: " + message, t);
        System.exit(-1);
    }
}
