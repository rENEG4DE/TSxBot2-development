package module;

import com.tsxbot.api.ModClass.HelloWorld;

/**
 * TSxBot2
 * Coded by Ulli Gerhard
 * on Mittwoch, 30 of November, 2016
 * 02:40
 */
public class HelloWorldImpl implements HelloWorld {
    @Override
    public void sayHello() {
        System.out.println("Hello World from Module!");
    }
}
