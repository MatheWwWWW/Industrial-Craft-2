/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.item.DyeColor
 */
package ic2.core.block.wiring;

import ic2.core.block.misc.WallBlock;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;

public enum CableFoam implements StringRepresentable
{
    SOFT("soft", null),
    HARD_WHITE("hard_white", DyeColor.WHITE),
    HARD_ORANGE("hard_orange", DyeColor.ORANGE),
    HARD_MAGENTA("hard_magenta", DyeColor.MAGENTA),
    HARD_LIGHT_BLUE("hard_light_blue", DyeColor.LIGHT_BLUE),
    HARD_YELLOW("hard_yellow", DyeColor.YELLOW),
    HARD_LIME("hard_lime", DyeColor.LIME),
    HARD_PINK("hard_pink", DyeColor.PINK),
    HARD_GRAY("hard_gray", DyeColor.GRAY),
    HARD_LIGHT_GRAY("hard_light_gray", DyeColor.LIGHT_GRAY),
    HARD_CYAN("hard_cyan", DyeColor.CYAN),
    HARD_PURPLE("hard_purple", DyeColor.PURPLE),
    HARD_BLUE("hard_blue", DyeColor.BLUE),
    HARD_BROWN("hard_brown", DyeColor.BROWN),
    HARD_GREEN("hard_green", DyeColor.GREEN),
    HARD_RED("hard_red", DyeColor.RED),
    HARD_BLACK("hard_black", DyeColor.BLACK);

    public static final CableFoam[] VALUES;
    private static final Map<String, CableFoam> NAME_MAP;
    private static final Map<DyeColor, CableFoam> COLOR_MAP;
    public static final CableFoam DEFAULT_HARD;
    private final String name;
    private final DyeColor color;

    public static CableFoam get(String string) {
        return NAME_MAP.get(string);
    }

    public static CableFoam getHard(DyeColor dyeColor) {
        return COLOR_MAP.get(dyeColor);
    }

    private CableFoam(String string2, DyeColor dyeColor) {
        this.name = string2;
        this.color = dyeColor;
    }

    public boolean isPresent() {
        return true;
    }

    public boolean isSoft() {
        return this == SOFT;
    }

    public boolean isHard() {
        return this.isPresent() && !this.isSoft();
    }

    public DyeColor getColor() {
        if (this.color == null) {
            throw new IllegalStateException();
        }
        return this.color;
    }

    public String m_7912_() {
        return this.name;
    }

    private static Map<String, CableFoam> createNameMap() {
        HashMap<String, CableFoam> hashMap = new HashMap<String, CableFoam>();
        for (CableFoam cableFoam : VALUES) {
            hashMap.put(cableFoam.name, cableFoam);
        }
        return hashMap;
    }

    private static Map<DyeColor, CableFoam> createColorMap() {
        EnumMap<DyeColor, CableFoam> enumMap = new EnumMap<DyeColor, CableFoam>(DyeColor.class);
        for (CableFoam cableFoam : VALUES) {
            if (cableFoam.color == null) continue;
            enumMap.put(cableFoam.color, cableFoam);
        }
        return enumMap;
    }

    static {
        VALUES = CableFoam.values();
        NAME_MAP = CableFoam.createNameMap();
        COLOR_MAP = CableFoam.createColorMap();
        DEFAULT_HARD = CableFoam.getHard(WallBlock.DEFAULT_COLOR);
    }
}

