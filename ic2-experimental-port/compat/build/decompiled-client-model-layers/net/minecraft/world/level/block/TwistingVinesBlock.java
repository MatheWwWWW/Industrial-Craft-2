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

public class TwistingVinesBlock
extends GrowingPlantHeadBlock {
    public static final VoxelShape f_154861_ = Block.m_49796_(4.0, 0.0, 4.0, 12.0, 15.0, 12.0);

    public TwistingVinesBlock(BlockBehaviour.Properties p_154864_) {
        super(p_154864_, Direction.UP, f_154861_, false, 0.1);
    }

    @Override
    protected int m_213627_(RandomSource p_222649_) {
        return NetherVines.m_221803_(p_222649_);
    }

    @Override
    protected Block m_7777_() {
        return Blocks.f_50653_;
    }

    @Override
    protected boolean m_5971_(BlockState p_154869_) {
        return NetherVines.m_54963_(p_154869_);
    }
}

