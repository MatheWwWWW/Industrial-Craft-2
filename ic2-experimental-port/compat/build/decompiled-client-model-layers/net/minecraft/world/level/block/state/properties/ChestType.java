/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class ChestType
extends Enum<ChestType>
implements StringRepresentable {
    public static final /* enum */ ChestType SINGLE = new ChestType("single", 0);
    public static final /* enum */ ChestType LEFT = new ChestType("left", 2);
    public static final /* enum */ ChestType RIGHT = new ChestType("right", 1);
    public static final ChestType[] f_61475_;
    private final String f_61476_;
    private final int f_61477_;
    private static final /* synthetic */ ChestType[] $VALUES;

    public static ChestType[] values() {
        return (ChestType[])$VALUES.clone();
    }

    public static ChestType valueOf(String p_61488_) {
        return Enum.valueOf(ChestType.class, p_61488_);
    }

    private ChestType(String p_61483_, int p_61484_) {
        this.f_61476_ = p_61483_;
        this.f_61477_ = p_61484_;
    }

    @Override
    public String m_7912_() {
        return this.f_61476_;
    }

    public ChestType m_61486_() {
        return f_61475_[this.f_61477_];
    }

    private static /* synthetic */ ChestType[] m_156001_() {
        return new ChestType[]{SINGLE, LEFT, RIGHT};
    }

    static {
        $VALUES = ChestType.m_156001_();
        f_61475_ = ChestType.values();
    }
}

