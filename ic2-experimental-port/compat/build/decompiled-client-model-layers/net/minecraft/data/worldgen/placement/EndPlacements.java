/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.placement;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.EndFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class EndPlacements {
    public static final Holder<PlacedFeature> f_195254_ = PlacementUtils.m_206513_("end_spike", EndFeatures.f_194982_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195255_ = PlacementUtils.m_206513_("end_gateway_return", EndFeatures.f_194983_, RarityFilter.m_191900_(700), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, RandomOffsetPlacement.m_191877_(UniformInt.m_146622_(3, 9)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195256_ = PlacementUtils.m_206513_("chorus_plant", EndFeatures.f_194985_, CountPlacement.m_191630_(UniformInt.m_146622_(0, 4)), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195257_ = PlacementUtils.m_206513_("end_island_decorated", EndFeatures.f_194986_, RarityFilter.m_191900_(14), PlacementUtils.m_195364_(1, 0.25f, 1), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(55), VerticalAnchor.m_158922_(70)), BiomeFilter.m_191561_());
}

