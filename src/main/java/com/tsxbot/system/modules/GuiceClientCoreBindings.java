package com.tsxbot.system.modules;

import com.google.inject.AbstractModule;
import com.tsxbot.system.core.Benchmark_Core;
import com.tsxbot.system.core.Future_test_Core;
import com.tsxbot.system.core.Guice_Core;
import com.tsxbot.system.core.Plugin_Test_Core;

/**
 * TSxBot2
 * Coded by Lenovo
 * on 27 of 11
 * 2016
 * 20:28
 */
public class GuiceClientCoreBindings extends AbstractModule {
    protected void configure() {
        bind(Benchmark_Core.class);
        bind(Future_test_Core.class);
        bind(Guice_Core.class);
        bind(Plugin_Test_Core.class);
    }
}
