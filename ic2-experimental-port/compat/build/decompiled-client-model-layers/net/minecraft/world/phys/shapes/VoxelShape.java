/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.math.DoubleMath
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 *  javax.annotation.Nullable
 */
package net.minecraft.world.phys.shapes;

import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.AxisCycle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.ArrayVoxelShape;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import net.minecraft.world.phys.shapes.OffsetDoubleList;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.SliceShape;

public abstract class VoxelShape {
    protected final DiscreteVoxelShape f_83211_;
    @Nullable
    private VoxelShape[] f_83212_;

    VoxelShape(DiscreteVoxelShape p_83214_) {
        this.f_83211_ = p_83214_;
    }

    public double m_83288_(Direction.Axis p_83289_) {
        int $$1 = this.f_83211_.m_6538_(p_83289_);
        if ($$1 >= this.f_83211_.m_82850_(p_83289_)) {
            return Double.POSITIVE_INFINITY;
        }
        return this.m_83256_(p_83289_, $$1);
    }

    public double m_83297_(Direction.Axis p_83298_) {
        int $$1 = this.f_83211_.m_6536_(p_83298_);
        if ($$1 <= 0) {
            return Double.NEGATIVE_INFINITY;
        }
        return this.m_83256_(p_83298_, $$1);
    }

    public AABB m_83215_() {
        if (this.m_83281_()) {
            throw Util.m_137570_(new UnsupportedOperationException("No bounds for empty shape."));
        }
        return new AABB(this.m_83288_(Direction.Axis.X), this.m_83288_(Direction.Axis.Y), this.m_83288_(Direction.Axis.Z), this.m_83297_(Direction.Axis.X), this.m_83297_(Direction.Axis.Y), this.m_83297_(Direction.Axis.Z));
    }

    protected double m_83256_(Direction.Axis p_83257_, int p_83258_) {
        return this.m_7700_(p_83257_).getDouble(p_83258_);
    }

    protected abstract DoubleList m_7700_(Direction.Axis var1);

    public boolean m_83281_() {
        return this.f_83211_.m_6224_();
    }

    public VoxelShape m_83216_(double p_83217_, double p_83218_, double p_83219_) {
        if (this.m_83281_()) {
            return Shapes.m_83040_();
        }
        return new ArrayVoxelShape(this.f_83211_, (DoubleList)new OffsetDoubleList(this.m_7700_(Direction.Axis.X), p_83217_), (DoubleList)new OffsetDoubleList(this.m_7700_(Direction.Axis.Y), p_83218_), (DoubleList)new OffsetDoubleList(this.m_7700_(Direction.Axis.Z), p_83219_));
    }

    public VoxelShape m_83296_() {
        VoxelShape[] $$0 = new VoxelShape[]{Shapes.m_83040_()};
        this.m_83286_((p_83275_, p_83276_, p_83277_, p_83278_, p_83279_, p_83280_) -> {
            p_83274_[0] = Shapes.m_83148_($$0[0], Shapes.m_83048_(p_83275_, p_83276_, p_83277_, p_83278_, p_83279_, p_83280_), BooleanOp.f_82695_);
        });
        return $$0[0];
    }

    public void m_83224_(Shapes.DoubleLineConsumer p_83225_) {
        this.f_83211_.m_82819_((p_83228_, p_83229_, p_83230_, p_83231_, p_83232_, p_83233_) -> p_83225_.m_83161_(this.m_83256_(Direction.Axis.X, p_83228_), this.m_83256_(Direction.Axis.Y, p_83229_), this.m_83256_(Direction.Axis.Z, p_83230_), this.m_83256_(Direction.Axis.X, p_83231_), this.m_83256_(Direction.Axis.Y, p_83232_), this.m_83256_(Direction.Axis.Z, p_83233_)), true);
    }

    public void m_83286_(Shapes.DoubleLineConsumer p_83287_) {
        DoubleList $$1 = this.m_7700_(Direction.Axis.X);
        DoubleList $$2 = this.m_7700_(Direction.Axis.Y);
        DoubleList $$3 = this.m_7700_(Direction.Axis.Z);
        this.f_83211_.m_82832_((p_83239_, p_83240_, p_83241_, p_83242_, p_83243_, p_83244_) -> p_83287_.m_83161_($$1.getDouble(p_83239_), $$2.getDouble(p_83240_), $$3.getDouble(p_83241_), $$1.getDouble(p_83242_), $$2.getDouble(p_83243_), $$3.getDouble(p_83244_)), true);
    }

    public List<AABB> m_83299_() {
        ArrayList $$0 = Lists.newArrayList();
        this.m_83286_((p_83267_, p_83268_, p_83269_, p_83270_, p_83271_, p_83272_) -> $$0.add(new AABB(p_83267_, p_83268_, p_83269_, p_83270_, p_83271_, p_83272_)));
        return $$0;
    }

    public double m_166078_(Direction.Axis p_166079_, double p_166080_, double p_166081_) {
        int $$6;
        Direction.Axis $$3 = AxisCycle.FORWARD.m_7314_(p_166079_);
        Direction.Axis $$4 = AxisCycle.BACKWARD.m_7314_(p_166079_);
        int $$5 = this.m_6595_($$3, p_166080_);
        int $$7 = this.f_83211_.m_165994_(p_166079_, $$5, $$6 = this.m_6595_($$4, p_166081_));
        if ($$7 >= this.f_83211_.m_82850_(p_166079_)) {
            return Double.POSITIVE_INFINITY;
        }
        return this.m_83256_(p_166079_, $$7);
    }

    public double m_83290_(Direction.Axis p_83291_, double p_83292_, double p_83293_) {
        int $$6;
        Direction.Axis $$3 = AxisCycle.FORWARD.m_7314_(p_83291_);
        Direction.Axis $$4 = AxisCycle.BACKWARD.m_7314_(p_83291_);
        int $$5 = this.m_6595_($$3, p_83292_);
        int $$7 = this.f_83211_.m_82841_(p_83291_, $$5, $$6 = this.m_6595_($$4, p_83293_));
        if ($$7 <= 0) {
            return Double.NEGATIVE_INFINITY;
        }
        return this.m_83256_(p_83291_, $$7);
    }

    protected int m_6595_(Direction.Axis p_83250_, double p_83251_) {
        return Mth.m_14049_(0, this.f_83211_.m_82850_(p_83250_) + 1, p_166066_ -> p_83251_ < this.m_83256_(p_83250_, p_166066_)) - 1;
    }

    @Nullable
    public BlockHitResult m_83220_(Vec3 p_83221_, Vec3 p_83222_, BlockPos p_83223_) {
        if (this.m_83281_()) {
            return null;
        }
        Vec3 $$3 = p_83222_.m_82546_(p_83221_);
        if ($$3.m_82556_() < 1.0E-7) {
            return null;
        }
        Vec3 $$4 = p_83221_.m_82549_($$3.m_82490_(0.001));
        if (this.f_83211_.m_82846_(this.m_6595_(Direction.Axis.X, $$4.f_82479_ - (double)p_83223_.m_123341_()), this.m_6595_(Direction.Axis.Y, $$4.f_82480_ - (double)p_83223_.m_123342_()), this.m_6595_(Direction.Axis.Z, $$4.f_82481_ - (double)p_83223_.m_123343_()))) {
            return new BlockHitResult($$4, Direction.m_122366_($$3.f_82479_, $$3.f_82480_, $$3.f_82481_).m_122424_(), p_83223_, true);
        }
        return AABB.m_82342_(this.m_83299_(), p_83221_, p_83222_, p_83223_);
    }

    public Optional<Vec3> m_166067_(Vec3 p_166068_) {
        if (this.m_83281_()) {
            return Optional.empty();
        }
        Vec3[] $$1 = new Vec3[1];
        this.m_83286_((p_166072_, p_166073_, p_166074_, p_166075_, p_166076_, p_166077_) -> {
            double $$8 = Mth.m_14008_(p_166068_.m_7096_(), p_166072_, p_166075_);
            double $$9 = Mth.m_14008_(p_166068_.m_7098_(), p_166073_, p_166076_);
            double $$10 = Mth.m_14008_(p_166068_.m_7094_(), p_166074_, p_166077_);
            if ($$1[0] == null || p_166068_.m_82531_($$8, $$9, $$10) < p_166068_.m_82557_($$1[0])) {
                p_166071_[0] = new Vec3($$8, $$9, $$10);
            }
        });
        return Optional.of($$1[0]);
    }

    public VoxelShape m_83263_(Direction p_83264_) {
        VoxelShape $$2;
        if (this.m_83281_() || this == Shapes.m_83144_()) {
            return this;
        }
        if (this.f_83212_ != null) {
            VoxelShape $$1 = this.f_83212_[p_83264_.ordinal()];
            if ($$1 != null) {
                return $$1;
            }
        } else {
            this.f_83212_ = new VoxelShape[6];
        }
        this.f_83212_[p_83264_.ordinal()] = $$2 = this.m_83294_(p_83264_);
        return $$2;
    }

    private VoxelShape m_83294_(Direction p_83295_) {
        Direction.Axis $$1 = p_83295_.m_122434_();
        DoubleList $$2 = this.m_7700_($$1);
        if ($$2.size() == 2 && DoubleMath.fuzzyEquals((double)$$2.getDouble(0), (double)0.0, (double)1.0E-7) && DoubleMath.fuzzyEquals((double)$$2.getDouble(1), (double)1.0, (double)1.0E-7)) {
            return this;
        }
        Direction.AxisDirection $$3 = p_83295_.m_122421_();
        int $$4 = this.m_6595_($$1, $$3 == Direction.AxisDirection.POSITIVE ? 0.9999999 : 1.0E-7);
        return new SliceShape(this, $$1, $$4);
    }

    public double m_83259_(Direction.Axis p_83260_, AABB p_83261_, double p_83262_) {
        return this.m_83245_(AxisCycle.m_121799_(p_83260_, Direction.Axis.X), p_83261_, p_83262_);
    }

    protected double m_83245_(AxisCycle p_83246_, AABB p_83247_, double p_83248_) {
        block11: {
            int $$14;
            int $$12;
            double $$8;
            Direction.Axis $$4;
            AxisCycle $$3;
            block10: {
                if (this.m_83281_()) {
                    return p_83248_;
                }
                if (Math.abs(p_83248_) < 1.0E-7) {
                    return 0.0;
                }
                $$3 = p_83246_.m_7634_();
                $$4 = $$3.m_7314_(Direction.Axis.X);
                Direction.Axis $$5 = $$3.m_7314_(Direction.Axis.Y);
                Direction.Axis $$6 = $$3.m_7314_(Direction.Axis.Z);
                double $$7 = p_83247_.m_82374_($$4);
                $$8 = p_83247_.m_82340_($$4);
                int $$9 = this.m_6595_($$4, $$8 + 1.0E-7);
                int $$10 = this.m_6595_($$4, $$7 - 1.0E-7);
                int $$11 = Math.max(0, this.m_6595_($$5, p_83247_.m_82340_($$5) + 1.0E-7));
                $$12 = Math.min(this.f_83211_.m_82850_($$5), this.m_6595_($$5, p_83247_.m_82374_($$5) - 1.0E-7) + 1);
                int $$13 = Math.max(0, this.m_6595_($$6, p_83247_.m_82340_($$6) + 1.0E-7));
                $$14 = Math.min(this.f_83211_.m_82850_($$6), this.m_6595_($$6, p_83247_.m_82374_($$6) - 1.0E-7) + 1);
                int $$15 = this.f_83211_.m_82850_($$4);
                if (!(p_83248_ > 0.0)) break block10;
                for (int $$16 = $$10 + 1; $$16 < $$15; ++$$16) {
                    for (int $$17 = $$11; $$17 < $$12; ++$$17) {
                        for (int $$18 = $$13; $$18 < $$14; ++$$18) {
                            if (!this.f_83211_.m_82822_($$3, $$16, $$17, $$18)) continue;
                            double $$19 = this.m_83256_($$4, $$16) - $$7;
                            if ($$19 >= -1.0E-7) {
                                p_83248_ = Math.min(p_83248_, $$19);
                            }
                            return p_83248_;
                        }
                    }
                }
                break block11;
            }
            if (!(p_83248_ < 0.0)) break block11;
            for (int $$20 = $$9 - 1; $$20 >= 0; --$$20) {
                for (int $$21 = $$11; $$21 < $$12; ++$$21) {
                    for (int $$22 = $$13; $$22 < $$14; ++$$22) {
                        if (!this.f_83211_.m_82822_($$3, $$20, $$21, $$22)) continue;
                        double $$23 = this.m_83256_($$4, $$20 + 1) - $$8;
                        if ($$23 <= 1.0E-7) {
                            p_83248_ = Math.max(p_83248_, $$23);
                        }
                        return p_83248_;
                    }
                }
            }
        }
        return p_83248_;
    }

    public String toString() {
        return this.m_83281_() ? "EMPTY" : "VoxelShape[" + this.m_83215_() + "]";
    }
}

