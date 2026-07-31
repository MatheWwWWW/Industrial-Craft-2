/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class PistonType
extends Enum<PistonType>
implements StringRepresentable {
    public static final /* enum */ PistonType DEFAULT = new PistonType("normal");
    public static final /* enum */ PistonType STICKY = new PistonType("sticky");
    private final String f_61674_;
    private static final /* synthetic */ PistonType[] $VALUES;

    public static PistonType[] values() {
        return (PistonType[])$VALUES.clone();
    }

    public static PistonType valueOf(String p_61684_) {
        return Enum.valueOf(PistonType.class, p_61684_);
    }

    private PistonType(String p_61680_) {
        this.f_61674_ = p_61680_;
    }

    public String toString() {
        return this.f_61674_;
    }

    @Override
    public String m_7912_() {
        return this.f_61674_;
    }

    private static /* synthetic */ PistonType[] m_156027_() {
        return new PistonType[]{DEFAULT, STICKY};
    }

    static {
        $VALUES = PistonType.m_156027_();
    }
}

