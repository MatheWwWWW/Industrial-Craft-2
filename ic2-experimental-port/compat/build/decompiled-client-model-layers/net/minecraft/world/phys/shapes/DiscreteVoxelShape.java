/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.phys.shapes;

import net.minecraft.core.AxisCycle;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;

public abstract class DiscreteVoxelShape {
    private static final Direction.Axis[] f_82784_ = Direction.Axis.values();
    protected final int f_82781_;
    protected final int f_82782_;
    protected final int f_82783_;

    protected DiscreteVoxelShape(int p_82787_, int p_82788_, int p_82789_) {
        if (p_82787_ < 0 || p_82788_ < 0 || p_82789_ < 0) {
            throw new IllegalArgumentException("Need all positive sizes: x: " + p_82787_ + ", y: " + p_82788_ + ", z: " + p_82789_);
        }
        this.f_82781_ = p_82787_;
        this.f_82782_ = p_82788_;
        this.f_82783_ = p_82789_;
    }

    public boolean m_82822_(AxisCycle p_82823_, int p_82824_, int p_82825_, int p_82826_) {
        return this.m_82846_(p_82823_.m_7758_(p_82824_, p_82825_, p_82826_, Direction.Axis.X), p_82823_.m_7758_(p_82824_, p_82825_, p_82826_, Direction.Axis.Y), p_82823_.m_7758_(p_82824_, p_82825_, p_82826_, Direction.Axis.Z));
    }

    public boolean m_82846_(int p_82847_, int p_82848_, int p_82849_) {
        if (p_82847_ < 0 || p_82848_ < 0 || p_82849_ < 0) {
            return false;
        }
        if (p_82847_ >= this.f_82781_ || p_82848_ >= this.f_82782_ || p_82849_ >= this.f_82783_) {
            return false;
        }
        return this.m_6696_(p_82847_, p_82848_, p_82849_);
    }

    public boolean m_82835_(AxisCycle p_82836_, int p_82837_, int p_82838_, int p_82839_) {
        return this.m_6696_(p_82836_.m_7758_(p_82837_, p_82838_, p_82839_, Direction.Axis.X), p_82836_.m_7758_(p_82837_, p_82838_, p_82839_, Direction.Axis.Y), p_82836_.m_7758_(p_82837_, p_82838_, p_82839_, Direction.Axis.Z));
    }

    public abstract boolean m_6696_(int var1, int var2, int var3);

    public abstract void m_142703_(int var1, int var2, int var3);

    public boolean m_6224_() {
        for (Direction.Axis $$0 : f_82784_) {
            if (this.m_6538_($$0) < this.m_6536_($$0)) continue;
            return true;
        }
        return false;
    }

    public abstract int m_6538_(Direction.Axis var1);

    public abstract int m_6536_(Direction.Axis var1);

    public int m_165994_(Direction.Axis p_165995_, int p_165996_, int p_165997_) {
        int $$3 = this.m_82850_(p_165995_);
        if (p_165996_ < 0 || p_165997_ < 0) {
            return $$3;
        }
        Direction.Axis $$4 = AxisCycle.FORWARD.m_7314_(p_165995_);
        Direction.Axis $$5 = AxisCycle.BACKWARD.m_7314_(p_165995_);
        if (p_165996_ >= this.m_82850_($$4) || p_165997_ >= this.m_82850_($$5)) {
            return $$3;
        }
        AxisCycle $$6 = AxisCycle.m_121799_(Direction.Axis.X, p_165995_);
        for (int $$7 = 0; $$7 < $$3; ++$$7) {
            if (!this.m_82835_($$6, $$7, p_165996_, p_165997_)) continue;
            return $$7;
        }
        return $$3;
    }

    public int m_82841_(Direction.Axis p_82842_, int p_82843_, int p_82844_) {
        if (p_82843_ < 0 || p_82844_ < 0) {
            return 0;
        }
        Direction.Axis $$3 = AxisCycle.FORWARD.m_7314_(p_82842_);
        Direction.Axis $$4 = AxisCycle.BACKWARD.m_7314_(p_82842_);
        if (p_82843_ >= this.m_82850_($$3) || p_82844_ >= this.m_82850_($$4)) {
            return 0;
        }
        int $$5 = this.m_82850_(p_82842_);
        AxisCycle $$6 = AxisCycle.m_121799_(Direction.Axis.X, p_82842_);
        for (int $$7 = $$5 - 1; $$7 >= 0; --$$7) {
            if (!this.m_82835_($$6, $$7, p_82843_, p_82844_)) continue;
            return $$7 + 1;
        }
        return 0;
    }

    public int m_82850_(Direction.Axis p_82851_) {
        return p_82851_.m_7863_(this.f_82781_, this.f_82782_, this.f_82783_);
    }

    public int m_82828_() {
        return this.m_82850_(Direction.Axis.X);
    }

    public int m_82845_() {
        return this.m_82850_(Direction.Axis.Y);
    }

    public int m_82852_() {
        return this.m_82850_(Direction.Axis.Z);
    }

    public void m_82819_(IntLineConsumer p_82820_, boolean p_82821_) {
        this.m_82815_(p_82820_, AxisCycle.NONE, p_82821_);
        this.m_82815_(p_82820_, AxisCycle.FORWARD, p_82821_);
        this.m_82815_(p_82820_, AxisCycle.BACKWARD, p_82821_);
    }

    private void m_82815_(IntLineConsumer p_82816_, AxisCycle p_82817_, boolean p_82818_) {
        AxisCycle $$3 = p_82817_.m_7634_();
        int $$4 = this.m_82850_($$3.m_7314_(Direction.Axis.X));
        int $$5 = this.m_82850_($$3.m_7314_(Direction.Axis.Y));
        int $$6 = this.m_82850_($$3.m_7314_(Direction.Axis.Z));
        for (int $$7 = 0; $$7 <= $$4; ++$$7) {
            for (int $$8 = 0; $$8 <= $$5; ++$$8) {
                int $$9 = -1;
                for (int $$10 = 0; $$10 <= $$6; ++$$10) {
                    int $$11 = 0;
                    int $$12 = 0;
                    for (int $$13 = 0; $$13 <= 1; ++$$13) {
                        for (int $$14 = 0; $$14 <= 1; ++$$14) {
                            if (!this.m_82822_($$3, $$7 + $$13 - 1, $$8 + $$14 - 1, $$10)) continue;
                            ++$$11;
                            $$12 ^= $$13 ^ $$14;
                        }
                    }
                    if ($$11 == 1 || $$11 == 3 || $$11 == 2 && !($$12 & true)) {
                        if (p_82818_) {
                            if ($$9 != -1) continue;
                            $$9 = $$10;
                            continue;
                        }
                        p_82816_.m_82858_($$3.m_7758_($$7, $$8, $$10, Direction.Axis.X), $$3.m_7758_($$7, $$8, $$10, Direction.Axis.Y), $$3.m_7758_($$7, $$8, $$10, Direction.Axis.Z), $$3.m_7758_($$7, $$8, $$10 + 1, Direction.Axis.X), $$3.m_7758_($$7, $$8, $$10 + 1, Direction.Axis.Y), $$3.m_7758_($$7, $$8, $$10 + 1, Direction.Axis.Z));
                        continue;
                    }
                    if ($$9 == -1) continue;
                    p_82816_.m_82858_($$3.m_7758_($$7, $$8, $$9, Direction.Axis.X), $$3.m_7758_($$7, $$8, $$9, Direction.Axis.Y), $$3.m_7758_($$7, $$8, $$9, Direction.Axis.Z), $$3.m_7758_($$7, $$8, $$10, Direction.Axis.X), $$3.m_7758_($$7, $$8, $$10, Direction.Axis.Y), $$3.m_7758_($$7, $$8, $$10, Direction.Axis.Z));
                    $$9 = -1;
                }
            }
        }
    }

    public void m_82832_(IntLineConsumer p_82833_, boolean p_82834_) {
        BitSetDiscreteVoxelShape.m_165963_(this, p_82833_, p_82834_);
    }

    public void m_82810_(IntFaceConsumer p_82811_) {
        this.m_82812_(p_82811_, AxisCycle.NONE);
        this.m_82812_(p_82811_, AxisCycle.FORWARD);
        this.m_82812_(p_82811_, AxisCycle.BACKWARD);
    }

    private void m_82812_(IntFaceConsumer p_82813_, AxisCycle p_82814_) {
        AxisCycle $$2 = p_82814_.m_7634_();
        Direction.Axis $$3 = $$2.m_7314_(Direction.Axis.Z);
        int $$4 = this.m_82850_($$2.m_7314_(Direction.Axis.X));
        int $$5 = this.m_82850_($$2.m_7314_(Direction.Axis.Y));
        int $$6 = this.m_82850_($$3);
        Direction $$7 = Direction.m_122387_($$3, Direction.AxisDirection.NEGATIVE);
        Direction $$8 = Direction.m_122387_($$3, Direction.AxisDirection.POSITIVE);
        for (int $$9 = 0; $$9 < $$4; ++$$9) {
            for (int $$10 = 0; $$10 < $$5; ++$$10) {
                boolean $$11 = false;
                for (int $$12 = 0; $$12 <= $$6; ++$$12) {
                    boolean $$13;
                    boolean bl = $$13 = $$12 != $$6 && this.m_82835_($$2, $$9, $$10, $$12);
                    if (!$$11 && $$13) {
                        p_82813_.m_82853_($$7, $$2.m_7758_($$9, $$10, $$12, Direction.Axis.X), $$2.m_7758_($$9, $$10, $$12, Direction.Axis.Y), $$2.m_7758_($$9, $$10, $$12, Direction.Axis.Z));
                    }
                    if ($$11 && !$$13) {
                        p_82813_.m_82853_($$8, $$2.m_7758_($$9, $$10, $$12 - 1, Direction.Axis.X), $$2.m_7758_($$9, $$10, $$12 - 1, Direction.Axis.Y), $$2.m_7758_($$9, $$10, $$12 - 1, Direction.Axis.Z));
                    }
                    $$11 = $$13;
                }
            }
        }
    }

    public static interface IntLineConsumer {
        public void m_82858_(int var1, int var2, int var3, int var4, int var5, int var6);
    }

    public static interface IntFaceConsumer {
        public void m_82853_(Direction var1, int var2, int var3, int var4);
    }
}

