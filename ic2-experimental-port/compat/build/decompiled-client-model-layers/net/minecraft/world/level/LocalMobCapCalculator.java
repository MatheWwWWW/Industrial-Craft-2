/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 */
package net.minecraft.world.level;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;

public class LocalMobCapCalculator {
    private final Long2ObjectMap<List<ServerPlayer>> f_186497_ = new Long2ObjectOpenHashMap();
    private final Map<ServerPlayer, MobCounts> f_186498_ = Maps.newHashMap();
    private final ChunkMap f_186499_;

    public LocalMobCapCalculator(ChunkMap p_186501_) {
        this.f_186499_ = p_186501_;
    }

    private List<ServerPlayer> m_186507_(ChunkPos p_186508_) {
        return (List)this.f_186497_.computeIfAbsent(p_186508_.m_45588_(), p_186511_ -> this.f_186499_.m_183888_(p_186508_));
    }

    public void m_186512_(ChunkPos p_186513_, MobCategory p_186514_) {
        for (ServerPlayer $$2 : this.m_186507_(p_186513_)) {
            this.f_186498_.computeIfAbsent($$2, p_186503_ -> new MobCounts()).m_186517_(p_186514_);
        }
    }

    public boolean m_186504_(MobCategory p_186505_, ChunkPos p_186506_) {
        for (ServerPlayer $$2 : this.m_186507_(p_186506_)) {
            MobCounts $$3 = this.f_186498_.get($$2);
            if ($$3 != null && !$$3.m_186522_(p_186505_)) continue;
            return true;
        }
        return false;
    }

    static class MobCounts {
        private final Object2IntMap<MobCategory> f_186515_ = new Object2IntOpenHashMap(MobCategory.values().length);

        MobCounts() {
        }

        public void m_186517_(MobCategory p_186518_) {
            this.f_186515_.computeInt((Object)p_186518_, (p_186520_, p_186521_) -> p_186521_ == null ? 1 : p_186521_ + 1);
        }

        public boolean m_186522_(MobCategory p_186523_) {
            return this.f_186515_.getOrDefault((Object)p_186523_, 0) < p_186523_.m_21608_();
        }
    }
}

