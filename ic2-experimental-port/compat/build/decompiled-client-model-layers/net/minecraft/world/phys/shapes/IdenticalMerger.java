/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 */
package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.world.phys.shapes.IndexMerger;

public class IdenticalMerger
implements IndexMerger {
    private final DoubleList f_82901_;

    public IdenticalMerger(DoubleList p_82903_) {
        this.f_82901_ = p_82903_;
    }

    @Override
    public boolean m_6200_(IndexMerger.IndexConsumer p_82906_) {
        int $$1 = this.f_82901_.size() - 1;
        for (int $$2 = 0; $$2 < $$1; ++$$2) {
            if (p_82906_.m_82908_($$2, $$2, $$2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public int size() {
        return this.f_82901_.size();
    }

    @Override
    public DoubleList m_6241_() {
        return this.f_82901_;
    }
}

