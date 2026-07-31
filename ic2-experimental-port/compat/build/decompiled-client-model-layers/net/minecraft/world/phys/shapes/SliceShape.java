/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 */
package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.CubePointRange;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import net.minecraft.world.phys.shapes.SubShape;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SliceShape
extends VoxelShape {
    private final VoxelShape f_83168_;
    private final Direction.Axis f_83169_;
    private static final DoubleList f_83170_ = new CubePointRange(1);

    public SliceShape(VoxelShape p_83173_, Direction.Axis p_83174_, int p_83175_) {
        super(SliceShape.m_83176_(p_83173_.f_83211_, p_83174_, p_83175_));
        this.f_83168_ = p_83173_;
        this.f_83169_ = p_83174_;
    }

    private static DiscreteVoxelShape m_83176_(DiscreteVoxelShape p_83177_, Direction.Axis p_83178_, int p_83179_) {
        return new SubShape(p_83177_, p_83178_.m_7863_(p_83179_, 0, 0), p_83178_.m_7863_(0, p_83179_, 0), p_83178_.m_7863_(0, 0, p_83179_), p_83178_.m_7863_(p_83179_ + 1, p_83177_.f_82781_, p_83177_.f_82781_), p_83178_.m_7863_(p_83177_.f_82782_, p_83179_ + 1, p_83177_.f_82782_), p_83178_.m_7863_(p_83177_.f_82783_, p_83177_.f_82783_, p_83179_ + 1));
    }

    @Override
    protected DoubleList m_7700_(Direction.Axis p_83181_) {
        if (p_83181_ == this.f_83169_) {
            return f_83170_;
        }
        return this.f_83168_.m_7700_(p_83181_);
    }
}

