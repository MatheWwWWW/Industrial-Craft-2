/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockSource
extends Position {
    @Override
    public double m_7096_();

    @Override
    public double m_7098_();

    @Override
    public double m_7094_();

    public BlockPos m_7961_();

    public BlockState m_6414_();

    public <T extends BlockEntity> T m_8118_();

    public ServerLevel m_7727_();
}

