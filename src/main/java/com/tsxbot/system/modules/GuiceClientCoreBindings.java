package com.tsxbot.system.modules;

import com.google.inject.AbstractModule;
import com.tsxbot.system.core.Benchmark_Core;
import com.tsxbot.system.core.Future_test_Core;
import com.tsxbot.system.core.Guice_Core;

/**
 * Created by Lenovo on 23.06.2016.
 */
public class GuiceClientCoreBindings extends AbstractModule {
    protected void configure() {
        bind(Benchmark_Core.class);
        bind(Future_test_Core.class);
        bind(Guice_Core.class);
    }
}
