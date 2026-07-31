/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.DyeColor
 */
package ic2.core.util;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.item.DyeColor;

public enum Ic2Color {
    BLACK(DyeColor.BLACK, 0x1D1D21),
    BLUE(DyeColor.BLUE, 3949738),
    BROWN(DyeColor.BROWN, 8606770),
    CYAN(DyeColor.CYAN, 1481884),
    GRAY(DyeColor.GRAY, 4673362),
    GREEN(DyeColor.GREEN, 6192150),
    LIGHT_BLUE(DyeColor.LIGHT_BLUE, 3847130),
    LIGHT_GRAY(DyeColor.LIGHT_GRAY, 0x9D9D97),
    LIME(DyeColor.LIME, 8439583),
    MAGENTA(DyeColor.MAGENTA, 13061821),
    ORANGE(DyeColor.ORANGE, 16351261),
    PINK(DyeColor.PINK, 15961002),
    PURPLE(DyeColor.PURPLE, 8991416),
    RED(DyeColor.RED, 11546150),
    WHITE(DyeColor.WHITE, 0xF9FFFE),
    YELLOW(DyeColor.YELLOW, 16701501);

    public static final Ic2Color[] values;
    private static final Map<DyeColor, Ic2Color> dyeColorMap;
    private static final Map<Integer, Ic2Color> colorMap;
    public final DyeColor dyeColor;
    public final int color;

    private Ic2Color(DyeColor dyeColor, int n2) {
        this.dyeColor = dyeColor;
        this.color = n2;
    }

    public int getId() {
        return this.ordinal();
    }

    public int getColor() {
        return this.color;
    }

    public static Ic2Color get(DyeColor dyeColor) {
        return dyeColorMap.get(dyeColor);
    }

    public static Ic2Color byColor(int n) {
        return colorMap.get(n);
    }

    static {
        values = Ic2Color.values();
        dyeColorMap = new EnumMap<DyeColor, Ic2Color>(DyeColor.class);
        colorMap = new HashMap<Integer, Ic2Color>();
        for (Ic2Color ic2Color : values) {
            dyeColorMap.put(ic2Color.dyeColor, ic2Color);
            colorMap.put(ic2Color.color, ic2Color);
        }
    }
}

