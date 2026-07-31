/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.math.DoubleMath
 *  com.google.common.math.IntMath
 *  it.unimi.dsi.fastutil.doubles.DoubleArrayList
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 */
package net.minecraft.world.phys.shapes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import java.util.Objects;
import net.minecraft.Util;
import net.minecraft.core.AxisCycle;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.ArrayVoxelShape;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CubePointRange;
import net.minecraft.world.phys.shapes.CubeVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteCubeMerger;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import net.minecraft.world.phys.shapes.IdenticalMerger;
import net.minecraft.world.phys.shapes.IndexMerger;
import net.minecraft.world.phys.shapes.IndirectMerger;
import net.minecraft.world.phys.shapes.NonOverlappingMerger;
import net.minecraft.world.phys.shapes.SliceShape;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class Shapes {
    public static final double f_166025_ = 1.0E-7;
    public static final double f_166026_ = 1.0E-6;
    private static final VoxelShape f_83037_ = Util.m_137537_(() -> {
        BitSetDiscreteVoxelShape $$0 = new BitSetDiscreteVoxelShape(1, 1, 1);
        ((DiscreteVoxelShape)$$0).m_142703_(0, 0, 0);
        return new CubeVoxelShape($$0);
    });
    public static final VoxelShape f_83036_ = Shapes.m_83048_(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    private static final VoxelShape f_83038_ = new ArrayVoxelShape((DiscreteVoxelShape)new BitSetDiscreteVoxelShape(0, 0, 0), (DoubleList)new DoubleArrayList(new double[]{0.0}), (DoubleList)new DoubleArrayList(new double[]{0.0}), (DoubleList)new DoubleArrayList(new double[]{0.0}));

    public static VoxelShape m_83040_() {
        return f_83038_;
    }

    public static VoxelShape m_83144_() {
        return f_83037_;
    }

    public static VoxelShape m_83048_(double p_83049_, double p_83050_, double p_83051_, double p_83052_, double p_83053_, double p_83054_) {
        if (p_83049_ > p_83052_ || p_83050_ > p_83053_ || p_83051_ > p_83054_) {
            throw new IllegalArgumentException("The min values need to be smaller or equals to the max values");
        }
        return Shapes.m_166049_(p_83049_, p_83050_, p_83051_, p_83052_, p_83053_, p_83054_);
    }

    public static VoxelShape m_166049_(double p_166050_, double p_166051_, double p_166052_, double p_166053_, double p_166054_, double p_166055_) {
        if (p_166053_ - p_166050_ < 1.0E-7 || p_166054_ - p_166051_ < 1.0E-7 || p_166055_ - p_166052_ < 1.0E-7) {
            return Shapes.m_83040_();
        }
        int $$6 = Shapes.m_83041_(p_166050_, p_166053_);
        int $$7 = Shapes.m_83041_(p_166051_, p_166054_);
        int $$8 = Shapes.m_83041_(p_166052_, p_166055_);
        if ($$6 < 0 || $$7 < 0 || $$8 < 0) {
            return new ArrayVoxelShape(Shapes.f_83037_.f_83211_, (DoubleList)DoubleArrayList.wrap((double[])new double[]{p_166050_, p_166053_}), (DoubleList)DoubleArrayList.wrap((double[])new double[]{p_166051_, p_166054_}), (DoubleList)DoubleArrayList.wrap((double[])new double[]{p_166052_, p_166055_}));
        }
        if ($$6 == 0 && $$7 == 0 && $$8 == 0) {
            return Shapes.m_83144_();
        }
        int $$9 = 1 << $$6;
        int $$10 = 1 << $$7;
        int $$11 = 1 << $$8;
        BitSetDiscreteVoxelShape $$12 = BitSetDiscreteVoxelShape.m_165932_($$9, $$10, $$11, (int)Math.round(p_166050_ * (double)$$9), (int)Math.round(p_166051_ * (double)$$10), (int)Math.round(p_166052_ * (double)$$11), (int)Math.round(p_166053_ * (double)$$9), (int)Math.round(p_166054_ * (double)$$10), (int)Math.round(p_166055_ * (double)$$11));
        return new CubeVoxelShape($$12);
    }

    public static VoxelShape m_83064_(AABB p_83065_) {
        return Shapes.m_166049_(p_83065_.f_82288_, p_83065_.f_82289_, p_83065_.f_82290_, p_83065_.f_82291_, p_83065_.f_82292_, p_83065_.f_82293_);
    }

    @VisibleForTesting
    protected static int m_83041_(double p_83042_, double p_83043_) {
        if (p_83042_ < -1.0E-7 || p_83043_ > 1.0000001) {
            return -1;
        }
        for (int $$2 = 0; $$2 <= 3; ++$$2) {
            boolean $$7;
            int $$3 = 1 << $$2;
            double $$4 = p_83042_ * (double)$$3;
            double $$5 = p_83043_ * (double)$$3;
            boolean $$6 = Math.abs($$4 - (double)Math.round($$4)) < 1.0E-7 * (double)$$3;
            boolean bl = $$7 = Math.abs($$5 - (double)Math.round($$5)) < 1.0E-7 * (double)$$3;
            if (!$$6 || !$$7) continue;
            return $$2;
        }
        return -1;
    }

    protected static long m_83055_(int p_83056_, int p_83057_) {
        return (long)p_83056_ * (long)(p_83057_ / IntMath.gcd((int)p_83056_, (int)p_83057_));
    }

    public static VoxelShape m_83110_(VoxelShape p_83111_, VoxelShape p_83112_) {
        return Shapes.m_83113_(p_83111_, p_83112_, BooleanOp.f_82695_);
    }

    public static VoxelShape m_83124_(VoxelShape p_83125_, VoxelShape ... p_83126_) {
        return Arrays.stream(p_83126_).reduce(p_83125_, Shapes::m_83110_);
    }

    public static VoxelShape m_83113_(VoxelShape p_83114_, VoxelShape p_83115_, BooleanOp p_83116_) {
        return Shapes.m_83148_(p_83114_, p_83115_, p_83116_).m_83296_();
    }

    public static VoxelShape m_83148_(VoxelShape p_83149_, VoxelShape p_83150_, BooleanOp p_83151_) {
        if (p_83151_.m_82701_(false, false)) {
            throw Util.m_137570_(new IllegalArgumentException());
        }
        if (p_83149_ == p_83150_) {
            return p_83151_.m_82701_(true, true) ? p_83149_ : Shapes.m_83040_();
        }
        boolean $$3 = p_83151_.m_82701_(true, false);
        boolean $$4 = p_83151_.m_82701_(false, true);
        if (p_83149_.m_83281_()) {
            return $$4 ? p_83150_ : Shapes.m_83040_();
        }
        if (p_83150_.m_83281_()) {
            return $$3 ? p_83149_ : Shapes.m_83040_();
        }
        IndexMerger $$5 = Shapes.m_83058_(1, p_83149_.m_7700_(Direction.Axis.X), p_83150_.m_7700_(Direction.Axis.X), $$3, $$4);
        IndexMerger $$6 = Shapes.m_83058_($$5.size() - 1, p_83149_.m_7700_(Direction.Axis.Y), p_83150_.m_7700_(Direction.Axis.Y), $$3, $$4);
        IndexMerger $$7 = Shapes.m_83058_(($$5.size() - 1) * ($$6.size() - 1), p_83149_.m_7700_(Direction.Axis.Z), p_83150_.m_7700_(Direction.Axis.Z), $$3, $$4);
        BitSetDiscreteVoxelShape $$8 = BitSetDiscreteVoxelShape.m_82641_(p_83149_.f_83211_, p_83150_.f_83211_, $$5, $$6, $$7, p_83151_);
        if ($$5 instanceof DiscreteCubeMerger && $$6 instanceof DiscreteCubeMerger && $$7 instanceof DiscreteCubeMerger) {
            return new CubeVoxelShape($$8);
        }
        return new ArrayVoxelShape((DiscreteVoxelShape)$$8, $$5.m_6241_(), $$6.m_6241_(), $$7.m_6241_());
    }

    public static boolean m_83157_(VoxelShape p_83158_, VoxelShape p_83159_, BooleanOp p_83160_) {
        if (p_83160_.m_82701_(false, false)) {
            throw Util.m_137570_(new IllegalArgumentException());
        }
        boolean $$3 = p_83158_.m_83281_();
        boolean $$4 = p_83159_.m_83281_();
        if ($$3 || $$4) {
            return p_83160_.m_82701_(!$$3, !$$4);
        }
        if (p_83158_ == p_83159_) {
            return p_83160_.m_82701_(true, true);
        }
        boolean $$5 = p_83160_.m_82701_(true, false);
        boolean $$6 = p_83160_.m_82701_(false, true);
        for (Direction.Axis $$7 : AxisCycle.f_121783_) {
            if (p_83158_.m_83297_($$7) < p_83159_.m_83288_($$7) - 1.0E-7) {
                return $$5 || $$6;
            }
            if (!(p_83159_.m_83297_($$7) < p_83158_.m_83288_($$7) - 1.0E-7)) continue;
            return $$5 || $$6;
        }
        IndexMerger $$8 = Shapes.m_83058_(1, p_83158_.m_7700_(Direction.Axis.X), p_83159_.m_7700_(Direction.Axis.X), $$5, $$6);
        IndexMerger $$9 = Shapes.m_83058_($$8.size() - 1, p_83158_.m_7700_(Direction.Axis.Y), p_83159_.m_7700_(Direction.Axis.Y), $$5, $$6);
        IndexMerger $$10 = Shapes.m_83058_(($$8.size() - 1) * ($$9.size() - 1), p_83158_.m_7700_(Direction.Axis.Z), p_83159_.m_7700_(Direction.Axis.Z), $$5, $$6);
        return Shapes.m_83103_($$8, $$9, $$10, p_83158_.f_83211_, p_83159_.f_83211_, p_83160_);
    }

    private static boolean m_83103_(IndexMerger p_83104_, IndexMerger p_83105_, IndexMerger p_83106_, DiscreteVoxelShape p_83107_, DiscreteVoxelShape p_83108_, BooleanOp p_83109_) {
        return !p_83104_.m_6200_((p_83100_, p_83101_, p_83102_) -> p_83105_.m_6200_((p_166046_, p_166047_, p_166048_) -> p_83106_.m_6200_((p_166036_, p_166037_, p_166038_) -> !p_83109_.m_82701_(p_83107_.m_82846_(p_83100_, p_166046_, p_166036_), p_83108_.m_82846_(p_83101_, p_166047_, p_166037_)))));
    }

    public static double m_193135_(Direction.Axis p_193136_, AABB p_193137_, Iterable<VoxelShape> p_193138_, double p_193139_) {
        for (VoxelShape $$4 : p_193138_) {
            if (Math.abs(p_193139_) < 1.0E-7) {
                return 0.0;
            }
            p_193139_ = $$4.m_83259_(p_193136_, p_193137_, p_193139_);
        }
        return p_193139_;
    }

    public static boolean m_83117_(VoxelShape p_83118_, VoxelShape p_83119_, Direction p_83120_) {
        if (p_83118_ == Shapes.m_83144_() && p_83119_ == Shapes.m_83144_()) {
            return true;
        }
        if (p_83119_.m_83281_()) {
            return false;
        }
        Direction.Axis $$3 = p_83120_.m_122434_();
        Direction.AxisDirection $$4 = p_83120_.m_122421_();
        VoxelShape $$5 = $$4 == Direction.AxisDirection.POSITIVE ? p_83118_ : p_83119_;
        VoxelShape $$6 = $$4 == Direction.AxisDirection.POSITIVE ? p_83119_ : p_83118_;
        BooleanOp $$7 = $$4 == Direction.AxisDirection.POSITIVE ? BooleanOp.f_82685_ : BooleanOp.f_82683_;
        return DoubleMath.fuzzyEquals((double)$$5.m_83297_($$3), (double)1.0, (double)1.0E-7) && DoubleMath.fuzzyEquals((double)$$6.m_83288_($$3), (double)0.0, (double)1.0E-7) && !Shapes.m_83157_(new SliceShape($$5, $$3, $$5.f_83211_.m_82850_($$3) - 1), new SliceShape($$6, $$3, 0), $$7);
    }

    public static VoxelShape m_83121_(VoxelShape p_83122_, Direction p_83123_) {
        int $$6;
        boolean $$5;
        if (p_83122_ == Shapes.m_83144_()) {
            return Shapes.m_83144_();
        }
        Direction.Axis $$2 = p_83123_.m_122434_();
        if (p_83123_.m_122421_() == Direction.AxisDirection.POSITIVE) {
            boolean $$3 = DoubleMath.fuzzyEquals((double)p_83122_.m_83297_($$2), (double)1.0, (double)1.0E-7);
            int $$4 = p_83122_.f_83211_.m_82850_($$2) - 1;
        } else {
            $$5 = DoubleMath.fuzzyEquals((double)p_83122_.m_83288_($$2), (double)0.0, (double)1.0E-7);
            $$6 = 0;
        }
        if (!$$5) {
            return Shapes.m_83040_();
        }
        return new SliceShape(p_83122_, $$2, $$6);
    }

    public static boolean m_83152_(VoxelShape p_83153_, VoxelShape p_83154_, Direction p_83155_) {
        VoxelShape $$6;
        if (p_83153_ == Shapes.m_83144_() || p_83154_ == Shapes.m_83144_()) {
            return true;
        }
        Direction.Axis $$3 = p_83155_.m_122434_();
        Direction.AxisDirection $$4 = p_83155_.m_122421_();
        VoxelShape $$5 = $$4 == Direction.AxisDirection.POSITIVE ? p_83153_ : p_83154_;
        VoxelShape voxelShape = $$6 = $$4 == Direction.AxisDirection.POSITIVE ? p_83154_ : p_83153_;
        if (!DoubleMath.fuzzyEquals((double)$$5.m_83297_($$3), (double)1.0, (double)1.0E-7)) {
            $$5 = Shapes.m_83040_();
        }
        if (!DoubleMath.fuzzyEquals((double)$$6.m_83288_($$3), (double)0.0, (double)1.0E-7)) {
            $$6 = Shapes.m_83040_();
        }
        return !Shapes.m_83157_(Shapes.m_83144_(), Shapes.m_83148_(new SliceShape($$5, $$3, $$5.f_83211_.m_82850_($$3) - 1), new SliceShape($$6, $$3, 0), BooleanOp.f_82695_), BooleanOp.f_82685_);
    }

    public static boolean m_83145_(VoxelShape p_83146_, VoxelShape p_83147_) {
        if (p_83146_ == Shapes.m_83144_() || p_83147_ == Shapes.m_83144_()) {
            return true;
        }
        if (p_83146_.m_83281_() && p_83147_.m_83281_()) {
            return false;
        }
        return !Shapes.m_83157_(Shapes.m_83144_(), Shapes.m_83148_(p_83146_, p_83147_, BooleanOp.f_82695_), BooleanOp.f_82685_);
    }

    @VisibleForTesting
    protected static IndexMerger m_83058_(int p_83059_, DoubleList p_83060_, DoubleList p_83061_, boolean p_83062_, boolean p_83063_) {
        long $$7;
        int $$5 = p_83060_.size() - 1;
        int $$6 = p_83061_.size() - 1;
        if (p_83060_ instanceof CubePointRange && p_83061_ instanceof CubePointRange && (long)p_83059_ * ($$7 = Shapes.m_83055_($$5, $$6)) <= 256L) {
            return new DiscreteCubeMerger($$5, $$6);
        }
        if (p_83060_.getDouble($$5) < p_83061_.getDouble(0) - 1.0E-7) {
            return new NonOverlappingMerger(p_83060_, p_83061_, false);
        }
        if (p_83061_.getDouble($$6) < p_83060_.getDouble(0) - 1.0E-7) {
            return new NonOverlappingMerger(p_83061_, p_83060_, true);
        }
        if ($$5 == $$6 && Objects.equals(p_83060_, p_83061_)) {
            return new IdenticalMerger(p_83060_);
        }
        return new IndirectMerger(p_83060_, p_83061_, p_83062_, p_83063_);
    }

    public static interface DoubleLineConsumer {
        public void m_83161_(double var1, double var3, double var5, double var7, double var9, double var11);
    }
}

