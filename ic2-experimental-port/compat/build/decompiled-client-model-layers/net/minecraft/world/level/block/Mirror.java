/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.block;

import com.mojang.math.OctahedralGroup;
import com.mojang.serialization.Codec;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Rotation;

public final class Mirror
extends Enum<Mirror>
implements StringRepresentable {
    public static final /* enum */ Mirror NONE = new Mirror("none", OctahedralGroup.IDENTITY);
    public static final /* enum */ Mirror LEFT_RIGHT = new Mirror("left_right", OctahedralGroup.INVERT_Z);
    public static final /* enum */ Mirror FRONT_BACK = new Mirror("front_back", OctahedralGroup.INVERT_X);
    public static final Codec<Mirror> f_221524_;
    private final String f_221525_;
    private final Component f_153781_;
    private final OctahedralGroup f_54835_;
    private static final /* synthetic */ Mirror[] $VALUES;

    public static Mirror[] values() {
        return (Mirror[])$VALUES.clone();
    }

    public static Mirror valueOf(String p_54851_) {
        return Enum.valueOf(Mirror.class, p_54851_);
    }

    private Mirror(String p_221529_, OctahedralGroup p_221530_) {
        this.f_221525_ = p_221529_;
        this.f_153781_ = Component.m_237115_("mirror." + p_221529_);
        this.f_54835_ = p_221530_;
    }

    public int m_54843_(int p_54844_, int p_54845_) {
        int $$2 = p_54845_ / 2;
        int $$3 = p_54844_ > $$2 ? p_54844_ - p_54845_ : p_54844_;
        switch (this) {
            case FRONT_BACK: {
                return (p_54845_ - $$3) % p_54845_;
            }
            case LEFT_RIGHT: {
                return ($$2 - $$3 + p_54845_) % p_54845_;
            }
        }
        return p_54844_;
    }

    public Rotation m_54846_(Direction p_54847_) {
        Direction.Axis $$1 = p_54847_.m_122434_();
        return this == LEFT_RIGHT && $$1 == Direction.Axis.Z || this == FRONT_BACK && $$1 == Direction.Axis.X ? Rotation.CLOCKWISE_180 : Rotation.NONE;
    }

    public Direction m_54848_(Direction p_54849_) {
        if (this == FRONT_BACK && p_54849_.m_122434_() == Direction.Axis.X) {
            return p_54849_.m_122424_();
        }
        if (this == LEFT_RIGHT && p_54849_.m_122434_() == Direction.Axis.Z) {
            return p_54849_.m_122424_();
        }
        return p_54849_;
    }

    public OctahedralGroup m_54842_() {
        return this.f_54835_;
    }

    public Component m_153787_() {
        return this.f_153781_;
    }

    @Override
    public String m_7912_() {
        return this.f_221525_;
    }

    private static /* synthetic */ Mirror[] m_153788_() {
        return new Mirror[]{NONE, LEFT_RIGHT, FRONT_BACK};
    }

    static {
        $VALUES = Mirror.m_153788_();
        f_221524_ = StringRepresentable.m_216439_(Mirror::values);
    }
}

