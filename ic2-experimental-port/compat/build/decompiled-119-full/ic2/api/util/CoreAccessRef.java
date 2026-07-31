/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.util;

import ic2.api.util.CoreAccess;

public final class CoreAccessRef {
    static CoreAccess CORE_ACCESS;

    public static CoreAccess get() {
        CoreAccess coreAccess = CORE_ACCESS;
        if (coreAccess == null) {
            throw new IllegalStateException("IC2 is not loaded");
        }
        if (!coreAccess.isCallingFromIc2()) {
            throw new IllegalAccessError("external core access");
        }
        return coreAccess;
    }

    public static boolean exists() {
        return CORE_ACCESS != null;
    }
}

