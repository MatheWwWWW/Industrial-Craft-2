/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.chunk;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class RenderRegionCache {
    private final Long2ObjectMap<ChunkInfo> f_200460_ = new Long2ObjectOpenHashMap();

    @Nullable
    public RenderChunkRegion m_200465_(Level p_200466_, BlockPos p_200467_, BlockPos p_200468_, int p_200469_) {
        int $$4 = SectionPos.m_123171_(p_200467_.m_123341_() - p_200469_);
        int $$5 = SectionPos.m_123171_(p_200467_.m_123343_() - p_200469_);
        int $$6 = SectionPos.m_123171_(p_200468_.m_123341_() + p_200469_);
        int $$7 = SectionPos.m_123171_(p_200468_.m_123343_() + p_200469_);
        ChunkInfo[][] $$8 = new ChunkInfo[$$6 - $$4 + 1][$$7 - $$5 + 1];
        for (int $$9 = $$4; $$9 <= $$6; ++$$9) {
            for (int $$10 = $$5; $$10 <= $$7; ++$$10) {
                $$8[$$9 - $$4][$$10 - $$5] = (ChunkInfo)this.f_200460_.computeIfAbsent(ChunkPos.m_45589_($$9, $$10), p_200464_ -> new ChunkInfo(p_200466_.m_6325_(ChunkPos.m_45592_(p_200464_), ChunkPos.m_45602_(p_200464_))));
            }
        }
        if (RenderRegionCache.m_200470_(p_200467_, p_200468_, $$4, $$5, $$8)) {
            return null;
        }
        RenderChunk[][] $$11 = new RenderChunk[$$6 - $$4 + 1][$$7 - $$5 + 1];
        for (int $$12 = $$4; $$12 <= $$6; ++$$12) {
            for (int $$13 = $$5; $$13 <= $$7; ++$$13) {
                $$11[$$12 - $$4][$$13 - $$5] = $$8[$$12 - $$4][$$13 - $$5].m_200481_();
            }
        }
        return new RenderChunkRegion(p_200466_, $$4, $$5, $$11);
    }

    private static boolean m_200470_(BlockPos p_200471_, BlockPos p_200472_, int p_200473_, int p_200474_, ChunkInfo[][] p_200475_) {
        int $$5 = SectionPos.m_123171_(p_200471_.m_123341_());
        int $$6 = SectionPos.m_123171_(p_200471_.m_123343_());
        int $$7 = SectionPos.m_123171_(p_200472_.m_123341_());
        int $$8 = SectionPos.m_123171_(p_200472_.m_123343_());
        for (int $$9 = $$5; $$9 <= $$7; ++$$9) {
            for (int $$10 = $$6; $$10 <= $$8; ++$$10) {
                LevelChunk $$11 = p_200475_[$$9 - p_200473_][$$10 - p_200474_].m_200480_();
                if ($$11.m_5566_(p_200471_.m_123342_(), p_200472_.m_123342_())) continue;
                return false;
            }
        }
        return true;
    }

    static final class ChunkInfo {
        private final LevelChunk f_200476_;
        @Nullable
        private RenderChunk f_200477_;

        ChunkInfo(LevelChunk p_200479_) {
            this.f_200476_ = p_200479_;
        }

        public LevelChunk m_200480_() {
            return this.f_200476_;
        }

        public RenderChunk m_200481_() {
            if (this.f_200477_ == null) {
                this.f_200477_ = new RenderChunk(this.f_200476_);
            }
            return this.f_200477_;
        }
    }
}

