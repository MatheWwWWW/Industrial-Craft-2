/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class Tilt
extends Enum<Tilt>
implements StringRepresentable {
    public static final /* enum */ Tilt NONE = new Tilt("none", true);
    public static final /* enum */ Tilt UNSTABLE = new Tilt("unstable", false);
    public static final /* enum */ Tilt PARTIAL = new Tilt("partial", true);
    public static final /* enum */ Tilt FULL = new Tilt("full", true);
    private final String f_156075_;
    private final boolean f_156076_;
    private static final /* synthetic */ Tilt[] $VALUES;

    public static Tilt[] values() {
        return (Tilt[])$VALUES.clone();
    }

    public static Tilt valueOf(String p_156088_) {
        return Enum.valueOf(Tilt.class, p_156088_);
    }

    private Tilt(String p_156082_, boolean p_156083_) {
        this.f_156075_ = p_156082_;
        this.f_156076_ = p_156083_;
    }

    @Override
    public String m_7912_() {
        return this.f_156075_;
    }

    public boolean m_156084_() {
        return this.f_156076_;
    }

    private static /* synthetic */ Tilt[] m_156085_() {
        return new Tilt[]{NONE, UNSTABLE, PARTIAL, FULL};
    }

    static {
        $VALUES = Tilt.m_156085_();
    }
}

