/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class GameMasterBlockItem
extends BlockItem {
    public GameMasterBlockItem(Block p_41318_, Item.Properties p_41319_) {
        super(p_41318_, p_41319_);
    }

    @Override
    @Nullable
    protected BlockState m_5965_(BlockPlaceContext p_41321_) {
        Player $$1 = p_41321_.m_43723_();
        return $$1 == null || $$1.m_36337_() ? super.m_5965_(p_41321_) : null;
    }
}

