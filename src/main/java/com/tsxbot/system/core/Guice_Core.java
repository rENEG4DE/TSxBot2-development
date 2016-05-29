package com.tsxbot.system.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.tsxbot.common.defaults.ClientSystemDescriptors;
import com.tsxbot.tsxdk.io.IO;
import com.tsxbot.tsxdk.modules.GuiceTSxDKBindings;
import com.tsxbot.tsxdk.model.TSServerConnectionModel;

/*
 * TSxBot2
 * Coded by rENEG4DE
 * on 29. of Mai
 * 2016
 * 09:21
 */
public class Guice_Core extends BaseCore {

    public Guice_Core() {
        super(ClientSystemDescriptors.CORE, Guice_Core.class);
        log.info("Guice_Core created");
    }

    protected void doStuff() {
        TSServerConnectionModel serverHandle;
        IO pipe;
        Injector injector;
        Provider<TSServerConnectionModel> provider;

        log.info("Environment: {}", cfg.getEnvironment());
        injector = Guice.createInjector(new GuiceTSxDKBindings());
        log.info("Created injector: {}", injector);
        provider = injector.getProvider(TSServerConnectionModel.class);
        log.info("Obtained provider: {}", provider);
        serverHandle = provider.get();
        log.info("Obtained serverHandle: {}", serverHandle);
        pipe = injector.getInstance(IO.class);
        log.info("Obtained pipe: {}", pipe);

        log.info("Guice_Core suspended");
    }
}
