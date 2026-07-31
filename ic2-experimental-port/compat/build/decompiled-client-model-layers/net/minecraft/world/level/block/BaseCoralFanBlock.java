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

public class BaseCoralFanBlock
extends BaseCoralPlantTypeBlock {
    private static final VoxelShape f_49103_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);

    protected BaseCoralFanBlock(BlockBehaviour.Properties p_49106_) {
        super(p_49106_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_49108_, BlockGetter p_49109_, BlockPos p_49110_, CollisionContext p_49111_) {
        return f_49103_;
    }
}

