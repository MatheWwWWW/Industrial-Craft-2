/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.placement;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class OrePlacements {
    public static final Holder<PlacedFeature> f_195315_ = PlacementUtils.m_206509_("ore_magma", OreFeatures.f_195082_, OrePlacements.m_195343_(4, HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(27), VerticalAnchor.m_158922_(36))));
    public static final Holder<PlacedFeature> f_195316_ = PlacementUtils.m_206509_("ore_soul_sand", OreFeatures.f_195083_, OrePlacements.m_195343_(12, HeightRangePlacement.m_191680_(VerticalAnchor.m_158921_(), VerticalAnchor.m_158922_(31))));
    public static final Holder<PlacedFeature> f_195317_ = PlacementUtils.m_206509_("ore_gold_deltas", OreFeatures.f_195084_, OrePlacements.m_195343_(20, PlacementUtils.f_195357_));
    public static final Holder<PlacedFeature> f_195318_ = PlacementUtils.m_206509_("ore_quartz_deltas", OreFeatures.f_195085_, OrePlacements.m_195343_(32, PlacementUtils.f_195357_));
    public static final Holder<PlacedFeature> f_195319_ = PlacementUtils.m_206509_("ore_gold_nether", OreFeatures.f_195084_, OrePlacements.m_195343_(10, PlacementUtils.f_195357_));
    public static final Holder<PlacedFeature> f_195320_ = PlacementUtils.m_206509_("ore_quartz_nether", OreFeatures.f_195085_, OrePlacements.m_195343_(16, PlacementUtils.f_195357_));
    public static final Holder<PlacedFeature> f_195321_ = PlacementUtils.m_206509_("ore_gravel_nether", OreFeatures.f_195086_, OrePlacements.m_195343_(2, HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(5), VerticalAnchor.m_158922_(41))));
    public static final Holder<PlacedFeature> f_195322_ = PlacementUtils.m_206509_("ore_blackstone", OreFeatures.f_195087_, OrePlacements.m_195343_(2, HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(5), VerticalAnchor.m_158922_(31))));
    public static final Holder<PlacedFeature> f_195323_ = PlacementUtils.m_206509_("ore_dirt", OreFeatures.f_195088_, OrePlacements.m_195343_(7, HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158922_(160))));
    public static final Holder<PlacedFeature> f_195324_ = PlacementUtils.m_206509_("ore_gravel", OreFeatures.f_195089_, OrePlacements.m_195343_(14, HeightRangePlacement.m_191680_(VerticalAnchor.m_158921_(), VerticalAnchor.m_158929_())));
    public static final Holder<PlacedFeature> f_195325_ = PlacementUtils.m_206509_("ore_granite_upper", OreFeatures.f_195090_, OrePlacements.m_195349_(6, HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(64), VerticalAnchor.m_158922_(128))));
    public static final Holder<PlacedFeature> f_195326_ = PlacementUtils.m_206509_("ore_granite_lower", OreFeatures.f_195090_, OrePlacements.m_195343_(2, HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158922_(60))));
    public static final Holder<PlacedFeature> f_195327_ = PlacementUtils.m_206509_("ore_diorite_upper", OreFeatures.f_195091_, OrePlacements.m_195349_(6, HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(64), VerticalAnchor.m_158922_(128))));
    public static final Holder<PlacedFeature> f_195328_ = PlacementUtils.m_206509_("ore_diorite_lower", OreFeatures.f_195091_, OrePlacements.m_195343_(2, HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158922_(60))));
    public static final Holder<PlacedFeature> f_195329_ = PlacementUtils.m_206509_("ore_andesite_upper", OreFeatures.f_195092_, OrePlacements.m_195349_(6, HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(64), VerticalAnchor.m_158922_(128))));
    public static final Holder<PlacedFeature> f_195330_ = PlacementUtils.m_206509_("ore_andesite_lower", OreFeatures.f_195092_, OrePlacements.m_195343_(2, HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158922_(60))));
    public static final Holder<PlacedFeature> f_195331_ = PlacementUtils.m_206509_("ore_tuff", OreFeatures.f_195093_, OrePlacements.m_195343_(2, HeightRangePlacement.m_191680_(VerticalAnchor.m_158921_(), VerticalAnchor.m_158922_(0))));
    public static final Holder<PlacedFeature> f_195332_ = PlacementUtils.m_206509_("ore_coal_upper", OreFeatures.f_195094_, OrePlacements.m_195343_(30, HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(136), VerticalAnchor.m_158929_())));
    public static final Holder<PlacedFeature> f_195333_ = PlacementUtils.m_206509_("ore_coal_lower", OreFeatures.f_195095_, OrePlacements.m_195343_(20, HeightRangePlacement.m_191692_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158922_(192))));
    public static final Holder<PlacedFeature> f_195334_ = PlacementUtils.m_206509_("ore_iron_upper", OreFeatures.f_195096_, OrePlacements.m_195343_(90, HeightRangePlacement.m_191692_(VerticalAnchor.m_158922_(80), VerticalAnchor.m_158922_(384))));
    public static final Holder<PlacedFeature> f_195335_ = PlacementUtils.m_206509_("ore_iron_middle", OreFeatures.f_195096_, OrePlacements.m_195343_(10, HeightRangePlacement.m_191692_(VerticalAnchor.m_158922_(-24), VerticalAnchor.m_158922_(56))));
    public static final Holder<PlacedFeature> f_195336_ = PlacementUtils.m_206509_("ore_iron_small", OreFeatures.f_195055_, OrePlacements.m_195343_(10, HeightRangePlacement.m_191680_(VerticalAnchor.m_158921_(), VerticalAnchor.m_158922_(72))));
    public static final Holder<PlacedFeature> f_195337_ = PlacementUtils.m_206509_("ore_gold_extra", OreFeatures.f_195056_, OrePlacements.m_195343_(50, HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(32), VerticalAnchor.m_158922_(256))));
    public static final Holder<PlacedFeature> f_195338_ = PlacementUtils.m_206509_("ore_gold", OreFeatures.f_195057_, OrePlacements.m_195343_(4, HeightRangePlacement.m_191692_(VerticalAnchor.m_158922_(-64), VerticalAnchor.m_158922_(32))));
    public static final Holder<PlacedFeature> f_195339_ = PlacementUtils.m_206509_("ore_gold_lower", OreFeatures.f_195057_, OrePlacements.m_195346_(CountPlacement.m_191630_(UniformInt.m_146622_(0, 1)), HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(-64), VerticalAnchor.m_158922_(-48))));
    public static final Holder<PlacedFeature> f_195340_ = PlacementUtils.m_206509_("ore_redstone", OreFeatures.f_195058_, OrePlacements.m_195343_(4, HeightRangePlacement.m_191680_(VerticalAnchor.m_158921_(), VerticalAnchor.m_158922_(15))));
    public static final Holder<PlacedFeature> f_195302_ = PlacementUtils.m_206509_("ore_redstone_lower", OreFeatures.f_195058_, OrePlacements.m_195343_(8, HeightRangePlacement.m_191692_(VerticalAnchor.m_158930_(-32), VerticalAnchor.m_158930_(32))));
    public static final Holder<PlacedFeature> f_195303_ = PlacementUtils.m_206509_("ore_diamond", OreFeatures.f_195059_, OrePlacements.m_195343_(7, HeightRangePlacement.m_191692_(VerticalAnchor.m_158930_(-80), VerticalAnchor.m_158930_(80))));
    public static final Holder<PlacedFeature> f_195304_ = PlacementUtils.m_206509_("ore_diamond_large", OreFeatures.f_195060_, OrePlacements.m_195349_(9, HeightRangePlacement.m_191692_(VerticalAnchor.m_158930_(-80), VerticalAnchor.m_158930_(80))));
    public static final Holder<PlacedFeature> f_195305_ = PlacementUtils.m_206509_("ore_diamond_buried", OreFeatures.f_195061_, OrePlacements.m_195343_(4, HeightRangePlacement.m_191692_(VerticalAnchor.m_158930_(-80), VerticalAnchor.m_158930_(80))));
    public static final Holder<PlacedFeature> f_195306_ = PlacementUtils.m_206509_("ore_lapis", OreFeatures.f_195062_, OrePlacements.m_195343_(2, HeightRangePlacement.m_191692_(VerticalAnchor.m_158922_(-32), VerticalAnchor.m_158922_(32))));
    public static final Holder<PlacedFeature> f_195307_ = PlacementUtils.m_206509_("ore_lapis_buried", OreFeatures.f_195063_, OrePlacements.m_195343_(4, HeightRangePlacement.m_191680_(VerticalAnchor.m_158921_(), VerticalAnchor.m_158922_(64))));
    public static final Holder<PlacedFeature> f_195308_ = PlacementUtils.m_206509_("ore_infested", OreFeatures.f_195064_, OrePlacements.m_195343_(14, HeightRangePlacement.m_191680_(VerticalAnchor.m_158921_(), VerticalAnchor.m_158922_(63))));
    public static final Holder<PlacedFeature> f_195309_ = PlacementUtils.m_206509_("ore_emerald", OreFeatures.f_195065_, OrePlacements.m_195343_(100, HeightRangePlacement.m_191692_(VerticalAnchor.m_158922_(-16), VerticalAnchor.m_158922_(480))));
    public static final Holder<PlacedFeature> f_195310_ = PlacementUtils.m_206513_("ore_ancient_debris_large", OreFeatures.f_195066_, InSquarePlacement.m_191715_(), HeightRangePlacement.m_191692_(VerticalAnchor.m_158922_(8), VerticalAnchor.m_158922_(24)), BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195311_ = PlacementUtils.m_206513_("ore_debris_small", OreFeatures.f_195067_, InSquarePlacement.m_191715_(), PlacementUtils.f_195358_, BiomeFilter.m_191561_());
    public static final Holder<PlacedFeature> f_195312_ = PlacementUtils.m_206509_("ore_copper", OreFeatures.f_195068_, OrePlacements.m_195343_(16, HeightRangePlacement.m_191692_(VerticalAnchor.m_158922_(-16), VerticalAnchor.m_158922_(112))));
    public static final Holder<PlacedFeature> f_195313_ = PlacementUtils.m_206509_("ore_copper_large", OreFeatures.f_195069_, OrePlacements.m_195343_(16, HeightRangePlacement.m_191692_(VerticalAnchor.m_158922_(-16), VerticalAnchor.m_158922_(112))));
    public static final Holder<PlacedFeature> f_195314_ = PlacementUtils.m_206509_("ore_clay", OreFeatures.f_195070_, OrePlacements.m_195343_(46, PlacementUtils.f_195360_));

    private static List<PlacementModifier> m_195346_(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.m_191715_(), p_195348_, BiomeFilter.m_191561_());
    }

    private static List<PlacementModifier> m_195343_(int p_195344_, PlacementModifier p_195345_) {
        return OrePlacements.m_195346_(CountPlacement.m_191628_(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> m_195349_(int p_195350_, PlacementModifier p_195351_) {
        return OrePlacements.m_195346_(RarityFilter.m_191900_(p_195350_), p_195351_);
    }
}

