/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.placement;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.material.Fluids;

public class MiscOverworldPlacements {
    public static final Holder<PlacedFeature> f_195260_ = PlacementUtils.m_206513_("ice_spike", MiscOverworldFeatures.f_195010_, CountPlacement.m_191628_(3), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195261_ = PlacementUtils.m_206513_("ice_patch", MiscOverworldFeatures.f_195011_, CountPlacement.m_191628_(2), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, RandomOffsetPlacement.m_191877_(ConstantInt.m_146483_(-1)), BlockPredicateFilter.m_191576_(BlockPredicate.m_224780_(Blocks.f_50127_)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195262_ = PlacementUtils.m_206513_("forest_rock", MiscOverworldFeatures.f_195012_, CountPlacement.m_191628_(2), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195263_ = PlacementUtils.m_206513_("iceberg_packed", MiscOverworldFeatures.f_195013_, RarityFilter.m_191900_(16), InSquarePlacement.m_191715_(), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195264_ = PlacementUtils.m_206513_("iceberg_blue", MiscOverworldFeatures.f_195014_, RarityFilter.m_191900_(200), InSquarePlacement.m_191715_(), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195265_ = PlacementUtils.m_206513_("blue_ice", MiscOverworldFeatures.f_195015_, CountPlacement.m_191630_(UniformInt.m_146622_(0, 19)), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(30), VerticalAnchor.m_158922_(61)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195266_ = PlacementUtils.m_206513_("lake_lava_underground", MiscOverworldFeatures.f_195016_, RarityFilter.m_191900_(9), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191683_(UniformHeight.m_162034_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158929_())), EnvironmentScanPlacement.m_191653_(Direction.DOWN, BlockPredicate.m_190404_(BlockPredicate.m_190402_(BlockPredicate.f_190393_), BlockPredicate.m_190433_(new BlockPos(0, -5, 0))), 32), SurfaceRelativeThresholdFilter.m_191930_(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195267_ = PlacementUtils.m_206513_("lake_lava_surface", MiscOverworldFeatures.f_195016_, RarityFilter.m_191900_(200), InSquarePlacement.m_191715_(), PlacementUtils.f_195354_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195268_ = PlacementUtils.m_206513_("disk_clay", MiscOverworldFeatures.f_195017_, InSquarePlacement.m_191715_(), PlacementUtils.f_195353_, BlockPredicateFilter.m_191576_(BlockPredicate.m_224782_(Fluids.f_76193_)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195269_ = PlacementUtils.m_206513_("disk_gravel", MiscOverworldFeatures.f_195018_, InSquarePlacement.m_191715_(), PlacementUtils.f_195353_, BlockPredicateFilter.m_191576_(BlockPredicate.m_224782_(Fluids.f_76193_)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195270_ = PlacementUtils.m_206513_("disk_sand", MiscOverworldFeatures.f_195019_, CountPlacement.m_191628_(3), InSquarePlacement.m_191715_(), PlacementUtils.f_195353_, BlockPredicateFilter.m_191576_(BlockPredicate.m_224782_(Fluids.f_76193_)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_236768_ = PlacementUtils.m_206513_("disk_grass", MiscOverworldFeatures.f_236760_, CountPlacement.m_191628_(1), InSquarePlacement.m_191715_(), PlacementUtils.f_195353_, RandomOffsetPlacement.m_191877_(ConstantInt.m_146483_(-1)), BlockPredicateFilter.m_191576_(BlockPredicate.m_224780_(Blocks.f_220864_)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195271_ = PlacementUtils.m_206513_("freeze_top_layer", MiscOverworldFeatures.f_195020_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195272_ = PlacementUtils.m_206513_("void_start_platform", MiscOverworldFeatures.f_195022_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195273_ = PlacementUtils.m_206513_("desert_well", MiscOverworldFeatures.f_195023_, RarityFilter.m_191900_(1000), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195274_ = PlacementUtils.m_206513_("spring_lava", MiscOverworldFeatures.f_195024_, CountPlacement.m_191628_(20), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191683_(VeryBiasedToBottomHeight.m_162058_(VerticalAnchor.m_158921_(), VerticalAnchor.m_158935_(8), 8)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195275_ = PlacementUtils.m_206513_("spring_lava_frozen", MiscOverworldFeatures.f_195025_, CountPlacement.m_191628_(20), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191683_(VeryBiasedToBottomHeight.m_162058_(VerticalAnchor.m_158921_(), VerticalAnchor.m_158935_(8), 8)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195276_ = PlacementUtils.m_206513_("spring_water", MiscOverworldFeatures.f_195026_, CountPlacement.m_191628_(25), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191680_(VerticalAnchor.m_158921_(), VerticalAnchor.m_158922_(192)), BiomeFilter.m_191561_());
}

