/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  javax.annotation.Nullable
 *  org.jetbrains.annotations.Contract
 */
package net.minecraft.world.item;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.Contract;

public final class DyeColor
extends Enum<DyeColor>
implements StringRepresentable {
    public static final /* enum */ DyeColor WHITE = new DyeColor(0, "white", 0xF9FFFE, MaterialColor.f_76406_, 0xF0F0F0, 0xFFFFFF);
    public static final /* enum */ DyeColor ORANGE = new DyeColor(1, "orange", 16351261, MaterialColor.f_76413_, 15435844, 16738335);
    public static final /* enum */ DyeColor MAGENTA = new DyeColor(2, "magenta", 13061821, MaterialColor.f_76414_, 12801229, 0xFF00FF);
    public static final /* enum */ DyeColor LIGHT_BLUE = new DyeColor(3, "light_blue", 3847130, MaterialColor.f_76415_, 6719955, 10141901);
    public static final /* enum */ DyeColor YELLOW = new DyeColor(4, "yellow", 16701501, MaterialColor.f_76416_, 14602026, 0xFFFF00);
    public static final /* enum */ DyeColor LIME = new DyeColor(5, "lime", 8439583, MaterialColor.f_76417_, 4312372, 0xBFFF00);
    public static final /* enum */ DyeColor PINK = new DyeColor(6, "pink", 15961002, MaterialColor.f_76418_, 14188952, 16738740);
    public static final /* enum */ DyeColor GRAY = new DyeColor(7, "gray", 4673362, MaterialColor.f_76419_, 0x434343, 0x808080);
    public static final /* enum */ DyeColor LIGHT_GRAY = new DyeColor(8, "light_gray", 0x9D9D97, MaterialColor.f_76420_, 0xABABAB, 0xD3D3D3);
    public static final /* enum */ DyeColor CYAN = new DyeColor(9, "cyan", 1481884, MaterialColor.f_76421_, 2651799, 65535);
    public static final /* enum */ DyeColor PURPLE = new DyeColor(10, "purple", 8991416, MaterialColor.f_76422_, 8073150, 10494192);
    public static final /* enum */ DyeColor BLUE = new DyeColor(11, "blue", 3949738, MaterialColor.f_76361_, 2437522, 255);
    public static final /* enum */ DyeColor BROWN = new DyeColor(12, "brown", 8606770, MaterialColor.f_76362_, 5320730, 9127187);
    public static final /* enum */ DyeColor GREEN = new DyeColor(13, "green", 6192150, MaterialColor.f_76363_, 3887386, 65280);
    public static final /* enum */ DyeColor RED = new DyeColor(14, "red", 11546150, MaterialColor.f_76364_, 11743532, 0xFF0000);
    public static final /* enum */ DyeColor BLACK = new DyeColor(15, "black", 0x1D1D21, MaterialColor.f_76365_, 0x1E1B1B, 0);
    private static final DyeColor[] f_41032_;
    private static final Int2ObjectOpenHashMap<DyeColor> f_41033_;
    private final int f_41034_;
    private final String f_41035_;
    private final MaterialColor f_41036_;
    private final float[] f_41039_;
    private final int f_41040_;
    private final int f_41041_;
    private static final /* synthetic */ DyeColor[] $VALUES;

    public static DyeColor[] values() {
        return (DyeColor[])$VALUES.clone();
    }

    public static DyeColor valueOf(String p_41074_) {
        return Enum.valueOf(DyeColor.class, p_41074_);
    }

    private DyeColor(int p_41046_, String p_41047_, int p_41048_, MaterialColor p_41049_, int p_41050_, int p_41051_) {
        this.f_41034_ = p_41046_;
        this.f_41035_ = p_41047_;
        this.f_41036_ = p_41049_;
        this.f_41041_ = p_41051_;
        int $$6 = (p_41048_ & 0xFF0000) >> 16;
        int $$7 = (p_41048_ & 0xFF00) >> 8;
        int $$8 = (p_41048_ & 0xFF) >> 0;
        this.f_41039_ = new float[]{(float)$$6 / 255.0f, (float)$$7 / 255.0f, (float)$$8 / 255.0f};
        this.f_41040_ = p_41050_;
    }

    public int m_41060_() {
        return this.f_41034_;
    }

    public String m_41065_() {
        return this.f_41035_;
    }

    public float[] m_41068_() {
        return this.f_41039_;
    }

    public MaterialColor m_41069_() {
        return this.f_41036_;
    }

    public int m_41070_() {
        return this.f_41040_;
    }

    public int m_41071_() {
        return this.f_41041_;
    }

    public static DyeColor m_41053_(int p_41054_) {
        if (p_41054_ < 0 || p_41054_ >= f_41032_.length) {
            p_41054_ = 0;
        }
        return f_41032_[p_41054_];
    }

    @Nullable
    @Contract(value="_,!null->!null;_,null->_")
    public static DyeColor m_41057_(String p_41058_, @Nullable DyeColor p_41059_) {
        for (DyeColor $$2 : DyeColor.values()) {
            if (!$$2.f_41035_.equals(p_41058_)) continue;
            return $$2;
        }
        return p_41059_;
    }

    @Nullable
    public static DyeColor m_41061_(int p_41062_) {
        return (DyeColor)f_41033_.get(p_41062_);
    }

    public String toString() {
        return this.f_41035_;
    }

    @Override
    public String m_7912_() {
        return this.f_41035_;
    }

    private static /* synthetic */ DyeColor[] m_150825_() {
        return new DyeColor[]{WHITE, ORANGE, MAGENTA, LIGHT_BLUE, YELLOW, LIME, PINK, GRAY, LIGHT_GRAY, CYAN, PURPLE, BLUE, BROWN, GREEN, RED, BLACK};
    }

    static {
        $VALUES = DyeColor.m_150825_();
        f_41032_ = (DyeColor[])Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::m_41060_)).toArray(DyeColor[]::new);
        f_41033_ = new Int2ObjectOpenHashMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(p_41064_ -> p_41064_.f_41040_, p_41056_ -> p_41056_)));
    }
}

