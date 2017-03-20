package com.tsxbot.system.plugin.runtime;

import com.google.inject.Injector;
import org.apache.commons.configuration.Configuration;

/**
 * Created by Ulli Gerhard on 17.10.2016.
 */
class PluginRuntimeLocked implements PluginRuntime {
    private final Configuration config;
    private final Injector injector;
    private boolean mounted;

    public PluginRuntimeLocked (PluginRuntimeLoading dirtyRuntime) {
        this.config = dirtyRuntime.getConfig();
        this.injector =  dirtyRuntime.getInjector();
    }

    @Override
    public Configuration getConfig() {
        return config;
    }

    @Override
    public Injector getInjector() {
        return injector;
    }

    boolean isMounted() {
        return mounted;
    }

    void mount(boolean mounted) {
        this.mounted = mounted;
    }

    @Override
    public boolean isLoaded() {
        return true;
    }

    @Override
    public void setInjector(Injector injector) {
        throw new IllegalAccessError("Injector for Plugin already instantiated, will not override");
    }

}
