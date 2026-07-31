/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RodBlock
extends DirectionalBlock {
    protected static final float f_154332_ = 6.0f;
    protected static final float f_154333_ = 10.0f;
    protected static final VoxelShape f_154334_ = Block.m_49796_(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    protected static final VoxelShape f_154335_ = Block.m_49796_(6.0, 6.0, 0.0, 10.0, 10.0, 16.0);
    protected static final VoxelShape f_154336_ = Block.m_49796_(0.0, 6.0, 6.0, 16.0, 10.0, 10.0);

    protected RodBlock(BlockBehaviour.Properties p_154339_) {
        super(p_154339_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_154346_, BlockGetter p_154347_, BlockPos p_154348_, CollisionContext p_154349_) {
        switch (p_154346_.m_61143_(f_52588_).m_122434_()) {
            default: {
                return f_154336_;
            }
            case Z: {
                return f_154335_;
            }
            case Y: 
        }
        return f_154334_;
    }

    @Override
    public BlockState m_6843_(BlockState p_154354_, Rotation p_154355_) {
        return (BlockState)p_154354_.m_61124_(f_52588_, p_154355_.m_55954_(p_154354_.m_61143_(f_52588_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_154351_, Mirror p_154352_) {
        return (BlockState)p_154351_.m_61124_(f_52588_, p_154352_.m_54848_(p_154351_.m_61143_(f_52588_)));
    }

    @Override
    public boolean m_7357_(BlockState p_154341_, BlockGetter p_154342_, BlockPos p_154343_, PathComputationType p_154344_) {
        return false;
    }
}

