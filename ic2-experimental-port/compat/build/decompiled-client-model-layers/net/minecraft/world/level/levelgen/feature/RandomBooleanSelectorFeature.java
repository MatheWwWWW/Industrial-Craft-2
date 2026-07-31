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
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;

public class RandomBooleanSelectorFeature
extends Feature<RandomBooleanFeatureConfiguration> {
    public RandomBooleanSelectorFeature(Codec<RandomBooleanFeatureConfiguration> p_66591_) {
        super(p_66591_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<RandomBooleanFeatureConfiguration> p_160208_) {
        RandomSource $$1 = p_160208_.m_225041_();
        RandomBooleanFeatureConfiguration $$2 = p_160208_.m_159778_();
        WorldGenLevel $$3 = p_160208_.m_159774_();
        ChunkGenerator $$4 = p_160208_.m_159775_();
        BlockPos $$5 = p_160208_.m_159777_();
        boolean $$6 = $$1.m_188499_();
        return ($$6 ? $$2.f_67868_ : $$2.f_67869_).m_203334_().m_226357_($$3, $$4, $$1, $$5);
    }
}

