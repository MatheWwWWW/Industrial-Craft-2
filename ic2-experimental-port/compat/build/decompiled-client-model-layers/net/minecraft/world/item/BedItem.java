/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BedItem
extends BlockItem {
    public BedItem(Block p_40558_, Item.Properties p_40559_) {
        super(p_40558_, p_40559_);
    }

    @Override
    protected boolean m_7429_(BlockPlaceContext p_40561_, BlockState p_40562_) {
        return p_40561_.m_43725_().m_7731_(p_40561_.m_8083_(), p_40562_, 26);
    }
}

