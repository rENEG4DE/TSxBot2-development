package com.tsxbot.system.run;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tsxbot.common.defaults.ClientSystemDescriptors;
import com.tsxbot.system.GuiceClientSystemBindings;
import com.tsxbot.system.core.*;
import com.tsxbot.tsxdk.base.TSX;
import com.tsxbot.tsxdk.modules.GuiceTSxDKBindings;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 15. of Mai
 *  2016
 *  20:44
 */
class Main extends TSX {
    private final Injector injector = Guice.createInjector(new GuiceClientSystemBindings());

    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        super(ClientSystemDescriptors.SYSTEM, Main.class);
        log.info("Starting {}", cfg.SYSTEM_SERVERLABEL);
        final BaseCore core = injector.getInstance(Benchmark_Core.class);
        core.start();
//        BaseCore core = injector.getInstance(Experi_mental_Core.class);
//        BaseCore core = injector.getInstance(Future_test_Core.class);
//        BaseCore core = injector.getInstance(Guice_Core.class);
        core.addShutdownHook(() -> log.info("{} terminated", cfg.SYSTEM_SERVERLABEL));
    }
}
