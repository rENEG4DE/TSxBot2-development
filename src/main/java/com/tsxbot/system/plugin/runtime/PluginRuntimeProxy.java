package com.tsxbot.system.plugin.runtime;

import com.google.inject.Injector;
import com.tsxbot.common.defaults.ClientSystemDescriptors;
import com.tsxbot.system.plugin.PluginData;
import com.tsxbot.tsxdk.base.TSX;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.Optional;

/**
 * Created by Ulli Gerhard on 23.10.2016.
 */
public class PluginRuntimeProxy extends TSX implements PluginRuntime{
    private PluginRuntime runtime;

    public PluginRuntimeProxy(PluginData data) {
        super(ClientSystemDescriptors.PLUGIN, PluginRuntime.class);
        log.info("Creating PluginRuntimeProxy for {}", data.getName());
        final PluginRuntimeLoading rt = new PluginRuntimeLoading();
        try {
            final PropertiesConfiguration config = new PropertiesConfiguration("plugin.properties");
            final Optional<String> configPath = data.getConfigPath();
            if (configPath.isPresent()) {
                log.info("Trying to load additional plugin-configuration {}", configPath.get());
                config.copy(new PropertiesConfiguration(configPath.get()));
            }

            rt.setConfig(config);
        } catch (ConfigurationException e) {
            log.error("Unable to parse configuration for plugin (" + data.getName() + ")", e);
        }

        runtime = rt;
    }

    public void lockPluginRuntime (){
        runtime = runtime.lock();
    }

    @Override
    public Configuration getConfig() {
        return runtime.getConfig();
    }

    @Override
    public Injector getInjector() {
        return runtime.getInjector();
    }

    @Override
    public boolean isLoaded() {
        return runtime.isLoaded();
    }

    @Override
    public void setInjector(Injector injector) {
        runtime.setInjector(injector);
    }
}
