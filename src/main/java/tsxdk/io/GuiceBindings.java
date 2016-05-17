package tsxdk.io;

import com.google.inject.Singleton;
import system.core.ServerConnectionModelProvider;
import tsxdk.model.TSServerConnectionModel;

public class GuiceBindings extends com.google.inject.AbstractModule {
    protected void configure() {
        bind(IO.class).to(IOImpl.class);
        bind(SocketConnection.class).to(SocketConnectionImpl.class);
        bind(TSServerConnectionModel.class).toProvider(ServerConnectionModelProvider.class).in(Singleton.class);
    }
}
