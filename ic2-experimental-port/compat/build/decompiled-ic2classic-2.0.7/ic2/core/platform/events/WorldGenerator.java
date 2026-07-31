/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Codec
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.core.Vec3i
 *  net.minecraft.data.BuiltinRegistries
 *  net.minecraft.data.worldgen.features.OreFeatures
 *  net.minecraft.data.worldgen.placement.PlacementUtils
 *  net.minecraft.data.worldgen.placement.VegetationPlacements
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.BiomeTags
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.GenerationStep$Decoration
 *  net.minecraft.world.level.levelgen.VerticalAnchor
 *  net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
 *  net.minecraft.world.level.levelgen.feature.ConfiguredFeature
 *  net.minecraft.world.level.levelgen.feature.Feature
 *  net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
 *  net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
 *  net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration$TargetBlockState
 *  net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration
 *  net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration$TreeConfigurationBuilder
 *  net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize
 *  net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize
 *  net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
 *  net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType
 *  net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
 *  net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer
 *  net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType
 *  net.minecraft.world.level.levelgen.placement.BiomeFilter
 *  net.minecraft.world.level.levelgen.placement.BlockPredicateFilter
 *  net.minecraft.world.level.levelgen.placement.CountPlacement
 *  net.minecraft.world.level.levelgen.placement.HeightRangePlacement
 *  net.minecraft.world.level.levelgen.placement.InSquarePlacement
 *  net.minecraft.world.level.levelgen.placement.PlacedFeature
 *  net.minecraft.world.level.levelgen.placement.PlacementModifier
 *  net.minecraft.world.level.levelgen.placement.RarityFilter
 *  net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
 *  net.minecraftforge.common.Tags$Biomes
 *  net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
 *  net.minecraftforge.common.world.BiomeModifier
 *  net.minecraftforge.common.world.BiomeModifier$Phase
 *  net.minecraftforge.common.world.ModifiableBiomeInfo$BiomeInfo$Builder
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.events;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import ic2.core.IC2;
import ic2.core.block.resource.rubber.RubberFoliagePlacer;
import ic2.core.block.resource.rubber.RubberTrunkPlacer;
import ic2.core.platform.corehacks.mixins.server.TrunkMixin;
import ic2.core.platform.registries.IC2Blocks;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;

public class WorldGenerator {
    static ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>> TIN_FEATURE;
    static ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>> URANIUM_FEATURE;
    static ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>> URANIUM_LARGE_FEATURE;
    static ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>> SILVER_FEATURE;
    static ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>> NETHER_ALUMINUM_FEATURE;
    static ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>> NETHER_SILVER_FEATURE;
    static Holder<PlacedFeature> ORE_TIN;
    static Holder<PlacedFeature> ORE_URANIUM;
    static Holder<PlacedFeature> ORE_URANIUM_DEEP;
    static Holder<PlacedFeature> ORE_SILVER;
    static Holder<PlacedFeature> NETHER_ORE_ALUMINUM;
    static Holder<PlacedFeature> NETHER_ORE_SILVER;
    static Holder<PlacedFeature> RUBBER_TREE;
    static ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>> RUBBER_TREE_FEATURE;
    public static ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>> RUBBER_TREE_SAPLING;
    public static TrunkPlacerType<RubberTrunkPlacer> RUBBER_TRUNK;
    public static FoliagePlacerType<RubberFoliagePlacer> RUBBER_FOLIAGE;

    public static void generateOres() {
        TIN_FEATURE = WorldGenerator.createOreFeature("ore_tin", 6, 0.0f, WorldGenerator.target(OreFeatures.f_195072_, IC2Blocks.TIN_ORE), WorldGenerator.target(OreFeatures.f_195073_, IC2Blocks.DEEPSLATE_TIN_ORE));
        URANIUM_FEATURE = WorldGenerator.createOreFeature("ore_uranium", 3, 0.0f, WorldGenerator.target(OreFeatures.f_195072_, IC2Blocks.URANIUM_ORE), WorldGenerator.target(OreFeatures.f_195073_, IC2Blocks.DEEPSLATE_URANIUM_ORE));
        URANIUM_LARGE_FEATURE = WorldGenerator.createOreFeature("ore_uranium_large", 15, 0.0f, WorldGenerator.target(OreFeatures.f_195072_, IC2Blocks.URANIUM_ORE), WorldGenerator.target(OreFeatures.f_195073_, IC2Blocks.DEEPSLATE_URANIUM_ORE));
        SILVER_FEATURE = WorldGenerator.createOreFeature("ore_silver", 5, 0.0f, WorldGenerator.target(OreFeatures.f_195072_, IC2Blocks.SILVER_ORE), WorldGenerator.target(OreFeatures.f_195073_, IC2Blocks.DEEPSLATE_SILVER_ORE));
        NETHER_ALUMINUM_FEATURE = WorldGenerator.createOreFeature("nether_ore_aluminum", 3, 0.5f, WorldGenerator.target(OreFeatures.f_195074_, IC2Blocks.ALUMINUM_ORE_NETHER));
        NETHER_SILVER_FEATURE = WorldGenerator.createOreFeature("nether_ore_silver", 3, 0.1f, WorldGenerator.target(OreFeatures.f_195074_, IC2Blocks.SILVER_ORE_NETHER));
        RUBBER_FOLIAGE = new FoliagePlacerType(RubberFoliagePlacer.ENCODER);
        ForgeRegistries.FOLIAGE_PLACER_TYPES.register(new ResourceLocation("ic2:rubber_leaves"), RUBBER_FOLIAGE);
        RUBBER_TRUNK = TrunkMixin.register("ic2:rubber_trunk", RubberTrunkPlacer.SERIALIZER);
        RUBBER_TREE_FEATURE = WorldGenerator.register(new ResourceLocation("ic2", "rubber_tree"), WorldGenerator.generateTree(false));
        RUBBER_TREE_SAPLING = WorldGenerator.generateTree(true);
        ORE_TIN = WorldGenerator.feature("ore_tin", TIN_FEATURE, List.of(CountPlacement.m_191628_((int)25), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191692_((VerticalAnchor)VerticalAnchor.m_158922_((int)-6), (VerticalAnchor)VerticalAnchor.m_158922_((int)122)), BiomeFilter.m_191561_()));
        ORE_URANIUM = WorldGenerator.feature("ore_uranium", URANIUM_FEATURE, List.of(CountPlacement.m_191628_((int)20), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191692_((VerticalAnchor)VerticalAnchor.m_158922_((int)-64), (VerticalAnchor)VerticalAnchor.m_158922_((int)16)), BiomeFilter.m_191561_()));
        ORE_URANIUM_DEEP = WorldGenerator.feature("ore_uranium_deep", URANIUM_LARGE_FEATURE, List.of(RarityFilter.m_191900_((int)128), CountPlacement.m_191628_((int)20), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191692_((VerticalAnchor)VerticalAnchor.m_158922_((int)-64), (VerticalAnchor)VerticalAnchor.m_158922_((int)-48)), BiomeFilter.m_191561_()));
        ORE_SILVER = WorldGenerator.feature("ore_silver", SILVER_FEATURE, List.of(CountPlacement.m_191628_((int)10), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191692_((VerticalAnchor)VerticalAnchor.m_158922_((int)-64), (VerticalAnchor)VerticalAnchor.m_158922_((int)22)), BiomeFilter.m_191561_()));
        NETHER_ORE_ALUMINUM = WorldGenerator.feature("ore_silver", NETHER_ALUMINUM_FEATURE, List.of(CountPlacement.m_191628_((int)10), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191680_((VerticalAnchor)VerticalAnchor.m_158922_((int)0), (VerticalAnchor)VerticalAnchor.m_158922_((int)128)), BiomeFilter.m_191561_()));
        NETHER_ORE_SILVER = WorldGenerator.feature("ore_silver", NETHER_SILVER_FEATURE, List.of(CountPlacement.m_191628_((int)10), InSquarePlacement.m_191715_(), HeightRangePlacement.m_191680_((VerticalAnchor)VerticalAnchor.m_158922_((int)0), (VerticalAnchor)VerticalAnchor.m_158922_((int)128)), BiomeFilter.m_191561_()));
        RUBBER_TREE = PlacementUtils.m_206513_((String)"rubber_trees", (Holder)Holder.m_205709_(RUBBER_TREE_FEATURE), (PlacementModifier[])new PlacementModifier[]{CountPlacement.m_191628_((int)10), InSquarePlacement.m_191715_(), VegetationPlacements.f_195420_, PlacementUtils.f_195355_, BlockPredicateFilter.m_191576_((BlockPredicate)BlockPredicate.m_190399_((BlockState)IC2Blocks.RUBBER_SAPLING.m_49966_(), (Vec3i)BlockPos.f_121853_)), BiomeFilter.m_191561_()});
    }

    public static void registerBiome(Holder<Biome> biome, BiomeGenerationSettingsBuilder builder) {
        if (IC2.CONFIG.oreTin.get()) {
            builder.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_TIN);
        }
        if (IC2.CONFIG.oreUranium.get()) {
            builder.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_URANIUM);
        }
        if (IC2.CONFIG.oreSilver.get()) {
            builder.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_SILVER);
        }
        if (IC2.CONFIG.oreUranium.get()) {
            builder.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_URANIUM_DEEP);
        }
        if (biome.m_203656_(BiomeTags.f_207612_)) {
            if (IC2.CONFIG.oreNetherAluminium.get()) {
                builder.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, NETHER_ORE_ALUMINUM);
            }
            if (IC2.CONFIG.oreNetherSilver.get()) {
                builder.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, NETHER_ORE_SILVER);
            }
        }
        if ((biome.m_203656_(BiomeTags.f_207610_) || biome.m_203656_(BiomeTags.f_207611_) || biome.m_203656_(Tags.Biomes.IS_SWAMP)) && IC2.CONFIG.oreRubberTree.get()) {
            builder.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, RUBBER_TREE);
        }
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<PlacedFeature> feature(String id, ConfiguredFeature<FC, F> feature, List<PlacementModifier> modifiers) {
        return WorldGenerator.feature(new ResourceLocation("ic2", id), feature, modifiers);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<PlacedFeature> feature(ResourceLocation id, ConfiguredFeature<FC, F> feature, List<PlacementModifier> modifiers) {
        return BuiltinRegistries.m_206388_((Registry)BuiltinRegistries.f_194653_, (ResourceLocation)id, (Object)new PlacedFeature(Holder.m_205709_(feature), modifiers));
    }

    public static ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>> createOreFeature(String id, int size, float invisibility, OreConfiguration.TargetBlockState ... targets) {
        return WorldGenerator.createOreFeature(new ResourceLocation("ic2", id), size, invisibility, targets);
    }

    public static ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>> createOreFeature(ResourceLocation id, int size, float invisibility, OreConfiguration.TargetBlockState ... targets) {
        return (ConfiguredFeature)Registry.m_122965_((Registry)BuiltinRegistries.f_123861_, (ResourceLocation)id, (Object)new ConfiguredFeature(Feature.f_65731_, (FeatureConfiguration)new OreConfiguration((List)ImmutableList.copyOf((Object[])targets), size, invisibility)));
    }

    public static OreConfiguration.TargetBlockState target(RuleTest test, Block block) {
        return OreConfiguration.m_161021_((RuleTest)test, (BlockState)block.m_49966_());
    }

    public static ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>> generateTree(boolean force) {
        TreeConfiguration.TreeConfigurationBuilder builder = new TreeConfiguration.TreeConfigurationBuilder((BlockStateProvider)BlockStateProvider.m_191384_((BlockState)IC2Blocks.RUBBERWOOD_LOG.m_49966_()), (TrunkPlacer)new RubberTrunkPlacer(5, 2, 0), (BlockStateProvider)BlockStateProvider.m_191384_((BlockState)IC2Blocks.RUBBER_LEAVES.m_49966_()), (FoliagePlacer)new RubberFoliagePlacer(), (FeatureSize)new TwoLayersFeatureSize(1, 0, 1)).m_68244_();
        return new ConfiguredFeature(Feature.f_65760_, (FeatureConfiguration)(force ? builder.m_161262_() : builder).m_68251_());
    }

    public static ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>> register(ResourceLocation id, ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>> config) {
        return (ConfiguredFeature)Registry.m_122965_((Registry)BuiltinRegistries.f_123861_, (ResourceLocation)id, config);
    }

    public static class IC2Modifier
    implements BiomeModifier {
        public static final Codec<IC2Modifier> CODEC = Codec.unit(IC2Modifier::new);

        public void modify(Holder<Biome> biome, BiomeModifier.Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == BiomeModifier.Phase.ADD) {
                WorldGenerator.registerBiome(biome, builder.getGenerationSettings());
            }
        }

        public Codec<? extends BiomeModifier> codec() {
            return CODEC;
        }
    }
}

