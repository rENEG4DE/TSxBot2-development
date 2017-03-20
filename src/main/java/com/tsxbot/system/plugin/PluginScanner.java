package com.tsxbot.system.plugin;

import com.google.common.collect.Lists;
import com.tsxbot.common.defaults.ClientSystemDescriptors;
import com.tsxbot.tsxdk.base.TSX;
import com.tsxbot.tsxdk.utility.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

class PluginScanner extends TSX {
    private final List<String> paths = Lists.newLinkedList();

    protected PluginScanner() {
        super(ClientSystemDescriptors.PLUGIN, PluginScanner.class);
        log.debug("Created PluginScanner");
    }

    private String fileToString(File file) {
        final StringBuilder builder = new StringBuilder();
        builder.append("File{");
        builder.append("absoluteFile=").append(file.getAbsoluteFile());
        builder.append(",absulutePath=").append(file.getAbsolutePath());
        try {
            builder.append(",canonicalFile=").append(file.getCanonicalFile());
            builder.append(",canonicalPath=").append(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        builder.append(",name=").append(file.getName());
        builder.append(",parentFile=").append(file.getParentFile());
        builder.append(",parent=").append(file.getParent());
        builder.append(",path=").append(file.getPath());
        builder.append("}");
        return builder.toString();
    }

    public void addPath(String path) {
        if (!paths.contains(path))
            paths.add(path);
        else
            log.info("Plugin-path {} is already present", path);
    }

    public List<PluginHandle> walkPluginPaths() {
        final List<PluginHandle> result = Lists.newLinkedList();

        paths.forEach(System.out::println);

        paths.stream().map(File::new).forEach(currentDirectory -> {
            if (currentDirectory.isDirectory()) {
                log.debug("Scanning folder for plugins ({})", currentDirectory.getAbsolutePath());
                for (File subFolder : currentDirectory.listFiles(File::isDirectory)) {
                    log.debug("Suspected plugin-directory {}", subFolder);
                    final PluginData pluginData = applyPluginContract(subFolder);
                    if (pluginData != null) {
                        result.add(createPluginHandle(pluginData));
                    }
                }
            } else {
                log.error("{} is not a directory", currentDirectory.getName());
            }
        });

        return result;
    }

    private PluginData applyPluginContract(File subFolder) {

        boolean pluginFound = false;
        String pluginName = "[EMPTY_PLUGIN_NAME]";
        File jar = null;
        Optional<File> cfg = Optional.empty();
        for (File f : subFolder.listFiles()) {
            if (f.isFile()) {
                final String name = f.getName();
                if (name.endsWith(".jar")) {
                    if (name.startsWith(subFolder.getName())) {
                        pluginFound = true;
                        jar = f;
                        pluginName = name.replace(".jar", "");
                        log.debug("Found plugin-jar {}", name);
                    } else {
                        log.debug("Ignoring jar {}", name);
                    }
                } else if (name.equals(Configuration.SYSTEM_PLUGINCFG)) {
                    cfg = Optional.of(f);
                    log.debug("Found plugin-config for {} ({})", subFolder.getName(), Configuration.SYSTEM_PLUGINCFG);
                }
            }
        }

        if (pluginFound && Objects.nonNull(jar)) {
            log.info("Found plugin-candidate ({})", pluginName);
            return new PluginData(pluginName,
                    jar.getPath(),
                    Optional.ofNullable(cfg.map(File::getPath).orElse(null)),
                    "[ENTRY_METHOD_CONTRACT]",
                    jar.toURI());
        } else {
            log.warn("{} does not adhere plugin-contract, ignored", subFolder.getName());
        }
        return null;
    }

    private PluginHandle createPluginHandle(PluginData pluginData) {
        return new PluginHandle(pluginData);
    }
}
