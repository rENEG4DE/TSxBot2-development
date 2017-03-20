package com.tsxbot.system.plugin;

import com.google.common.collect.Maps;
import com.tsxbot.common.defaults.ClientSystemDescriptors;
import com.tsxbot.tsxdk.base.TSX;
import com.tsxbot.tsxdk.utility.Configuration;

import java.util.Map;
import java.util.stream.Stream;

public class PluginManager extends TSX {
    private final Map<String, PluginHandle> pluginMap = Maps.newHashMap();

    public PluginManager() {
        super(ClientSystemDescriptors.PLUGIN, PluginManager.class);
    }

    public void loadPlugins () {
        final PluginScanner finder = new PluginScanner();
        finder.addPath(Configuration.SYSTEM_PLUGINPATH);
        finder.walkPluginPaths().forEach(this::registerHandle);
        printManagedPlugins();
    }

    public void mountPlugins () {
        final PluginIncarnator incarnator = new PluginIncarnator(this);
        incarnator.incarnateAll();
    }

    public void printManagedPlugins() {
        log.info("Managed plugins START");
        pluginMap.values().forEach(s -> log.info(s.toString()));
        log.info("Managed plugins END");
    }

    public boolean registerHandle(PluginHandle ctr) {
        final String name = ctr.getName();
        if (pluginMap.get(name) != null) {
            log.error("Conflict - a plugin with name {} has already been registered", name);
            return false;
        }

        log.info("Plugin {} is now registered", name);
        pluginMap.put(name, ctr);
        return true;
    }

    public Stream<Map.Entry<String, PluginHandle>> stream() {
        return pluginMap.entrySet().stream();
    }
}
