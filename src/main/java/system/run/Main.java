package system.run;

import common.defaults.SystemDescriptors;
import common.utility.Configuration;
import tsxdk.base.TSX;
import system.core.Core;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 15. of Mai
 *  2016
 *  20:44
 */
class Main extends TSX {
    private final Core core;

    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        super(SystemDescriptors.SYSTEM, Main.class);
        log.info("Starting {}", Configuration.SYSTEM_SERVERLABEL);
        core = Core.get();
//        core.start();
        core.addShutdownHook(() -> log.info("{} terminated", Configuration.SYSTEM_SERVERLABEL));
    }
}
