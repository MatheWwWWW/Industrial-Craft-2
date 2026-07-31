/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.placement;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class NetherPlacements {
    public static final Holder<PlacedFeature> f_195279_ = PlacementUtils.m_206513_("delta", NetherFeatures.f_195029_, CountOnEveryLayerPlacement.m_191604_(40), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195280_ = PlacementUtils.m_206513_("small_basalt_columns", NetherFeatures.f_195030_, CountOnEveryLayerPlacement.m_191604_(4), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195281_ = PlacementUtils.m_206513_("large_basalt_columns", NetherFeatures.f_195031_, CountOnEveryLayerPlacement.m_191604_(2), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195282_ = PlacementUtils.m_206513_("basalt_blobs", NetherFeatures.f_195032_, CountPlacement.m_191628_(75), InSquarePlacement.m_191715_(), PlacementUtils.f_195356_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195283_ = PlacementUtils.m_206513_("blackstone_blobs", NetherFeatures.f_195033_, CountPlacement.m_191628_(25), InSquarePlacement.m_191715_(), PlacementUtils.f_195356_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195284_ = PlacementUtils.m_206513_("glowstone_extra", NetherFeatures.f_195034_, CountPlacement.m_191630_(BiasedToBottomInt.m_146367_(0, 9)), InSquarePlacement.m_191715_(), PlacementUtils.f_195359_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195285_ = PlacementUtils.m_206513_("glowstone", NetherFeatures.f_195034_, CountPlacement.m_191628_(10), InSquarePlacement.m_191715_(), PlacementUtils.f_195356_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195286_ = PlacementUtils.m_206513_("crimson_forest_vegetation", NetherFeatures.f_195036_, CountOnEveryLayerPlacement.m_191604_(6), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195287_ = PlacementUtils.m_206513_("warped_forest_vegetation", NetherFeatures.f_195039_, CountOnEveryLayerPlacement.m_191604_(5), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195288_ = PlacementUtils.m_206513_("nether_sprouts", NetherFeatures.f_195041_, CountOnEveryLayerPlacement.m_191604_(4), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195289_ = PlacementUtils.m_206513_("twisting_vines", NetherFeatures.f_195043_, CountPlacement.m_191628_(10), InSquarePlacement.m_191715_(), PlacementUtils.f_195356_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195290_ = PlacementUtils.m_206513_("weeping_vines", NetherFeatures.f_195045_, CountPlacement.m_191628_(10), InSquarePlacement.m_191715_(), PlacementUtils.f_195356_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195291_ = PlacementUtils.m_206513_("patch_crimson_roots", NetherFeatures.f_195046_, PlacementUtils.f_195356_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195292_ = PlacementUtils.m_206513_("basalt_pillar", NetherFeatures.f_195047_, CountPlacement.m_191628_(10), InSquarePlacement.m_191715_(), PlacementUtils.f_195356_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195293_ = PlacementUtils.m_206513_("spring_delta", NetherFeatures.f_195048_, CountPlacement.m_191628_(16), InSquarePlacement.m_191715_(), PlacementUtils.f_195359_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195294_ = PlacementUtils.m_206513_("spring_closed", NetherFeatures.f_195049_, CountPlacement.m_191628_(16), InSquarePlacement.m_191715_(), PlacementUtils.f_195357_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195295_ = PlacementUtils.m_206513_("spring_closed_double", NetherFeatures.f_195049_, CountPlacement.m_191628_(32), InSquarePlacement.m_191715_(), PlacementUtils.f_195357_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195296_ = PlacementUtils.m_206513_("spring_open", NetherFeatures.f_195050_, CountPlacement.m_191628_(8), InSquarePlacement.m_191715_(), PlacementUtils.f_195359_, BiomeFilter.m_191561_());
    public static final List<PlacementModifier> f_195297_ = List.of(CountPlacement.m_191630_(UniformInt.m_146622_(0, 5)), InSquarePlacement.m_191715_(), PlacementUtils.f_195359_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195298_ = PlacementUtils.m_206509_("patch_soul_fire", NetherFeatures.f_195052_, f_195297_);
    public static final Holder<PlacedFeature> f_195299_ = PlacementUtils.m_206509_("patch_fire", NetherFeatures.f_195051_, f_195297_);
}

