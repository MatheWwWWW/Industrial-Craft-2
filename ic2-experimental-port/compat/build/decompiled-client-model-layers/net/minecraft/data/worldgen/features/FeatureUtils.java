/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.features;

import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.EndFeatures;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.features.PileFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FeatureUtils {
    public static Holder<? extends ConfiguredFeature<?, ?>> m_236677_(Registry<ConfiguredFeature<?, ?>> p_236678_) {
        List<Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>>> $$1 = List.of(AquaticFeatures.f_194931_, CaveFeatures.f_194951_, EndFeatures.f_194985_, MiscOverworldFeatures.f_195024_, NetherFeatures.f_195032_, OreFeatures.f_195066_, PileFeatures.f_195099_, TreeFeatures.f_195139_, VegetationFeatures.f_195169_);
        return Util.m_214621_($$1, RandomSource.m_216327_());
    }

    private static BlockPredicate m_195008_(List<Block> p_195009_) {
        BlockPredicate $$2;
        if (!p_195009_.isEmpty()) {
            BlockPredicate $$1 = BlockPredicate.m_190404_(BlockPredicate.f_190393_, BlockPredicate.m_224771_(Direction.DOWN.m_122436_(), p_195009_));
        } else {
            $$2 = BlockPredicate.f_190393_;
        }
        return $$2;
    }

    public static RandomPatchConfiguration m_206470_(int p_206471_, Holder<PlacedFeature> p_206472_) {
        return new RandomPatchConfiguration(p_206471_, 7, 3, p_206472_);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> RandomPatchConfiguration m_206480_(F p_206481_, FC p_206482_, List<Block> p_206483_, int p_206484_) {
        return FeatureUtils.m_206470_(p_206484_, PlacementUtils.m_206498_(p_206481_, p_206482_, FeatureUtils.m_195008_(p_206483_)));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> RandomPatchConfiguration m_206476_(F p_206477_, FC p_206478_, List<Block> p_206479_) {
        return FeatureUtils.m_206480_(p_206477_, p_206478_, p_206479_, 96);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> RandomPatchConfiguration m_206473_(F p_206474_, FC p_206475_) {
        return FeatureUtils.m_206480_(p_206474_, p_206475_, List.of(), 96);
    }

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> m_206485_(String p_206486_, Feature<NoneFeatureConfiguration> p_206487_) {
        return FeatureUtils.m_206488_(p_206486_, p_206487_, FeatureConfiguration.f_67737_);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> m_206488_(String p_206489_, F p_206490_, FC p_206491_) {
        return BuiltinRegistries.m_206380_(BuiltinRegistries.f_123861_, p_206489_, new ConfiguredFeature<FC, F>(p_206490_, p_206491_));
    }
}

