/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.item.upgrade;

import java.util.Locale;

public enum NbtSettings {
    IGNORED,
    FUZZY,
    EXACT;

    final String name = "ic2.upgrade.advancedGUI." + this.name().toLowerCase(Locale.ENGLISH);
    public static final NbtSettings[] VALUES;

    public boolean enabled() {
        return this != IGNORED;
    }

    public byte getForNBT() {
        return (byte)this.ordinal();
    }

    public static NbtSettings getFromNBT(byte type) {
        return VALUES[type];
    }

    static {
        VALUES = NbtSettings.values();
    }
}

