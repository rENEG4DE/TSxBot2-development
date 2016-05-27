package system.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import common.defaults.SystemDescriptors;
import common.utility.Configuration;
import tsxdk.base.TSX;
import tsxdk.io.IO;
import tsxdk.model.TSServerConnectionModel;
import tsxdk.modules.GuiceBindings;
import tsxdk.query.QueryChannel;
import tsxdk.query.QueryEngine;
import tsxdk.query.QueryFactory;
import tsxdk.query.model.Query;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 15. of Mai
 * 2016
 * 20:44
 */
public class Experi_mental_Core extends TSX {

    private final Injector injector = Guice.createInjector(new GuiceBindings());
    private final Provider<TSServerConnectionModel> provider = injector.getProvider(TSServerConnectionModel.class);

    private final IO pipe = injector.getInstance(IO.class);
    private final TSServerConnectionModel serverHandle = provider.get();

    /*
        Note:
        Objects that need construction (TSServerConnectionModel) need Providers to exist
        Interfaces that can be used (IO) only need injection
     */

    private Experi_mental_Core() {
        super(SystemDescriptors.SYSTEM, Experi_mental_Core.class);
        log.info("Creating core");
        log.info("Environment: {}", cfg.getEnvironment());
        log.debug("Created injector: {}", injector);
        log.debug("Obtained provider: {}", provider);
        log.debug("Obtained serverHandle: {}", serverHandle);
        try {
            start ();
        } catch (InterruptedException e) {
            log.error ("Something aweful happened", e);
        }
        log.info("Core created");
    }

    public static Experi_mental_Core get() {
        return CORE_INSTANCE_HOLDER.INSTANCE;
    }

    private QueryEngine obtainQueryEngine() {
        return injector.getInstance(QueryEngine.class);
    }

    private void start() throws InterruptedException {
        log.info("Starting core");
        final ScheduledThreadPoolExecutor tpe = new ScheduledThreadPoolExecutor(1);
        {
            tpe.setRemoveOnCancelPolicy(true);
            tpe.scheduleAtFixedRate(this::doStuff, 300, 20, TimeUnit.MILLISECONDS);
        }

//        doStuff();

        log.info("Core started");

        do {
            Thread.sleep(100);
        } while (true);
    }

    final int[] clientIdx = {0};

    private void doStuff() {
        try {
            final QueryChannel queryChannel = obtainQueryChannel ();
            System.out.println("Obtained the " + ++clientIdx[0] + "th connection");
            final QueryFactory queryFactory = new QueryFactory();

            Query query = queryFactory.login (cfg.TSSERVER_LOGIN, cfg.TSSERVER_PASSWORD);
            queryChannel.deploy (query);
            query = queryFactory.use(1);
            queryChannel.deploy(query);
            query = queryFactory.channellist ();
            queryChannel.deployAndSync(query);
            query = queryFactory.logout();
            queryChannel.deployAndSync(query);

            queryChannel.shutdown();
        } catch (Exception e) {
            log.error ("Something happened that was not supposed to", e);
        }
    }

    private QueryChannel obtainQueryChannel() {
        return injector.getInstance(QueryChannel.class);
    }

    public void addShutdownHook(Runnable runnable) {
        Runtime.getRuntime().addShutdownHook(new Thread(runnable));
    }

    private static final class CORE_INSTANCE_HOLDER {
        private static final Experi_mental_Core INSTANCE = createCore();
        private static Experi_mental_Core createCore() {
            return new Experi_mental_Core();
        }
    }
}
