package com.tsxbot.system.plugin.runtime;

import com.google.inject.Injector;
import org.apache.commons.configuration.Configuration;

/**
 * Created by Ulli Gerhard on 23.10.2016.
 */
public interface PluginRuntime {
    Configuration getConfig();

    Injector getInjector();

    void setInjector(Injector injector);

    boolean isLoaded();

    default PluginRuntime lock() {
        return this;
    }
}
