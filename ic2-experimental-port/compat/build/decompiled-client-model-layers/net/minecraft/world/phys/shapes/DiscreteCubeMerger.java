/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.math.IntMath
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 */
package net.minecraft.world.phys.shapes;

import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.world.phys.shapes.CubePointRange;
import net.minecraft.world.phys.shapes.IndexMerger;
import net.minecraft.world.phys.shapes.Shapes;

public final class DiscreteCubeMerger
implements IndexMerger {
    private final CubePointRange f_82771_;
    private final int f_165991_;
    private final int f_165992_;

    DiscreteCubeMerger(int p_82776_, int p_82777_) {
        this.f_82771_ = new CubePointRange((int)Shapes.m_83055_(p_82776_, p_82777_));
        int $$2 = IntMath.gcd((int)p_82776_, (int)p_82777_);
        this.f_165991_ = p_82776_ / $$2;
        this.f_165992_ = p_82777_ / $$2;
    }

    @Override
    public boolean m_6200_(IndexMerger.IndexConsumer p_82780_) {
        int $$1 = this.f_82771_.size() - 1;
        for (int $$2 = 0; $$2 < $$1; ++$$2) {
            if (p_82780_.m_82908_($$2 / this.f_165992_, $$2 / this.f_165991_, $$2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public int size() {
        return this.f_82771_.size();
    }

    @Override
    public DoubleList m_6241_() {
        return this.f_82771_;
    }
}

