/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui.dynamic;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum GuiEnvironment {
    GAME,
    JEI;

    private static final Map<String, GuiEnvironment> map;
    public final String name = this.name().toLowerCase(Locale.ENGLISH);

    public static GuiEnvironment get(String name) {
        return map.get(name);
    }

    private static Map<String, GuiEnvironment> getMap() {
        GuiEnvironment[] values = GuiEnvironment.values();
        HashMap<String, GuiEnvironment> ret = new HashMap<String, GuiEnvironment>(values.length);
        for (GuiEnvironment value : values) {
            ret.put(value.name, value);
        }
        return ret;
    }

    static {
        map = GuiEnvironment.getMap();
    }
}

