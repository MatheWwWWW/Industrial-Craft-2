/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

public class KelpBlock
extends GrowingPlantHeadBlock
implements LiquidBlockContainer {
    protected static final VoxelShape f_54297_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);
    private static final double f_153453_ = 0.14;

    protected KelpBlock(BlockBehaviour.Properties p_54300_) {
        super(p_54300_, Direction.UP, f_54297_, true, 0.14);
    }

    @Override
    protected boolean m_5971_(BlockState p_54321_) {
        return p_54321_.m_60713_(Blocks.f_49990_);
    }

    @Override
    protected Block m_7777_() {
        return Blocks.f_50576_;
    }

    @Override
    protected boolean m_142209_(BlockState p_153455_) {
        return !p_153455_.m_60713_(Blocks.f_50450_);
    }

    @Override
    public boolean m_6044_(BlockGetter p_54304_, BlockPos p_54305_, BlockState p_54306_, Fluid p_54307_) {
        return false;
    }

    @Override
    public boolean m_7361_(LevelAccessor p_54309_, BlockPos p_54310_, BlockState p_54311_, FluidState p_54312_) {
        return false;
    }

    @Override
    protected int m_213627_(RandomSource p_221366_) {
        return 1;
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_54302_) {
        FluidState $$1 = p_54302_.m_43725_().m_6425_(p_54302_.m_8083_());
        if ($$1.m_205070_(FluidTags.f_13131_) && $$1.m_76186_() == 8) {
            return super.m_5573_(p_54302_);
        }
        return null;
    }

    @Override
    public FluidState m_5888_(BlockState p_54319_) {
        return Fluids.f_76193_.m_76068_(false);
    }
}

