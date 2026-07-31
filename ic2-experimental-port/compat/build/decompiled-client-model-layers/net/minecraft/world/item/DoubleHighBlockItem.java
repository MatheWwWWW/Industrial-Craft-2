/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DoubleHighBlockItem
extends BlockItem {
    public DoubleHighBlockItem(Block p_41010_, Item.Properties p_41011_) {
        super(p_41010_, p_41011_);
    }

    @Override
    protected boolean m_7429_(BlockPlaceContext p_41013_, BlockState p_41014_) {
        BlockPos $$3;
        Level $$2 = p_41013_.m_43725_();
        BlockState $$4 = $$2.m_46801_($$3 = p_41013_.m_8083_().m_7494_()) ? Blocks.f_49990_.m_49966_() : Blocks.f_50016_.m_49966_();
        $$2.m_7731_($$3, $$4, 27);
        return super.m_7429_(p_41013_, p_41014_);
    }
}

