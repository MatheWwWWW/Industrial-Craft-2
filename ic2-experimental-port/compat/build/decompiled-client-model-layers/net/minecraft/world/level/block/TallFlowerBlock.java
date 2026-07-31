/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class TallFlowerBlock
extends DoublePlantBlock
implements BonemealableBlock {
    public TallFlowerBlock(BlockBehaviour.Properties p_57296_) {
        super(p_57296_);
    }

    @Override
    public boolean m_6864_(BlockState p_57313_, BlockPlaceContext p_57314_) {
        return false;
    }

    @Override
    public boolean m_7370_(BlockGetter p_57303_, BlockPos p_57304_, BlockState p_57305_, boolean p_57306_) {
        return true;
    }

    @Override
    public boolean m_214167_(Level p_222573_, RandomSource p_222574_, BlockPos p_222575_, BlockState p_222576_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_222568_, RandomSource p_222569_, BlockPos p_222570_, BlockState p_222571_) {
        TallFlowerBlock.m_49840_(p_222568_, p_222570_, new ItemStack(this));
    }
}

