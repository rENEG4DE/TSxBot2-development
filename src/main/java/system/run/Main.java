package system.run;

import common.base.SystemDesignator;
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
        super(SystemDesignator.SYSTEM, Main.class);
        log.info("Starting {}", cfg.SYSTEM_SERVERLABEL);
        core = Core.get();
        core.start();
        Core.get().addShutdownHook(() -> {
            log.info("{} has been terminated", cfg.SYSTEM_SERVERLABEL);
        });
    }
}
