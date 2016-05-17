/*
 * Created by IntelliJ IDEA.
 * User: Lenovo
 * Date: 06.03.2016
 * Time: 13:12
 */
package system.core;

import com.google.inject.Provider;
import com.google.inject.Provides;
import common.utility.Configuration;
import tsxdk.model.TSServerConnectionModel;

public class ServerConnectionModelProvider implements Provider<TSServerConnectionModel> {
    @Provides
    public TSServerConnectionModel get() {
        return new TSServerConnectionModel(Configuration.TSSERVER_HOST, Configuration.TSSERVER_PORT);
    }
}
