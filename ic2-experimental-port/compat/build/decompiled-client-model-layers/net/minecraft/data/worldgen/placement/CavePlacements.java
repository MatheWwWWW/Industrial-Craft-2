/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.placement;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;

public class CavePlacements {
    public static final Holder<PlacedFeature> f_195235_ = PlacementUtils.m_206513_("monster_room", CaveFeatures.f_194938_, CountPlacement.m_191628_(10), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158929_()), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195236_ = PlacementUtils.m_206513_("monster_room_deep", CaveFeatures.f_194938_, CountPlacement.m_191628_(4), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191680_(VerticalAnchor.m_158930_(6), VerticalAnchor.m_158922_(-1)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195237_ = PlacementUtils.m_206513_("fossil_upper", CaveFeatures.f_194939_, RarityFilter.m_191900_(64), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158929_()), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195238_ = PlacementUtils.m_206513_("fossil_lower", CaveFeatures.f_194940_, RarityFilter.m_191900_(64), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191680_(VerticalAnchor.m_158921_(), VerticalAnchor.m_158922_(-8)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195239_ = PlacementUtils.m_206513_("dripstone_cluster", CaveFeatures.f_194941_, CountPlacement.m_191630_(UniformInt.m_146622_(48, 96)), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195240_ = PlacementUtils.m_206513_("large_dripstone", CaveFeatures.f_194942_, CountPlacement.m_191630_(UniformInt.m_146622_(10, 48)), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195241_ = PlacementUtils.m_206513_("pointed_dripstone", CaveFeatures.f_194943_, CountPlacement.m_191630_(UniformInt.m_146622_(192, 256)), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, CountPlacement.m_191630_(UniformInt.m_146622_(1, 5)), RandomOffsetPlacement.m_191879_(ClampedNormalInt.m_185879_(0.0f, 3.0f, -10, 10), ClampedNormalInt.m_185879_(0.0f, 0.6f, -2, 2)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195242_ = PlacementUtils.m_206513_("underwater_magma", CaveFeatures.f_194944_, CountPlacement.m_191630_(UniformInt.m_146622_(44, 52)), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, SurfaceRelativeThresholdFilter.m_191930_(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -2), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195243_ = PlacementUtils.m_206513_("glow_lichen", CaveFeatures.f_194945_, CountPlacement.m_191630_(UniformInt.m_146622_(104, 157)), PlacementUtils.f_195360_, InSquarePlacement.m_191715_(), SurfaceRelativeThresholdFilter.m_191930_(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195244_ = PlacementUtils.m_206513_("rooted_azalea_tree", CaveFeatures.f_194946_, CountPlacement.m_191630_(UniformInt.m_146622_(1, 2)), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, EnvironmentScanPlacement.m_191657_(Direction.UP, BlockPredicate.m_190432_(), BlockPredicate.f_190393_, 12), RandomOffsetPlacement.m_191877_(ConstantInt.m_146483_(-1)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195245_ = PlacementUtils.m_206513_("cave_vines", CaveFeatures.f_194947_, CountPlacement.m_191628_(188), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, EnvironmentScanPlacement.m_191657_(Direction.UP, BlockPredicate.m_198913_(Direction.DOWN), BlockPredicate.f_190393_, 12), RandomOffsetPlacement.m_191877_(ConstantInt.m_146483_(-1)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195246_ = PlacementUtils.m_206513_("lush_caves_vegetation", CaveFeatures.f_194950_, CountPlacement.m_191628_(125), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, EnvironmentScanPlacement.m_191657_(Direction.DOWN, BlockPredicate.m_190432_(), BlockPredicate.f_190393_, 12), RandomOffsetPlacement.m_191877_(ConstantInt.m_146483_(1)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195247_ = PlacementUtils.m_206513_("lush_caves_clay", CaveFeatures.f_194955_, CountPlacement.m_191628_(62), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, EnvironmentScanPlacement.m_191657_(Direction.DOWN, BlockPredicate.m_190432_(), BlockPredicate.f_190393_, 12), RandomOffsetPlacement.m_191877_(ConstantInt.m_146483_(1)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195248_ = PlacementUtils.m_206513_("lush_caves_ceiling_vegetation", CaveFeatures.f_194956_, CountPlacement.m_191628_(125), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, EnvironmentScanPlacement.m_191657_(Direction.UP, BlockPredicate.m_190432_(), BlockPredicate.f_190393_, 12), RandomOffsetPlacement.m_191877_(ConstantInt.m_146483_(-1)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195249_ = PlacementUtils.m_206513_("spore_blossom", CaveFeatures.f_194957_, CountPlacement.m_191628_(25), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, EnvironmentScanPlacement.m_191657_(Direction.UP, BlockPredicate.m_190432_(), BlockPredicate.f_190393_, 12), RandomOffsetPlacement.m_191877_(ConstantInt.m_146483_(-1)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195250_ = PlacementUtils.m_206513_("classic_vines_cave_feature", VegetationFeatures.f_195175_, CountPlacement.m_191628_(256), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195251_ = PlacementUtils.m_206513_("amethyst_geode", CaveFeatures.f_194958_, RarityFilter.m_191900_(24), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191680_(VerticalAnchor.m_158930_(6), VerticalAnchor.m_158922_(30)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_236765_ = PlacementUtils.m_206513_("sculk_patch_deep_dark", CaveFeatures.f_236674_, CountPlacement.m_191630_(ConstantInt.m_146483_(256)), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_236766_ = PlacementUtils.m_206513_("sculk_patch_ancient_city", CaveFeatures.f_236675_, new PlacementModifier[0]);
    public static final Holder<PlacedFeature> f_236767_ = PlacementUtils.m_206513_("sculk_vein", CaveFeatures.f_236676_, CountPlacement.m_191630_(UniformInt.m_146622_(204, 250)), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, BiomeFilter.m_191561_());
}

