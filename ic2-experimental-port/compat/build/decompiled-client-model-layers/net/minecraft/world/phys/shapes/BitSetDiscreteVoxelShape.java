/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.phys.shapes;

import java.util.BitSet;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import net.minecraft.world.phys.shapes.IndexMerger;

public final class BitSetDiscreteVoxelShape
extends DiscreteVoxelShape {
    private final BitSet f_82580_;
    private int f_82581_;
    private int f_82582_;
    private int f_82583_;
    private int f_82584_;
    private int f_82585_;
    private int f_82586_;

    public BitSetDiscreteVoxelShape(int p_82588_, int p_82589_, int p_82590_) {
        super(p_82588_, p_82589_, p_82590_);
        this.f_82580_ = new BitSet(p_82588_ * p_82589_ * p_82590_);
        this.f_82581_ = p_82588_;
        this.f_82582_ = p_82589_;
        this.f_82583_ = p_82590_;
    }

    public static BitSetDiscreteVoxelShape m_165932_(int p_165933_, int p_165934_, int p_165935_, int p_165936_, int p_165937_, int p_165938_, int p_165939_, int p_165940_, int p_165941_) {
        BitSetDiscreteVoxelShape $$9 = new BitSetDiscreteVoxelShape(p_165933_, p_165934_, p_165935_);
        $$9.f_82581_ = p_165936_;
        $$9.f_82582_ = p_165937_;
        $$9.f_82583_ = p_165938_;
        $$9.f_82584_ = p_165939_;
        $$9.f_82585_ = p_165940_;
        $$9.f_82586_ = p_165941_;
        for (int $$10 = p_165936_; $$10 < p_165939_; ++$$10) {
            for (int $$11 = p_165937_; $$11 < p_165940_; ++$$11) {
                for (int $$12 = p_165938_; $$12 < p_165941_; ++$$12) {
                    $$9.m_165942_($$10, $$11, $$12, false);
                }
            }
        }
        return $$9;
    }

    public BitSetDiscreteVoxelShape(DiscreteVoxelShape p_82602_) {
        super(p_82602_.f_82781_, p_82602_.f_82782_, p_82602_.f_82783_);
        if (p_82602_ instanceof BitSetDiscreteVoxelShape) {
            this.f_82580_ = (BitSet)((BitSetDiscreteVoxelShape)p_82602_).f_82580_.clone();
        } else {
            this.f_82580_ = new BitSet(this.f_82781_ * this.f_82782_ * this.f_82783_);
            for (int $$1 = 0; $$1 < this.f_82781_; ++$$1) {
                for (int $$2 = 0; $$2 < this.f_82782_; ++$$2) {
                    for (int $$3 = 0; $$3 < this.f_82783_; ++$$3) {
                        if (!p_82602_.m_6696_($$1, $$2, $$3)) continue;
                        this.f_82580_.set(this.m_82604_($$1, $$2, $$3));
                    }
                }
            }
        }
        this.f_82581_ = p_82602_.m_6538_(Direction.Axis.X);
        this.f_82582_ = p_82602_.m_6538_(Direction.Axis.Y);
        this.f_82583_ = p_82602_.m_6538_(Direction.Axis.Z);
        this.f_82584_ = p_82602_.m_6536_(Direction.Axis.X);
        this.f_82585_ = p_82602_.m_6536_(Direction.Axis.Y);
        this.f_82586_ = p_82602_.m_6536_(Direction.Axis.Z);
    }

    protected int m_82604_(int p_82605_, int p_82606_, int p_82607_) {
        return (p_82605_ * this.f_82782_ + p_82606_) * this.f_82783_ + p_82607_;
    }

    @Override
    public boolean m_6696_(int p_82676_, int p_82677_, int p_82678_) {
        return this.f_82580_.get(this.m_82604_(p_82676_, p_82677_, p_82678_));
    }

    private void m_165942_(int p_165943_, int p_165944_, int p_165945_, boolean p_165946_) {
        this.f_82580_.set(this.m_82604_(p_165943_, p_165944_, p_165945_));
        if (p_165946_) {
            this.f_82581_ = Math.min(this.f_82581_, p_165943_);
            this.f_82582_ = Math.min(this.f_82582_, p_165944_);
            this.f_82583_ = Math.min(this.f_82583_, p_165945_);
            this.f_82584_ = Math.max(this.f_82584_, p_165943_ + 1);
            this.f_82585_ = Math.max(this.f_82585_, p_165944_ + 1);
            this.f_82586_ = Math.max(this.f_82586_, p_165945_ + 1);
        }
    }

    @Override
    public void m_142703_(int p_165987_, int p_165988_, int p_165989_) {
        this.m_165942_(p_165987_, p_165988_, p_165989_, true);
    }

    @Override
    public boolean m_6224_() {
        return this.f_82580_.isEmpty();
    }

    @Override
    public int m_6538_(Direction.Axis p_82674_) {
        return p_82674_.m_7863_(this.f_82581_, this.f_82582_, this.f_82583_);
    }

    @Override
    public int m_6536_(Direction.Axis p_82680_) {
        return p_82680_.m_7863_(this.f_82584_, this.f_82585_, this.f_82586_);
    }

    static BitSetDiscreteVoxelShape m_82641_(DiscreteVoxelShape p_82642_, DiscreteVoxelShape p_82643_, IndexMerger p_82644_, IndexMerger p_82645_, IndexMerger p_82646_, BooleanOp p_82647_) {
        BitSetDiscreteVoxelShape $$6 = new BitSetDiscreteVoxelShape(p_82644_.size() - 1, p_82645_.size() - 1, p_82646_.size() - 1);
        int[] $$7 = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
        p_82644_.m_6200_((p_82670_, p_82671_, p_82672_) -> {
            boolean[] $$10 = new boolean[]{false};
            p_82645_.m_6200_((p_165978_, p_165979_, p_165980_) -> {
                boolean[] $$13 = new boolean[]{false};
                p_82646_.m_6200_((p_165960_, p_165961_, p_165962_) -> {
                    if (p_82647_.m_82701_(p_82642_.m_82846_(p_82670_, p_165978_, p_165960_), p_82643_.m_82846_(p_82671_, p_165979_, p_165961_))) {
                        p_165955_.f_82580_.set($$6.m_82604_(p_82672_, p_165980_, p_165962_));
                        p_165958_[2] = Math.min($$7[2], p_165962_);
                        p_165958_[5] = Math.max($$7[5], p_165962_);
                        p_165959_[0] = true;
                    }
                    return true;
                });
                if ($$13[0]) {
                    p_165976_[1] = Math.min($$7[1], p_165980_);
                    p_165976_[4] = Math.max($$7[4], p_165980_);
                    p_165977_[0] = true;
                }
                return true;
            });
            if ($$10[0]) {
                p_82669_[0] = Math.min($$7[0], p_82672_);
                p_82669_[3] = Math.max($$7[3], p_82672_);
            }
            return true;
        });
        $$6.f_82581_ = $$7[0];
        $$6.f_82582_ = $$7[1];
        $$6.f_82583_ = $$7[2];
        $$6.f_82584_ = $$7[3] + 1;
        $$6.f_82585_ = $$7[4] + 1;
        $$6.f_82586_ = $$7[5] + 1;
        return $$6;
    }

    protected static void m_165963_(DiscreteVoxelShape p_165964_, DiscreteVoxelShape.IntLineConsumer p_165965_, boolean p_165966_) {
        BitSetDiscreteVoxelShape $$3 = new BitSetDiscreteVoxelShape(p_165964_);
        for (int $$4 = 0; $$4 < $$3.f_82782_; ++$$4) {
            for (int $$5 = 0; $$5 < $$3.f_82781_; ++$$5) {
                int $$6 = -1;
                for (int $$7 = 0; $$7 <= $$3.f_82783_; ++$$7) {
                    if ($$3.m_82846_($$5, $$4, $$7)) {
                        if (p_165966_) {
                            if ($$6 != -1) continue;
                            $$6 = $$7;
                            continue;
                        }
                        p_165965_.m_82858_($$5, $$4, $$7, $$5 + 1, $$4 + 1, $$7 + 1);
                        continue;
                    }
                    if ($$6 == -1) continue;
                    int $$8 = $$5;
                    int $$9 = $$4;
                    $$3.m_165981_($$6, $$7, $$5, $$4);
                    while ($$3.m_82608_($$6, $$7, $$8 + 1, $$4)) {
                        $$3.m_165981_($$6, $$7, $$8 + 1, $$4);
                        ++$$8;
                    }
                    while ($$3.m_165926_($$5, $$8 + 1, $$6, $$7, $$9 + 1)) {
                        for (int $$10 = $$5; $$10 <= $$8; ++$$10) {
                            $$3.m_165981_($$6, $$7, $$10, $$9 + 1);
                        }
                        ++$$9;
                    }
                    p_165965_.m_82858_($$5, $$4, $$6, $$8 + 1, $$9 + 1, $$7);
                    $$6 = -1;
                }
            }
        }
    }

    private boolean m_82608_(int p_82609_, int p_82610_, int p_82611_, int p_82612_) {
        if (p_82611_ >= this.f_82781_ || p_82612_ >= this.f_82782_) {
            return false;
        }
        return this.f_82580_.nextClearBit(this.m_82604_(p_82611_, p_82612_, p_82609_)) >= this.m_82604_(p_82611_, p_82612_, p_82610_);
    }

    private boolean m_165926_(int p_165927_, int p_165928_, int p_165929_, int p_165930_, int p_165931_) {
        for (int $$5 = p_165927_; $$5 < p_165928_; ++$$5) {
            if (this.m_82608_(p_165929_, p_165930_, $$5, p_165931_)) continue;
            return false;
        }
        return true;
    }

    private void m_165981_(int p_165982_, int p_165983_, int p_165984_, int p_165985_) {
        this.f_82580_.clear(this.m_82604_(p_165984_, p_165985_, p_165982_), this.m_82604_(p_165984_, p_165985_, p_165983_));
    }
}

