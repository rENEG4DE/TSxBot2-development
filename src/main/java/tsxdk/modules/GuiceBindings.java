package tsxdk.modules;

import com.google.inject.Singleton;
import common.utility.Configuration;
import tsxdk.io.IO;
import tsxdk.io.IOImpl;
import tsxdk.io.SocketConnection;
import tsxdk.io.SocketConnectionImpl;
import tsxdk.model.TSServerConnectionModel;
import tsxdk.query.QueryChannel;
import tsxdk.query.QueryChannelImpl;
import tsxdk.query.QueryEngine;
import tsxdk.query.QueryEngineImpl;

public class GuiceBindings extends com.google.inject.AbstractModule {
    protected void configure() {
        bind(IO.class).to(IOImpl.class);
        bind(SocketConnection.class).to(SocketConnectionImpl.class);
        bind(QueryEngine.class).to(QueryEngineImpl.class);
        bind(TSServerConnectionModel.class).toProvider(Configuration.class).in(Singleton.class);
        bind(QueryChannel.class).to(QueryChannelImpl.class);
    }
}
