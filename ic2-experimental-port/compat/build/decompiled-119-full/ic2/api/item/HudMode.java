/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.item;

public enum HudMode {
    DISABLED("ic2.hud.disabled"),
    BASIC("ic2.hud.basic"),
    EXTENDED("ic2.hud.extended"),
    ADVANCED("ic2.hud.advanced");

    private final String translationKey;
    private static final HudMode[] VALUES;

    private HudMode(String string2) {
        this.translationKey = string2;
    }

    public boolean shouldDisplay() {
        return this != DISABLED;
    }

    public boolean hasTooltip() {
        return this == EXTENDED || this == ADVANCED;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public int getID() {
        return this.ordinal();
    }

    public static HudMode getFromID(int n) {
        return VALUES[n % VALUES.length];
    }

    public static int getMaxMode() {
        return VALUES.length - 1;
    }

    static {
        VALUES = HudMode.values();
    }
}

