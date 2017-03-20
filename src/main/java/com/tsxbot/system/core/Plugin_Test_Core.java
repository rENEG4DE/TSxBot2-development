package com.tsxbot.system.core;

import com.tsxbot.common.defaults.ClientSystemDescriptors;
import com.tsxbot.system.plugin.PluginManager;

/**
 * TSxBot2
 * Coded by Ulli Gerhard
 * on Sonntag, 27 of November, 2016
 * 20:43
 */
public class Plugin_Test_Core extends BaseCore {

    public Plugin_Test_Core() {
        super(ClientSystemDescriptors.SYSTEM, Plugin_Test_Core.class);
    }

    @Override
    protected void doStuff() {
        final PluginManager mgr = new PluginManager();
        mgr.loadPlugins();
        mgr.mountPlugins();
    }
}
