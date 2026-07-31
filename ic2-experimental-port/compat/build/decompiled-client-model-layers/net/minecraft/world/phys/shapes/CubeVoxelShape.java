/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 */
package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.CubePointRange;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class CubeVoxelShape
extends VoxelShape {
    protected CubeVoxelShape(DiscreteVoxelShape p_82765_) {
        super(p_82765_);
    }

    @Override
    protected DoubleList m_7700_(Direction.Axis p_82767_) {
        return new CubePointRange(this.f_83211_.m_82850_(p_82767_));
    }

    @Override
    protected int m_6595_(Direction.Axis p_82769_, double p_82770_) {
        int $$2 = this.f_83211_.m_82850_(p_82769_);
        return Mth.m_14107_(Mth.m_14008_(p_82770_ * (double)$$2, -1.0, $$2));
    }
}

