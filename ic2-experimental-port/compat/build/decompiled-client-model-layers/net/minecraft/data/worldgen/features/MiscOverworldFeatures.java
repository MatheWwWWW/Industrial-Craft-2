/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.features;

import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.material.Fluids;

public class MiscOverworldFeatures {
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> f_195010_ = FeatureUtils.m_206485_("ice_spike", Feature.f_65773_);
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> f_195011_ = FeatureUtils.m_206488_("ice_patch", Feature.f_65781_, new DiskConfiguration(RuleBasedBlockStateProvider.m_225936_(Blocks.f_50354_), BlockPredicate.m_198311_(List.of(Blocks.f_50493_, Blocks.f_50440_, Blocks.f_50599_, Blocks.f_50546_, Blocks.f_50195_, Blocks.f_50127_, Blocks.f_50126_)), UniformInt.m_146622_(2, 3), 1));
    public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> f_195012_ = FeatureUtils.m_206488_("forest_rock", Feature.f_65780_, new BlockStateConfiguration(Blocks.f_50079_.m_49966_()));
    public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> f_195013_ = FeatureUtils.m_206488_("iceberg_packed", Feature.f_65779_, new BlockStateConfiguration(Blocks.f_50354_.m_49966_()));
    public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> f_195014_ = FeatureUtils.m_206488_("iceberg_blue", Feature.f_65779_, new BlockStateConfiguration(Blocks.f_50568_.m_49966_()));
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> f_195015_ = FeatureUtils.m_206485_("blue_ice", Feature.f_65778_);
    public static final Holder<ConfiguredFeature<LakeFeature.Configuration, ?>> f_195016_ = FeatureUtils.m_206488_("lake_lava", Feature.f_65783_, new LakeFeature.Configuration(BlockStateProvider.m_191384_(Blocks.f_49991_.m_49966_()), BlockStateProvider.m_191384_(Blocks.f_50069_.m_49966_())));
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> f_195017_ = FeatureUtils.m_206488_("disk_clay", Feature.f_65781_, new DiskConfiguration(RuleBasedBlockStateProvider.m_225936_(Blocks.f_50129_), BlockPredicate.m_198311_(List.of(Blocks.f_50493_, Blocks.f_50129_)), UniformInt.m_146622_(2, 3), 1));
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> f_195018_ = FeatureUtils.m_206488_("disk_gravel", Feature.f_65781_, new DiskConfiguration(RuleBasedBlockStateProvider.m_225936_(Blocks.f_49994_), BlockPredicate.m_198311_(List.of(Blocks.f_50493_, Blocks.f_50440_)), UniformInt.m_146622_(2, 5), 2));
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> f_195019_ = FeatureUtils.m_206488_("disk_sand", Feature.f_65781_, new DiskConfiguration(new RuleBasedBlockStateProvider(BlockStateProvider.m_191382_(Blocks.f_49992_), List.of(new RuleBasedBlockStateProvider.Rule(BlockPredicate.m_224774_(Direction.DOWN.m_122436_(), Blocks.f_50016_), BlockStateProvider.m_191382_(Blocks.f_50062_)))), BlockPredicate.m_198311_(List.of(Blocks.f_50493_, Blocks.f_50440_)), UniformInt.m_146622_(2, 6), 2));
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> f_195020_ = FeatureUtils.m_206485_("freeze_top_layer", Feature.f_65775_);
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> f_236760_ = FeatureUtils.m_206488_("disk_grass", Feature.f_65781_, new DiskConfiguration(new RuleBasedBlockStateProvider(BlockStateProvider.m_191382_(Blocks.f_50493_), List.of(new RuleBasedBlockStateProvider.Rule(BlockPredicate.m_190402_(BlockPredicate.m_190420_(BlockPredicate.m_190423_(Direction.UP.m_122436_()), BlockPredicate.m_224777_(Direction.UP.m_122436_(), Fluids.f_76193_))), BlockStateProvider.m_191382_(Blocks.f_50440_)))), BlockPredicate.m_198311_(List.of(Blocks.f_50493_, Blocks.f_220864_)), UniformInt.m_146622_(2, 6), 2));
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> f_195021_ = FeatureUtils.m_206485_("bonus_chest", Feature.f_65751_);
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> f_195022_ = FeatureUtils.m_206485_("void_start_platform", Feature.f_65768_);
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> f_195023_ = FeatureUtils.m_206485_("desert_well", Feature.f_65769_);
    public static final Holder<ConfiguredFeature<SpringConfiguration, ?>> f_195024_ = FeatureUtils.m_206488_("spring_lava_overworld", Feature.f_65765_, new SpringConfiguration(Fluids.f_76195_.m_76145_(), true, 4, 1, HolderSet.m_205806_(Block::m_204297_, Blocks.f_50069_, Blocks.f_50122_, Blocks.f_50228_, Blocks.f_50334_, Blocks.f_152550_, Blocks.f_152496_, Blocks.f_152497_, Blocks.f_50493_)));
    public static final Holder<ConfiguredFeature<SpringConfiguration, ?>> f_195025_ = FeatureUtils.m_206488_("spring_lava_frozen", Feature.f_65765_, new SpringConfiguration(Fluids.f_76195_.m_76145_(), true, 4, 1, HolderSet.m_205806_(Block::m_204297_, Blocks.f_50127_, Blocks.f_152499_, Blocks.f_50354_)));
    public static final Holder<ConfiguredFeature<SpringConfiguration, ?>> f_195026_ = FeatureUtils.m_206488_("spring_water", Feature.f_65765_, new SpringConfiguration(Fluids.f_76193_.m_76145_(), true, 4, 1, HolderSet.m_205806_(Block::m_204297_, Blocks.f_50069_, Blocks.f_50122_, Blocks.f_50228_, Blocks.f_50334_, Blocks.f_152550_, Blocks.f_152496_, Blocks.f_152497_, Blocks.f_50493_, Blocks.f_50127_, Blocks.f_152499_, Blocks.f_50354_)));
}

