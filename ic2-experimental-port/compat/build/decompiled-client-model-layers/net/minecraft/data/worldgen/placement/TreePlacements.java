/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.placement;

import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class TreePlacements {
    public static final Holder<PlacedFeature> f_195372_ = PlacementUtils.m_206513_("crimson_fungi", TreeFeatures.f_195117_, CountOnEveryLayerPlacement.m_191604_(8), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195373_ = PlacementUtils.m_206513_("warped_fungi", TreeFeatures.f_195119_, CountOnEveryLayerPlacement.m_191604_(8), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195374_ = PlacementUtils.m_206513_("oak_checked", TreeFeatures.f_195123_, PlacementUtils.m_206493_(Blocks.f_50746_));
    public static final Holder<PlacedFeature> f_195375_ = PlacementUtils.m_206513_("dark_oak_checked", TreeFeatures.f_195124_, PlacementUtils.m_206493_(Blocks.f_50751_));
    public static final Holder<PlacedFeature> f_195376_ = PlacementUtils.m_206513_("birch_checked", TreeFeatures.f_195125_, PlacementUtils.m_206493_(Blocks.f_50748_));
    public static final Holder<PlacedFeature> f_195377_ = PlacementUtils.m_206513_("acacia_checked", TreeFeatures.f_195126_, PlacementUtils.m_206493_(Blocks.f_50750_));
    public static final Holder<PlacedFeature> f_195378_ = PlacementUtils.m_206513_("spruce_checked", TreeFeatures.f_195127_, PlacementUtils.m_206493_(Blocks.f_50747_));
    public static final Holder<PlacedFeature> f_236771_ = PlacementUtils.m_206513_("mangrove_checked", TreeFeatures.f_236762_, PlacementUtils.m_206493_(Blocks.f_220831_));
    public static final BlockPredicate f_195379_ = BlockPredicate.m_224774_(Direction.DOWN.m_122436_(), Blocks.f_50127_, Blocks.f_152499_);
    public static final List<PlacementModifier> f_195380_ = List.of(EnvironmentScanPlacement.m_191653_(Direction.UP, BlockPredicate.m_190402_(BlockPredicate.m_224780_(Blocks.f_152499_)), 8), BlockPredicateFilter.m_191576_(f_195379_));
    public static final Holder<PlacedFeature> f_195381_ = PlacementUtils.m_206509_("pine_on_snow", TreeFeatures.f_195128_, f_195380_);
    public static final Holder<PlacedFeature> f_195382_ = PlacementUtils.m_206509_("spruce_on_snow", TreeFeatures.f_195127_, f_195380_);
    public static final Holder<PlacedFeature> f_195383_ = PlacementUtils.m_206513_("pine_checked", TreeFeatures.f_195128_, PlacementUtils.m_206493_(Blocks.f_50747_));
    public static final Holder<PlacedFeature> f_195384_ = PlacementUtils.m_206513_("jungle_tree", TreeFeatures.f_195129_, PlacementUtils.m_206493_(Blocks.f_50749_));
    public static final Holder<PlacedFeature> f_195385_ = PlacementUtils.m_206513_("fancy_oak_checked", TreeFeatures.f_195130_, PlacementUtils.m_206493_(Blocks.f_50746_));
    public static final Holder<PlacedFeature> f_195386_ = PlacementUtils.m_206513_("mega_jungle_tree_checked", TreeFeatures.f_195132_, PlacementUtils.m_206493_(Blocks.f_50749_));
    public static final Holder<PlacedFeature> f_195387_ = PlacementUtils.m_206513_("mega_spruce_checked", TreeFeatures.f_195133_, PlacementUtils.m_206493_(Blocks.f_50747_));
    public static final Holder<PlacedFeature> f_195388_ = PlacementUtils.m_206513_("mega_pine_checked", TreeFeatures.f_195134_, PlacementUtils.m_206493_(Blocks.f_50747_));
    public static final Holder<PlacedFeature> f_236772_ = PlacementUtils.m_206513_("tall_mangrove_checked", TreeFeatures.f_236763_, PlacementUtils.m_206493_(Blocks.f_220831_));
    public static final Holder<PlacedFeature> f_195389_ = PlacementUtils.m_206513_("jungle_bush", TreeFeatures.f_195138_, PlacementUtils.m_206493_(Blocks.f_50746_));
    public static final Holder<PlacedFeature> f_195390_ = PlacementUtils.m_206513_("super_birch_bees_0002", TreeFeatures.f_195135_, PlacementUtils.m_206493_(Blocks.f_50748_));
    public static final Holder<PlacedFeature> f_195391_ = PlacementUtils.m_206513_("super_birch_bees", TreeFeatures.f_195136_, PlacementUtils.m_206493_(Blocks.f_50748_));
    public static final Holder<PlacedFeature> f_195392_ = PlacementUtils.m_206513_("oak_bees_0002", TreeFeatures.f_195140_, PlacementUtils.m_206493_(Blocks.f_50746_));
    public static final Holder<PlacedFeature> f_195393_ = PlacementUtils.m_206513_("oak_bees_002", TreeFeatures.f_195141_, PlacementUtils.m_206493_(Blocks.f_50746_));
    public static final Holder<PlacedFeature> f_195394_ = PlacementUtils.m_206513_("birch_bees_0002", TreeFeatures.f_195106_, PlacementUtils.m_206493_(Blocks.f_50748_));
    public static final Holder<PlacedFeature> f_195395_ = PlacementUtils.m_206513_("birch_bees_002", TreeFeatures.f_195107_, PlacementUtils.m_206493_(Blocks.f_50748_));
    public static final Holder<PlacedFeature> f_195396_ = PlacementUtils.m_206513_("fancy_oak_bees_0002", TreeFeatures.f_195109_, PlacementUtils.m_206493_(Blocks.f_50746_));
    public static final Holder<PlacedFeature> f_195397_ = PlacementUtils.m_206513_("fancy_oak_bees_002", TreeFeatures.f_195110_, PlacementUtils.m_206493_(Blocks.f_50746_));
    public static final Holder<PlacedFeature> f_195371_ = PlacementUtils.m_206513_("fancy_oak_bees", TreeFeatures.f_195112_, PlacementUtils.m_206493_(Blocks.f_50746_));
}

