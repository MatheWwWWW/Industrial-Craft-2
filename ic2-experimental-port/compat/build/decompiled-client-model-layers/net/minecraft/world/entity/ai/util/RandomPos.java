/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.util;

import com.google.common.annotations.VisibleForTesting;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class RandomPos {
    private static final int f_148535_ = 10;

    public static BlockPos m_217851_(RandomSource p_217852_, int p_217853_, int p_217854_) {
        int $$3 = p_217852_.m_188503_(2 * p_217853_ + 1) - p_217853_;
        int $$4 = p_217852_.m_188503_(2 * p_217854_ + 1) - p_217854_;
        int $$5 = p_217852_.m_188503_(2 * p_217853_ + 1) - p_217853_;
        return new BlockPos($$3, $$4, $$5);
    }

    @Nullable
    public static BlockPos m_217855_(RandomSource p_217856_, int p_217857_, int p_217858_, int p_217859_, double p_217860_, double p_217861_, double p_217862_) {
        double $$7 = Mth.m_14136_(p_217861_, p_217860_) - 1.5707963705062866;
        double $$8 = $$7 + (double)(2.0f * p_217856_.m_188501_() - 1.0f) * p_217862_;
        double $$9 = Math.sqrt(p_217856_.m_188500_()) * (double)Mth.f_13994_ * (double)p_217857_;
        double $$10 = -$$9 * Math.sin($$8);
        double $$11 = $$9 * Math.cos($$8);
        if (Math.abs($$10) > (double)p_217857_ || Math.abs($$11) > (double)p_217857_) {
            return null;
        }
        int $$12 = p_217856_.m_188503_(2 * p_217858_ + 1) - p_217858_ + p_217859_;
        return new BlockPos($$10, (double)$$12, $$11);
    }

    @VisibleForTesting
    public static BlockPos m_148545_(BlockPos p_148546_, int p_148547_, Predicate<BlockPos> p_148548_) {
        if (p_148548_.test(p_148546_)) {
            BlockPos $$3 = p_148546_.m_7494_();
            while ($$3.m_123342_() < p_148547_ && p_148548_.test($$3)) {
                $$3 = $$3.m_7494_();
            }
            return $$3;
        }
        return p_148546_;
    }

    @VisibleForTesting
    public static BlockPos m_26947_(BlockPos p_26948_, int p_26949_, int p_26950_, Predicate<BlockPos> p_26951_) {
        if (p_26949_ < 0) {
            throw new IllegalArgumentException("aboveSolidAmount was " + p_26949_ + ", expected >= 0");
        }
        if (p_26951_.test(p_26948_)) {
            BlockPos $$6;
            BlockPos $$4 = p_26948_.m_7494_();
            while ($$4.m_123342_() < p_26950_ && p_26951_.test($$4)) {
                $$4 = $$4.m_7494_();
            }
            BlockPos $$5 = $$4;
            while ($$5.m_123342_() < p_26950_ && $$5.m_123342_() - $$4.m_123342_() < p_26949_ && !p_26951_.test($$6 = $$5.m_7494_())) {
                $$5 = $$6;
            }
            return $$5;
        }
        return p_26948_;
    }

    @Nullable
    public static Vec3 m_148542_(PathfinderMob p_148543_, Supplier<BlockPos> p_148544_) {
        return RandomPos.m_148561_(p_148544_, p_148543_::m_21692_);
    }

    @Nullable
    public static Vec3 m_148561_(Supplier<BlockPos> p_148562_, ToDoubleFunction<BlockPos> p_148563_) {
        double $$2 = Double.NEGATIVE_INFINITY;
        BlockPos $$3 = null;
        for (int $$4 = 0; $$4 < 10; ++$$4) {
            double $$6;
            BlockPos $$5 = p_148562_.get();
            if ($$5 == null || !(($$6 = p_148563_.applyAsDouble($$5)) > $$2)) continue;
            $$2 = $$6;
            $$3 = $$5;
        }
        return $$3 != null ? Vec3.m_82539_($$3) : null;
    }

    public static BlockPos m_217863_(PathfinderMob p_217864_, int p_217865_, RandomSource p_217866_, BlockPos p_217867_) {
        int $$4 = p_217867_.m_123341_();
        int $$5 = p_217867_.m_123343_();
        if (p_217864_.m_21536_() && p_217865_ > 1) {
            BlockPos $$6 = p_217864_.m_21534_();
            $$4 = p_217864_.m_20185_() > (double)$$6.m_123341_() ? ($$4 -= p_217866_.m_188503_(p_217865_ / 2)) : ($$4 += p_217866_.m_188503_(p_217865_ / 2));
            $$5 = p_217864_.m_20189_() > (double)$$6.m_123343_() ? ($$5 -= p_217866_.m_188503_(p_217865_ / 2)) : ($$5 += p_217866_.m_188503_(p_217865_ / 2));
        }
        return new BlockPos((double)$$4 + p_217864_.m_20185_(), (double)p_217867_.m_123342_() + p_217864_.m_20186_(), (double)$$5 + p_217864_.m_20189_());
    }
}

