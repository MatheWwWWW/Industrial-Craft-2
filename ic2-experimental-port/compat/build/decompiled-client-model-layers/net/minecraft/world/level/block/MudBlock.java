/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MudBlock
extends Block {
    protected static final VoxelShape f_221542_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

    public MudBlock(BlockBehaviour.Properties p_221545_) {
        super(p_221545_);
    }

    @Override
    public VoxelShape m_5939_(BlockState p_221561_, BlockGetter p_221562_, BlockPos p_221563_, CollisionContext p_221564_) {
        return f_221542_;
    }

    @Override
    public VoxelShape m_7947_(BlockState p_221566_, BlockGetter p_221567_, BlockPos p_221568_) {
        return Shapes.m_83144_();
    }

    @Override
    public VoxelShape m_5909_(BlockState p_221556_, BlockGetter p_221557_, BlockPos p_221558_, CollisionContext p_221559_) {
        return Shapes.m_83144_();
    }

    @Override
    public boolean m_7357_(BlockState p_221547_, BlockGetter p_221548_, BlockPos p_221549_, PathComputationType p_221550_) {
        return false;
    }

    @Override
    public float m_7749_(BlockState p_221552_, BlockGetter p_221553_, BlockPos p_221554_) {
        return 0.2f;
    }
}

