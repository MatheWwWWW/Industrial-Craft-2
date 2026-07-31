/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.features;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class AquaticFeatures {
    public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> f_194925_ = FeatureUtils.m_206488_("seagrass_short", Feature.f_65735_, new ProbabilityFeatureConfiguration(0.3f));
    public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> f_194926_ = FeatureUtils.m_206488_("seagrass_slightly_less_short", Feature.f_65735_, new ProbabilityFeatureConfiguration(0.4f));
    public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> f_194927_ = FeatureUtils.m_206488_("seagrass_mid", Feature.f_65735_, new ProbabilityFeatureConfiguration(0.6f));
    public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> f_194928_ = FeatureUtils.m_206488_("seagrass_tall", Feature.f_65735_, new ProbabilityFeatureConfiguration(0.8f));
    public static final Holder<ConfiguredFeature<CountConfiguration, ?>> f_194929_ = FeatureUtils.m_206488_("sea_pickle", Feature.f_65740_, new CountConfiguration(20));
    public static final Holder<ConfiguredFeature<SimpleBlockConfiguration, ?>> f_194930_ = FeatureUtils.m_206488_("seagrass_simple", Feature.f_65741_, new SimpleBlockConfiguration(BlockStateProvider.m_191382_(Blocks.f_50037_)));
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> f_194931_ = FeatureUtils.m_206485_("kelp", Feature.f_65736_);
    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> f_194932_ = FeatureUtils.m_206488_("warm_ocean_vegetation", Feature.f_65755_, new SimpleRandomFeatureConfiguration(HolderSet.m_205809_(PlacementUtils.m_206502_(Feature.f_65737_, FeatureConfiguration.f_67737_, new PlacementModifier[0]), PlacementUtils.m_206502_(Feature.f_65739_, FeatureConfiguration.f_67737_, new PlacementModifier[0]), PlacementUtils.m_206502_(Feature.f_65738_, FeatureConfiguration.f_67737_, new PlacementModifier[0]))));
}

