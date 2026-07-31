/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;

public class BulkSectionAccess
implements AutoCloseable {
    private final LevelAccessor f_156098_;
    private final Long2ObjectMap<LevelChunkSection> f_156099_ = new Long2ObjectOpenHashMap();
    @Nullable
    private LevelChunkSection f_156100_;
    private long f_156101_;

    public BulkSectionAccess(LevelAccessor p_156103_) {
        this.f_156098_ = p_156103_;
    }

    @Nullable
    public LevelChunkSection m_156104_(BlockPos p_156105_) {
        int $$1 = this.f_156098_.m_151564_(p_156105_.m_123342_());
        if ($$1 < 0 || $$1 >= this.f_156098_.m_151559_()) {
            return null;
        }
        long $$2 = SectionPos.m_175568_(p_156105_);
        if (this.f_156100_ == null || this.f_156101_ != $$2) {
            this.f_156100_ = (LevelChunkSection)this.f_156099_.computeIfAbsent($$2, p_156109_ -> {
                ChunkAccess $$3 = this.f_156098_.m_6325_(SectionPos.m_123171_(p_156105_.m_123341_()), SectionPos.m_123171_(p_156105_.m_123343_()));
                LevelChunkSection $$4 = $$3.m_183278_($$1);
                $$4.m_62981_();
                return $$4;
            });
            this.f_156101_ = $$2;
        }
        return this.f_156100_;
    }

    public BlockState m_156110_(BlockPos p_156111_) {
        LevelChunkSection $$1 = this.m_156104_(p_156111_);
        if ($$1 == null) {
            return Blocks.f_50016_.m_49966_();
        }
        int $$2 = SectionPos.m_123207_(p_156111_.m_123341_());
        int $$3 = SectionPos.m_123207_(p_156111_.m_123342_());
        int $$4 = SectionPos.m_123207_(p_156111_.m_123343_());
        return $$1.m_62982_($$2, $$3, $$4);
    }

    @Override
    public void close() {
        for (LevelChunkSection $$0 : this.f_156099_.values()) {
            $$0.m_63006_();
        }
    }
}

