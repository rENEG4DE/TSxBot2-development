package tsxdk.base;

import common.utility.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

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

    protected TSX(SystemDescriptor subSystem, Class<?> clazz) {
        log = LoggerFactory.getLogger(createLoggerDescriptor(subSystem, clazz));
        cfg = new Configuration ();
    }

    protected void throwFatal (String message, Throwable t) {
        log.error("Fatal: " + message, t);
        System.exit(-1);
    }

    public static String createLoggerDescriptor(SystemDescriptor subSystem, Class<?> clazz) {
        return subSystem.get() + "::" + clazz.getSimpleName();
    }

    public interface SystemDescriptor extends Supplier<String> {}
}
