/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BaseCoralPlantBlock
extends BaseCoralPlantTypeBlock {
    protected static final float f_152104_ = 6.0f;
    protected static final VoxelShape f_49148_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 15.0, 14.0);

    protected BaseCoralPlantBlock(BlockBehaviour.Properties p_49151_) {
        super(p_49151_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_49153_, BlockGetter p_49154_, BlockPos p_49155_, CollisionContext p_49156_) {
        return f_49148_;
    }
}

