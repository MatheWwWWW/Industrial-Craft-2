/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.features;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RotatedBlockProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class PileFeatures {
    public static final Holder<ConfiguredFeature<BlockPileConfiguration, ?>> f_195099_ = FeatureUtils.m_206488_("pile_hay", Feature.f_65764_, new BlockPileConfiguration(new RotatedBlockProvider(Blocks.f_50335_)));
    public static final Holder<ConfiguredFeature<BlockPileConfiguration, ?>> f_195100_ = FeatureUtils.m_206488_("pile_melon", Feature.f_65764_, new BlockPileConfiguration(BlockStateProvider.m_191382_(Blocks.f_50186_)));
    public static final Holder<ConfiguredFeature<BlockPileConfiguration, ?>> f_195101_ = FeatureUtils.m_206488_("pile_snow", Feature.f_65764_, new BlockPileConfiguration(BlockStateProvider.m_191382_(Blocks.f_50125_)));
    public static final Holder<ConfiguredFeature<BlockPileConfiguration, ?>> f_195102_ = FeatureUtils.m_206488_("pile_ice", Feature.f_65764_, new BlockPileConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.m_146263_().m_146271_(Blocks.f_50568_.m_49966_(), 1).m_146271_(Blocks.f_50354_.m_49966_(), 5))));
    public static final Holder<ConfiguredFeature<BlockPileConfiguration, ?>> f_195103_ = FeatureUtils.m_206488_("pile_pumpkin", Feature.f_65764_, new BlockPileConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.m_146263_().m_146271_(Blocks.f_50133_.m_49966_(), 19).m_146271_(Blocks.f_50144_.m_49966_(), 1))));
}

