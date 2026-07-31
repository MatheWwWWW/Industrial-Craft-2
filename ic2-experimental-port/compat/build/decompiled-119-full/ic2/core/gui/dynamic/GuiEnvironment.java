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

    public static GuiEnvironment get(String string) {
        return map.get(string);
    }

    private static Map<String, GuiEnvironment> getMap() {
        GuiEnvironment[] guiEnvironmentArray = GuiEnvironment.values();
        HashMap<String, GuiEnvironment> hashMap = new HashMap<String, GuiEnvironment>(guiEnvironmentArray.length);
        for (GuiEnvironment guiEnvironment : guiEnvironmentArray) {
            hashMap.put(guiEnvironment.name, guiEnvironment);
        }
        return hashMap;
    }

    static {
        map = GuiEnvironment.getMap();
    }
}

