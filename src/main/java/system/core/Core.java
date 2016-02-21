package system.core;

/**
 * Created by Ulli Gerhard on 21.02.2016.
 */
public class Core {
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("system.Core");

    private static final class CORE_INSTANCE_HOLDER {
        private static final Core INSTANCE = createCore();
    }

    public static Core get() {
        return CORE_INSTANCE_HOLDER.INSTANCE;
    }

    private static Core createCore() {
        return new Core();
    }

    private Core () {
        log.trace("Core ctor");
        log.trace("Core ctor end");
    }

    public void start() {
        log.info("Starting core");
        log.info("Core has been started");
    }
}
