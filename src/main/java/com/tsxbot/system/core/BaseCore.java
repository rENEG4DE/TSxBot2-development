package com.tsxbot.system.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tsxbot.tsxdk.base.TSX;
import com.tsxbot.tsxdk.modules.GuiceTSxDKBindings;
import com.tsxbot.tsxdk.query.QueryGateway;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 29. of Mai
 * 2016
 * 08:39
 */
public abstract class BaseCore extends TSX {
    public BaseCore(SystemDescriptor subSystem, Class<?> clazz) {
        super(subSystem, clazz);
        log.info("Environment: {}", cfg.getEnvironment());
    }

    public void run() {
        log.info("Running core-tasks now");
        doStuff();
        log.info("Core-tasks are finished");
        System.exit(0);
    }

    protected abstract void doStuff();

    protected final Injector injector = Guice.createInjector(new GuiceTSxDKBindings());

    protected QueryGateway obtainQueryGateway() {
        return injector.getInstance(QueryGateway.class);
    }

    public void addShutdownHook(Runnable runnable) {
        Runtime.getRuntime().addShutdownHook(new Thread(runnable));
    }
}
