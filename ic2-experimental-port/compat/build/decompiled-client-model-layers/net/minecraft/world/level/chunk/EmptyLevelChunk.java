/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.chunk;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class EmptyLevelChunk
extends LevelChunk {
    private final Holder<Biome> f_204420_;

    public EmptyLevelChunk(Level p_204422_, ChunkPos p_204423_, Holder<Biome> p_204424_) {
        super(p_204422_, p_204423_);
        this.f_204420_ = p_204424_;
    }

    @Override
    public BlockState m_8055_(BlockPos p_62625_) {
        return Blocks.f_50626_.m_49966_();
    }

    @Override
    @Nullable
    public BlockState m_6978_(BlockPos p_62605_, BlockState p_62606_, boolean p_62607_) {
        return null;
    }

    @Override
    public FluidState m_6425_(BlockPos p_62621_) {
        return Fluids.f_76191_.m_76145_();
    }

    @Override
    public int m_7146_(BlockPos p_62628_) {
        return 0;
    }

    @Override
    @Nullable
    public BlockEntity m_5685_(BlockPos p_62609_, LevelChunk.EntityCreationType p_62610_) {
        return null;
    }

    @Override
    public void m_142170_(BlockEntity p_156346_) {
    }

    @Override
    public void m_142169_(BlockEntity p_156344_) {
    }

    @Override
    public void m_8114_(BlockPos p_62623_) {
    }

    @Override
    public boolean m_6430_() {
        return true;
    }

    @Override
    public boolean m_5566_(int p_62587_, int p_62588_) {
        return true;
    }

    @Override
    public ChunkHolder.FullChunkStatus m_6708_() {
        return ChunkHolder.FullChunkStatus.BORDER;
    }

    @Override
    public Holder<Biome> m_203495_(int p_204426_, int p_204427_, int p_204428_) {
        return this.f_204420_;
    }
}

