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
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SmithingTableBlock
extends CraftingTableBlock {
    private static final Component f_56417_ = Component.m_237115_("container.upgrade");

    protected SmithingTableBlock(BlockBehaviour.Properties p_56420_) {
        super(p_56420_);
    }

    @Override
    public MenuProvider m_7246_(BlockState p_56435_, Level p_56436_, BlockPos p_56437_) {
        return new SimpleMenuProvider((p_56424_, p_56425_, p_56426_) -> new SmithingMenu(p_56424_, p_56425_, ContainerLevelAccess.m_39289_(p_56436_, p_56437_)), f_56417_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_56428_, Level p_56429_, BlockPos p_56430_, Player p_56431_, InteractionHand p_56432_, BlockHitResult p_56433_) {
        if (p_56429_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        p_56431_.m_5893_(p_56428_.m_60750_(p_56429_, p_56430_));
        p_56431_.m_36220_(Stats.f_12954_);
        return InteractionResult.CONSUME;
    }
}

