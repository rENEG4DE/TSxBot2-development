package system.run;

import common.utility.Config;
import system.core.Core;

/**
 * Created by Ulli Gerhard on 21.02.2016.
 */
public class Main {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("system.Main");
    private static final org.apache.commons.configuration.Configuration cfg = Config.get();

    public static void main(String[] args) {
        log.info ("Starting TSxBot2");

        final Core core = Core.get ();
        core.start ();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    log.info ("TSxBot2 has been terminated");
                })
        );
    }
}
