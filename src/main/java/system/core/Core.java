package system.core;

import common.base.SystemDesignator;
import common.base.TSX;
import tsxdk.io.NIOSocketIOImplDirty;

/**
 * Created by Ulli Gerhard on 21.02.2016.
 */
public class Core extends TSX {
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
        super(SystemDesignator.SYSTEM, Core.class);
        log.trace("Core ctor");
//        NIOSocketIOImplDirty io = IO.createIO ();
        NIOSocketIOImplDirty io = new NIOSocketIOImplDirty();
        log.trace("Core ctor end");
    }

    public void start() {
        log.info("Starting core");
        log.info("Core has been started");
    }

    public void addShutdownHook (Runnable runnable) {
        Runtime.getRuntime().addShutdownHook(new Thread(runnable));
    }
}
