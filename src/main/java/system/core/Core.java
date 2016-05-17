package system.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import common.defaults.SystemDescriptors;
import tsxdk.base.TSX;
import tsxdk.io.IO;
import tsxdk.io.GuiceBindings;
import tsxdk.model.TSServerConnectionModel;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 15. of Mai
 *  2016
 *  20:44
 */
public class Core extends TSX {

    public static Core get() {
        return CORE_INSTANCE_HOLDER.INSTANCE;
    }

    private static final class CORE_INSTANCE_HOLDER {
        private static final Core INSTANCE = createCore();
        private static Core createCore() {
            return new Core();
        }

    }

    private final TSServerConnectionModel serverHandle;
    private final IO pipe;

    private Core() {
        super(SystemDescriptors.SYSTEM, Core.class);
        log.info("Creating core");
        log.info("Environment: {}", cfg.getEnvironment());
        Injector injector = Guice.createInjector(new GuiceBindings());
        log.debug("Created injector: {}", injector);
        Provider<TSServerConnectionModel> provider = injector.getProvider(TSServerConnectionModel.class);
        log.debug("Obtained provider: {}", provider);
        serverHandle = provider.get();
        System.out.print(serverHandle + " " + provider.get());
        pipe = createPipe();
        start();
        log.info("Core created");
    }

    private void start() {
        log.info("Starting core");
        log.info("Core started");
    }

    private IO createPipe() {
        Injector injector = Guice.createInjector(new GuiceBindings());
        return injector.getInstance(IO.class);
    }

    public void addShutdownHook(Runnable runnable) {
        Runtime.getRuntime().addShutdownHook(new Thread(runnable));
    }
}
