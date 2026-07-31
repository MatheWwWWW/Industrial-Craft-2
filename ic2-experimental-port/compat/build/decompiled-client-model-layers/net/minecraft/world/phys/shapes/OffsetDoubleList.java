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

public class OffsetDoubleList
extends AbstractDoubleList {
    private final DoubleList f_83028_;
    private final double f_83029_;

    public OffsetDoubleList(DoubleList p_83031_, double p_83032_) {
        this.f_83028_ = p_83031_;
        this.f_83029_ = p_83032_;
    }

    public double getDouble(int p_83034_) {
        return this.f_83028_.getDouble(p_83034_) + this.f_83029_;
    }

    public int size() {
        return this.f_83028_.size();
    }
}

