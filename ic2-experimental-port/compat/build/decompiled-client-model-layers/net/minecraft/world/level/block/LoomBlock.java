/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class LoomBlock
extends HorizontalDirectionalBlock {
    private static final Component f_54774_ = Component.m_237115_("container.loom");

    protected LoomBlock(BlockBehaviour.Properties p_54777_) {
        super(p_54777_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_54787_, Level p_54788_, BlockPos p_54789_, Player p_54790_, InteractionHand p_54791_, BlockHitResult p_54792_) {
        if (p_54788_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        p_54790_.m_5893_(p_54787_.m_60750_(p_54788_, p_54789_));
        p_54790_.m_36220_(Stats.f_12977_);
        return InteractionResult.CONSUME;
    }

    @Override
    public MenuProvider m_7246_(BlockState p_54796_, Level p_54797_, BlockPos p_54798_) {
        return new SimpleMenuProvider((p_54783_, p_54784_, p_54785_) -> new LoomMenu(p_54783_, p_54784_, ContainerLevelAccess.m_39289_(p_54797_, p_54798_)), f_54774_);
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_54779_) {
        return (BlockState)this.m_49966_().m_61124_(f_54117_, p_54779_.m_8125_().m_122424_());
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54794_) {
        p_54794_.m_61104_(f_54117_);
    }
}

