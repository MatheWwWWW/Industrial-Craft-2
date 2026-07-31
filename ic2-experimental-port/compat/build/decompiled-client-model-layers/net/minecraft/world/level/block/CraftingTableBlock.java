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
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CraftingTableBlock
extends Block {
    private static final Component f_52222_ = Component.m_237115_("container.crafting");

    protected CraftingTableBlock(BlockBehaviour.Properties p_52225_) {
        super(p_52225_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_52233_, Level p_52234_, BlockPos p_52235_, Player p_52236_, InteractionHand p_52237_, BlockHitResult p_52238_) {
        if (p_52234_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        p_52236_.m_5893_(p_52233_.m_60750_(p_52234_, p_52235_));
        p_52236_.m_36220_(Stats.f_12967_);
        return InteractionResult.CONSUME;
    }

    @Override
    public MenuProvider m_7246_(BlockState p_52240_, Level p_52241_, BlockPos p_52242_) {
        return new SimpleMenuProvider((p_52229_, p_52230_, p_52231_) -> new CraftingMenu(p_52229_, p_52230_, ContainerLevelAccess.m_39289_(p_52241_, p_52242_)), f_52222_);
    }
}

