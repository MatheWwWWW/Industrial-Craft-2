/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Holder
 *  net.minecraft.util.random.SimpleWeightedRandomList
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.GenerationStep$Decoration
 *  net.minecraft.world.level.levelgen.feature.ConfiguredFeature
 *  net.minecraft.world.level.levelgen.feature.Feature
 *  net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
 *  net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration
 *  net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration$TreeConfigurationBuilder
 *  net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize
 *  net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize
 *  net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
 *  net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
 *  net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider
 *  net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer
 *  net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer
 */
package ic2.core.world;

import ic2.core.IC2;
import ic2.core.block.misc.RubberLogBlock;
import ic2.core.proxy.EnvProxy;
import ic2.core.ref.Ic2Blocks;
import ic2.core.world.RubberTreeFoliagePlacer;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.Holder;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

public class Ic2WorldGen {
    private static final WeightedStateProvider RUBBER_LOG_PROVIDER = new WeightedStateProvider(SimpleWeightedRandomList.m_146263_().m_146271_((Object)Ic2Blocks.RUBBER_LOG.m_49966_(), 16).m_146271_((Object)((BlockState)Ic2Blocks.RUBBER_LOG.m_49966_().m_61124_(RubberLogBlock.stateProperty, (Comparable)((Object)RubberLogBlock.RubberWoodState.wet_north))), 1).m_146271_((Object)((BlockState)Ic2Blocks.RUBBER_LOG.m_49966_().m_61124_(RubberLogBlock.stateProperty, (Comparable)((Object)RubberLogBlock.RubberWoodState.wet_east))), 1).m_146271_((Object)((BlockState)Ic2Blocks.RUBBER_LOG.m_49966_().m_61124_(RubberLogBlock.stateProperty, (Comparable)((Object)RubberLogBlock.RubberWoodState.wet_south))), 1).m_146271_((Object)((BlockState)Ic2Blocks.RUBBER_LOG.m_49966_().m_61124_(RubberLogBlock.stateProperty, (Comparable)((Object)RubberLogBlock.RubberWoodState.wet_west))), 1));
    public static final CompletableFuture<Holder<ConfiguredFeature<TreeConfiguration, ?>>> RUBBER_TREE = Ic2WorldGen.register("rubber_tree", Feature.f_65760_, new TreeConfiguration.TreeConfigurationBuilder((BlockStateProvider)RUBBER_LOG_PROVIDER, (TrunkPlacer)new StraightTrunkPlacer(4, 4, 0), (BlockStateProvider)BlockStateProvider.m_191382_((Block)Ic2Blocks.RUBBER_LEAVES), (FoliagePlacer)RubberTreeFoliagePlacer.INSTANCE, (FeatureSize)new TwoLayersFeatureSize(1, 0, 1)).m_68244_().m_68251_());

    public static void init() {
        Ic2WorldGen.attachOreFeatureToBiome("ore_lead");
        Ic2WorldGen.attachOreFeatureToBiome("ore_lead_lower");
        Ic2WorldGen.attachOreFeatureToBiome("ore_tin_small");
        Ic2WorldGen.attachOreFeatureToBiome("ore_tin_upper");
        Ic2WorldGen.attachOreFeatureToBiome("ore_uranium");
        Ic2WorldGen.attachOreFeatureToBiome("ore_uranium_buried");
        Ic2WorldGen.attachOreFeatureToBiome("ore_uranium_large");
        Ic2WorldGen.attachRubberTreeFeatureToBiome("trees_rubber_jungle", EnvProxy.BiomeSelector.JUNGLE);
        Ic2WorldGen.attachRubberTreeFeatureToBiome("trees_rubber_forest", EnvProxy.BiomeSelector.FOREST);
        Ic2WorldGen.attachRubberTreeFeatureToBiome("trees_rubber_swamp", EnvProxy.BiomeSelector.SWAMP);
        RubberTreeFoliagePlacer.init();
    }

    private static void attachOreFeatureToBiome(String string) {
        IC2.envProxy.attachPlacedFeatureToBiome(IC2.getIdentifier(string), EnvProxy.BiomeSelector.OVERWORLD, GenerationStep.Decoration.UNDERGROUND_ORES);
    }

    private static void attachRubberTreeFeatureToBiome(String string, EnvProxy.BiomeSelector biomeSelector) {
        IC2.envProxy.attachPlacedFeatureToBiome(IC2.getIdentifier(string), biomeSelector, GenerationStep.Decoration.VEGETAL_DECORATION);
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> CompletableFuture<Holder<ConfiguredFeature<FC, ?>>> register(String string, F f, FC FC) {
        return IC2.envProxy.registerConfiguredFeature(IC2.getIdentifier(string), f, FC);
    }
}

