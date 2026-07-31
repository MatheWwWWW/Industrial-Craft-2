/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.features;

import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.SmallDripleafBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FossilFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.UnderwaterMagmaConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;

public class CaveFeatures {
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> f_194938_ = FeatureUtils.m_206485_("monster_room", Feature.f_65777_);
    private static final List<ResourceLocation> f_194959_ = List.of(new ResourceLocation("fossil/spine_1"), new ResourceLocation("fossil/spine_2"), new ResourceLocation("fossil/spine_3"), new ResourceLocation("fossil/spine_4"), new ResourceLocation("fossil/skull_1"), new ResourceLocation("fossil/skull_2"), new ResourceLocation("fossil/skull_3"), new ResourceLocation("fossil/skull_4"));
    private static final List<ResourceLocation> f_194960_ = List.of(new ResourceLocation("fossil/spine_1_coal"), new ResourceLocation("fossil/spine_2_coal"), new ResourceLocation("fossil/spine_3_coal"), new ResourceLocation("fossil/spine_4_coal"), new ResourceLocation("fossil/skull_1_coal"), new ResourceLocation("fossil/skull_2_coal"), new ResourceLocation("fossil/skull_3_coal"), new ResourceLocation("fossil/skull_4_coal"));
    public static final Holder<ConfiguredFeature<FossilFeatureConfiguration, ?>> f_194939_ = FeatureUtils.m_206488_("fossil_coal", Feature.f_65770_, new FossilFeatureConfiguration(f_194959_, f_194960_, ProcessorLists.f_177022_, ProcessorLists.f_177023_, 4));
    public static final Holder<ConfiguredFeature<FossilFeatureConfiguration, ?>> f_194940_ = FeatureUtils.m_206488_("fossil_diamonds", Feature.f_65770_, new FossilFeatureConfiguration(f_194959_, f_194960_, ProcessorLists.f_177022_, ProcessorLists.f_177024_, 4));
    public static final Holder<ConfiguredFeature<DripstoneClusterConfiguration, ?>> f_194941_ = FeatureUtils.m_206488_("dripstone_cluster", Feature.f_159729_, new DripstoneClusterConfiguration(12, UniformInt.m_146622_(3, 6), UniformInt.m_146622_(2, 8), 1, 3, UniformInt.m_146622_(2, 4), UniformFloat.m_146605_(0.3f, 0.7f), ClampedNormalFloat.m_146423_(0.1f, 0.3f, 0.1f, 0.9f), 0.1f, 3, 8));
    public static final Holder<ConfiguredFeature<LargeDripstoneConfiguration, ?>> f_194942_ = FeatureUtils.m_206488_("large_dripstone", Feature.f_159730_, new LargeDripstoneConfiguration(30, UniformInt.m_146622_(3, 19), UniformFloat.m_146605_(0.4f, 2.0f), 0.33f, UniformFloat.m_146605_(0.3f, 0.9f), UniformFloat.m_146605_(0.4f, 1.0f), UniformFloat.m_146605_(0.0f, 0.3f), 4, 0.6f));
    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> f_194943_ = FeatureUtils.m_206488_("pointed_dripstone", Feature.f_65755_, new SimpleRandomFeatureConfiguration(HolderSet.m_205809_(PlacementUtils.m_206502_(Feature.f_190874_, new PointedDripstoneConfiguration(0.2f, 0.7f, 0.5f, 0.5f), EnvironmentScanPlacement.m_191657_(Direction.DOWN, BlockPredicate.m_190432_(), BlockPredicate.f_190394_, 12), RandomOffsetPlacement.m_191877_(ConstantInt.m_146483_(1))), PlacementUtils.m_206502_(Feature.f_190874_, new PointedDripstoneConfiguration(0.2f, 0.7f, 0.5f, 0.5f), EnvironmentScanPlacement.m_191657_(Direction.UP, BlockPredicate.m_190432_(), BlockPredicate.f_190394_, 12), RandomOffsetPlacement.m_191877_(ConstantInt.m_146483_(-1))))));
    public static final Holder<ConfiguredFeature<UnderwaterMagmaConfiguration, ?>> f_194944_ = FeatureUtils.m_206488_("underwater_magma", Feature.f_159726_, new UnderwaterMagmaConfiguration(5, 1, 0.5f));
    private static final MultifaceBlock f_236672_ = (MultifaceBlock)Blocks.f_152475_;
    public static final Holder<ConfiguredFeature<MultifaceGrowthConfiguration, ?>> f_194945_ = FeatureUtils.m_206488_("glow_lichen", Feature.f_225026_, new MultifaceGrowthConfiguration(f_236672_, 20, false, true, true, 0.5f, HolderSet.m_205806_(Block::m_204297_, Blocks.f_50069_, Blocks.f_50334_, Blocks.f_50228_, Blocks.f_50122_, Blocks.f_152537_, Blocks.f_152497_, Blocks.f_152496_, Blocks.f_152550_)));
    public static final Holder<ConfiguredFeature<RootSystemConfiguration, ?>> f_194946_ = FeatureUtils.m_206488_("rooted_azalea_tree", Feature.f_159724_, new RootSystemConfiguration(PlacementUtils.m_206506_(TreeFeatures.f_195139_, new PlacementModifier[0]), 3, 3, BlockTags.f_198159_, BlockStateProvider.m_191382_(Blocks.f_152549_), 20, 100, 3, 2, BlockStateProvider.m_191382_(Blocks.f_152548_), 20, 2, BlockPredicate.m_190404_(BlockPredicate.m_190430_(BlockPredicate.m_198311_(List.of(Blocks.f_50016_, Blocks.f_50627_, Blocks.f_50626_, Blocks.f_49990_)), BlockPredicate.m_204677_(BlockTags.f_13035_), BlockPredicate.m_204677_(BlockTags.f_198158_)), BlockPredicate.m_224768_(Direction.DOWN.m_122436_(), BlockTags.f_198157_))));
    private static final WeightedStateProvider f_194961_ = new WeightedStateProvider(SimpleWeightedRandomList.m_146263_().m_146271_(Blocks.f_152539_.m_49966_(), 4).m_146271_((BlockState)Blocks.f_152539_.m_49966_().m_61124_(CaveVines.f_152949_, true), 1));
    private static final RandomizedIntStateProvider f_194962_ = new RandomizedIntStateProvider((BlockStateProvider)new WeightedStateProvider(SimpleWeightedRandomList.m_146263_().m_146271_(Blocks.f_152538_.m_49966_(), 4).m_146271_((BlockState)Blocks.f_152538_.m_49966_().m_61124_(CaveVines.f_152949_, true), 1)), CaveVinesBlock.f_53924_, (IntProvider)UniformInt.m_146622_(23, 25));
    public static final Holder<ConfiguredFeature<BlockColumnConfiguration, ?>> f_194947_ = FeatureUtils.m_206488_("cave_vine", Feature.f_190875_, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.m_191218_(new WeightedListInt(SimpleWeightedRandomList.m_146263_().m_146271_(UniformInt.m_146622_(0, 19), 2).m_146271_(UniformInt.m_146622_(0, 2), 3).m_146271_(UniformInt.m_146622_(0, 6), 10).m_146270_()), f_194961_), BlockColumnConfiguration.m_191218_(ConstantInt.m_146483_(1), f_194962_)), Direction.DOWN, BlockPredicate.f_190393_, true));
    public static final Holder<ConfiguredFeature<BlockColumnConfiguration, ?>> f_194948_ = FeatureUtils.m_206488_("cave_vine_in_moss", Feature.f_190875_, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.m_191218_(new WeightedListInt(SimpleWeightedRandomList.m_146263_().m_146271_(UniformInt.m_146622_(0, 3), 5).m_146271_(UniformInt.m_146622_(1, 7), 1).m_146270_()), f_194961_), BlockColumnConfiguration.m_191218_(ConstantInt.m_146483_(1), f_194962_)), Direction.DOWN, BlockPredicate.f_190393_, true));
    public static final Holder<ConfiguredFeature<SimpleBlockConfiguration, ?>> f_194949_ = FeatureUtils.m_206488_("moss_vegetation", Feature.f_65741_, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.m_146263_().m_146271_(Blocks.f_152542_.m_49966_(), 4).m_146271_(Blocks.f_152541_.m_49966_(), 7).m_146271_(Blocks.f_152543_.m_49966_(), 25).m_146271_(Blocks.f_50034_.m_49966_(), 50).m_146271_(Blocks.f_50359_.m_49966_(), 10))));
    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> f_194950_ = FeatureUtils.m_206488_("moss_patch", Feature.f_159734_, new VegetationPatchConfiguration(BlockTags.f_144276_, BlockStateProvider.m_191382_(Blocks.f_152544_), PlacementUtils.m_206506_(f_194949_, new PlacementModifier[0]), CaveSurface.FLOOR, ConstantInt.m_146483_(1), 0.0f, 5, 0.8f, UniformInt.m_146622_(4, 7), 0.3f));
    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> f_194951_ = FeatureUtils.m_206488_("moss_patch_bonemeal", Feature.f_159734_, new VegetationPatchConfiguration(BlockTags.f_144276_, BlockStateProvider.m_191382_(Blocks.f_152544_), PlacementUtils.m_206506_(f_194949_, new PlacementModifier[0]), CaveSurface.FLOOR, ConstantInt.m_146483_(1), 0.0f, 5, 0.6f, UniformInt.m_146622_(1, 2), 0.75f));
    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> f_194952_ = FeatureUtils.m_206488_("dripleaf", Feature.f_65755_, new SimpleRandomFeatureConfiguration(HolderSet.m_205809_(CaveFeatures.m_206466_(), CaveFeatures.m_206467_(Direction.EAST), CaveFeatures.m_206467_(Direction.WEST), CaveFeatures.m_206467_(Direction.SOUTH), CaveFeatures.m_206467_(Direction.NORTH))));
    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> f_194953_ = FeatureUtils.m_206488_("clay_with_dripleaves", Feature.f_159734_, new VegetationPatchConfiguration(BlockTags.f_144277_, BlockStateProvider.m_191382_(Blocks.f_50129_), PlacementUtils.m_206506_(f_194952_, new PlacementModifier[0]), CaveSurface.FLOOR, ConstantInt.m_146483_(3), 0.8f, 2, 0.05f, UniformInt.m_146622_(4, 7), 0.7f));
    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> f_194954_ = FeatureUtils.m_206488_("clay_pool_with_dripleaves", Feature.f_159735_, new VegetationPatchConfiguration(BlockTags.f_144277_, BlockStateProvider.m_191382_(Blocks.f_50129_), PlacementUtils.m_206506_(f_194952_, new PlacementModifier[0]), CaveSurface.FLOOR, ConstantInt.m_146483_(3), 0.8f, 5, 0.1f, UniformInt.m_146622_(4, 7), 0.7f));
    public static final Holder<ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> f_194955_ = FeatureUtils.m_206488_("lush_caves_clay", Feature.f_65756_, new RandomBooleanFeatureConfiguration(PlacementUtils.m_206506_(f_194953_, new PlacementModifier[0]), PlacementUtils.m_206506_(f_194954_, new PlacementModifier[0])));
    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> f_194956_ = FeatureUtils.m_206488_("moss_patch_ceiling", Feature.f_159734_, new VegetationPatchConfiguration(BlockTags.f_144276_, BlockStateProvider.m_191382_(Blocks.f_152544_), PlacementUtils.m_206506_(f_194948_, new PlacementModifier[0]), CaveSurface.CEILING, UniformInt.m_146622_(1, 2), 0.0f, 5, 0.08f, UniformInt.m_146622_(4, 7), 0.3f));
    public static final Holder<ConfiguredFeature<SimpleBlockConfiguration, ?>> f_194957_ = FeatureUtils.m_206488_("spore_blossom", Feature.f_65741_, new SimpleBlockConfiguration(BlockStateProvider.m_191382_(Blocks.f_152540_)));
    public static final Holder<ConfiguredFeature<GeodeConfiguration, ?>> f_194958_ = FeatureUtils.m_206488_("amethyst_geode", Feature.f_159728_, new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.m_191382_(Blocks.f_50016_), BlockStateProvider.m_191382_(Blocks.f_152490_), BlockStateProvider.m_191382_(Blocks.f_152491_), BlockStateProvider.m_191382_(Blocks.f_152497_), BlockStateProvider.m_191382_(Blocks.f_152597_), List.of(Blocks.f_152495_.m_49966_(), Blocks.f_152494_.m_49966_(), Blocks.f_152493_.m_49966_(), Blocks.f_152492_.m_49966_()), BlockTags.f_144287_, BlockTags.f_144289_), new GeodeLayerSettings(1.7, 2.2, 3.2, 4.2), new GeodeCrackSettings(0.95, 2.0, 2), 0.35, 0.083, true, UniformInt.m_146622_(4, 6), UniformInt.m_146622_(3, 4), UniformInt.m_146622_(1, 2), -16, 16, 0.05, 1));
    public static final Holder<ConfiguredFeature<SculkPatchConfiguration, ?>> f_236674_ = FeatureUtils.m_206488_("sculk_patch_deep_dark", Feature.f_225027_, new SculkPatchConfiguration(10, 32, 64, 0, 1, ConstantInt.m_146483_(0), 0.5f));
    public static final Holder<ConfiguredFeature<SculkPatchConfiguration, ?>> f_236675_ = FeatureUtils.m_206488_("sculk_patch_ancient_city", Feature.f_225027_, new SculkPatchConfiguration(10, 32, 64, 0, 1, UniformInt.m_146622_(1, 3), 0.5f));
    private static final MultifaceBlock f_236673_ = (MultifaceBlock)Blocks.f_220856_;
    public static final Holder<ConfiguredFeature<MultifaceGrowthConfiguration, ?>> f_236676_ = FeatureUtils.m_206488_("sculk_vein", Feature.f_225026_, new MultifaceGrowthConfiguration(f_236673_, 20, true, true, true, 1.0f, HolderSet.m_205806_(Block::m_204297_, Blocks.f_50069_, Blocks.f_50334_, Blocks.f_50228_, Blocks.f_50122_, Blocks.f_152537_, Blocks.f_152497_, Blocks.f_152496_, Blocks.f_152550_)));

    private static Holder<PlacedFeature> m_206467_(Direction p_206468_) {
        return PlacementUtils.m_206502_(Feature.f_190875_, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.m_191218_(new WeightedListInt(SimpleWeightedRandomList.m_146263_().m_146271_(UniformInt.m_146622_(0, 4), 2).m_146271_((UniformInt)((Object)ConstantInt.m_146483_(0)), 1).m_146270_()), BlockStateProvider.m_191384_((BlockState)Blocks.f_152546_.m_49966_().m_61124_(BlockStateProperties.f_61374_, p_206468_))), BlockColumnConfiguration.m_191218_(ConstantInt.m_146483_(1), BlockStateProvider.m_191384_((BlockState)Blocks.f_152545_.m_49966_().m_61124_(BlockStateProperties.f_61374_, p_206468_)))), Direction.UP, BlockPredicate.f_190394_, true), new PlacementModifier[0]);
    }

    private static Holder<PlacedFeature> m_206466_() {
        return PlacementUtils.m_206502_(Feature.f_65741_, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.m_146263_().m_146271_((BlockState)Blocks.f_152547_.m_49966_().m_61124_(SmallDripleafBlock.f_154577_, Direction.EAST), 1).m_146271_((BlockState)Blocks.f_152547_.m_49966_().m_61124_(SmallDripleafBlock.f_154577_, Direction.WEST), 1).m_146271_((BlockState)Blocks.f_152547_.m_49966_().m_61124_(SmallDripleafBlock.f_154577_, Direction.NORTH), 1).m_146271_((BlockState)Blocks.f_152547_.m_49966_().m_61124_(SmallDripleafBlock.f_154577_, Direction.SOUTH), 1))), new PlacementModifier[0]);
    }
}

