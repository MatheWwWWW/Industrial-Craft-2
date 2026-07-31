/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WeepingVinesBlock
extends GrowingPlantHeadBlock {
    protected static final VoxelShape f_154963_ = Block.m_49796_(4.0, 9.0, 4.0, 12.0, 16.0, 12.0);

    public WeepingVinesBlock(BlockBehaviour.Properties p_154966_) {
        super(p_154966_, Direction.DOWN, f_154963_, false, 0.1);
    }

    @Override
    protected int m_213627_(RandomSource p_222680_) {
        return NetherVines.m_221803_(p_222680_);
    }

    @Override
    protected Block m_7777_() {
        return Blocks.f_50703_;
    }

    @Override
    protected boolean m_5971_(BlockState p_154971_) {
        return NetherVines.m_54963_(p_154971_);
    }
}

