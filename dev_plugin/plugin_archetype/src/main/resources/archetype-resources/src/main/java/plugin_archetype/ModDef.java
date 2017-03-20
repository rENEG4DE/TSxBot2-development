package plugin_a;

import com.google.inject.AbstractModule;
import com.tsxbot.api.ModClass.HelloWorld;
import module.HelloWorldImpl;

/**
 * Bind API-Interfaces to Plugin-Implementations
 */
public class ModDef extends AbstractModule {
    @Override
    protected void configure() {
        bind(HelloWorld.class).to(HelloWorldImpl.class);
    }
}
