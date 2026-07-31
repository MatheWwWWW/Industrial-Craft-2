/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.DoubleArrayList
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 *  it.unimi.dsi.fastutil.doubles.DoubleLists
 */
package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleLists;
import net.minecraft.world.phys.shapes.IndexMerger;

public class IndirectMerger
implements IndexMerger {
    private static final DoubleList f_166021_ = DoubleLists.unmodifiable((DoubleList)DoubleArrayList.wrap((double[])new double[]{0.0}));
    private final double[] f_82997_;
    private final int[] f_82998_;
    private final int[] f_82999_;
    private final int f_166022_;

    public IndirectMerger(DoubleList p_83001_, DoubleList p_83002_, boolean p_83003_, boolean p_83004_) {
        double $$4 = Double.NaN;
        int $$5 = p_83001_.size();
        int $$6 = p_83002_.size();
        int $$7 = $$5 + $$6;
        this.f_82997_ = new double[$$7];
        this.f_82998_ = new int[$$7];
        this.f_82999_ = new int[$$7];
        boolean $$8 = !p_83003_;
        boolean $$9 = !p_83004_;
        int $$10 = 0;
        int $$11 = 0;
        int $$12 = 0;
        while (true) {
            double $$18;
            boolean $$15;
            boolean $$14;
            boolean $$13 = $$11 >= $$5;
            boolean bl = $$14 = $$12 >= $$6;
            if ($$13 && $$14) break;
            boolean bl2 = $$15 = !$$13 && ($$14 || p_83001_.getDouble($$11) < p_83002_.getDouble($$12) + 1.0E-7);
            if ($$15) {
                ++$$11;
                if ($$8 && ($$12 == 0 || $$14)) {
                    continue;
                }
            } else {
                ++$$12;
                if ($$9 && ($$11 == 0 || $$13)) continue;
            }
            int $$16 = $$11 - 1;
            int $$17 = $$12 - 1;
            double d = $$18 = $$15 ? p_83001_.getDouble($$16) : p_83002_.getDouble($$17);
            if (!($$4 >= $$18 - 1.0E-7)) {
                this.f_82998_[$$10] = $$16;
                this.f_82999_[$$10] = $$17;
                this.f_82997_[$$10] = $$18;
                ++$$10;
                $$4 = $$18;
                continue;
            }
            this.f_82998_[$$10 - 1] = $$16;
            this.f_82999_[$$10 - 1] = $$17;
        }
        this.f_166022_ = Math.max(1, $$10);
    }

    @Override
    public boolean m_6200_(IndexMerger.IndexConsumer p_83007_) {
        int $$1 = this.f_166022_ - 1;
        for (int $$2 = 0; $$2 < $$1; ++$$2) {
            if (p_83007_.m_82908_(this.f_82998_[$$2], this.f_82999_[$$2], $$2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public int size() {
        return this.f_166022_;
    }

    @Override
    public DoubleList m_6241_() {
        return this.f_166022_ <= 1 ? f_166021_ : DoubleArrayList.wrap((double[])this.f_82997_, (int)this.f_166022_);
    }
}

