package system.core;

import common.base.SystemDesignator;
import common.base.TSX;
import common.utility.Configuration;
import tsxdk.io.IO;
import tsxdk.io.IOImpl;
import tsxdk.io.SocketConnection;
import tsxdk.io.SocketConnectionImpl;
import tsxdk.model.TSServerHandle;

/**
 * Created by Ulli Gerhard on 21.02.2016.
 */
public class Core extends TSX {
    private static final class CORE_INSTANCE_HOLDER {
        private static final Core INSTANCE = createCore();

        private static Core createCore() {
            return new Core();
        }
    }

    public static Core get() {
        return CORE_INSTANCE_HOLDER.INSTANCE;
    }

    private final TSServerHandle serverHandle;
    private final SocketConnection pipe;

    private Core() {
        super(SystemDesignator.SYSTEM, Core.class);
        log.info("Creating core");
        log.info("Environment: {}", getEnvironment());
        serverHandle = createServerHandle();
        pipe = createPipe();
        start();
        log.info("Core created");
    }

    private void start() {
        log.info("Starting core");
        log.info("Core started");
    }

    private TSServerHandle createServerHandle() {
        return new TSServerHandle(Configuration.TSSERVER_HOST, Configuration.TSSERVER_PORT);
    }

    private SocketConnection createPipe() {
        return new SocketConnectionImpl(serverHandle);
    }

    public void addShutdownHook(Runnable runnable) {
        Runtime.getRuntime().addShutdownHook(new Thread(runnable));
    }
}
