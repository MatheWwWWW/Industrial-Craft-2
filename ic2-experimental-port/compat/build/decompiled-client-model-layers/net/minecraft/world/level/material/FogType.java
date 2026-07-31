/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.material;

public final class FogType
extends Enum<FogType> {
    public static final /* enum */ FogType LAVA = new FogType();
    public static final /* enum */ FogType WATER = new FogType();
    public static final /* enum */ FogType POWDER_SNOW = new FogType();
    public static final /* enum */ FogType NONE = new FogType();
    private static final /* synthetic */ FogType[] $VALUES;

    public static FogType[] values() {
        return (FogType[])$VALUES.clone();
    }

    public static FogType valueOf(String p_164526_) {
        return Enum.valueOf(FogType.class, p_164526_);
    }

    private static /* synthetic */ FogType[] m_164524_() {
        return new FogType[]{LAVA, WATER, POWDER_SNOW, NONE};
    }

    static {
        $VALUES = FogType.m_164524_();
    }
}

