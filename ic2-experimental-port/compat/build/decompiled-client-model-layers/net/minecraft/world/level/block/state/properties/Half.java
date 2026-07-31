/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class Half
extends Enum<Half>
implements StringRepresentable {
    public static final /* enum */ Half TOP = new Half("top");
    public static final /* enum */ Half BOTTOM = new Half("bottom");
    private final String f_61609_;
    private static final /* synthetic */ Half[] $VALUES;

    public static Half[] values() {
        return (Half[])$VALUES.clone();
    }

    public static Half valueOf(String p_61619_) {
        return Enum.valueOf(Half.class, p_61619_);
    }

    private Half(String p_61615_) {
        this.f_61609_ = p_61615_;
    }

    public String toString() {
        return this.f_61609_;
    }

    @Override
    public String m_7912_() {
        return this.f_61609_;
    }

    private static /* synthetic */ Half[] m_156025_() {
        return new Half[]{TOP, BOTTOM};
    }

    static {
        $VALUES = Half.m_156025_();
    }
}

