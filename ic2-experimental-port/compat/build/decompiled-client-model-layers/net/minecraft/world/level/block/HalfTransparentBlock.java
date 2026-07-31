/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class HalfTransparentBlock
extends Block {
    protected HalfTransparentBlock(BlockBehaviour.Properties p_53970_) {
        super(p_53970_);
    }

    @Override
    public boolean m_6104_(BlockState p_53972_, BlockState p_53973_, Direction p_53974_) {
        if (p_53973_.m_60713_(this)) {
            return true;
        }
        return super.m_6104_(p_53972_, p_53973_, p_53974_);
    }
}

