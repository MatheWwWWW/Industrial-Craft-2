/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParticleUtils {
    public static void m_216313_(Level p_216314_, BlockPos p_216315_, ParticleOptions p_216316_, IntProvider p_216317_) {
        for (Direction $$4 : Direction.values()) {
            ParticleUtils.m_216318_(p_216314_, p_216315_, p_216316_, p_216317_, $$4, () -> ParticleUtils.m_216302_(p_216305_.f_46441_), 0.55);
        }
    }

    public static void m_216318_(Level p_216319_, BlockPos p_216320_, ParticleOptions p_216321_, IntProvider p_216322_, Direction p_216323_, Supplier<Vec3> p_216324_, double p_216325_) {
        int $$7 = p_216322_.m_214085_(p_216319_.f_46441_);
        for (int $$8 = 0; $$8 < $$7; ++$$8) {
            ParticleUtils.m_216306_(p_216319_, p_216320_, p_216323_, p_216321_, p_216324_.get(), p_216325_);
        }
    }

    private static Vec3 m_216302_(RandomSource p_216303_) {
        return new Vec3(Mth.m_216263_(p_216303_, -0.5, 0.5), Mth.m_216263_(p_216303_, -0.5, 0.5), Mth.m_216263_(p_216303_, -0.5, 0.5));
    }

    public static void m_144967_(Direction.Axis p_144968_, Level p_144969_, BlockPos p_144970_, double p_144971_, ParticleOptions p_144972_, UniformInt p_144973_) {
        Vec3 $$6 = Vec3.m_82512_(p_144970_);
        boolean $$7 = p_144968_ == Direction.Axis.X;
        boolean $$8 = p_144968_ == Direction.Axis.Y;
        boolean $$9 = p_144968_ == Direction.Axis.Z;
        int $$10 = p_144973_.m_214085_(p_144969_.f_46441_);
        for (int $$11 = 0; $$11 < $$10; ++$$11) {
            double $$12 = $$6.f_82479_ + Mth.m_216263_(p_144969_.f_46441_, -1.0, 1.0) * ($$7 ? 0.5 : p_144971_);
            double $$13 = $$6.f_82480_ + Mth.m_216263_(p_144969_.f_46441_, -1.0, 1.0) * ($$8 ? 0.5 : p_144971_);
            double $$14 = $$6.f_82481_ + Mth.m_216263_(p_144969_.f_46441_, -1.0, 1.0) * ($$9 ? 0.5 : p_144971_);
            double $$15 = $$7 ? Mth.m_216263_(p_144969_.f_46441_, -1.0, 1.0) : 0.0;
            double $$16 = $$8 ? Mth.m_216263_(p_144969_.f_46441_, -1.0, 1.0) : 0.0;
            double $$17 = $$9 ? Mth.m_216263_(p_144969_.f_46441_, -1.0, 1.0) : 0.0;
            p_144969_.m_7106_(p_144972_, $$12, $$13, $$14, $$15, $$16, $$17);
        }
    }

    public static void m_216306_(Level p_216307_, BlockPos p_216308_, Direction p_216309_, ParticleOptions p_216310_, Vec3 p_216311_, double p_216312_) {
        Vec3 $$6 = Vec3.m_82512_(p_216308_);
        int $$7 = p_216309_.m_122429_();
        int $$8 = p_216309_.m_122430_();
        int $$9 = p_216309_.m_122431_();
        double $$10 = $$6.f_82479_ + ($$7 == 0 ? Mth.m_216263_(p_216307_.f_46441_, -0.5, 0.5) : (double)$$7 * p_216312_);
        double $$11 = $$6.f_82480_ + ($$8 == 0 ? Mth.m_216263_(p_216307_.f_46441_, -0.5, 0.5) : (double)$$8 * p_216312_);
        double $$12 = $$6.f_82481_ + ($$9 == 0 ? Mth.m_216263_(p_216307_.f_46441_, -0.5, 0.5) : (double)$$9 * p_216312_);
        double $$13 = $$7 == 0 ? p_216311_.m_7096_() : 0.0;
        double $$14 = $$8 == 0 ? p_216311_.m_7098_() : 0.0;
        double $$15 = $$9 == 0 ? p_216311_.m_7094_() : 0.0;
        p_216307_.m_7106_(p_216310_, $$10, $$11, $$12, $$13, $$14, $$15);
    }
}

