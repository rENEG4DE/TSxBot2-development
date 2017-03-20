package com.tsxbot.system.plugin;

//import api.plugin.Plugin;
//import api.service.BotServices;
//import bot.BotException;
//import system.core.Context;

import com.google.common.base.Joiner;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Stage;
import com.tsxbot.api.ModClass.HelloWorld;
import com.tsxbot.common.defaults.ClientSystemDescriptors;
import com.tsxbot.tsxdk.base.TSX;
import com.tsxbot.tsxdk.utility.Modules;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.StringJoiner;

public class PluginIncarnator extends TSX {
    private final PluginManager mgr;

    public PluginIncarnator(PluginManager manager) {
        super(ClientSystemDescriptors.PLUGIN, PluginIncarnator.class);
        this.mgr = manager;
    }

    private static URL uriToUrl(URI uri) {
        URL url;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            url = null;
            e.printStackTrace();
        }
        return url;
    }

    public void incarnateAll() {
        log.info("Incarnating all plugins ({})", mgr.stream().map(Map.Entry::getKey).reduce("", (s, s2) -> s = s + ", " + s2 ));
        final StringJoiner stringJoiner = new StringJoiner(", ");
        mgr.stream().forEach(entry -> stringJoiner.add(entry.getKey()));

        log.info("Incarnating plugins ({})", stringJoiner.toString());

        try (URLClassLoader loader =
                        URLClassLoader.newInstance(
                                mgr.stream()
                                        .map(Map.Entry::getValue)
                                        .map(PluginHandle::getUri)
                                        .map(PluginIncarnator::uriToUrl)
                                        .toArray(URL[]::new),
                                getClass().getClassLoader())) {        //TODO: Maybe using an own class-loader here ?

            log.debug("URLClassLoader has been created");
            log.info("Trying to incarnate recognized plugins now");
//            mgr.stream().map(Map.Entry::getValue).forEach(pluginHandle -> incarnatePlugin(pluginHandle, loader));
            mgr.stream().forEach(entry -> incarnatePlugin(entry.getValue(), loader));
            loader.close();
        } catch (IOException e) {
            log.error("Exception while creating URLClassLoader", e);
        }
    }

    private void incarnatePlugin(PluginHandle pluginHandle, URLClassLoader loader) {
        if (!pluginHandle.isLoaded()) {
            final String module = pluginHandle.getName() + ".ModDef";
            try {
                log.info("Loading module-defintion {}", module);
                final Class pluginClass = loader.loadClass(module);
                log.debug("Creating Injector, {}", pluginClass);
                pluginHandle.setInjector(
                        Guice.createInjector(
                                Stage.PRODUCTION,
                                (AbstractModule) pluginClass.getConstructor().newInstance()
                        )
                );
                log.info("Finished plugin-incarnation, locking runtime");
                pluginHandle.lock();

                log.debug(Modules.listInjectorMeta(pluginHandle.getInjector()));

                log.info("Testing Injector");
                final HelloWorld testInstance = pluginHandle.getRuntime().getInjector().getInstance(HelloWorld.class);
                testInstance.sayHello();
            } catch (ClassNotFoundException
                    | NoSuchMethodException
                    | InstantiationException
                    | IllegalAccessException
                    | InvocationTargetException e) {
                log.error("Exception while loading module " + pluginHandle.getName() + ": ", e);
            }
        } else {
            log.info("Plugin {} has already been loaded", pluginHandle.getName());
        }
    }
}