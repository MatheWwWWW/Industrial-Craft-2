/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BarrierBlock
extends Block {
    protected BarrierBlock(BlockBehaviour.Properties p_49092_) {
        super(p_49092_);
    }

    @Override
    public boolean m_7420_(BlockState p_49100_, BlockGetter p_49101_, BlockPos p_49102_) {
        return true;
    }

    @Override
    public RenderShape m_7514_(BlockState p_49098_) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public float m_7749_(BlockState p_49094_, BlockGetter p_49095_, BlockPos p_49096_) {
        return 1.0f;
    }
}

