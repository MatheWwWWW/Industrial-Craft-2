/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StructureVoidBlock
extends Block {
    private static final double f_154734_ = 5.0;
    private static final VoxelShape f_57147_ = Block.m_49796_(5.0, 5.0, 5.0, 11.0, 11.0, 11.0);

    protected StructureVoidBlock(BlockBehaviour.Properties p_57150_) {
        super(p_57150_);
    }

    @Override
    public RenderShape m_7514_(BlockState p_57156_) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_57158_, BlockGetter p_57159_, BlockPos p_57160_, CollisionContext p_57161_) {
        return f_57147_;
    }

    @Override
    public float m_7749_(BlockState p_57152_, BlockGetter p_57153_, BlockPos p_57154_) {
        return 1.0f;
    }

    @Override
    public PushReaction m_5537_(BlockState p_57163_) {
        return PushReaction.DESTROY;
    }
}

