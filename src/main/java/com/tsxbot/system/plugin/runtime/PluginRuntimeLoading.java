package com.tsxbot.system.plugin.runtime;

import com.google.inject.Injector;
import org.apache.commons.configuration.Configuration;

/**
 * Created by Ulli Gerhard on 17.10.2016.
 */
class PluginRuntimeLoading implements PluginRuntime {
    private Configuration config;
    private Injector injector;

    @Override
    public PluginRuntime lock() {
        return new PluginRuntimeLocked(this);
    }

    @Override
    public Configuration getConfig() {
        return config;
    }

    void setConfig(Configuration config) {
        this.config = config;
    }

    @Override
    public Injector getInjector() {
        return injector;
    }

    @Override
    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    @Override
    public boolean isLoaded() {
        return false;
    }
}
