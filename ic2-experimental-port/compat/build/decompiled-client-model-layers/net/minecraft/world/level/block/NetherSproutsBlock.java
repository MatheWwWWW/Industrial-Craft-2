/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NetherSproutsBlock
extends BushBlock {
    protected static final VoxelShape f_54949_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 3.0, 14.0);

    public NetherSproutsBlock(BlockBehaviour.Properties p_54952_) {
        super(p_54952_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_54955_, BlockGetter p_54956_, BlockPos p_54957_, CollisionContext p_54958_) {
        return f_54949_;
    }

    @Override
    protected boolean m_6266_(BlockState p_54960_, BlockGetter p_54961_, BlockPos p_54962_) {
        return p_54960_.m_204336_(BlockTags.f_13077_) || p_54960_.m_60713_(Blocks.f_50136_) || super.m_6266_(p_54960_, p_54961_, p_54962_);
    }
}

