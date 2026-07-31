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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ChorusPlantFeature
extends Feature<NoneFeatureConfiguration> {
    public ChorusPlantFeature(Codec<NoneFeatureConfiguration> p_65360_) {
        super(p_65360_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159521_) {
        WorldGenLevel $$1 = p_159521_.m_159774_();
        BlockPos $$2 = p_159521_.m_159777_();
        RandomSource $$3 = p_159521_.m_225041_();
        if ($$1.m_46859_($$2) && $$1.m_8055_($$2.m_7495_()).m_60713_(Blocks.f_50259_)) {
            ChorusFlowerBlock.m_220962_($$1, $$2, $$3, 8);
            return true;
        }
        return false;
    }
}

