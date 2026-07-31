/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.DoubleArrayList
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 */
package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ArrayVoxelShape
extends VoxelShape {
    private final DoubleList f_82563_;
    private final DoubleList f_82564_;
    private final DoubleList f_82565_;

    protected ArrayVoxelShape(DiscreteVoxelShape p_82572_, double[] p_82573_, double[] p_82574_, double[] p_82575_) {
        this(p_82572_, (DoubleList)DoubleArrayList.wrap((double[])Arrays.copyOf(p_82573_, p_82572_.m_82828_() + 1)), (DoubleList)DoubleArrayList.wrap((double[])Arrays.copyOf(p_82574_, p_82572_.m_82845_() + 1)), (DoubleList)DoubleArrayList.wrap((double[])Arrays.copyOf(p_82575_, p_82572_.m_82852_() + 1)));
    }

    ArrayVoxelShape(DiscreteVoxelShape p_82567_, DoubleList p_82568_, DoubleList p_82569_, DoubleList p_82570_) {
        super(p_82567_);
        int $$4 = p_82567_.m_82828_() + 1;
        int $$5 = p_82567_.m_82845_() + 1;
        int $$6 = p_82567_.m_82852_() + 1;
        if ($$4 != p_82568_.size() || $$5 != p_82569_.size() || $$6 != p_82570_.size()) {
            throw Util.m_137570_(new IllegalArgumentException("Lengths of point arrays must be consistent with the size of the VoxelShape."));
        }
        this.f_82563_ = p_82568_;
        this.f_82564_ = p_82569_;
        this.f_82565_ = p_82570_;
    }

    @Override
    protected DoubleList m_7700_(Direction.Axis p_82577_) {
        switch (p_82577_) {
            case X: {
                return this.f_82563_;
            }
            case Y: {
                return this.f_82564_;
            }
            case Z: {
                return this.f_82565_;
            }
        }
        throw new IllegalArgumentException();
    }
}

