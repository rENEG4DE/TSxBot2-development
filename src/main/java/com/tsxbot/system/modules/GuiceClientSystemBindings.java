/*
 * Created by IntelliJ IDEA.
 * User: Lenovo
 * Date: 29.05.2016
 * Time: 10:33
 */
package com.tsxbot.system.modules;

import com.google.inject.AbstractModule;
import com.tsxbot.tsxdk.modules.TSxDKBindings;

public class GuiceClientSystemBindings extends AbstractModule {
    protected void configure() {
        install(new TSxDKBindings());
        install(new GuiceClientCoreBindings());
//        bind(Configuration.class).toProvider(Configuration.class);
    }
}
