/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class TintedGlassBlock
extends AbstractGlassBlock {
    public TintedGlassBlock(BlockBehaviour.Properties p_154822_) {
        super(p_154822_);
    }

    @Override
    public boolean m_7420_(BlockState p_154824_, BlockGetter p_154825_, BlockPos p_154826_) {
        return false;
    }

    @Override
    public int m_7753_(BlockState p_154828_, BlockGetter p_154829_, BlockPos p_154830_) {
        return p_154829_.m_7469_();
    }
}

