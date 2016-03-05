package system.core;

import common.base.SystemDescriptor;
import common.base.TSX;
import common.utility.Configuration;
import tsxdk.io.IO;
import tsxdk.io.IOImpl;
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
    private final IO pipe;

    private Core() {
        super(SystemDescriptor.SYSTEM, Core.class);
        log.info("Creating core");
        log.info("Environment: {}", cfg.getEnvironment());
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

    private IO createPipe() {
        return new IOImpl(new SocketConnectionImpl(serverHandle));
    }

    public void addShutdownHook(Runnable runnable) {
        Runtime.getRuntime().addShutdownHook(new Thread(runnable));
    }
}
