/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

public final class CaveSurface
extends Enum<CaveSurface>
implements StringRepresentable {
    public static final /* enum */ CaveSurface CEILING = new CaveSurface(Direction.UP, 1, "ceiling");
    public static final /* enum */ CaveSurface FLOOR = new CaveSurface(Direction.DOWN, -1, "floor");
    public static final Codec<CaveSurface> f_162094_;
    private final Direction f_162095_;
    private final int f_162096_;
    private final String f_162097_;
    private static final /* synthetic */ CaveSurface[] $VALUES;

    public static CaveSurface[] values() {
        return (CaveSurface[])$VALUES.clone();
    }

    public static CaveSurface valueOf(String p_162114_) {
        return Enum.valueOf(CaveSurface.class, p_162114_);
    }

    private CaveSurface(Direction p_162104_, int p_162105_, String p_162106_) {
        this.f_162095_ = p_162104_;
        this.f_162096_ = p_162105_;
        this.f_162097_ = p_162106_;
    }

    public Direction m_162107_() {
        return this.f_162095_;
    }

    public int m_162110_() {
        return this.f_162096_;
    }

    @Override
    public String m_7912_() {
        return this.f_162097_;
    }

    private static /* synthetic */ CaveSurface[] m_162112_() {
        return new CaveSurface[]{CEILING, FLOOR};
    }

    static {
        $VALUES = CaveSurface.m_162112_();
        f_162094_ = StringRepresentable.m_216439_(CaveSurface::values);
    }
}

