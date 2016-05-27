package system.run;

import common.defaults.SystemDescriptors;
import common.utility.Configuration;
import system.core.Experi_mental_Core;
import tsxdk.base.TSX;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 15. of Mai
 *  2016
 *  20:44
 */
class Main extends TSX {
//    private final Core core;

    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        super(SystemDescriptors.SYSTEM, Main.class);
        log.info("Starting {}", cfg.SYSTEM_SERVERLABEL);
//        core = Core.get();
        Experi_mental_Core.get();
//        core.start();
        Experi_mental_Core.get ().addShutdownHook(() -> log.info("{} terminated", cfg.SYSTEM_SERVERLABEL));
    }
}
