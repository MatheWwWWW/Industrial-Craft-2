/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  javax.annotation.Nullable
 */
package net.minecraft.data.worldgen.placement;

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;

public class VegetationPlacements {
    public static final Holder<PlacedFeature> f_195426_ = PlacementUtils.m_206513_("bamboo_light", VegetationFeatures.f_195173_, RarityFilter.m_191900_(4), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195447_ = PlacementUtils.m_206513_("bamboo", VegetationFeatures.f_195174_, NoiseBasedCountPlacement.m_191731_(160, 80.0, 0.3), InSquarePlacement.m_191715_(), PlacementUtils.f_195354_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195448_ = PlacementUtils.m_206513_("vines", VegetationFeatures.f_195175_, CountPlacement.m_191628_(127), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(64), VerticalAnchor.m_158922_(100)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195449_ = PlacementUtils.m_206513_("patch_sunflower", VegetationFeatures.f_195178_, RarityFilter.m_191900_(3), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195450_ = PlacementUtils.m_206513_("patch_pumpkin", VegetationFeatures.f_195179_, RarityFilter.m_191900_(300), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195451_ = PlacementUtils.m_206513_("patch_grass_plain", VegetationFeatures.f_195182_, NoiseThresholdCountPlacement.m_191756_(-0.8, 5, 10), InSquarePlacement.m_191715_(), PlacementUtils.f_195354_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195452_ = PlacementUtils.m_206509_("patch_grass_forest", VegetationFeatures.f_195182_, VegetationPlacements.m_195474_(2));
    public static final Holder<PlacedFeature> f_195453_ = PlacementUtils.m_206513_("patch_grass_badlands", VegetationFeatures.f_195182_, InSquarePlacement.m_191715_(), PlacementUtils.f_195354_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195454_ = PlacementUtils.m_206509_("patch_grass_savanna", VegetationFeatures.f_195182_, VegetationPlacements.m_195474_(20));
    public static final Holder<PlacedFeature> f_195455_ = PlacementUtils.m_206509_("patch_grass_normal", VegetationFeatures.f_195182_, VegetationPlacements.m_195474_(5));
    public static final Holder<PlacedFeature> f_195456_ = PlacementUtils.m_206513_("patch_grass_taiga_2", VegetationFeatures.f_195181_, InSquarePlacement.m_191715_(), PlacementUtils.f_195354_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195457_ = PlacementUtils.m_206509_("patch_grass_taiga", VegetationFeatures.f_195181_, VegetationPlacements.m_195474_(7));
    public static final Holder<PlacedFeature> f_195458_ = PlacementUtils.m_206509_("patch_grass_jungle", VegetationFeatures.f_195183_, VegetationPlacements.m_195474_(25));
    public static final Holder<PlacedFeature> f_195459_ = PlacementUtils.m_206513_("grass_bonemeal", VegetationFeatures.f_195184_, PlacementUtils.m_206517_());
    public static final Holder<PlacedFeature> f_195460_ = PlacementUtils.m_206509_("patch_dead_bush_2", VegetationFeatures.f_195185_, VegetationPlacements.m_195474_(2));
    public static final Holder<PlacedFeature> f_195461_ = PlacementUtils.m_206513_("patch_dead_bush", VegetationFeatures.f_195185_, InSquarePlacement.m_191715_(), PlacementUtils.f_195354_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195462_ = PlacementUtils.m_206509_("patch_dead_bush_badlands", VegetationFeatures.f_195185_, VegetationPlacements.m_195474_(20));
    public static final Holder<PlacedFeature> f_195463_ = PlacementUtils.m_206513_("patch_melon", VegetationFeatures.f_195186_, RarityFilter.m_191900_(6), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_198935_ = PlacementUtils.m_206513_("patch_melon_sparse", VegetationFeatures.f_195186_, RarityFilter.m_191900_(64), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195464_ = PlacementUtils.m_206513_("patch_berry_common", VegetationFeatures.f_195180_, RarityFilter.m_191900_(32), InSquarePlacement.m_191715_(), PlacementUtils.f_195354_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195465_ = PlacementUtils.m_206513_("patch_berry_rare", VegetationFeatures.f_195180_, RarityFilter.m_191900_(384), InSquarePlacement.m_191715_(), PlacementUtils.f_195354_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195466_ = PlacementUtils.m_206509_("patch_waterlily", VegetationFeatures.f_195187_, VegetationPlacements.m_195474_(4));
    public static final Holder<PlacedFeature> f_195467_ = PlacementUtils.m_206513_("patch_tall_grass_2", VegetationFeatures.f_195188_, NoiseThresholdCountPlacement.m_191756_(-0.8, 0, 7), RarityFilter.m_191900_(32), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195468_ = PlacementUtils.m_206513_("patch_tall_grass", VegetationFeatures.f_195188_, RarityFilter.m_191900_(5), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195469_ = PlacementUtils.m_206513_("patch_large_fern", VegetationFeatures.f_195189_, RarityFilter.m_191900_(5), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195470_ = PlacementUtils.m_206513_("patch_cactus_desert", VegetationFeatures.f_195190_, RarityFilter.m_191900_(6), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195471_ = PlacementUtils.m_206513_("patch_cactus_decorated", VegetationFeatures.f_195190_, RarityFilter.m_191900_(13), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195400_ = PlacementUtils.m_206513_("patch_sugar_cane_swamp", VegetationFeatures.f_195191_, RarityFilter.m_191900_(3), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195401_ = PlacementUtils.m_206513_("patch_sugar_cane_desert", VegetationFeatures.f_195191_, InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195402_ = PlacementUtils.m_206513_("patch_sugar_cane_badlands", VegetationFeatures.f_195191_, RarityFilter.m_191900_(5), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195403_ = PlacementUtils.m_206513_("patch_sugar_cane", VegetationFeatures.f_195191_, RarityFilter.m_191900_(6), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195404_ = PlacementUtils.m_206513_("brown_mushroom_nether", VegetationFeatures.f_195176_, RarityFilter.m_191900_(2), InSquarePlacement.m_191715_(), PlacementUtils.f_195356_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195405_ = PlacementUtils.m_206513_("red_mushroom_nether", VegetationFeatures.f_195177_, RarityFilter.m_191900_(2), InSquarePlacement.m_191715_(), PlacementUtils.f_195356_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195406_ = PlacementUtils.m_206509_("brown_mushroom_normal", VegetationFeatures.f_195176_, VegetationPlacements.m_195476_(256, null));
    public static final Holder<PlacedFeature> f_195407_ = PlacementUtils.m_206509_("red_mushroom_normal", VegetationFeatures.f_195177_, VegetationPlacements.m_195476_(512, null));
    public static final Holder<PlacedFeature> f_195408_ = PlacementUtils.m_206509_("brown_mushroom_taiga", VegetationFeatures.f_195176_, VegetationPlacements.m_195476_(4, null));
    public static final Holder<PlacedFeature> f_195409_ = PlacementUtils.m_206509_("red_mushroom_taiga", VegetationFeatures.f_195177_, VegetationPlacements.m_195476_(256, null));
    public static final Holder<PlacedFeature> f_195410_ = PlacementUtils.m_206509_("brown_mushroom_old_growth", VegetationFeatures.f_195176_, VegetationPlacements.m_195476_(4, CountPlacement.m_191628_(3)));
    public static final Holder<PlacedFeature> f_195411_ = PlacementUtils.m_206509_("red_mushroom_old_growth", VegetationFeatures.f_195177_, VegetationPlacements.m_195476_(171, null));
    public static final Holder<PlacedFeature> f_195412_ = PlacementUtils.m_206509_("brown_mushroom_swamp", VegetationFeatures.f_195176_, VegetationPlacements.m_195476_(0, CountPlacement.m_191628_(2)));
    public static final Holder<PlacedFeature> f_195413_ = PlacementUtils.m_206509_("red_mushroom_swamp", VegetationFeatures.f_195177_, VegetationPlacements.m_195476_(64, null));
    public static final Holder<PlacedFeature> f_195414_ = PlacementUtils.m_206513_("flower_warm", VegetationFeatures.f_195192_, RarityFilter.m_191900_(16), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195415_ = PlacementUtils.m_206513_("flower_default", VegetationFeatures.f_195192_, RarityFilter.m_191900_(32), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195416_ = PlacementUtils.m_206513_("flower_flower_forest", VegetationFeatures.f_195193_, CountPlacement.m_191628_(3), RarityFilter.m_191900_(2), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195417_ = PlacementUtils.m_206513_("flower_swamp", VegetationFeatures.f_195194_, RarityFilter.m_191900_(32), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_197412_ = PlacementUtils.m_206513_("flower_plains", VegetationFeatures.f_195195_, NoiseThresholdCountPlacement.m_191756_(-0.8, 15, 4), RarityFilter.m_191900_(32), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195419_ = PlacementUtils.m_206513_("flower_meadow", VegetationFeatures.f_195196_, InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final PlacementModifier f_195420_ = SurfaceWaterDepthFilter.m_191950_(0);
    public static final Holder<PlacedFeature> f_195421_ = PlacementUtils.m_206513_("trees_plains", VegetationFeatures.f_195166_, PlacementUtils.m_195364_(0, 0.05f, 1), InSquarePlacement.m_191715_(), f_195420_, PlacementUtils.f_195355_, BlockPredicateFilter.m_191576_(BlockPredicate.m_190399_(Blocks.f_50746_.m_49966_(), BlockPos.f_121853_)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195422_ = PlacementUtils.m_206513_("dark_forest_vegetation", VegetationFeatures.f_195198_, CountPlacement.m_191628_(16), InSquarePlacement.m_191715_(), f_195420_, PlacementUtils.f_195355_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195423_ = PlacementUtils.m_206513_("flower_forest_flowers", VegetationFeatures.f_195197_, RarityFilter.m_191900_(7), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, CountPlacement.m_191630_(ClampedInt.m_146395_(UniformInt.m_146622_(-1, 3), 0, 3)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195424_ = PlacementUtils.m_206513_("forest_flowers", VegetationFeatures.f_195197_, RarityFilter.m_191900_(7), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, CountPlacement.m_191630_(ClampedInt.m_146395_(UniformInt.m_146622_(-3, 1), 0, 1)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195425_ = PlacementUtils.m_206509_("trees_flower_forest", VegetationFeatures.f_195157_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(6, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195427_ = PlacementUtils.m_206509_("trees_meadow", VegetationFeatures.f_195158_, VegetationPlacements.m_195479_(RarityFilter.m_191900_(100)));
    public static final Holder<PlacedFeature> f_195428_ = PlacementUtils.m_206509_("trees_taiga", VegetationFeatures.f_195159_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(10, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195429_ = PlacementUtils.m_206509_("trees_grove", VegetationFeatures.f_195160_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(10, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195430_ = PlacementUtils.m_206509_("trees_badlands", TreeFeatures.f_195123_, VegetationPlacements.m_195481_(PlacementUtils.m_195364_(5, 0.1f, 1), Blocks.f_50746_));
    public static final Holder<PlacedFeature> f_195431_ = PlacementUtils.m_206509_("trees_snowy", TreeFeatures.f_195127_, VegetationPlacements.m_195481_(PlacementUtils.m_195364_(0, 0.1f, 1), Blocks.f_50747_));
    public static final Holder<PlacedFeature> f_195432_ = PlacementUtils.m_206513_("trees_swamp", TreeFeatures.f_195137_, PlacementUtils.m_195364_(2, 0.1f, 1), InSquarePlacement.m_191715_(), SurfaceWaterDepthFilter.m_191950_(2), PlacementUtils.f_195355_, BiomeFilter.m_191561_(), BlockPredicateFilter.m_191576_(BlockPredicate.m_190399_(Blocks.f_50746_.m_49966_(), BlockPos.f_121853_)));
    public static final Holder<PlacedFeature> f_195433_ = PlacementUtils.m_206509_("trees_windswept_savanna", VegetationFeatures.f_195161_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(2, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195434_ = PlacementUtils.m_206509_("trees_savanna", VegetationFeatures.f_195161_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(1, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195435_ = PlacementUtils.m_206509_("birch_tall", VegetationFeatures.f_195162_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(10, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195436_ = PlacementUtils.m_206509_("trees_birch", TreeFeatures.f_195106_, VegetationPlacements.m_195481_(PlacementUtils.m_195364_(10, 0.1f, 1), Blocks.f_50748_));
    public static final Holder<PlacedFeature> f_195437_ = PlacementUtils.m_206509_("trees_windswept_forest", VegetationFeatures.f_195163_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(3, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195438_ = PlacementUtils.m_206509_("trees_windswept_hills", VegetationFeatures.f_195163_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(0, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195439_ = PlacementUtils.m_206509_("trees_water", VegetationFeatures.f_195164_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(0, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195440_ = PlacementUtils.m_206509_("trees_birch_and_oak", VegetationFeatures.f_195165_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(10, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195441_ = PlacementUtils.m_206509_("trees_sparse_jungle", VegetationFeatures.f_195167_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(2, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195442_ = PlacementUtils.m_206509_("trees_old_growth_spruce_taiga", VegetationFeatures.f_195168_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(10, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195443_ = PlacementUtils.m_206509_("trees_old_growth_pine_taiga", VegetationFeatures.f_195169_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(10, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195444_ = PlacementUtils.m_206509_("trees_jungle", VegetationFeatures.f_195170_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(50, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195445_ = PlacementUtils.m_206509_("bamboo_vegetation", VegetationFeatures.f_195171_, VegetationPlacements.m_195479_(PlacementUtils.m_195364_(30, 0.1f, 1)));
    public static final Holder<PlacedFeature> f_195446_ = PlacementUtils.m_206513_("mushroom_island_vegetation", VegetationFeatures.f_195172_, InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_236773_ = PlacementUtils.m_206513_("trees_mangrove", VegetationFeatures.f_236764_, CountPlacement.m_191628_(25), InSquarePlacement.m_191715_(), SurfaceWaterDepthFilter.m_191950_(5), PlacementUtils.f_195355_, BiomeFilter.m_191561_(), BlockPredicateFilter.m_191576_(BlockPredicate.m_190399_(Blocks.f_220831_.m_49966_(), BlockPos.f_121853_)));

    public static List<PlacementModifier> m_195474_(int p_195475_) {
        return List.of(CountPlacement.m_191628_(p_195475_), InSquarePlacement.m_191715_(), PlacementUtils.f_195354_, BiomeFilter.m_191561_());
    }

    private static List<PlacementModifier> m_195476_(int p_195477_, @Nullable PlacementModifier p_195478_) {
        ImmutableList.Builder $$2 = ImmutableList.builder();
        if (p_195478_ != null) {
            $$2.add((Object)p_195478_);
        }
        if (p_195477_ != 0) {
            $$2.add((Object)RarityFilter.m_191900_(p_195477_));
        }
        $$2.add((Object)InSquarePlacement.m_191715_());
        $$2.add((Object)PlacementUtils.f_195352_);
        $$2.add((Object)BiomeFilter.m_191561_());
        return $$2.build();
    }

    private static ImmutableList.Builder<PlacementModifier> m_195484_(PlacementModifier p_195485_) {
        return ImmutableList.builder().add((Object)p_195485_).add((Object)InSquarePlacement.m_191715_()).add((Object)f_195420_).add((Object)PlacementUtils.f_195355_).add((Object)BiomeFilter.m_191561_());
    }

    public static List<PlacementModifier> m_195479_(PlacementModifier p_195480_) {
        return VegetationPlacements.m_195484_(p_195480_).build();
    }

    public static List<PlacementModifier> m_195481_(PlacementModifier p_195482_, Block p_195483_) {
        return VegetationPlacements.m_195484_(p_195482_).add((Object)BlockPredicateFilter.m_191576_(BlockPredicate.m_190399_(p_195483_.m_49966_(), BlockPos.f_121853_))).build();
    }
}

