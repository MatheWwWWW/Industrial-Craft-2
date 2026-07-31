/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.material;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EmptyFluid
extends Fluid {
    @Override
    public Item m_6859_() {
        return Items.f_41852_;
    }

    @Override
    public boolean m_5486_(FluidState p_75930_, BlockGetter p_75931_, BlockPos p_75932_, Fluid p_75933_, Direction p_75934_) {
        return true;
    }

    @Override
    public Vec3 m_7000_(BlockGetter p_75918_, BlockPos p_75919_, FluidState p_75920_) {
        return Vec3.f_82478_;
    }

    @Override
    public int m_6718_(LevelReader p_75922_) {
        return 0;
    }

    @Override
    protected boolean m_6759_() {
        return true;
    }

    @Override
    protected float m_6752_() {
        return 0.0f;
    }

    @Override
    public float m_6098_(FluidState p_75926_, BlockGetter p_75927_, BlockPos p_75928_) {
        return 0.0f;
    }

    @Override
    public float m_7427_(FluidState p_75924_) {
        return 0.0f;
    }

    @Override
    protected BlockState m_5804_(FluidState p_75937_) {
        return Blocks.f_50016_.m_49966_();
    }

    @Override
    public boolean m_7444_(FluidState p_75944_) {
        return false;
    }

    @Override
    public int m_7430_(FluidState p_75946_) {
        return 0;
    }

    @Override
    public VoxelShape m_7999_(FluidState p_75939_, BlockGetter p_75940_, BlockPos p_75941_) {
        return Shapes.m_83040_();
    }
}

