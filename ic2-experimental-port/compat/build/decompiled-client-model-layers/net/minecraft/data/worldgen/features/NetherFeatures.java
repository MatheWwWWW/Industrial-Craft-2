/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.features;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TwistingVinesConfig;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.material.Fluids;

public class NetherFeatures {
    public static final Holder<ConfiguredFeature<DeltaFeatureConfiguration, ?>> f_195029_ = FeatureUtils.m_206488_("delta", Feature.f_65748_, new DeltaFeatureConfiguration(Blocks.f_49991_.m_49966_(), Blocks.f_50450_.m_49966_(), UniformInt.m_146622_(3, 7), UniformInt.m_146622_(0, 2)));
    public static final Holder<ConfiguredFeature<ColumnFeatureConfiguration, ?>> f_195030_ = FeatureUtils.m_206488_("small_basalt_columns", Feature.f_65747_, new ColumnFeatureConfiguration(ConstantInt.m_146483_(1), UniformInt.m_146622_(1, 4)));
    public static final Holder<ConfiguredFeature<ColumnFeatureConfiguration, ?>> f_195031_ = FeatureUtils.m_206488_("large_basalt_columns", Feature.f_65747_, new ColumnFeatureConfiguration(UniformInt.m_146622_(2, 3), UniformInt.m_146622_(5, 10)));
    public static final Holder<ConfiguredFeature<ReplaceSphereConfiguration, ?>> f_195032_ = FeatureUtils.m_206488_("basalt_blobs", Feature.f_65749_, new ReplaceSphereConfiguration(Blocks.f_50134_.m_49966_(), Blocks.f_50137_.m_49966_(), UniformInt.m_146622_(3, 7)));
    public static final Holder<ConfiguredFeature<ReplaceSphereConfiguration, ?>> f_195033_ = FeatureUtils.m_206488_("blackstone_blobs", Feature.f_65749_, new ReplaceSphereConfiguration(Blocks.f_50134_.m_49966_(), Blocks.f_50730_.m_49966_(), UniformInt.m_146622_(3, 7)));
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> f_195034_ = FeatureUtils.m_206485_("glowstone_extra", Feature.f_65774_);
    public static final WeightedStateProvider f_195035_ = new WeightedStateProvider(SimpleWeightedRandomList.m_146263_().m_146271_(Blocks.f_50654_.m_49966_(), 87).m_146271_(Blocks.f_50700_.m_49966_(), 11).m_146271_(Blocks.f_50691_.m_49966_(), 1));
    public static final Holder<ConfiguredFeature<NetherForestVegetationConfig, ?>> f_195036_ = FeatureUtils.m_206488_("crimson_forest_vegetation", Feature.f_65744_, new NetherForestVegetationConfig(f_195035_, 8, 4));
    public static final Holder<ConfiguredFeature<NetherForestVegetationConfig, ?>> f_195037_ = FeatureUtils.m_206488_("crimson_forest_vegetation_bonemeal", Feature.f_65744_, new NetherForestVegetationConfig(f_195035_, 3, 1));
    public static final WeightedStateProvider f_195038_ = new WeightedStateProvider(SimpleWeightedRandomList.m_146263_().m_146271_(Blocks.f_50693_.m_49966_(), 85).m_146271_(Blocks.f_50654_.m_49966_(), 1).m_146271_(Blocks.f_50691_.m_49966_(), 13).m_146271_(Blocks.f_50700_.m_49966_(), 1));
    public static final Holder<ConfiguredFeature<NetherForestVegetationConfig, ?>> f_195039_ = FeatureUtils.m_206488_("warped_forest_vegetation", Feature.f_65744_, new NetherForestVegetationConfig(f_195038_, 8, 4));
    public static final Holder<ConfiguredFeature<NetherForestVegetationConfig, ?>> f_195040_ = FeatureUtils.m_206488_("warped_forest_vegetation_bonemeal", Feature.f_65744_, new NetherForestVegetationConfig(f_195038_, 3, 1));
    public static final Holder<ConfiguredFeature<NetherForestVegetationConfig, ?>> f_195041_ = FeatureUtils.m_206488_("nether_sprouts", Feature.f_65744_, new NetherForestVegetationConfig(BlockStateProvider.m_191382_(Blocks.f_50694_), 8, 4));
    public static final Holder<ConfiguredFeature<NetherForestVegetationConfig, ?>> f_195042_ = FeatureUtils.m_206488_("nether_sprouts_bonemeal", Feature.f_65744_, new NetherForestVegetationConfig(BlockStateProvider.m_191382_(Blocks.f_50694_), 3, 1));
    public static final Holder<ConfiguredFeature<TwistingVinesConfig, ?>> f_195043_ = FeatureUtils.m_206488_("twisting_vines", Feature.f_65746_, new TwistingVinesConfig(8, 4, 8));
    public static final Holder<ConfiguredFeature<TwistingVinesConfig, ?>> f_195044_ = FeatureUtils.m_206488_("twisting_vines_bonemeal", Feature.f_65746_, new TwistingVinesConfig(3, 1, 2));
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> f_195045_ = FeatureUtils.m_206485_("weeping_vines", Feature.f_65745_);
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> f_195046_ = FeatureUtils.m_206488_("patch_crimson_roots", Feature.f_65763_, FeatureUtils.m_206473_(Feature.f_65741_, new SimpleBlockConfiguration(BlockStateProvider.m_191382_(Blocks.f_50654_))));
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> f_195047_ = FeatureUtils.m_206485_("basalt_pillar", Feature.f_65752_);
    public static final Holder<ConfiguredFeature<SpringConfiguration, ?>> f_195048_ = FeatureUtils.m_206488_("spring_lava_nether", Feature.f_65765_, new SpringConfiguration(Fluids.f_76195_.m_76145_(), true, 4, 1, HolderSet.m_205806_(Block::m_204297_, Blocks.f_50134_, Blocks.f_50135_, Blocks.f_49994_, Blocks.f_50450_, Blocks.f_50730_)));
    public static final Holder<ConfiguredFeature<SpringConfiguration, ?>> f_195049_ = FeatureUtils.m_206488_("spring_nether_closed", Feature.f_65765_, new SpringConfiguration(Fluids.f_76195_.m_76145_(), false, 5, 0, HolderSet.m_205806_(Block::m_204297_, Blocks.f_50134_)));
    public static final Holder<ConfiguredFeature<SpringConfiguration, ?>> f_195050_ = FeatureUtils.m_206488_("spring_nether_open", Feature.f_65765_, new SpringConfiguration(Fluids.f_76195_.m_76145_(), false, 4, 1, HolderSet.m_205806_(Block::m_204297_, Blocks.f_50134_)));
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> f_195051_ = FeatureUtils.m_206488_("patch_fire", Feature.f_65763_, FeatureUtils.m_206476_(Feature.f_65741_, new SimpleBlockConfiguration(BlockStateProvider.m_191382_(Blocks.f_50083_)), List.of(Blocks.f_50134_)));
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> f_195052_ = FeatureUtils.m_206488_("patch_soul_fire", Feature.f_65763_, FeatureUtils.m_206476_(Feature.f_65741_, new SimpleBlockConfiguration(BlockStateProvider.m_191382_(Blocks.f_50084_)), List.of(Blocks.f_50136_)));
}

