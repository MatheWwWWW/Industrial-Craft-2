/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class DoubleBlockHalf
extends Enum<DoubleBlockHalf>
implements StringRepresentable {
    public static final /* enum */ DoubleBlockHalf UPPER = new DoubleBlockHalf();
    public static final /* enum */ DoubleBlockHalf LOWER = new DoubleBlockHalf();
    private static final /* synthetic */ DoubleBlockHalf[] $VALUES;

    public static DoubleBlockHalf[] values() {
        return (DoubleBlockHalf[])$VALUES.clone();
    }

    public static DoubleBlockHalf valueOf(String p_61574_) {
        return Enum.valueOf(DoubleBlockHalf.class, p_61574_);
    }

    public String toString() {
        return this.m_7912_();
    }

    @Override
    public String m_7912_() {
        return this == UPPER ? "upper" : "lower";
    }

    private static /* synthetic */ DoubleBlockHalf[] m_156006_() {
        return new DoubleBlockHalf[]{UPPER, LOWER};
    }

    static {
        $VALUES = DoubleBlockHalf.m_156006_();
    }
}

