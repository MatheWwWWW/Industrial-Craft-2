/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.data.worldgen.features;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.CocoaDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;

public class TreeFeatures {
    public static final Holder<ConfiguredFeature<HugeFungusConfiguration, ?>> f_195117_ = FeatureUtils.m_206488_("crimson_fungus", Feature.f_65743_, new HugeFungusConfiguration(Blocks.f_50699_.m_49966_(), Blocks.f_50695_.m_49966_(), Blocks.f_50451_.m_49966_(), Blocks.f_50701_.m_49966_(), false));
    public static final Holder<ConfiguredFeature<HugeFungusConfiguration, ?>> f_195118_ = FeatureUtils.m_206488_("crimson_fungus_planted", Feature.f_65743_, new HugeFungusConfiguration(Blocks.f_50699_.m_49966_(), Blocks.f_50695_.m_49966_(), Blocks.f_50451_.m_49966_(), Blocks.f_50701_.m_49966_(), true));
    public static final Holder<ConfiguredFeature<HugeFungusConfiguration, ?>> f_195119_ = FeatureUtils.m_206488_("warped_fungus", Feature.f_65743_, new HugeFungusConfiguration(Blocks.f_50690_.m_49966_(), Blocks.f_50686_.m_49966_(), Blocks.f_50692_.m_49966_(), Blocks.f_50701_.m_49966_(), false));
    public static final Holder<ConfiguredFeature<HugeFungusConfiguration, ?>> f_195120_ = FeatureUtils.m_206488_("warped_fungus_planted", Feature.f_65743_, new HugeFungusConfiguration(Blocks.f_50690_.m_49966_(), Blocks.f_50686_.m_49966_(), Blocks.f_50692_.m_49966_(), Blocks.f_50701_.m_49966_(), true));
    public static final Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> f_195121_ = FeatureUtils.m_206488_("huge_brown_mushroom", Feature.f_65772_, new HugeMushroomFeatureConfiguration(BlockStateProvider.m_191384_((BlockState)((BlockState)Blocks.f_50180_.m_49966_().m_61124_(HugeMushroomBlock.f_54131_, true)).m_61124_(HugeMushroomBlock.f_54132_, false)), BlockStateProvider.m_191384_((BlockState)((BlockState)Blocks.f_50182_.m_49966_().m_61124_(HugeMushroomBlock.f_54131_, false)).m_61124_(HugeMushroomBlock.f_54132_, false)), 3));
    public static final Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> f_195122_ = FeatureUtils.m_206488_("huge_red_mushroom", Feature.f_65771_, new HugeMushroomFeatureConfiguration(BlockStateProvider.m_191384_((BlockState)Blocks.f_50181_.m_49966_().m_61124_(HugeMushroomBlock.f_54132_, false)), BlockStateProvider.m_191384_((BlockState)((BlockState)Blocks.f_50182_.m_49966_().m_61124_(HugeMushroomBlock.f_54131_, false)).m_61124_(HugeMushroomBlock.f_54132_, false)), 2));
    private static final BeehiveDecorator f_195113_ = new BeehiveDecorator(0.002f);
    private static final BeehiveDecorator f_236761_ = new BeehiveDecorator(0.01f);
    private static final BeehiveDecorator f_195114_ = new BeehiveDecorator(0.02f);
    private static final BeehiveDecorator f_195115_ = new BeehiveDecorator(0.05f);
    private static final BeehiveDecorator f_195116_ = new BeehiveDecorator(1.0f);
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195123_ = FeatureUtils.m_206488_("oak", Feature.f_65760_, TreeFeatures.m_195145_().m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195124_ = FeatureUtils.m_206488_("dark_oak", Feature.f_65760_, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.m_191382_(Blocks.f_50004_), new DarkOakTrunkPlacer(6, 2, 1), BlockStateProvider.m_191382_(Blocks.f_50055_), new DarkOakFoliagePlacer(ConstantInt.m_146483_(0), ConstantInt.m_146483_(0)), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())).m_68244_().m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195125_ = FeatureUtils.m_206488_("birch", Feature.f_65760_, TreeFeatures.m_195153_().m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195126_ = FeatureUtils.m_206488_("acacia", Feature.f_65760_, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.m_191382_(Blocks.f_50003_), new ForkingTrunkPlacer(5, 2, 2), BlockStateProvider.m_191382_(Blocks.f_50054_), new AcaciaFoliagePlacer(ConstantInt.m_146483_(2), ConstantInt.m_146483_(0)), new TwoLayersFeatureSize(1, 0, 2)).m_68244_().m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195127_ = FeatureUtils.m_206488_("spruce", Feature.f_65760_, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.m_191382_(Blocks.f_50000_), new StraightTrunkPlacer(5, 2, 1), BlockStateProvider.m_191382_(Blocks.f_50051_), new SpruceFoliagePlacer(UniformInt.m_146622_(2, 3), UniformInt.m_146622_(0, 2), UniformInt.m_146622_(1, 2)), new TwoLayersFeatureSize(2, 0, 2)).m_68244_().m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195128_ = FeatureUtils.m_206488_("pine", Feature.f_65760_, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.m_191382_(Blocks.f_50000_), new StraightTrunkPlacer(6, 4, 0), BlockStateProvider.m_191382_(Blocks.f_50051_), new PineFoliagePlacer(ConstantInt.m_146483_(1), ConstantInt.m_146483_(1), UniformInt.m_146622_(3, 4)), new TwoLayersFeatureSize(2, 0, 2)).m_68244_().m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195129_ = FeatureUtils.m_206488_("jungle_tree", Feature.f_65760_, TreeFeatures.m_195155_().m_68249_((List<TreeDecorator>)ImmutableList.of((Object)new CocoaDecorator(0.2f), (Object)TrunkVineDecorator.f_70056_, (Object)new LeaveVineDecorator(0.25f))).m_68244_().m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195130_ = FeatureUtils.m_206488_("fancy_oak", Feature.f_65760_, TreeFeatures.m_195156_().m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195131_ = FeatureUtils.m_206488_("jungle_tree_no_vine", Feature.f_65760_, TreeFeatures.m_195155_().m_68244_().m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195132_ = FeatureUtils.m_206488_("mega_jungle_tree", Feature.f_65760_, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.m_191382_(Blocks.f_50002_), new MegaJungleTrunkPlacer(10, 2, 19), BlockStateProvider.m_191382_(Blocks.f_50053_), new MegaJungleFoliagePlacer(ConstantInt.m_146483_(2), ConstantInt.m_146483_(0), 2), new TwoLayersFeatureSize(1, 1, 2)).m_68249_((List<TreeDecorator>)ImmutableList.of((Object)TrunkVineDecorator.f_70056_, (Object)new LeaveVineDecorator(0.25f))).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195133_ = FeatureUtils.m_206488_("mega_spruce", Feature.f_65760_, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.m_191382_(Blocks.f_50000_), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.m_191382_(Blocks.f_50051_), new MegaPineFoliagePlacer(ConstantInt.m_146483_(0), ConstantInt.m_146483_(0), UniformInt.m_146622_(13, 17)), new TwoLayersFeatureSize(1, 1, 2)).m_68249_((List<TreeDecorator>)ImmutableList.of((Object)new AlterGroundDecorator(BlockStateProvider.m_191382_(Blocks.f_50599_)))).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195134_ = FeatureUtils.m_206488_("mega_pine", Feature.f_65760_, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.m_191382_(Blocks.f_50000_), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.m_191382_(Blocks.f_50051_), new MegaPineFoliagePlacer(ConstantInt.m_146483_(0), ConstantInt.m_146483_(0), UniformInt.m_146622_(3, 7)), new TwoLayersFeatureSize(1, 1, 2)).m_68249_((List<TreeDecorator>)ImmutableList.of((Object)new AlterGroundDecorator(BlockStateProvider.m_191382_(Blocks.f_50599_)))).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195135_ = FeatureUtils.m_206488_("super_birch_bees_0002", Feature.f_65760_, TreeFeatures.m_195154_().m_68249_((List<TreeDecorator>)ImmutableList.of((Object)f_195113_)).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195136_ = FeatureUtils.m_206488_("super_birch_bees", Feature.f_65760_, TreeFeatures.m_195154_().m_68249_((List<TreeDecorator>)ImmutableList.of((Object)f_195116_)).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195137_ = FeatureUtils.m_206488_("swamp_oak", Feature.f_65760_, TreeFeatures.m_195146_(Blocks.f_49999_, Blocks.f_50050_, 5, 3, 0, 3).m_68249_((List<TreeDecorator>)ImmutableList.of((Object)new LeaveVineDecorator(0.25f))).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195138_ = FeatureUtils.m_206488_("jungle_bush", Feature.f_65760_, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.m_191382_(Blocks.f_50002_), new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.m_191382_(Blocks.f_50050_), new BushFoliagePlacer(ConstantInt.m_146483_(2), ConstantInt.m_146483_(1), 2), new TwoLayersFeatureSize(0, 0, 0)).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195139_ = FeatureUtils.m_206488_("azalea_tree", Feature.f_65760_, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.m_191382_(Blocks.f_49999_), new BendingTrunkPlacer(4, 2, 0, 3, UniformInt.m_146622_(1, 2)), new WeightedStateProvider(SimpleWeightedRandomList.m_146263_().m_146271_(Blocks.f_152470_.m_49966_(), 3).m_146271_(Blocks.f_152471_.m_49966_(), 1)), new RandomSpreadFoliagePlacer(ConstantInt.m_146483_(3), ConstantInt.m_146483_(0), ConstantInt.m_146483_(2), 50), new TwoLayersFeatureSize(1, 0, 1)).m_161260_(BlockStateProvider.m_191382_(Blocks.f_152549_)).m_161262_().m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_236762_ = FeatureUtils.m_206488_("mangrove", Feature.f_65760_, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.m_191382_(Blocks.f_220832_), new UpwardsBranchingTrunkPlacer(2, 1, 4, UniformInt.m_146622_(1, 4), 0.5f, UniformInt.m_146622_(0, 1), Registry.f_122824_.m_203561_(BlockTags.f_215829_)), BlockStateProvider.m_191382_(Blocks.f_220838_), new RandomSpreadFoliagePlacer(ConstantInt.m_146483_(3), ConstantInt.m_146483_(0), ConstantInt.m_146483_(2), 70), Optional.of(new MangroveRootPlacer(UniformInt.m_146622_(1, 3), BlockStateProvider.m_191382_(Blocks.f_220833_), Optional.of(new AboveRootPlacement(BlockStateProvider.m_191382_(Blocks.f_152543_), 0.5f)), new MangroveRootPlacement(Registry.f_122824_.m_203561_(BlockTags.f_215830_), HolderSet.m_205806_(Block::m_204297_, Blocks.f_220864_, Blocks.f_220834_), BlockStateProvider.m_191382_(Blocks.f_220834_), 8, 15, 0.2f))), new TwoLayersFeatureSize(2, 0, 2)).m_68249_(List.of(new LeaveVineDecorator(0.125f), new AttachedToLeavesDecorator(0.14f, 1, 0, new RandomizedIntStateProvider((BlockStateProvider)BlockStateProvider.m_191384_((BlockState)Blocks.f_220831_.m_49966_().m_61124_(MangrovePropaguleBlock.f_221443_, true)), MangrovePropaguleBlock.f_221441_, (IntProvider)UniformInt.m_146622_(0, 4)), 2, List.of(Direction.DOWN)), f_236761_)).m_68244_().m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_236763_ = FeatureUtils.m_206488_("tall_mangrove", Feature.f_65760_, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.m_191382_(Blocks.f_220832_), new UpwardsBranchingTrunkPlacer(4, 1, 9, UniformInt.m_146622_(1, 6), 0.5f, UniformInt.m_146622_(0, 1), Registry.f_122824_.m_203561_(BlockTags.f_215829_)), BlockStateProvider.m_191382_(Blocks.f_220838_), new RandomSpreadFoliagePlacer(ConstantInt.m_146483_(3), ConstantInt.m_146483_(0), ConstantInt.m_146483_(2), 70), Optional.of(new MangroveRootPlacer(UniformInt.m_146622_(3, 7), BlockStateProvider.m_191382_(Blocks.f_220833_), Optional.of(new AboveRootPlacement(BlockStateProvider.m_191382_(Blocks.f_152543_), 0.5f)), new MangroveRootPlacement(Registry.f_122824_.m_203561_(BlockTags.f_215830_), HolderSet.m_205806_(Block::m_204297_, Blocks.f_220864_, Blocks.f_220834_), BlockStateProvider.m_191382_(Blocks.f_220834_), 8, 15, 0.2f))), new TwoLayersFeatureSize(3, 0, 2)).m_68249_(List.of(new LeaveVineDecorator(0.125f), new AttachedToLeavesDecorator(0.14f, 1, 0, new RandomizedIntStateProvider((BlockStateProvider)BlockStateProvider.m_191384_((BlockState)Blocks.f_220831_.m_49966_().m_61124_(MangrovePropaguleBlock.f_221443_, true)), MangrovePropaguleBlock.f_221441_, (IntProvider)UniformInt.m_146622_(0, 4)), 2, List.of(Direction.DOWN)), f_236761_)).m_68244_().m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195140_ = FeatureUtils.m_206488_("oak_bees_0002", Feature.f_65760_, TreeFeatures.m_195145_().m_68249_(List.of(f_195113_)).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195141_ = FeatureUtils.m_206488_("oak_bees_002", Feature.f_65760_, TreeFeatures.m_195145_().m_68249_(List.of(f_195114_)).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195142_ = FeatureUtils.m_206488_("oak_bees_005", Feature.f_65760_, TreeFeatures.m_195145_().m_68249_(List.of(f_195115_)).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195106_ = FeatureUtils.m_206488_("birch_bees_0002", Feature.f_65760_, TreeFeatures.m_195153_().m_68249_(List.of(f_195113_)).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195107_ = FeatureUtils.m_206488_("birch_bees_002", Feature.f_65760_, TreeFeatures.m_195153_().m_68249_(List.of(f_195114_)).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195108_ = FeatureUtils.m_206488_("birch_bees_005", Feature.f_65760_, TreeFeatures.m_195153_().m_68249_(List.of(f_195115_)).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195109_ = FeatureUtils.m_206488_("fancy_oak_bees_0002", Feature.f_65760_, TreeFeatures.m_195156_().m_68249_(List.of(f_195113_)).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195110_ = FeatureUtils.m_206488_("fancy_oak_bees_002", Feature.f_65760_, TreeFeatures.m_195156_().m_68249_(List.of(f_195114_)).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195111_ = FeatureUtils.m_206488_("fancy_oak_bees_005", Feature.f_65760_, TreeFeatures.m_195156_().m_68249_(List.of(f_195115_)).m_68251_());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> f_195112_ = FeatureUtils.m_206488_("fancy_oak_bees", Feature.f_65760_, TreeFeatures.m_195156_().m_68249_(List.of(f_195116_)).m_68251_());

    private static TreeConfiguration.TreeConfigurationBuilder m_195146_(Block p_195147_, Block p_195148_, int p_195149_, int p_195150_, int p_195151_, int p_195152_) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.m_191382_(p_195147_), new StraightTrunkPlacer(p_195149_, p_195150_, p_195151_), BlockStateProvider.m_191382_(p_195148_), new BlobFoliagePlacer(ConstantInt.m_146483_(p_195152_), ConstantInt.m_146483_(0), 3), new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeConfiguration.TreeConfigurationBuilder m_195145_() {
        return TreeFeatures.m_195146_(Blocks.f_49999_, Blocks.f_50050_, 4, 2, 0, 2).m_68244_();
    }

    private static TreeConfiguration.TreeConfigurationBuilder m_195153_() {
        return TreeFeatures.m_195146_(Blocks.f_50001_, Blocks.f_50052_, 5, 2, 0, 2).m_68244_();
    }

    private static TreeConfiguration.TreeConfigurationBuilder m_195154_() {
        return TreeFeatures.m_195146_(Blocks.f_50001_, Blocks.f_50052_, 5, 2, 6, 2).m_68244_();
    }

    private static TreeConfiguration.TreeConfigurationBuilder m_195155_() {
        return TreeFeatures.m_195146_(Blocks.f_50002_, Blocks.f_50053_, 4, 8, 0, 2);
    }

    private static TreeConfiguration.TreeConfigurationBuilder m_195156_() {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.m_191382_(Blocks.f_49999_), new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.m_191382_(Blocks.f_50050_), new FancyFoliagePlacer(ConstantInt.m_146483_(2), ConstantInt.m_146483_(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).m_68244_();
    }
}

