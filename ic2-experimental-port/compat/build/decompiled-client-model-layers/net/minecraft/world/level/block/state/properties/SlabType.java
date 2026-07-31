/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class SlabType
extends Enum<SlabType>
implements StringRepresentable {
    public static final /* enum */ SlabType TOP = new SlabType("top");
    public static final /* enum */ SlabType BOTTOM = new SlabType("bottom");
    public static final /* enum */ SlabType DOUBLE = new SlabType("double");
    private final String f_61769_;
    private static final /* synthetic */ SlabType[] $VALUES;

    public static SlabType[] values() {
        return (SlabType[])$VALUES.clone();
    }

    public static SlabType valueOf(String p_61779_) {
        return Enum.valueOf(SlabType.class, p_61779_);
    }

    private SlabType(String p_61775_) {
        this.f_61769_ = p_61775_;
    }

    public String toString() {
        return this.f_61769_;
    }

    @Override
    public String m_7912_() {
        return this.f_61769_;
    }

    private static /* synthetic */ SlabType[] m_156057_() {
        return new SlabType[]{TOP, BOTTOM, DOUBLE};
    }

    static {
        $VALUES = SlabType.m_156057_();
    }
}

