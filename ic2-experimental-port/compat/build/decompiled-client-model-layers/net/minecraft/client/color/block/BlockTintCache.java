/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.color.block;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.ToIntFunction;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;

public class BlockTintCache {
    private static final int f_168641_ = 256;
    private final ThreadLocal<LatestCacheInfo> f_92650_ = ThreadLocal.withInitial(LatestCacheInfo::new);
    private final Long2ObjectLinkedOpenHashMap<CacheData> f_92651_ = new Long2ObjectLinkedOpenHashMap(256, 0.25f);
    private final ReentrantReadWriteLock f_92652_ = new ReentrantReadWriteLock();
    private final ToIntFunction<BlockPos> f_193809_;

    public BlockTintCache(ToIntFunction<BlockPos> p_193811_) {
        this.f_193809_ = p_193811_;
    }

    public int m_193812_(BlockPos p_193813_) {
        int $$9;
        int $$1 = SectionPos.m_123171_(p_193813_.m_123341_());
        int $$2 = SectionPos.m_123171_(p_193813_.m_123343_());
        LatestCacheInfo $$3 = this.f_92650_.get();
        if ($$3.f_92665_ != $$1 || $$3.f_92666_ != $$2 || $$3.f_92667_ == null) {
            $$3.f_92665_ = $$1;
            $$3.f_92666_ = $$2;
            $$3.f_92667_ = this.m_193814_($$1, $$2);
        }
        int[] $$4 = $$3.f_92667_.m_193823_(p_193813_.m_123342_());
        int $$5 = p_193813_.m_123341_() & 0xF;
        int $$6 = p_193813_.m_123343_() & 0xF;
        int $$7 = $$6 << 4 | $$5;
        int $$8 = $$4[$$7];
        if ($$8 != -1) {
            return $$8;
        }
        $$4[$$7] = $$9 = this.f_193809_.applyAsInt(p_193813_);
        return $$9;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_92655_(int p_92656_, int p_92657_) {
        try {
            this.f_92652_.writeLock().lock();
            for (int $$2 = -1; $$2 <= 1; ++$$2) {
                for (int $$3 = -1; $$3 <= 1; ++$$3) {
                    long $$4 = ChunkPos.m_45589_(p_92656_ + $$2, p_92657_ + $$3);
                    this.f_92651_.remove($$4);
                }
            }
        }
        finally {
            this.f_92652_.writeLock().unlock();
        }
    }

    public void m_92654_() {
        try {
            this.f_92652_.writeLock().lock();
            this.f_92651_.clear();
        }
        finally {
            this.f_92652_.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private CacheData m_193814_(int p_193815_, int p_193816_) {
        long $$2 = ChunkPos.m_45589_(p_193815_, p_193816_);
        this.f_92652_.readLock().lock();
        try {
            CacheData $$3 = (CacheData)this.f_92651_.get($$2);
            if ($$3 != null) {
                CacheData cacheData = $$3;
                return cacheData;
            }
        }
        finally {
            this.f_92652_.readLock().unlock();
        }
        this.f_92652_.writeLock().lock();
        try {
            CacheData $$4 = (CacheData)this.f_92651_.get($$2);
            if ($$4 != null) {
                CacheData cacheData = $$4;
                return cacheData;
            }
            CacheData $$5 = new CacheData();
            if (this.f_92651_.size() >= 256) {
                this.f_92651_.removeFirst();
            }
            this.f_92651_.put($$2, (Object)$$5);
            CacheData cacheData = $$5;
            return cacheData;
        }
        finally {
            this.f_92652_.writeLock().unlock();
        }
    }

    static class LatestCacheInfo {
        public int f_92665_ = Integer.MIN_VALUE;
        public int f_92666_ = Integer.MIN_VALUE;
        @Nullable
        CacheData f_92667_;

        private LatestCacheInfo() {
        }
    }

    static class CacheData {
        private final Int2ObjectArrayMap<int[]> f_193817_ = new Int2ObjectArrayMap(16);
        private final ReentrantReadWriteLock f_193818_ = new ReentrantReadWriteLock();
        private static final int f_193819_ = Mth.m_144944_(16);

        CacheData() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public int[] m_193823_(int p_193824_) {
            this.f_193818_.readLock().lock();
            try {
                int[] $$1 = (int[])this.f_193817_.get(p_193824_);
                if ($$1 != null) {
                    int[] nArray = $$1;
                    return nArray;
                }
            }
            finally {
                this.f_193818_.readLock().unlock();
            }
            this.f_193818_.writeLock().lock();
            try {
                int[] nArray = (int[])this.f_193817_.computeIfAbsent(p_193824_, p_193826_ -> this.m_193822_());
                return nArray;
            }
            finally {
                this.f_193818_.writeLock().unlock();
            }
        }

        private int[] m_193822_() {
            int[] $$0 = new int[f_193819_];
            Arrays.fill($$0, -1);
            return $$0;
        }
    }
}

