/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.phys;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class AABB {
    private static final double f_165879_ = 1.0E-7;
    public final double f_82288_;
    public final double f_82289_;
    public final double f_82290_;
    public final double f_82291_;
    public final double f_82292_;
    public final double f_82293_;

    public AABB(double p_82295_, double p_82296_, double p_82297_, double p_82298_, double p_82299_, double p_82300_) {
        this.f_82288_ = Math.min(p_82295_, p_82298_);
        this.f_82289_ = Math.min(p_82296_, p_82299_);
        this.f_82290_ = Math.min(p_82297_, p_82300_);
        this.f_82291_ = Math.max(p_82295_, p_82298_);
        this.f_82292_ = Math.max(p_82296_, p_82299_);
        this.f_82293_ = Math.max(p_82297_, p_82300_);
    }

    public AABB(BlockPos p_82305_) {
        this(p_82305_.m_123341_(), p_82305_.m_123342_(), p_82305_.m_123343_(), p_82305_.m_123341_() + 1, p_82305_.m_123342_() + 1, p_82305_.m_123343_() + 1);
    }

    public AABB(BlockPos p_82307_, BlockPos p_82308_) {
        this(p_82307_.m_123341_(), p_82307_.m_123342_(), p_82307_.m_123343_(), p_82308_.m_123341_(), p_82308_.m_123342_(), p_82308_.m_123343_());
    }

    public AABB(Vec3 p_82302_, Vec3 p_82303_) {
        this(p_82302_.f_82479_, p_82302_.f_82480_, p_82302_.f_82481_, p_82303_.f_82479_, p_82303_.f_82480_, p_82303_.f_82481_);
    }

    public static AABB m_82321_(BoundingBox p_82322_) {
        return new AABB(p_82322_.m_162395_(), p_82322_.m_162396_(), p_82322_.m_162398_(), p_82322_.m_162399_() + 1, p_82322_.m_162400_() + 1, p_82322_.m_162401_() + 1);
    }

    public static AABB m_82333_(Vec3 p_82334_) {
        return new AABB(p_82334_.f_82479_, p_82334_.f_82480_, p_82334_.f_82481_, p_82334_.f_82479_ + 1.0, p_82334_.f_82480_ + 1.0, p_82334_.f_82481_ + 1.0);
    }

    public AABB m_165880_(double p_165881_) {
        return new AABB(p_165881_, this.f_82289_, this.f_82290_, this.f_82291_, this.f_82292_, this.f_82293_);
    }

    public AABB m_165887_(double p_165888_) {
        return new AABB(this.f_82288_, p_165888_, this.f_82290_, this.f_82291_, this.f_82292_, this.f_82293_);
    }

    public AABB m_165889_(double p_165890_) {
        return new AABB(this.f_82288_, this.f_82289_, p_165890_, this.f_82291_, this.f_82292_, this.f_82293_);
    }

    public AABB m_165891_(double p_165892_) {
        return new AABB(this.f_82288_, this.f_82289_, this.f_82290_, p_165892_, this.f_82292_, this.f_82293_);
    }

    public AABB m_165893_(double p_165894_) {
        return new AABB(this.f_82288_, this.f_82289_, this.f_82290_, this.f_82291_, p_165894_, this.f_82293_);
    }

    public AABB m_165895_(double p_165896_) {
        return new AABB(this.f_82288_, this.f_82289_, this.f_82290_, this.f_82291_, this.f_82292_, p_165896_);
    }

    public double m_82340_(Direction.Axis p_82341_) {
        return p_82341_.m_6150_(this.f_82288_, this.f_82289_, this.f_82290_);
    }

    public double m_82374_(Direction.Axis p_82375_) {
        return p_82375_.m_6150_(this.f_82291_, this.f_82292_, this.f_82293_);
    }

    public boolean equals(Object p_82398_) {
        if (this == p_82398_) {
            return true;
        }
        if (!(p_82398_ instanceof AABB)) {
            return false;
        }
        AABB $$1 = (AABB)p_82398_;
        if (Double.compare($$1.f_82288_, this.f_82288_) != 0) {
            return false;
        }
        if (Double.compare($$1.f_82289_, this.f_82289_) != 0) {
            return false;
        }
        if (Double.compare($$1.f_82290_, this.f_82290_) != 0) {
            return false;
        }
        if (Double.compare($$1.f_82291_, this.f_82291_) != 0) {
            return false;
        }
        if (Double.compare($$1.f_82292_, this.f_82292_) != 0) {
            return false;
        }
        return Double.compare($$1.f_82293_, this.f_82293_) == 0;
    }

    public int hashCode() {
        long $$0 = Double.doubleToLongBits(this.f_82288_);
        int $$1 = (int)($$0 ^ $$0 >>> 32);
        $$0 = Double.doubleToLongBits(this.f_82289_);
        $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32);
        $$0 = Double.doubleToLongBits(this.f_82290_);
        $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32);
        $$0 = Double.doubleToLongBits(this.f_82291_);
        $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32);
        $$0 = Double.doubleToLongBits(this.f_82292_);
        $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32);
        $$0 = Double.doubleToLongBits(this.f_82293_);
        $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32);
        return $$1;
    }

    public AABB m_82310_(double p_82311_, double p_82312_, double p_82313_) {
        double $$3 = this.f_82288_;
        double $$4 = this.f_82289_;
        double $$5 = this.f_82290_;
        double $$6 = this.f_82291_;
        double $$7 = this.f_82292_;
        double $$8 = this.f_82293_;
        if (p_82311_ < 0.0) {
            $$3 -= p_82311_;
        } else if (p_82311_ > 0.0) {
            $$6 -= p_82311_;
        }
        if (p_82312_ < 0.0) {
            $$4 -= p_82312_;
        } else if (p_82312_ > 0.0) {
            $$7 -= p_82312_;
        }
        if (p_82313_ < 0.0) {
            $$5 -= p_82313_;
        } else if (p_82313_ > 0.0) {
            $$8 -= p_82313_;
        }
        return new AABB($$3, $$4, $$5, $$6, $$7, $$8);
    }

    public AABB m_82369_(Vec3 p_82370_) {
        return this.m_82363_(p_82370_.f_82479_, p_82370_.f_82480_, p_82370_.f_82481_);
    }

    public AABB m_82363_(double p_82364_, double p_82365_, double p_82366_) {
        double $$3 = this.f_82288_;
        double $$4 = this.f_82289_;
        double $$5 = this.f_82290_;
        double $$6 = this.f_82291_;
        double $$7 = this.f_82292_;
        double $$8 = this.f_82293_;
        if (p_82364_ < 0.0) {
            $$3 += p_82364_;
        } else if (p_82364_ > 0.0) {
            $$6 += p_82364_;
        }
        if (p_82365_ < 0.0) {
            $$4 += p_82365_;
        } else if (p_82365_ > 0.0) {
            $$7 += p_82365_;
        }
        if (p_82366_ < 0.0) {
            $$5 += p_82366_;
        } else if (p_82366_ > 0.0) {
            $$8 += p_82366_;
        }
        return new AABB($$3, $$4, $$5, $$6, $$7, $$8);
    }

    public AABB m_82377_(double p_82378_, double p_82379_, double p_82380_) {
        double $$3 = this.f_82288_ - p_82378_;
        double $$4 = this.f_82289_ - p_82379_;
        double $$5 = this.f_82290_ - p_82380_;
        double $$6 = this.f_82291_ + p_82378_;
        double $$7 = this.f_82292_ + p_82379_;
        double $$8 = this.f_82293_ + p_82380_;
        return new AABB($$3, $$4, $$5, $$6, $$7, $$8);
    }

    public AABB m_82400_(double p_82401_) {
        return this.m_82377_(p_82401_, p_82401_, p_82401_);
    }

    public AABB m_82323_(AABB p_82324_) {
        double $$1 = Math.max(this.f_82288_, p_82324_.f_82288_);
        double $$2 = Math.max(this.f_82289_, p_82324_.f_82289_);
        double $$3 = Math.max(this.f_82290_, p_82324_.f_82290_);
        double $$4 = Math.min(this.f_82291_, p_82324_.f_82291_);
        double $$5 = Math.min(this.f_82292_, p_82324_.f_82292_);
        double $$6 = Math.min(this.f_82293_, p_82324_.f_82293_);
        return new AABB($$1, $$2, $$3, $$4, $$5, $$6);
    }

    public AABB m_82367_(AABB p_82368_) {
        double $$1 = Math.min(this.f_82288_, p_82368_.f_82288_);
        double $$2 = Math.min(this.f_82289_, p_82368_.f_82289_);
        double $$3 = Math.min(this.f_82290_, p_82368_.f_82290_);
        double $$4 = Math.max(this.f_82291_, p_82368_.f_82291_);
        double $$5 = Math.max(this.f_82292_, p_82368_.f_82292_);
        double $$6 = Math.max(this.f_82293_, p_82368_.f_82293_);
        return new AABB($$1, $$2, $$3, $$4, $$5, $$6);
    }

    public AABB m_82386_(double p_82387_, double p_82388_, double p_82389_) {
        return new AABB(this.f_82288_ + p_82387_, this.f_82289_ + p_82388_, this.f_82290_ + p_82389_, this.f_82291_ + p_82387_, this.f_82292_ + p_82388_, this.f_82293_ + p_82389_);
    }

    public AABB m_82338_(BlockPos p_82339_) {
        return new AABB(this.f_82288_ + (double)p_82339_.m_123341_(), this.f_82289_ + (double)p_82339_.m_123342_(), this.f_82290_ + (double)p_82339_.m_123343_(), this.f_82291_ + (double)p_82339_.m_123341_(), this.f_82292_ + (double)p_82339_.m_123342_(), this.f_82293_ + (double)p_82339_.m_123343_());
    }

    public AABB m_82383_(Vec3 p_82384_) {
        return this.m_82386_(p_82384_.f_82479_, p_82384_.f_82480_, p_82384_.f_82481_);
    }

    public boolean m_82381_(AABB p_82382_) {
        return this.m_82314_(p_82382_.f_82288_, p_82382_.f_82289_, p_82382_.f_82290_, p_82382_.f_82291_, p_82382_.f_82292_, p_82382_.f_82293_);
    }

    public boolean m_82314_(double p_82315_, double p_82316_, double p_82317_, double p_82318_, double p_82319_, double p_82320_) {
        return this.f_82288_ < p_82318_ && this.f_82291_ > p_82315_ && this.f_82289_ < p_82319_ && this.f_82292_ > p_82316_ && this.f_82290_ < p_82320_ && this.f_82293_ > p_82317_;
    }

    public boolean m_82335_(Vec3 p_82336_, Vec3 p_82337_) {
        return this.m_82314_(Math.min(p_82336_.f_82479_, p_82337_.f_82479_), Math.min(p_82336_.f_82480_, p_82337_.f_82480_), Math.min(p_82336_.f_82481_, p_82337_.f_82481_), Math.max(p_82336_.f_82479_, p_82337_.f_82479_), Math.max(p_82336_.f_82480_, p_82337_.f_82480_), Math.max(p_82336_.f_82481_, p_82337_.f_82481_));
    }

    public boolean m_82390_(Vec3 p_82391_) {
        return this.m_82393_(p_82391_.f_82479_, p_82391_.f_82480_, p_82391_.f_82481_);
    }

    public boolean m_82393_(double p_82394_, double p_82395_, double p_82396_) {
        return p_82394_ >= this.f_82288_ && p_82394_ < this.f_82291_ && p_82395_ >= this.f_82289_ && p_82395_ < this.f_82292_ && p_82396_ >= this.f_82290_ && p_82396_ < this.f_82293_;
    }

    public double m_82309_() {
        double $$0 = this.m_82362_();
        double $$1 = this.m_82376_();
        double $$2 = this.m_82385_();
        return ($$0 + $$1 + $$2) / 3.0;
    }

    public double m_82362_() {
        return this.f_82291_ - this.f_82288_;
    }

    public double m_82376_() {
        return this.f_82292_ - this.f_82289_;
    }

    public double m_82385_() {
        return this.f_82293_ - this.f_82290_;
    }

    public AABB m_165897_(double p_165898_, double p_165899_, double p_165900_) {
        return this.m_82377_(-p_165898_, -p_165899_, -p_165900_);
    }

    public AABB m_82406_(double p_82407_) {
        return this.m_82400_(-p_82407_);
    }

    public Optional<Vec3> m_82371_(Vec3 p_82372_, Vec3 p_82373_) {
        double[] $$2 = new double[]{1.0};
        double $$3 = p_82373_.f_82479_ - p_82372_.f_82479_;
        double $$4 = p_82373_.f_82480_ - p_82372_.f_82480_;
        double $$5 = p_82373_.f_82481_ - p_82372_.f_82481_;
        Direction $$6 = AABB.m_82325_(this, p_82372_, $$2, null, $$3, $$4, $$5);
        if ($$6 == null) {
            return Optional.empty();
        }
        double $$7 = $$2[0];
        return Optional.of(p_82372_.m_82520_($$7 * $$3, $$7 * $$4, $$7 * $$5));
    }

    @Nullable
    public static BlockHitResult m_82342_(Iterable<AABB> p_82343_, Vec3 p_82344_, Vec3 p_82345_, BlockPos p_82346_) {
        double[] $$4 = new double[]{1.0};
        Direction $$5 = null;
        double $$6 = p_82345_.f_82479_ - p_82344_.f_82479_;
        double $$7 = p_82345_.f_82480_ - p_82344_.f_82480_;
        double $$8 = p_82345_.f_82481_ - p_82344_.f_82481_;
        for (AABB $$9 : p_82343_) {
            $$5 = AABB.m_82325_($$9.m_82338_(p_82346_), p_82344_, $$4, $$5, $$6, $$7, $$8);
        }
        if ($$5 == null) {
            return null;
        }
        double $$10 = $$4[0];
        return new BlockHitResult(p_82344_.m_82520_($$10 * $$6, $$10 * $$7, $$10 * $$8), $$5, p_82346_, false);
    }

    @Nullable
    private static Direction m_82325_(AABB p_82326_, Vec3 p_82327_, double[] p_82328_, @Nullable Direction p_82329_, double p_82330_, double p_82331_, double p_82332_) {
        if (p_82330_ > 1.0E-7) {
            p_82329_ = AABB.m_82347_(p_82328_, p_82329_, p_82330_, p_82331_, p_82332_, p_82326_.f_82288_, p_82326_.f_82289_, p_82326_.f_82292_, p_82326_.f_82290_, p_82326_.f_82293_, Direction.WEST, p_82327_.f_82479_, p_82327_.f_82480_, p_82327_.f_82481_);
        } else if (p_82330_ < -1.0E-7) {
            p_82329_ = AABB.m_82347_(p_82328_, p_82329_, p_82330_, p_82331_, p_82332_, p_82326_.f_82291_, p_82326_.f_82289_, p_82326_.f_82292_, p_82326_.f_82290_, p_82326_.f_82293_, Direction.EAST, p_82327_.f_82479_, p_82327_.f_82480_, p_82327_.f_82481_);
        }
        if (p_82331_ > 1.0E-7) {
            p_82329_ = AABB.m_82347_(p_82328_, p_82329_, p_82331_, p_82332_, p_82330_, p_82326_.f_82289_, p_82326_.f_82290_, p_82326_.f_82293_, p_82326_.f_82288_, p_82326_.f_82291_, Direction.DOWN, p_82327_.f_82480_, p_82327_.f_82481_, p_82327_.f_82479_);
        } else if (p_82331_ < -1.0E-7) {
            p_82329_ = AABB.m_82347_(p_82328_, p_82329_, p_82331_, p_82332_, p_82330_, p_82326_.f_82292_, p_82326_.f_82290_, p_82326_.f_82293_, p_82326_.f_82288_, p_82326_.f_82291_, Direction.UP, p_82327_.f_82480_, p_82327_.f_82481_, p_82327_.f_82479_);
        }
        if (p_82332_ > 1.0E-7) {
            p_82329_ = AABB.m_82347_(p_82328_, p_82329_, p_82332_, p_82330_, p_82331_, p_82326_.f_82290_, p_82326_.f_82288_, p_82326_.f_82291_, p_82326_.f_82289_, p_82326_.f_82292_, Direction.NORTH, p_82327_.f_82481_, p_82327_.f_82479_, p_82327_.f_82480_);
        } else if (p_82332_ < -1.0E-7) {
            p_82329_ = AABB.m_82347_(p_82328_, p_82329_, p_82332_, p_82330_, p_82331_, p_82326_.f_82293_, p_82326_.f_82288_, p_82326_.f_82291_, p_82326_.f_82289_, p_82326_.f_82292_, Direction.SOUTH, p_82327_.f_82481_, p_82327_.f_82479_, p_82327_.f_82480_);
        }
        return p_82329_;
    }

    @Nullable
    private static Direction m_82347_(double[] p_82348_, @Nullable Direction p_82349_, double p_82350_, double p_82351_, double p_82352_, double p_82353_, double p_82354_, double p_82355_, double p_82356_, double p_82357_, Direction p_82358_, double p_82359_, double p_82360_, double p_82361_) {
        double $$14 = (p_82353_ - p_82359_) / p_82350_;
        double $$15 = p_82360_ + $$14 * p_82351_;
        double $$16 = p_82361_ + $$14 * p_82352_;
        if (0.0 < $$14 && $$14 < p_82348_[0] && p_82354_ - 1.0E-7 < $$15 && $$15 < p_82355_ + 1.0E-7 && p_82356_ - 1.0E-7 < $$16 && $$16 < p_82357_ + 1.0E-7) {
            p_82348_[0] = $$14;
            return p_82358_;
        }
        return p_82349_;
    }

    public String toString() {
        return "AABB[" + this.f_82288_ + ", " + this.f_82289_ + ", " + this.f_82290_ + "] -> [" + this.f_82291_ + ", " + this.f_82292_ + ", " + this.f_82293_ + "]";
    }

    public boolean m_82392_() {
        return Double.isNaN(this.f_82288_) || Double.isNaN(this.f_82289_) || Double.isNaN(this.f_82290_) || Double.isNaN(this.f_82291_) || Double.isNaN(this.f_82292_) || Double.isNaN(this.f_82293_);
    }

    public Vec3 m_82399_() {
        return new Vec3(Mth.m_14139_(0.5, this.f_82288_, this.f_82291_), Mth.m_14139_(0.5, this.f_82289_, this.f_82292_), Mth.m_14139_(0.5, this.f_82290_, this.f_82293_));
    }

    public static AABB m_165882_(Vec3 p_165883_, double p_165884_, double p_165885_, double p_165886_) {
        return new AABB(p_165883_.f_82479_ - p_165884_ / 2.0, p_165883_.f_82480_ - p_165885_ / 2.0, p_165883_.f_82481_ - p_165886_ / 2.0, p_165883_.f_82479_ + p_165884_ / 2.0, p_165883_.f_82480_ + p_165885_ / 2.0, p_165883_.f_82481_ + p_165886_ / 2.0);
    }
}

