/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EndIslandFeature
extends Feature<NoneFeatureConfiguration> {
    public EndIslandFeature(Codec<NoneFeatureConfiguration> p_65701_) {
        super(p_65701_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159717_) {
        WorldGenLevel $$1 = p_159717_.m_159774_();
        RandomSource $$2 = p_159717_.m_225041_();
        BlockPos $$3 = p_159717_.m_159777_();
        float $$4 = (float)$$2.m_188503_(3) + 4.0f;
        int $$5 = 0;
        while ($$4 > 0.5f) {
            for (int $$6 = Mth.m_14143_(-$$4); $$6 <= Mth.m_14167_($$4); ++$$6) {
                for (int $$7 = Mth.m_14143_(-$$4); $$7 <= Mth.m_14167_($$4); ++$$7) {
                    if (!((float)($$6 * $$6 + $$7 * $$7) <= ($$4 + 1.0f) * ($$4 + 1.0f))) continue;
                    this.m_5974_($$1, $$3.m_7918_($$6, $$5, $$7), Blocks.f_50259_.m_49966_());
                }
            }
            $$4 -= (float)$$2.m_188503_(2) + 0.5f;
            --$$5;
        }
        return true;
    }
}

