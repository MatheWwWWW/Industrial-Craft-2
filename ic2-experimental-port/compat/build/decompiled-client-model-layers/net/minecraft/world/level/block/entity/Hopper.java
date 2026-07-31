/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.world.Container;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface Hopper
extends Container {
    public static final VoxelShape f_59296_ = Block.m_49796_(2.0, 11.0, 2.0, 14.0, 16.0, 14.0);
    public static final VoxelShape f_59297_ = Block.m_49796_(0.0, 16.0, 0.0, 16.0, 32.0, 16.0);
    public static final VoxelShape f_59298_ = Shapes.m_83110_(f_59296_, f_59297_);

    default public VoxelShape m_59300_() {
        return f_59298_;
    }

    public double m_6343_();

    public double m_6358_();

    public double m_6446_();
}

