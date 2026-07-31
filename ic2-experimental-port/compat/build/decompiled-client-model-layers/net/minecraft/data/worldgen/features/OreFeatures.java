/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.features;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class OreFeatures {
    public static final RuleTest f_195071_ = new TagMatchTest(BlockTags.f_13061_);
    public static final RuleTest f_195072_ = new TagMatchTest(BlockTags.f_144266_);
    public static final RuleTest f_195073_ = new TagMatchTest(BlockTags.f_144267_);
    public static final RuleTest f_195074_ = new BlockMatchTest(Blocks.f_50134_);
    public static final RuleTest f_195075_ = new TagMatchTest(BlockTags.f_13062_);
    public static final List<OreConfiguration.TargetBlockState> f_195076_ = List.of(OreConfiguration.m_161021_(f_195072_, Blocks.f_49996_.m_49966_()), OreConfiguration.m_161021_(f_195073_, Blocks.f_152468_.m_49966_()));
    public static final List<OreConfiguration.TargetBlockState> f_195077_ = List.of(OreConfiguration.m_161021_(f_195072_, Blocks.f_49995_.m_49966_()), OreConfiguration.m_161021_(f_195073_, Blocks.f_152467_.m_49966_()));
    public static final List<OreConfiguration.TargetBlockState> f_195078_ = List.of(OreConfiguration.m_161021_(f_195072_, Blocks.f_50089_.m_49966_()), OreConfiguration.m_161021_(f_195073_, Blocks.f_152474_.m_49966_()));
    public static final List<OreConfiguration.TargetBlockState> f_195079_ = List.of(OreConfiguration.m_161021_(f_195072_, Blocks.f_50059_.m_49966_()), OreConfiguration.m_161021_(f_195073_, Blocks.f_152472_.m_49966_()));
    public static final List<OreConfiguration.TargetBlockState> f_195080_ = List.of(OreConfiguration.m_161021_(f_195072_, Blocks.f_152505_.m_49966_()), OreConfiguration.m_161021_(f_195073_, Blocks.f_152506_.m_49966_()));
    public static final List<OreConfiguration.TargetBlockState> f_195081_ = List.of(OreConfiguration.m_161021_(f_195072_, Blocks.f_49997_.m_49966_()), OreConfiguration.m_161021_(f_195073_, Blocks.f_152469_.m_49966_()));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195082_ = FeatureUtils.m_206488_("ore_magma", Feature.f_65731_, new OreConfiguration(f_195074_, Blocks.f_50450_.m_49966_(), 33));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195083_ = FeatureUtils.m_206488_("ore_soul_sand", Feature.f_65731_, new OreConfiguration(f_195074_, Blocks.f_50135_.m_49966_(), 12));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195084_ = FeatureUtils.m_206488_("ore_nether_gold", Feature.f_65731_, new OreConfiguration(f_195074_, Blocks.f_49998_.m_49966_(), 10));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195085_ = FeatureUtils.m_206488_("ore_quartz", Feature.f_65731_, new OreConfiguration(f_195074_, Blocks.f_50331_.m_49966_(), 14));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195086_ = FeatureUtils.m_206488_("ore_gravel_nether", Feature.f_65731_, new OreConfiguration(f_195074_, Blocks.f_49994_.m_49966_(), 33));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195087_ = FeatureUtils.m_206488_("ore_blackstone", Feature.f_65731_, new OreConfiguration(f_195074_, Blocks.f_50730_.m_49966_(), 33));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195088_ = FeatureUtils.m_206488_("ore_dirt", Feature.f_65731_, new OreConfiguration(f_195071_, Blocks.f_50493_.m_49966_(), 33));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195089_ = FeatureUtils.m_206488_("ore_gravel", Feature.f_65731_, new OreConfiguration(f_195071_, Blocks.f_49994_.m_49966_(), 33));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195090_ = FeatureUtils.m_206488_("ore_granite", Feature.f_65731_, new OreConfiguration(f_195071_, Blocks.f_50122_.m_49966_(), 64));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195091_ = FeatureUtils.m_206488_("ore_diorite", Feature.f_65731_, new OreConfiguration(f_195071_, Blocks.f_50228_.m_49966_(), 64));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195092_ = FeatureUtils.m_206488_("ore_andesite", Feature.f_65731_, new OreConfiguration(f_195071_, Blocks.f_50334_.m_49966_(), 64));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195093_ = FeatureUtils.m_206488_("ore_tuff", Feature.f_65731_, new OreConfiguration(f_195071_, Blocks.f_152496_.m_49966_(), 64));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195094_ = FeatureUtils.m_206488_("ore_coal", Feature.f_65731_, new OreConfiguration(f_195081_, 17));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195095_ = FeatureUtils.m_206488_("ore_coal_buried", Feature.f_65731_, new OreConfiguration(f_195081_, 17, 0.5f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195096_ = FeatureUtils.m_206488_("ore_iron", Feature.f_65731_, new OreConfiguration(f_195076_, 9));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195055_ = FeatureUtils.m_206488_("ore_iron_small", Feature.f_65731_, new OreConfiguration(f_195076_, 4));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195056_ = FeatureUtils.m_206488_("ore_gold", Feature.f_65731_, new OreConfiguration(f_195077_, 9));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195057_ = FeatureUtils.m_206488_("ore_gold_buried", Feature.f_65731_, new OreConfiguration(f_195077_, 9, 0.5f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195058_ = FeatureUtils.m_206488_("ore_redstone", Feature.f_65731_, new OreConfiguration(List.of(OreConfiguration.m_161021_(f_195072_, Blocks.f_50173_.m_49966_()), OreConfiguration.m_161021_(f_195073_, Blocks.f_152473_.m_49966_())), 8));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195059_ = FeatureUtils.m_206488_("ore_diamond_small", Feature.f_65731_, new OreConfiguration(f_195078_, 4, 0.5f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195060_ = FeatureUtils.m_206488_("ore_diamond_large", Feature.f_65731_, new OreConfiguration(f_195078_, 12, 0.7f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195061_ = FeatureUtils.m_206488_("ore_diamond_buried", Feature.f_65731_, new OreConfiguration(f_195078_, 8, 1.0f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195062_ = FeatureUtils.m_206488_("ore_lapis", Feature.f_65731_, new OreConfiguration(f_195079_, 7));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195063_ = FeatureUtils.m_206488_("ore_lapis_buried", Feature.f_65731_, new OreConfiguration(f_195079_, 7, 1.0f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195064_ = FeatureUtils.m_206488_("ore_infested", Feature.f_65731_, new OreConfiguration(List.of(OreConfiguration.m_161021_(f_195072_, Blocks.f_50226_.m_49966_()), OreConfiguration.m_161021_(f_195073_, Blocks.f_152596_.m_49966_())), 9));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195065_ = FeatureUtils.m_206488_("ore_emerald", Feature.f_65731_, new OreConfiguration(List.of(OreConfiguration.m_161021_(f_195072_, Blocks.f_50264_.m_49966_()), OreConfiguration.m_161021_(f_195073_, Blocks.f_152479_.m_49966_())), 3));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195066_ = FeatureUtils.m_206488_("ore_ancient_debris_large", Feature.f_159727_, new OreConfiguration(f_195075_, Blocks.f_50722_.m_49966_(), 3, 1.0f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195067_ = FeatureUtils.m_206488_("ore_ancient_debris_small", Feature.f_159727_, new OreConfiguration(f_195075_, Blocks.f_50722_.m_49966_(), 2, 1.0f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195068_ = FeatureUtils.m_206488_("ore_copper_small", Feature.f_65731_, new OreConfiguration(f_195080_, 10));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195069_ = FeatureUtils.m_206488_("ore_copper_large", Feature.f_65731_, new OreConfiguration(f_195080_, 20));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> f_195070_ = FeatureUtils.m_206488_("ore_clay", Feature.f_65731_, new OreConfiguration(f_195071_, Blocks.f_50129_.m_49966_(), 33));
}

