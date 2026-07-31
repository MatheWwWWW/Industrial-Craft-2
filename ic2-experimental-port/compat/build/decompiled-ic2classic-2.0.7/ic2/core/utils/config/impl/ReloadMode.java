/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.impl;

import ic2.core.utils.config.api.IReloadMode;

public enum ReloadMode implements IReloadMode
{
    WORLD("Config Synced Please Rejoin the World"),
    GAME("Config Syncedd Please Restart the Game");

    String message;

    private ReloadMode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public static ReloadMode or(ReloadMode original, IReloadMode other) {
        return ReloadMode.getByIndex(Math.max(ReloadMode.getModeIndex(original), ReloadMode.getModeIndex(other instanceof ReloadMode ? (ReloadMode)other : null)));
    }

    private static int getModeIndex(ReloadMode mode) {
        return mode == null ? -1 : mode.ordinal();
    }

    private static ReloadMode getByIndex(int index) {
        return index == 0 ? WORLD : (index == 1 ? GAME : null);
    }
}

