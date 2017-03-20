package plugin_a;

import com.google.inject.AbstractModule;
import com.tsxbot.api.ModClass.HelloWorld;
import module.HelloWorldImpl;

/*
 * TSxBot2
 * Coded by Ulli Gerhard
 * on Mittwoch, 30 of November, 2016
 * 02:41
 */
public class ModDef extends AbstractModule {
    @Override
    protected void configure() {
        bind(HelloWorld.class).to(HelloWorldImpl.class);
    }
}
