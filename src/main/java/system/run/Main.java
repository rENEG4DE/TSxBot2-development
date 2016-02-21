package system.run;

import system.core.Core;

/**
 * Created by Ulli Gerhard on 21.02.2016.
 */
public class Main {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("system.Main");
    private final Core core;

    public static void main(String[] args) {
        log.info ("Starting TSxBot2");

        new Main ();

        Core.get().addShutdownHook(() -> {
            log.info ("TSxBot2 has been terminated");
        });
    }

    private Main () {
        core = Core.get();
        core.start ();
    }
}
