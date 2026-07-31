/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.chunk;

import java.io.IOException;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LevelLightEngine;

public abstract class ChunkSource
implements LightChunkGetter,
AutoCloseable {
    @Nullable
    public LevelChunk m_62227_(int p_62228_, int p_62229_, boolean p_62230_) {
        return (LevelChunk)this.m_7587_(p_62228_, p_62229_, ChunkStatus.f_62326_, p_62230_);
    }

    @Nullable
    public LevelChunk m_7131_(int p_62221_, int p_62222_) {
        return this.m_62227_(p_62221_, p_62222_, false);
    }

    @Override
    @Nullable
    public BlockGetter m_6196_(int p_62241_, int p_62242_) {
        return this.m_7587_(p_62241_, p_62242_, ChunkStatus.f_62314_, false);
    }

    public boolean m_5563_(int p_62238_, int p_62239_) {
        return this.m_7587_(p_62238_, p_62239_, ChunkStatus.f_62326_, false) != null;
    }

    @Nullable
    public abstract ChunkAccess m_7587_(int var1, int var2, ChunkStatus var3, boolean var4);

    public abstract void m_201698_(BooleanSupplier var1, boolean var2);

    public abstract String m_6754_();

    public abstract int m_8482_();

    @Override
    public void close() throws IOException {
    }

    public abstract LevelLightEngine m_7827_();

    public void m_6707_(boolean p_62236_, boolean p_62237_) {
    }

    public void m_6692_(ChunkPos p_62233_, boolean p_62234_) {
    }
}

