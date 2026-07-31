/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DeadBushBlock
extends BushBlock {
    protected static final float f_153120_ = 6.0f;
    protected static final VoxelShape f_52414_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    protected DeadBushBlock(BlockBehaviour.Properties p_52417_) {
        super(p_52417_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_52419_, BlockGetter p_52420_, BlockPos p_52421_, CollisionContext p_52422_) {
        return f_52414_;
    }

    @Override
    protected boolean m_6266_(BlockState p_52424_, BlockGetter p_52425_, BlockPos p_52426_) {
        return p_52424_.m_204336_(BlockTags.f_215831_);
    }
}

