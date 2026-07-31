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
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class SimpleRandomSelectorFeature
extends Feature<SimpleRandomFeatureConfiguration> {
    public SimpleRandomSelectorFeature(Codec<SimpleRandomFeatureConfiguration> p_66822_) {
        super(p_66822_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<SimpleRandomFeatureConfiguration> p_160343_) {
        RandomSource $$1 = p_160343_.m_225041_();
        SimpleRandomFeatureConfiguration $$2 = p_160343_.m_159778_();
        WorldGenLevel $$3 = p_160343_.m_159774_();
        BlockPos $$4 = p_160343_.m_159777_();
        ChunkGenerator $$5 = p_160343_.m_159775_();
        int $$6 = $$1.m_188503_($$2.f_68090_.m_203632_());
        PlacedFeature $$7 = $$2.f_68090_.m_203662_($$6).m_203334_();
        return $$7.m_226357_($$3, $$5, $$1, $$4);
    }
}

