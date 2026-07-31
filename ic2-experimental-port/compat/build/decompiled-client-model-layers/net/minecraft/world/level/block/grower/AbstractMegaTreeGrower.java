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
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public abstract class AbstractMegaTreeGrower
extends AbstractTreeGrower {
    @Override
    public boolean m_213817_(ServerLevel p_222891_, ChunkGenerator p_222892_, BlockPos p_222893_, BlockState p_222894_, RandomSource p_222895_) {
        for (int $$5 = 0; $$5 >= -1; --$$5) {
            for (int $$6 = 0; $$6 >= -1; --$$6) {
                if (!AbstractMegaTreeGrower.m_59998_(p_222894_, p_222891_, p_222893_, $$5, $$6)) continue;
                return this.m_222896_(p_222891_, p_222892_, p_222893_, p_222894_, p_222895_, $$5, $$6);
            }
        }
        return super.m_213817_(p_222891_, p_222892_, p_222893_, p_222894_, p_222895_);
    }

    @Nullable
    protected abstract Holder<? extends ConfiguredFeature<?, ?>> m_213566_(RandomSource var1);

    public boolean m_222896_(ServerLevel p_222897_, ChunkGenerator p_222898_, BlockPos p_222899_, BlockState p_222900_, RandomSource p_222901_, int p_222902_, int p_222903_) {
        Holder<ConfiguredFeature<?, ?>> $$7 = this.m_213566_(p_222901_);
        if ($$7 == null) {
            return false;
        }
        ConfiguredFeature<?, ?> $$8 = $$7.m_203334_();
        BlockState $$9 = Blocks.f_50016_.m_49966_();
        p_222897_.m_7731_(p_222899_.m_7918_(p_222902_, 0, p_222903_), $$9, 4);
        p_222897_.m_7731_(p_222899_.m_7918_(p_222902_ + 1, 0, p_222903_), $$9, 4);
        p_222897_.m_7731_(p_222899_.m_7918_(p_222902_, 0, p_222903_ + 1), $$9, 4);
        p_222897_.m_7731_(p_222899_.m_7918_(p_222902_ + 1, 0, p_222903_ + 1), $$9, 4);
        if ($$8.m_224953_(p_222897_, p_222898_, p_222901_, p_222899_.m_7918_(p_222902_, 0, p_222903_))) {
            return true;
        }
        p_222897_.m_7731_(p_222899_.m_7918_(p_222902_, 0, p_222903_), p_222900_, 4);
        p_222897_.m_7731_(p_222899_.m_7918_(p_222902_ + 1, 0, p_222903_), p_222900_, 4);
        p_222897_.m_7731_(p_222899_.m_7918_(p_222902_, 0, p_222903_ + 1), p_222900_, 4);
        p_222897_.m_7731_(p_222899_.m_7918_(p_222902_ + 1, 0, p_222903_ + 1), p_222900_, 4);
        return false;
    }

    public static boolean m_59998_(BlockState p_59999_, BlockGetter p_60000_, BlockPos p_60001_, int p_60002_, int p_60003_) {
        Block $$5 = p_59999_.m_60734_();
        return p_60000_.m_8055_(p_60001_.m_7918_(p_60002_, 0, p_60003_)).m_60713_($$5) && p_60000_.m_8055_(p_60001_.m_7918_(p_60002_ + 1, 0, p_60003_)).m_60713_($$5) && p_60000_.m_8055_(p_60001_.m_7918_(p_60002_, 0, p_60003_ + 1)).m_60713_($$5) && p_60000_.m_8055_(p_60001_.m_7918_(p_60002_ + 1, 0, p_60003_ + 1)).m_60713_($$5);
    }
}

