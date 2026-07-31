/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.placement;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.PileFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class VillagePlacements {
    public static final Holder<PlacedFeature> f_197413_ = PlacementUtils.m_206513_("pile_hay", PileFeatures.f_195099_, new PlacementModifier[0]);
    public static final Holder<PlacedFeature> f_197414_ = PlacementUtils.m_206513_("pile_melon", PileFeatures.f_195100_, new PlacementModifier[0]);
    public static final Holder<PlacedFeature> f_197415_ = PlacementUtils.m_206513_("pile_snow", PileFeatures.f_195101_, new PlacementModifier[0]);
    public static final Holder<PlacedFeature> f_197416_ = PlacementUtils.m_206513_("pile_ice", PileFeatures.f_195102_, new PlacementModifier[0]);
    public static final Holder<PlacedFeature> f_197417_ = PlacementUtils.m_206513_("pile_pumpkin", PileFeatures.f_195103_, new PlacementModifier[0]);
    public static final Holder<PlacedFeature> f_197418_ = PlacementUtils.m_206513_("oak", TreeFeatures.f_195123_, PlacementUtils.m_206493_(Blocks.f_50746_));
    public static final Holder<PlacedFeature> f_197419_ = PlacementUtils.m_206513_("acacia", TreeFeatures.f_195126_, PlacementUtils.m_206493_(Blocks.f_50750_));
    public static final Holder<PlacedFeature> f_197420_ = PlacementUtils.m_206513_("spruce", TreeFeatures.f_195127_, PlacementUtils.m_206493_(Blocks.f_50747_));
    public static final Holder<PlacedFeature> f_197421_ = PlacementUtils.m_206513_("pine", TreeFeatures.f_195128_, PlacementUtils.m_206493_(Blocks.f_50747_));
    public static final Holder<PlacedFeature> f_197422_ = PlacementUtils.m_206513_("patch_cactus", VegetationFeatures.f_195190_, new PlacementModifier[0]);
    public static final Holder<PlacedFeature> f_197423_ = PlacementUtils.m_206513_("flower_plain", VegetationFeatures.f_195195_, new PlacementModifier[0]);
    public static final Holder<PlacedFeature> f_197424_ = PlacementUtils.m_206513_("patch_taiga_grass", VegetationFeatures.f_195181_, new PlacementModifier[0]);
    public static final Holder<PlacedFeature> f_197425_ = PlacementUtils.m_206513_("patch_berry_bush", VegetationFeatures.f_195180_, new PlacementModifier[0]);
}

