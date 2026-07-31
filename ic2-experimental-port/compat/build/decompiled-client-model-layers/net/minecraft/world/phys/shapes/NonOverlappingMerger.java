/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.AbstractDoubleList
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 */
package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.world.phys.shapes.IndexMerger;

public class NonOverlappingMerger
extends AbstractDoubleList
implements IndexMerger {
    private final DoubleList f_83008_;
    private final DoubleList f_83009_;
    private final boolean f_83010_;

    protected NonOverlappingMerger(DoubleList p_83012_, DoubleList p_83013_, boolean p_83014_) {
        this.f_83008_ = p_83012_;
        this.f_83009_ = p_83013_;
        this.f_83010_ = p_83014_;
    }

    @Override
    public int size() {
        return this.f_83008_.size() + this.f_83009_.size();
    }

    @Override
    public boolean m_6200_(IndexMerger.IndexConsumer p_83017_) {
        if (this.f_83010_) {
            return this.m_83023_((p_83020_, p_83021_, p_83022_) -> p_83017_.m_82908_(p_83021_, p_83020_, p_83022_));
        }
        return this.m_83023_(p_83017_);
    }

    private boolean m_83023_(IndexMerger.IndexConsumer p_83024_) {
        int $$1 = this.f_83008_.size();
        for (int $$2 = 0; $$2 < $$1; ++$$2) {
            if (p_83024_.m_82908_($$2, -1, $$2)) continue;
            return false;
        }
        int $$3 = this.f_83009_.size() - 1;
        for (int $$4 = 0; $$4 < $$3; ++$$4) {
            if (p_83024_.m_82908_($$1 - 1, $$4, $$1 + $$4)) continue;
            return false;
        }
        return true;
    }

    public double getDouble(int p_83026_) {
        if (p_83026_ < this.f_83008_.size()) {
            return this.f_83008_.getDouble(p_83026_);
        }
        return this.f_83009_.getDouble(p_83026_ - this.f_83008_.size());
    }

    @Override
    public DoubleList m_6241_() {
        return this;
    }
}

