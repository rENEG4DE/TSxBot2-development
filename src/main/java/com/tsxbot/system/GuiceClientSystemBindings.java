/*
 * Created by IntelliJ IDEA.
 * User: Lenovo
 * Date: 29.05.2016
 * Time: 10:33
 */
package com.tsxbot.system;

import com.google.inject.AbstractModule;
import com.tsxbot.system.core.Benchmark_Core;
import com.tsxbot.system.core.Experi_mental_Core;
import com.tsxbot.system.core.Future_test_Core;
import com.tsxbot.system.core.Guice_Core;

public class GuiceClientSystemBindings extends AbstractModule {
    protected void configure() {
        bind(Benchmark_Core.class);
        bind(Experi_mental_Core.class);
        bind(Future_test_Core.class);
        bind(Guice_Core.class);
    }
}
