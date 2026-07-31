/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.NeighborUpdater;

public class InstantNeighborUpdater
implements NeighborUpdater {
    private final Level f_230741_;

    public InstantNeighborUpdater(Level p_230743_) {
        this.f_230741_ = p_230743_;
    }

    @Override
    public void m_213547_(Direction p_230755_, BlockState p_230756_, BlockPos p_230757_, BlockPos p_230758_, int p_230759_, int p_230760_) {
        NeighborUpdater.m_230770_(this.f_230741_, p_230755_, p_230756_, p_230757_, p_230758_, p_230759_, p_230760_ - 1);
    }

    @Override
    public void m_214026_(BlockPos p_230751_, Block p_230752_, BlockPos p_230753_) {
        BlockState $$3 = this.f_230741_.m_8055_(p_230751_);
        this.m_213858_($$3, p_230751_, p_230752_, p_230753_, false);
    }

    @Override
    public void m_213858_(BlockState p_230745_, BlockPos p_230746_, Block p_230747_, BlockPos p_230748_, boolean p_230749_) {
        NeighborUpdater.m_230763_(this.f_230741_, p_230745_, p_230746_, p_230747_, p_230748_, p_230749_);
    }
}

