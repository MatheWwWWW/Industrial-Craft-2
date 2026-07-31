/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.PushReaction;

public class GlazedTerracottaBlock
extends HorizontalDirectionalBlock {
    public GlazedTerracottaBlock(BlockBehaviour.Properties p_53677_) {
        super(p_53677_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53681_) {
        p_53681_.m_61104_(f_54117_);
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_53679_) {
        return (BlockState)this.m_49966_().m_61124_(f_54117_, p_53679_.m_8125_().m_122424_());
    }

    @Override
    public PushReaction m_5537_(BlockState p_53683_) {
        return PushReaction.PUSH_ONLY;
    }
}

