package system.run;

import common.base.SystemDescriptor;
import common.base.TSX;
import system.core.Core;

/**
 * Created by Ulli Gerhard on 21.02.2016.
 */
public class Main extends TSX {
    private final Core core;

    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        super(SystemDescriptor.SYSTEM, Main.class);
        log.info("Starting {}", cfg.SYSTEM_SERVERLABEL);
        core = Core.get();
//        core.start();
        core.addShutdownHook(() -> {
            log.info("{} terminated", cfg.SYSTEM_SERVERLABEL);
        });
    }
}
