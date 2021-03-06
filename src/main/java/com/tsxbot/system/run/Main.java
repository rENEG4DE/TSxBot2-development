package com.tsxbot.system.run;

import com.tsxbot.common.defaults.ClientSystemDescriptors;
import com.tsxbot.system.core.BaseCore;
import com.tsxbot.system.core.Plugin_Test_Core;
import com.tsxbot.tsxdk.base.TSX;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 15. of Mai
 * 2016
 * 20:44
 */
class Main extends TSX {
//    private final Class<? extends BaseCore> coreClass = Future_test_Core.class;
    private final Class<? extends BaseCore> coreClass = Plugin_Test_Core.class;

    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        super(ClientSystemDescriptors.SYSTEM, Main.class);
        log.info("Starting {}", cfg.SYSTEM_SERVERLABEL);
        final BaseCore core = injector.getInstance(coreClass);
        core.addShutdownHook(() -> log.info("{} terminated", cfg.SYSTEM_SERVERLABEL));
        core.run();
    }
}
