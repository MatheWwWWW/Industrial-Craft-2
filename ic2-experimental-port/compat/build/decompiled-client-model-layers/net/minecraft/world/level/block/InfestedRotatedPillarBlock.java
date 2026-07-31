/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class InfestedRotatedPillarBlock
extends InfestedBlock {
    public InfestedRotatedPillarBlock(Block p_153438_, BlockBehaviour.Properties p_153439_) {
        super(p_153438_, p_153439_);
        this.m_49959_((BlockState)this.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, Direction.Axis.Y));
    }

    @Override
    public BlockState m_6843_(BlockState p_153443_, Rotation p_153444_) {
        return RotatedPillarBlock.m_154376_(p_153443_, p_153444_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_153446_) {
        p_153446_.m_61104_(RotatedPillarBlock.f_55923_);
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_153441_) {
        return (BlockState)this.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, p_153441_.m_43719_().m_122434_());
    }
}

