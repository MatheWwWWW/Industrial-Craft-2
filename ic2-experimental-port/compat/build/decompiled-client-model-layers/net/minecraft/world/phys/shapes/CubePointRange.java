/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.AbstractDoubleList
 */
package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;

public class CubePointRange
extends AbstractDoubleList {
    private final int f_82758_;

    CubePointRange(int p_82760_) {
        if (p_82760_ <= 0) {
            throw new IllegalArgumentException("Need at least 1 part");
        }
        this.f_82758_ = p_82760_;
    }

    public double getDouble(int p_82762_) {
        return (double)p_82762_ / (double)this.f_82758_;
    }

    public int size() {
        return this.f_82758_ + 1;
    }
}

