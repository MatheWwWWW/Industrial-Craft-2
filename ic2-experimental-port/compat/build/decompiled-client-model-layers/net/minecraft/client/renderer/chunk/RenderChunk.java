/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.chunk;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.EmptyLevelChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.levelgen.DebugLevelSource;

class RenderChunk {
    private final Map<BlockPos, BlockEntity> f_200441_;
    @Nullable
    private final List<PalettedContainer<BlockState>> f_200442_;
    private final boolean f_200443_;
    private final LevelChunk f_200444_;

    RenderChunk(LevelChunk p_200446_) {
        this.f_200444_ = p_200446_;
        this.f_200443_ = p_200446_.m_62953_().m_46659_();
        this.f_200441_ = ImmutableMap.copyOf(p_200446_.m_62954_());
        if (p_200446_ instanceof EmptyLevelChunk) {
            this.f_200442_ = null;
        } else {
            LevelChunkSection[] $$1 = p_200446_.m_7103_();
            this.f_200442_ = new ArrayList<PalettedContainer<BlockState>>($$1.length);
            for (LevelChunkSection $$2 : $$1) {
                this.f_200442_.add($$2.m_188008_() ? null : $$2.m_63019_().m_199931_());
            }
        }
    }

    @Nullable
    public BlockEntity m_200451_(BlockPos p_200452_) {
        return this.f_200441_.get(p_200452_);
    }

    public BlockState m_200453_(BlockPos p_200454_) {
        int $$1 = p_200454_.m_123341_();
        int $$2 = p_200454_.m_123342_();
        int $$3 = p_200454_.m_123343_();
        if (this.f_200443_) {
            BlockState $$4 = null;
            if ($$2 == 60) {
                $$4 = Blocks.f_50375_.m_49966_();
            }
            if ($$2 == 70) {
                $$4 = DebugLevelSource.m_64148_($$1, $$3);
            }
            return $$4 == null ? Blocks.f_50016_.m_49966_() : $$4;
        }
        if (this.f_200442_ == null) {
            return Blocks.f_50016_.m_49966_();
        }
        try {
            PalettedContainer<BlockState> $$6;
            int $$5 = this.f_200444_.m_151564_($$2);
            if ($$5 >= 0 && $$5 < this.f_200442_.size() && ($$6 = this.f_200442_.get($$5)) != null) {
                return $$6.m_63087_($$1 & 0xF, $$2 & 0xF, $$3 & 0xF);
            }
            return Blocks.f_50016_.m_49966_();
        }
        catch (Throwable $$7) {
            CrashReport $$8 = CrashReport.m_127521_($$7, "Getting block state");
            CrashReportCategory $$9 = $$8.m_127514_("Block being got");
            $$9.m_128165_("Location", () -> CrashReportCategory.m_178942_(this.f_200444_, $$1, $$2, $$3));
            throw new ReportedException($$8);
        }
    }
}

