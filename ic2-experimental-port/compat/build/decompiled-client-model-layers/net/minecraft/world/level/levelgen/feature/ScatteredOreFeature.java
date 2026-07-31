/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class ScatteredOreFeature
extends Feature<OreConfiguration> {
    private static final int f_160302_ = 7;

    ScatteredOreFeature(Codec<OreConfiguration> p_160304_) {
        super(p_160304_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<OreConfiguration> p_160306_) {
        WorldGenLevel $$1 = p_160306_.m_159774_();
        RandomSource $$2 = p_160306_.m_225041_();
        OreConfiguration $$3 = p_160306_.m_159778_();
        BlockPos $$4 = p_160306_.m_159777_();
        int $$5 = $$2.m_188503_($$3.f_67839_ + 1);
        BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
        block0: for (int $$7 = 0; $$7 < $$5; ++$$7) {
            this.m_225231_($$6, $$2, $$4, Math.min($$7, 7));
            BlockState $$8 = $$1.m_8055_($$6);
            for (OreConfiguration.TargetBlockState $$9 : $$3.f_161005_) {
                if (!OreFeature.m_225186_($$8, $$1::m_8055_, $$2, $$3, $$9, $$6)) continue;
                $$1.m_7731_($$6, $$9.f_161033_, 2);
                continue block0;
            }
        }
        return true;
    }

    private void m_225231_(BlockPos.MutableBlockPos p_225232_, RandomSource p_225233_, BlockPos p_225234_, int p_225235_) {
        int $$4 = this.m_225228_(p_225233_, p_225235_);
        int $$5 = this.m_225228_(p_225233_, p_225235_);
        int $$6 = this.m_225228_(p_225233_, p_225235_);
        p_225232_.m_122154_(p_225234_, $$4, $$5, $$6);
    }

    private int m_225228_(RandomSource p_225229_, int p_225230_) {
        return Math.round((p_225229_.m_188501_() - p_225229_.m_188501_()) * (float)p_225230_);
    }
}

