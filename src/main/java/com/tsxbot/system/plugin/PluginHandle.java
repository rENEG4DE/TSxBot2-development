package com.tsxbot.system.plugin;

import com.google.inject.Injector;
import com.tsxbot.system.plugin.runtime.PluginRuntime;
import com.tsxbot.system.plugin.runtime.PluginRuntimeProxy;

import java.net.URI;
import java.util.Optional;

/**
 * TSxBot2
 * Coded by Ulli Gerhard
 * on Sonntag, 27 of November, 2016
 * 22:40
 */
public class PluginHandle {
    private final PluginData data;
    private final PluginRuntimeProxy runtime;

    public PluginHandle(PluginData data) {
        this.data = data;
        this.runtime = new PluginRuntimeProxy(data);
    }

    public PluginData getData() {
        return data;
    }

    @Override
    public String toString() {
        return "PluginHandle{" +
                "data=" + data +
                '}';
    }

    public String getName() {
        return data.getName();
    }

    public String getPath() {
        return data.getPath();
    }

    public Optional<String> getConfigPath() {
        return data.getConfigPath();
    }

    public URI getUri() {
        return data.getUri();
    }

    public void setInjector(Injector injector) {
        runtime.setInjector(injector);
    }

    public Injector getInjector() {
        return runtime.getInjector();
    }

    public PluginRuntime getRuntime() {
        return runtime;
    }

    public boolean isLoaded() {
        return runtime.isLoaded();
    }

    public void lock() {
        runtime.lockPluginRuntime();
    }
}
