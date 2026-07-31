/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity;

public final class MoverType
extends Enum<MoverType> {
    public static final /* enum */ MoverType SELF = new MoverType();
    public static final /* enum */ MoverType PLAYER = new MoverType();
    public static final /* enum */ MoverType PISTON = new MoverType();
    public static final /* enum */ MoverType SHULKER_BOX = new MoverType();
    public static final /* enum */ MoverType SHULKER = new MoverType();
    private static final /* synthetic */ MoverType[] $VALUES;

    public static MoverType[] values() {
        return (MoverType[])$VALUES.clone();
    }

    public static MoverType valueOf(String p_21658_) {
        return Enum.valueOf(MoverType.class, p_21658_);
    }

    private static /* synthetic */ MoverType[] m_147277_() {
        return new MoverType[]{SELF, PLAYER, PISTON, SHULKER_BOX, SHULKER};
    }

    static {
        $VALUES = MoverType.m_147277_();
    }
}

