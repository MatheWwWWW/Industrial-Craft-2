/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.block;

import com.mojang.math.OctahedralGroup;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;

public final class Rotation
extends Enum<Rotation>
implements StringRepresentable {
    public static final /* enum */ Rotation NONE = new Rotation("none", OctahedralGroup.IDENTITY);
    public static final /* enum */ Rotation CLOCKWISE_90 = new Rotation("clockwise_90", OctahedralGroup.ROT_90_Y_NEG);
    public static final /* enum */ Rotation CLOCKWISE_180 = new Rotation("180", OctahedralGroup.ROT_180_FACE_XZ);
    public static final /* enum */ Rotation COUNTERCLOCKWISE_90 = new Rotation("counterclockwise_90", OctahedralGroup.ROT_90_Y_POS);
    public static final Codec<Rotation> f_221983_;
    private final String f_221984_;
    private final OctahedralGroup f_55941_;
    private static final /* synthetic */ Rotation[] $VALUES;

    public static Rotation[] values() {
        return (Rotation[])$VALUES.clone();
    }

    public static Rotation valueOf(String p_55961_) {
        return Enum.valueOf(Rotation.class, p_55961_);
    }

    private Rotation(String p_221988_, OctahedralGroup p_221989_) {
        this.f_221984_ = p_221988_;
        this.f_55941_ = p_221989_;
    }

    public Rotation m_55952_(Rotation p_55953_) {
        switch (p_55953_) {
            case CLOCKWISE_180: {
                switch (this) {
                    case NONE: {
                        return CLOCKWISE_180;
                    }
                    case CLOCKWISE_90: {
                        return COUNTERCLOCKWISE_90;
                    }
                    case CLOCKWISE_180: {
                        return NONE;
                    }
                    case COUNTERCLOCKWISE_90: {
                        return CLOCKWISE_90;
                    }
                }
            }
            case COUNTERCLOCKWISE_90: {
                switch (this) {
                    case NONE: {
                        return COUNTERCLOCKWISE_90;
                    }
                    case CLOCKWISE_90: {
                        return NONE;
                    }
                    case CLOCKWISE_180: {
                        return CLOCKWISE_90;
                    }
                    case COUNTERCLOCKWISE_90: {
                        return CLOCKWISE_180;
                    }
                }
            }
            case CLOCKWISE_90: {
                switch (this) {
                    case NONE: {
                        return CLOCKWISE_90;
                    }
                    case CLOCKWISE_90: {
                        return CLOCKWISE_180;
                    }
                    case CLOCKWISE_180: {
                        return COUNTERCLOCKWISE_90;
                    }
                    case COUNTERCLOCKWISE_90: {
                        return NONE;
                    }
                }
            }
        }
        return this;
    }

    public OctahedralGroup m_55948_() {
        return this.f_55941_;
    }

    public Direction m_55954_(Direction p_55955_) {
        if (p_55955_.m_122434_() == Direction.Axis.Y) {
            return p_55955_;
        }
        switch (this) {
            case CLOCKWISE_180: {
                return p_55955_.m_122424_();
            }
            case COUNTERCLOCKWISE_90: {
                return p_55955_.m_122428_();
            }
            case CLOCKWISE_90: {
                return p_55955_.m_122427_();
            }
        }
        return p_55955_;
    }

    public int m_55949_(int p_55950_, int p_55951_) {
        switch (this) {
            case CLOCKWISE_180: {
                return (p_55950_ + p_55951_ / 2) % p_55951_;
            }
            case COUNTERCLOCKWISE_90: {
                return (p_55950_ + p_55951_ * 3 / 4) % p_55951_;
            }
            case CLOCKWISE_90: {
                return (p_55950_ + p_55951_ / 4) % p_55951_;
            }
        }
        return p_55950_;
    }

    public static Rotation m_221990_(RandomSource p_221991_) {
        return Util.m_214670_(Rotation.values(), p_221991_);
    }

    public static List<Rotation> m_221992_(RandomSource p_221993_) {
        return Util.m_214681_(Rotation.values(), p_221993_);
    }

    @Override
    public String m_7912_() {
        return this.f_221984_;
    }

    private static /* synthetic */ Rotation[] m_154379_() {
        return new Rotation[]{NONE, CLOCKWISE_90, CLOCKWISE_180, COUNTERCLOCKWISE_90};
    }

    static {
        $VALUES = Rotation.m_154379_();
        f_221983_ = StringRepresentable.m_216439_(Rotation::values);
    }
}

