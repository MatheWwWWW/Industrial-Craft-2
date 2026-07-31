/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class GlowstoneFeature
extends Feature<NoneFeatureConfiguration> {
    public GlowstoneFeature(Codec<NoneFeatureConfiguration> p_65865_) {
        super(p_65865_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159861_) {
        WorldGenLevel $$1 = p_159861_.m_159774_();
        BlockPos $$2 = p_159861_.m_159777_();
        RandomSource $$3 = p_159861_.m_225041_();
        if (!$$1.m_46859_($$2)) {
            return false;
        }
        BlockState $$4 = $$1.m_8055_($$2.m_7494_());
        if (!($$4.m_60713_(Blocks.f_50134_) || $$4.m_60713_(Blocks.f_50137_) || $$4.m_60713_(Blocks.f_50730_))) {
            return false;
        }
        $$1.m_7731_($$2, Blocks.f_50141_.m_49966_(), 2);
        for (int $$5 = 0; $$5 < 1500; ++$$5) {
            BlockPos $$6 = $$2.m_7918_($$3.m_188503_(8) - $$3.m_188503_(8), -$$3.m_188503_(12), $$3.m_188503_(8) - $$3.m_188503_(8));
            if (!$$1.m_8055_($$6).m_60795_()) continue;
            int $$7 = 0;
            for (Direction $$8 : Direction.values()) {
                if ($$1.m_8055_($$6.m_121945_($$8)).m_60713_(Blocks.f_50141_)) {
                    ++$$7;
                }
                if ($$7 > 1) break;
            }
            if ($$7 != true) continue;
            $$1.m_7731_($$6, Blocks.f_50141_.m_49966_(), 2);
        }
        return true;
    }
}

