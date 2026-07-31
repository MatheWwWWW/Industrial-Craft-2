/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  javax.annotation.concurrent.Immutable
 */
package net.minecraft.core;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.function.Function;
import java.util.stream.IntStream;
import javax.annotation.concurrent.Immutable;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.util.Mth;

@Immutable
public class Vec3i
implements Comparable<Vec3i> {
    public static final Codec<Vec3i> f_123287_ = Codec.INT_STREAM.comapFlatMap(p_123318_ -> Util.m_137539_(p_123318_, 3).map(p_175586_ -> new Vec3i(p_175586_[0], p_175586_[1], p_175586_[2])), p_123313_ -> IntStream.of(p_123313_.m_123341_(), p_123313_.m_123342_(), p_123313_.m_123343_()));
    public static final Vec3i f_123288_ = new Vec3i(0, 0, 0);
    private int f_123285_;
    private int f_123286_;
    private int f_123289_;

    private static Function<Vec3i, DataResult<Vec3i>> m_194645_(int p_194646_) {
        return p_194649_ -> {
            if (Math.abs(p_194649_.m_123341_()) < p_194646_ && Math.abs(p_194649_.m_123342_()) < p_194646_ && Math.abs(p_194649_.m_123343_()) < p_194646_) {
                return DataResult.success((Object)p_194649_);
            }
            return DataResult.error((String)("Position out of range, expected at most " + p_194646_ + ": " + p_194649_));
        };
    }

    public static Codec<Vec3i> m_194650_(int p_194651_) {
        return f_123287_.flatXmap(Vec3i.m_194645_(p_194651_), Vec3i.m_194645_(p_194651_));
    }

    public Vec3i(int p_123296_, int p_123297_, int p_123298_) {
        this.f_123285_ = p_123296_;
        this.f_123286_ = p_123297_;
        this.f_123289_ = p_123298_;
    }

    public Vec3i(double p_123292_, double p_123293_, double p_123294_) {
        this(Mth.m_14107_(p_123292_), Mth.m_14107_(p_123293_), Mth.m_14107_(p_123294_));
    }

    public boolean equals(Object p_123327_) {
        if (this == p_123327_) {
            return true;
        }
        if (!(p_123327_ instanceof Vec3i)) {
            return false;
        }
        Vec3i $$1 = (Vec3i)p_123327_;
        if (this.m_123341_() != $$1.m_123341_()) {
            return false;
        }
        if (this.m_123342_() != $$1.m_123342_()) {
            return false;
        }
        return this.m_123343_() == $$1.m_123343_();
    }

    public int hashCode() {
        return (this.m_123342_() + this.m_123343_() * 31) * 31 + this.m_123341_();
    }

    @Override
    public int compareTo(Vec3i p_123330_) {
        if (this.m_123342_() == p_123330_.m_123342_()) {
            if (this.m_123343_() == p_123330_.m_123343_()) {
                return this.m_123341_() - p_123330_.m_123341_();
            }
            return this.m_123343_() - p_123330_.m_123343_();
        }
        return this.m_123342_() - p_123330_.m_123342_();
    }

    public int m_123341_() {
        return this.f_123285_;
    }

    public int m_123342_() {
        return this.f_123286_;
    }

    public int m_123343_() {
        return this.f_123289_;
    }

    protected Vec3i m_142451_(int p_175605_) {
        this.f_123285_ = p_175605_;
        return this;
    }

    protected Vec3i m_142448_(int p_175604_) {
        this.f_123286_ = p_175604_;
        return this;
    }

    protected Vec3i m_142443_(int p_175603_) {
        this.f_123289_ = p_175603_;
        return this;
    }

    public Vec3i m_7637_(double p_175587_, double p_175588_, double p_175589_) {
        if (p_175587_ == 0.0 && p_175588_ == 0.0 && p_175589_ == 0.0) {
            return this;
        }
        return new Vec3i((double)this.m_123341_() + p_175587_, (double)this.m_123342_() + p_175588_, (double)this.m_123343_() + p_175589_);
    }

    public Vec3i m_7918_(int p_175593_, int p_175594_, int p_175595_) {
        if (p_175593_ == 0 && p_175594_ == 0 && p_175595_ == 0) {
            return this;
        }
        return new Vec3i(this.m_123341_() + p_175593_, this.m_123342_() + p_175594_, this.m_123343_() + p_175595_);
    }

    public Vec3i m_121955_(Vec3i p_175597_) {
        return this.m_7918_(p_175597_.m_123341_(), p_175597_.m_123342_(), p_175597_.m_123343_());
    }

    public Vec3i m_121996_(Vec3i p_175596_) {
        return this.m_7918_(-p_175596_.m_123341_(), -p_175596_.m_123342_(), -p_175596_.m_123343_());
    }

    public Vec3i m_142393_(int p_175602_) {
        if (p_175602_ == 1) {
            return this;
        }
        if (p_175602_ == 0) {
            return f_123288_;
        }
        return new Vec3i(this.m_123341_() * p_175602_, this.m_123342_() * p_175602_, this.m_123343_() * p_175602_);
    }

    public Vec3i m_7494_() {
        return this.m_6630_(1);
    }

    public Vec3i m_6630_(int p_123336_) {
        return this.m_5484_(Direction.UP, p_123336_);
    }

    public Vec3i m_7495_() {
        return this.m_6625_(1);
    }

    public Vec3i m_6625_(int p_123335_) {
        return this.m_5484_(Direction.DOWN, p_123335_);
    }

    public Vec3i m_122012_() {
        return this.m_122013_(1);
    }

    public Vec3i m_122013_(int p_175601_) {
        return this.m_5484_(Direction.NORTH, p_175601_);
    }

    public Vec3i m_122019_() {
        return this.m_122020_(1);
    }

    public Vec3i m_122020_(int p_175600_) {
        return this.m_5484_(Direction.SOUTH, p_175600_);
    }

    public Vec3i m_122024_() {
        return this.m_122025_(1);
    }

    public Vec3i m_122025_(int p_175599_) {
        return this.m_5484_(Direction.WEST, p_175599_);
    }

    public Vec3i m_122029_() {
        return this.m_122030_(1);
    }

    public Vec3i m_122030_(int p_175598_) {
        return this.m_5484_(Direction.EAST, p_175598_);
    }

    public Vec3i m_121945_(Direction p_175592_) {
        return this.m_5484_(p_175592_, 1);
    }

    public Vec3i m_5484_(Direction p_123321_, int p_123322_) {
        if (p_123322_ == 0) {
            return this;
        }
        return new Vec3i(this.m_123341_() + p_123321_.m_122429_() * p_123322_, this.m_123342_() + p_123321_.m_122430_() * p_123322_, this.m_123343_() + p_123321_.m_122431_() * p_123322_);
    }

    public Vec3i m_5487_(Direction.Axis p_175590_, int p_175591_) {
        if (p_175591_ == 0) {
            return this;
        }
        int $$2 = p_175590_ == Direction.Axis.X ? p_175591_ : 0;
        int $$3 = p_175590_ == Direction.Axis.Y ? p_175591_ : 0;
        int $$4 = p_175590_ == Direction.Axis.Z ? p_175591_ : 0;
        return new Vec3i(this.m_123341_() + $$2, this.m_123342_() + $$3, this.m_123343_() + $$4);
    }

    public Vec3i m_7724_(Vec3i p_123325_) {
        return new Vec3i(this.m_123342_() * p_123325_.m_123343_() - this.m_123343_() * p_123325_.m_123342_(), this.m_123343_() * p_123325_.m_123341_() - this.m_123341_() * p_123325_.m_123343_(), this.m_123341_() * p_123325_.m_123342_() - this.m_123342_() * p_123325_.m_123341_());
    }

    public boolean m_123314_(Vec3i p_123315_, double p_123316_) {
        return this.m_123331_(p_123315_) < Mth.m_144952_(p_123316_);
    }

    public boolean m_203195_(Position p_203196_, double p_203197_) {
        return this.m_203193_(p_203196_) < Mth.m_144952_(p_203197_);
    }

    public double m_123331_(Vec3i p_123332_) {
        return this.m_203202_(p_123332_.m_123341_(), p_123332_.m_123342_(), p_123332_.m_123343_());
    }

    public double m_203193_(Position p_203194_) {
        return this.m_203198_(p_203194_.m_7096_(), p_203194_.m_7098_(), p_203194_.m_7094_());
    }

    public double m_203198_(double p_203199_, double p_203200_, double p_203201_) {
        double $$3 = (double)this.m_123341_() + 0.5 - p_203199_;
        double $$4 = (double)this.m_123342_() + 0.5 - p_203200_;
        double $$5 = (double)this.m_123343_() + 0.5 - p_203201_;
        return $$3 * $$3 + $$4 * $$4 + $$5 * $$5;
    }

    public double m_203202_(double p_203203_, double p_203204_, double p_203205_) {
        double $$3 = (double)this.m_123341_() - p_203203_;
        double $$4 = (double)this.m_123342_() - p_203204_;
        double $$5 = (double)this.m_123343_() - p_203205_;
        return $$3 * $$3 + $$4 * $$4 + $$5 * $$5;
    }

    public int m_123333_(Vec3i p_123334_) {
        float $$1 = Math.abs(p_123334_.m_123341_() - this.m_123341_());
        float $$2 = Math.abs(p_123334_.m_123342_() - this.m_123342_());
        float $$3 = Math.abs(p_123334_.m_123343_() - this.m_123343_());
        return (int)($$1 + $$2 + $$3);
    }

    public int m_123304_(Direction.Axis p_123305_) {
        return p_123305_.m_7863_(this.f_123285_, this.f_123286_, this.f_123289_);
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object)this).add("x", this.m_123341_()).add("y", this.m_123342_()).add("z", this.m_123343_()).toString();
    }

    public String m_123344_() {
        return this.m_123341_() + ", " + this.m_123342_() + ", " + this.m_123343_();
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.compareTo((Vec3i)object);
    }
}

