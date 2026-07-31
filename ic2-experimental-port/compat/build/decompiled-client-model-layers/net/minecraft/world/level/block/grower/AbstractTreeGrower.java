/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.grower;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public abstract class AbstractTreeGrower {
    @Nullable
    protected abstract Holder<? extends ConfiguredFeature<?, ?>> m_213888_(RandomSource var1, boolean var2);

    public boolean m_213817_(ServerLevel p_222905_, ChunkGenerator p_222906_, BlockPos p_222907_, BlockState p_222908_, RandomSource p_222909_) {
        Holder<ConfiguredFeature<?, ?>> $$5 = this.m_213888_(p_222909_, this.m_60011_(p_222905_, p_222907_));
        if ($$5 == null) {
            return false;
        }
        ConfiguredFeature<?, ?> $$6 = $$5.m_203334_();
        BlockState $$7 = p_222905_.m_6425_(p_222907_).m_76188_();
        p_222905_.m_7731_(p_222907_, $$7, 4);
        if ($$6.m_224953_(p_222905_, p_222906_, p_222909_, p_222907_)) {
            if (p_222905_.m_8055_(p_222907_) == $$7) {
                p_222905_.m_7260_(p_222907_, p_222908_, $$7, 2);
            }
            return true;
        }
        p_222905_.m_7731_(p_222907_, p_222908_, 4);
        return false;
    }

    private boolean m_60011_(LevelAccessor p_60012_, BlockPos p_60013_) {
        for (BlockPos $$2 : BlockPos.MutableBlockPos.m_121940_(p_60013_.m_7495_().m_122013_(2).m_122025_(2), p_60013_.m_7494_().m_122020_(2).m_122030_(2))) {
            if (!p_60012_.m_8055_($$2).m_204336_(BlockTags.f_13041_)) continue;
            return true;
        }
        return false;
    }
}

