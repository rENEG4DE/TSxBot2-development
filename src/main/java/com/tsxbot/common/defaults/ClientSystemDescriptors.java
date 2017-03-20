package com.tsxbot.common.defaults;

import com.tsxbot.tsxdk.base.TSX;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 15. of Mai
 * 2016
 * 20:27
 */
public enum ClientSystemDescriptors implements TSX.SystemDescriptor {
    SYSTEM, CORE, PLUGIN;

    @Override
    public String get() {
        return name();
    }
}
