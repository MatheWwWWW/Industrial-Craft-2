/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class WallSide
extends Enum<WallSide>
implements StringRepresentable {
    public static final /* enum */ WallSide NONE = new WallSide("none");
    public static final /* enum */ WallSide LOW = new WallSide("low");
    public static final /* enum */ WallSide TALL = new WallSide("tall");
    private final String f_61818_;
    private static final /* synthetic */ WallSide[] $VALUES;

    public static WallSide[] values() {
        return (WallSide[])$VALUES.clone();
    }

    public static WallSide valueOf(String p_61828_) {
        return Enum.valueOf(WallSide.class, p_61828_);
    }

    private WallSide(String p_61824_) {
        this.f_61818_ = p_61824_;
    }

    public String toString() {
        return this.m_7912_();
    }

    @Override
    public String m_7912_() {
        return this.f_61818_;
    }

    private static /* synthetic */ WallSide[] m_156090_() {
        return new WallSide[]{NONE, LOW, TALL};
    }

    static {
        $VALUES = WallSide.m_156090_();
    }
}

