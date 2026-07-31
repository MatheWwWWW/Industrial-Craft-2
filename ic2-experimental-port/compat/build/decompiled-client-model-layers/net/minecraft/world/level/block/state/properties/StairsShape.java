/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class StairsShape
extends Enum<StairsShape>
implements StringRepresentable {
    public static final /* enum */ StairsShape STRAIGHT = new StairsShape("straight");
    public static final /* enum */ StairsShape INNER_LEFT = new StairsShape("inner_left");
    public static final /* enum */ StairsShape INNER_RIGHT = new StairsShape("inner_right");
    public static final /* enum */ StairsShape OUTER_LEFT = new StairsShape("outer_left");
    public static final /* enum */ StairsShape OUTER_RIGHT = new StairsShape("outer_right");
    private final String f_61786_;
    private static final /* synthetic */ StairsShape[] $VALUES;

    public static StairsShape[] values() {
        return (StairsShape[])$VALUES.clone();
    }

    public static StairsShape valueOf(String p_61796_) {
        return Enum.valueOf(StairsShape.class, p_61796_);
    }

    private StairsShape(String p_61792_) {
        this.f_61786_ = p_61792_;
    }

    public String toString() {
        return this.f_61786_;
    }

    @Override
    public String m_7912_() {
        return this.f_61786_;
    }

    private static /* synthetic */ StairsShape[] m_156069_() {
        return new StairsShape[]{STRAIGHT, INNER_LEFT, INNER_RIGHT, OUTER_LEFT, OUTER_RIGHT};
    }

    static {
        $VALUES = StairsShape.m_156069_();
    }
}

