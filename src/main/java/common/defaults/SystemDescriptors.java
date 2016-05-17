package common.defaults;

import tsxdk.base.TSX;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 15. of Mai
 * 2016
 * 20:27
 */
public enum SystemDescriptors implements TSX.SystemDescriptor {
    SYSTEM, UTILITY, IO;

    @Override
    public String get() {
        return name();
    }
}
