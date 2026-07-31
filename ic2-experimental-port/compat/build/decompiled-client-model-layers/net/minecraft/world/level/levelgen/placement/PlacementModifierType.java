/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CarvingMaskPlacement;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;

public interface PlacementModifierType<P extends PlacementModifier> {
    public static final PlacementModifierType<BlockPredicateFilter> f_191848_ = PlacementModifierType.m_191866_("block_predicate_filter", BlockPredicateFilter.f_191569_);
    public static final PlacementModifierType<RarityFilter> f_191849_ = PlacementModifierType.m_191866_("rarity_filter", RarityFilter.f_191895_);
    public static final PlacementModifierType<SurfaceRelativeThresholdFilter> f_191850_ = PlacementModifierType.m_191866_("surface_relative_threshold_filter", SurfaceRelativeThresholdFilter.f_191919_);
    public static final PlacementModifierType<SurfaceWaterDepthFilter> f_191851_ = PlacementModifierType.m_191866_("surface_water_depth_filter", SurfaceWaterDepthFilter.f_191945_);
    public static final PlacementModifierType<BiomeFilter> f_191852_ = PlacementModifierType.m_191866_("biome", BiomeFilter.f_191557_);
    public static final PlacementModifierType<CountPlacement> f_191853_ = PlacementModifierType.m_191866_("count", CountPlacement.f_191623_);
    public static final PlacementModifierType<NoiseBasedCountPlacement> f_191854_ = PlacementModifierType.m_191866_("noise_based_count", NoiseBasedCountPlacement.f_191722_);
    public static final PlacementModifierType<NoiseThresholdCountPlacement> f_191855_ = PlacementModifierType.m_191866_("noise_threshold_count", NoiseThresholdCountPlacement.f_191747_);
    public static final PlacementModifierType<CountOnEveryLayerPlacement> f_191856_ = PlacementModifierType.m_191866_("count_on_every_layer", CountOnEveryLayerPlacement.f_191599_);
    public static final PlacementModifierType<EnvironmentScanPlacement> f_191857_ = PlacementModifierType.m_191866_("environment_scan", EnvironmentScanPlacement.f_191638_);
    public static final PlacementModifierType<HeightmapPlacement> f_191858_ = PlacementModifierType.m_191866_("heightmap", HeightmapPlacement.f_191695_);
    public static final PlacementModifierType<HeightRangePlacement> f_191859_ = PlacementModifierType.m_191866_("height_range", HeightRangePlacement.f_191673_);
    public static final PlacementModifierType<InSquarePlacement> f_191860_ = PlacementModifierType.m_191866_("in_square", InSquarePlacement.f_191711_);
    public static final PlacementModifierType<RandomOffsetPlacement> f_191861_ = PlacementModifierType.m_191866_("random_offset", RandomOffsetPlacement.f_191870_);
    public static final PlacementModifierType<CarvingMaskPlacement> f_191862_ = PlacementModifierType.m_191866_("carving_mask", CarvingMaskPlacement.f_191585_);

    public Codec<P> m_191869_();

    private static <P extends PlacementModifier> PlacementModifierType<P> m_191866_(String p_191867_, Codec<P> p_191868_) {
        return Registry.m_122961_(Registry.f_194570_, p_191867_, () -> p_191868_);
    }
}

