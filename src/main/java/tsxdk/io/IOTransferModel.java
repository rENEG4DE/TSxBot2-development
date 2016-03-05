package tsxdk.io;

import common.utility.Configuration;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by Ulli Gerhard on 05.03.2016.
 */
public class IOTransferModel {
    /**
     * Input coming from Teamspeak
     * <<TS
     */
    public class Input {
        private final ByteBuffer buffer = ByteBuffer.allocateDirect(Configuration.TSIO_INPUTBUFFERSIZE);

        void write () {
            ByteArrayInputStream stream = new ByteArrayInputStream(buffer.);
        }
    }
}
