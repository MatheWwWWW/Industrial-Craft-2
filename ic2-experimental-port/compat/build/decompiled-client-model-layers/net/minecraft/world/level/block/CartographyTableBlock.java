/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CartographyTableBlock
extends Block {
    private static final Component f_51346_ = Component.m_237115_("container.cartography_table");

    protected CartographyTableBlock(BlockBehaviour.Properties p_51349_) {
        super(p_51349_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_51357_, Level p_51358_, BlockPos p_51359_, Player p_51360_, InteractionHand p_51361_, BlockHitResult p_51362_) {
        if (p_51358_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        p_51360_.m_5893_(p_51357_.m_60750_(p_51358_, p_51359_));
        p_51360_.m_36220_(Stats.f_12976_);
        return InteractionResult.CONSUME;
    }

    @Override
    @Nullable
    public MenuProvider m_7246_(BlockState p_51364_, Level p_51365_, BlockPos p_51366_) {
        return new SimpleMenuProvider((p_51353_, p_51354_, p_51355_) -> new CartographyTableMenu(p_51353_, p_51354_, ContainerLevelAccess.m_39289_(p_51365_, p_51366_)), f_51346_);
    }
}

