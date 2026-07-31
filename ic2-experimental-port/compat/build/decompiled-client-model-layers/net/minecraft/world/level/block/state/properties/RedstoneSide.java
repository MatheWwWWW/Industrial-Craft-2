/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class RedstoneSide
extends Enum<RedstoneSide>
implements StringRepresentable {
    public static final /* enum */ RedstoneSide UP = new RedstoneSide("up");
    public static final /* enum */ RedstoneSide SIDE = new RedstoneSide("side");
    public static final /* enum */ RedstoneSide NONE = new RedstoneSide("none");
    private final String f_61753_;
    private static final /* synthetic */ RedstoneSide[] $VALUES;

    public static RedstoneSide[] values() {
        return (RedstoneSide[])$VALUES.clone();
    }

    public static RedstoneSide valueOf(String p_61764_) {
        return Enum.valueOf(RedstoneSide.class, p_61764_);
    }

    private RedstoneSide(String p_61759_) {
        this.f_61753_ = p_61759_;
    }

    public String toString() {
        return this.m_7912_();
    }

    @Override
    public String m_7912_() {
        return this.f_61753_;
    }

    public boolean m_61761_() {
        return this != NONE;
    }

    private static /* synthetic */ RedstoneSide[] m_156040_() {
        return new RedstoneSide[]{UP, SIDE, NONE};
    }

    static {
        $VALUES = RedstoneSide.m_156040_();
    }
}

