/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class ComparatorMode
extends Enum<ComparatorMode>
implements StringRepresentable {
    public static final /* enum */ ComparatorMode COMPARE = new ComparatorMode("compare");
    public static final /* enum */ ComparatorMode SUBTRACT = new ComparatorMode("subtract");
    private final String f_61528_;
    private static final /* synthetic */ ComparatorMode[] $VALUES;

    public static ComparatorMode[] values() {
        return (ComparatorMode[])$VALUES.clone();
    }

    public static ComparatorMode valueOf(String p_61538_) {
        return Enum.valueOf(ComparatorMode.class, p_61538_);
    }

    private ComparatorMode(String p_61534_) {
        this.f_61528_ = p_61534_;
    }

    public String toString() {
        return this.f_61528_;
    }

    @Override
    public String m_7912_() {
        return this.f_61528_;
    }

    private static /* synthetic */ ComparatorMode[] m_156002_() {
        return new ComparatorMode[]{COMPARE, SUBTRACT};
    }

    static {
        $VALUES = ComparatorMode.m_156002_();
    }
}

