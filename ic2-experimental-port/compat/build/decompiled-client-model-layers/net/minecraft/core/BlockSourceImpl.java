/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockSourceImpl
implements BlockSource {
    private final ServerLevel f_122210_;
    private final BlockPos f_122211_;

    public BlockSourceImpl(ServerLevel p_122213_, BlockPos p_122214_) {
        this.f_122210_ = p_122213_;
        this.f_122211_ = p_122214_;
    }

    @Override
    public ServerLevel m_7727_() {
        return this.f_122210_;
    }

    @Override
    public double m_7096_() {
        return (double)this.f_122211_.m_123341_() + 0.5;
    }

    @Override
    public double m_7098_() {
        return (double)this.f_122211_.m_123342_() + 0.5;
    }

    @Override
    public double m_7094_() {
        return (double)this.f_122211_.m_123343_() + 0.5;
    }

    @Override
    public BlockPos m_7961_() {
        return this.f_122211_;
    }

    @Override
    public BlockState m_6414_() {
        return this.f_122210_.m_8055_(this.f_122211_);
    }

    @Override
    public <T extends BlockEntity> T m_8118_() {
        return (T)this.f_122210_.m_7702_(this.f_122211_);
    }
}

