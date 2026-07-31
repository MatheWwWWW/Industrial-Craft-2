/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.placement;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CarvingMaskPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class AquaticPlacements {
    public static final Holder<PlacedFeature> f_195218_ = PlacementUtils.m_206509_("seagrass_warm", AquaticFeatures.f_194925_, AquaticPlacements.m_195233_(80));
    public static final Holder<PlacedFeature> f_195219_ = PlacementUtils.m_206509_("seagrass_normal", AquaticFeatures.f_194925_, AquaticPlacements.m_195233_(48));
    public static final Holder<PlacedFeature> f_195220_ = PlacementUtils.m_206509_("seagrass_cold", AquaticFeatures.f_194925_, AquaticPlacements.m_195233_(32));
    public static final Holder<PlacedFeature> f_195221_ = PlacementUtils.m_206509_("seagrass_river", AquaticFeatures.f_194926_, AquaticPlacements.m_195233_(48));
    public static final Holder<PlacedFeature> f_195222_ = PlacementUtils.m_206509_("seagrass_swamp", AquaticFeatures.f_194927_, AquaticPlacements.m_195233_(64));
    public static final Holder<PlacedFeature> f_195223_ = PlacementUtils.m_206509_("seagrass_deep_warm", AquaticFeatures.f_194928_, AquaticPlacements.m_195233_(80));
    public static final Holder<PlacedFeature> f_195224_ = PlacementUtils.m_206509_("seagrass_deep", AquaticFeatures.f_194928_, AquaticPlacements.m_195233_(48));
    public static final Holder<PlacedFeature> f_195225_ = PlacementUtils.m_206509_("seagrass_deep_cold", AquaticFeatures.f_194928_, AquaticPlacements.m_195233_(40));
    public static final Holder<PlacedFeature> f_195226_ = PlacementUtils.m_206513_("seagrass_simple", AquaticFeatures.f_194930_, CarvingMaskPlacement.m_191590_(GenerationStep.Carving.LIQUID), RarityFilter.m_191900_(10), BlockPredicateFilter.m_191576_(BlockPredicate.m_190417_(BlockPredicate.m_224774_(Direction.DOWN.m_122436_(), Blocks.f_50069_), BlockPredicate.m_224774_(BlockPos.f_121853_, Blocks.f_49990_), BlockPredicate.m_224774_(Direction.UP.m_122436_(), Blocks.f_49990_))), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195227_ = PlacementUtils.m_206513_("sea_pickle", AquaticFeatures.f_194929_, RarityFilter.m_191900_(16), InSquarePlacement.m_191715_(), PlacementUtils.f_195353_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195228_ = PlacementUtils.m_206513_("kelp_cold", AquaticFeatures.f_194931_, NoiseBasedCountPlacement.m_191731_(120, 80.0, 0.0), InSquarePlacement.m_191715_(), PlacementUtils.f_195353_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195229_ = PlacementUtils.m_206513_("kelp_warm", AquaticFeatures.f_194931_, NoiseBasedCountPlacement.m_191731_(80, 80.0, 0.0), InSquarePlacement.m_191715_(), PlacementUtils.f_195353_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195230_ = PlacementUtils.m_206513_("warm_ocean_vegetation", AquaticFeatures.f_194932_, NoiseBasedCountPlacement.m_191731_(20, 400.0, 0.0), InSquarePlacement.m_191715_(), PlacementUtils.f_195353_, BiomeFilter.m_191561_());

    public static List<PlacementModifier> m_195233_(int p_195234_) {
        return List.of(InSquarePlacement.m_191715_(), PlacementUtils.f_195353_, CountPlacement.m_191628_(p_195234_), BiomeFilter.m_191561_());
    }
}

