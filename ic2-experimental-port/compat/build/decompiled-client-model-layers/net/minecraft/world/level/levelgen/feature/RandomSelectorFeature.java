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
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;

public class RandomSelectorFeature
extends Feature<RandomFeatureConfiguration> {
    public RandomSelectorFeature(Codec<RandomFeatureConfiguration> p_66619_) {
        super(p_66619_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<RandomFeatureConfiguration> p_160212_) {
        RandomFeatureConfiguration $$1 = p_160212_.m_159778_();
        RandomSource $$2 = p_160212_.m_225041_();
        WorldGenLevel $$3 = p_160212_.m_159774_();
        ChunkGenerator $$4 = p_160212_.m_159775_();
        BlockPos $$5 = p_160212_.m_159777_();
        for (WeightedPlacedFeature $$6 : $$1.f_67882_) {
            if (!($$2.m_188501_() < $$6.f_191173_)) continue;
            return $$6.m_225367_($$3, $$4, $$2, $$5);
        }
        return $$1.f_67883_.m_203334_().m_226357_($$3, $$4, $$2, $$5);
    }
}

