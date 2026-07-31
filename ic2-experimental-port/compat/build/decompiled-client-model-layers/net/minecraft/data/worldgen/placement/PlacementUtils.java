/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.placement;

import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.data.worldgen.placement.VillagePlacements;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class PlacementUtils {
    public static final PlacementModifier f_195352_ = HeightmapPlacement.m_191702_(Heightmap.Types.MOTION_BLOCKING);
    public static final PlacementModifier f_195353_ = HeightmapPlacement.m_191702_(Heightmap.Types.OCEAN_FLOOR_WG);
    public static final PlacementModifier f_195354_ = HeightmapPlacement.m_191702_(Heightmap.Types.WORLD_SURFACE_WG);
    public static final PlacementModifier f_195355_ = HeightmapPlacement.m_191702_(Heightmap.Types.OCEAN_FLOOR);
    public static final PlacementModifier f_195356_ = HeightRangePlacement.m_191680_(VerticalAnchor.m_158921_(), VerticalAnchor.m_158929_());
    public static final PlacementModifier f_195357_ = HeightRangePlacement.m_191680_(VerticalAnchor.m_158930_(10), VerticalAnchor.m_158935_(10));
    public static final PlacementModifier f_195358_ = HeightRangePlacement.m_191680_(VerticalAnchor.m_158930_(8), VerticalAnchor.m_158935_(8));
    public static final PlacementModifier f_195359_ = HeightRangePlacement.m_191680_(VerticalAnchor.m_158930_(4), VerticalAnchor.m_158935_(4));
    public static final PlacementModifier f_195360_ = HeightRangePlacement.m_191680_(VerticalAnchor.m_158921_(), VerticalAnchor.m_158922_(256));

    public static Holder<PlacedFeature> m_236769_(Registry<PlacedFeature> p_236770_) {
        List<Holder<PlacedFeature>> $$1 = List.of(AquaticPlacements.f_195228_, CavePlacements.f_195245_, EndPlacements.f_195256_, MiscOverworldPlacements.f_195265_, NetherPlacements.f_195282_, OrePlacements.f_195310_, TreePlacements.f_195377_, VegetationPlacements.f_195445_, VillagePlacements.f_197413_);
        return Util.m_214621_($$1, RandomSource.m_216327_());
    }

    public static Holder<PlacedFeature> m_206509_(String p_206510_, Holder<? extends ConfiguredFeature<?, ?>> p_206511_, List<PlacementModifier> p_206512_) {
        return BuiltinRegistries.m_206396_(BuiltinRegistries.f_194653_, p_206510_, new PlacedFeature(Holder.m_205706_(p_206511_), List.copyOf(p_206512_)));
    }

    public static Holder<PlacedFeature> m_206513_(String p_206514_, Holder<? extends ConfiguredFeature<?, ?>> p_206515_, PlacementModifier ... p_206516_) {
        return PlacementUtils.m_206509_(p_206514_, p_206515_, List.of(p_206516_));
    }

    public static PlacementModifier m_195364_(int p_195365_, float p_195366_, int p_195367_) {
        float $$3 = 1.0f / p_195366_;
        if (Math.abs($$3 - (float)((int)$$3)) > 1.0E-5f) {
            throw new IllegalStateException("Chance data cannot be represented as list weight");
        }
        SimpleWeightedRandomList<IntProvider> $$4 = SimpleWeightedRandomList.m_146263_().m_146271_(ConstantInt.m_146483_(p_195365_), (int)$$3 - 1).m_146271_(ConstantInt.m_146483_(p_195365_ + p_195367_), 1).m_146270_();
        return CountPlacement.m_191630_(new WeightedListInt($$4));
    }

    public static PlacementFilter m_206517_() {
        return BlockPredicateFilter.m_191576_(BlockPredicate.f_190393_);
    }

    public static BlockPredicateFilter m_206493_(Block p_206494_) {
        return BlockPredicateFilter.m_191576_(BlockPredicate.m_190399_(p_206494_.m_49966_(), BlockPos.f_121853_));
    }

    public static Holder<PlacedFeature> m_206506_(Holder<? extends ConfiguredFeature<?, ?>> p_206507_, PlacementModifier ... p_206508_) {
        return Holder.m_205709_(new PlacedFeature(Holder.m_205706_(p_206507_), List.of(p_206508_)));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<PlacedFeature> m_206502_(F p_206503_, FC p_206504_, PlacementModifier ... p_206505_) {
        return PlacementUtils.m_206506_(Holder.m_205709_(new ConfiguredFeature<FC, F>(p_206503_, p_206504_)), p_206505_);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<PlacedFeature> m_206495_(F p_206496_, FC p_206497_) {
        return PlacementUtils.m_206498_(p_206496_, p_206497_, BlockPredicate.f_190393_);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<PlacedFeature> m_206498_(F p_206499_, FC p_206500_, BlockPredicate p_206501_) {
        return PlacementUtils.m_206502_(p_206499_, p_206500_, BlockPredicateFilter.m_191576_(p_206501_));
    }
}

