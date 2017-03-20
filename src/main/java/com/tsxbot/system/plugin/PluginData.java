package com.tsxbot.system.plugin;

import java.net.URI;
import java.util.Optional;

/**
 * Created by Ulli Gerhard on 23.10.2016.
 */
public class PluginData {
    private final String name;
    private final String path;
    private final Optional<String> configPath;
    private final String entryMethod;
    private final URI uri;

    public PluginData(String name, String path, Optional<String> configPath, String entryMethod, URI url) {
        this.name = name;
        this.path = path;
        this.configPath = configPath;
        this.entryMethod = entryMethod;
        this.uri = url;
    }

    @Override
    public String toString() {
        return "PluginData{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", configPath=" + configPath +
                ", entryMethod='" + entryMethod + '\'' +
                ", uri=" + uri +
                '}';
    }

    public String getEntryMethod() {
        return entryMethod;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public URI getUri() {
        return uri;
    }

    public Optional<String> getConfigPath() {
        return configPath;
    }
}