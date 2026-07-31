/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Keyable
 *  com.mojang.serialization.Lifecycle
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 *  org.slf4j.Logger
 */
package net.minecraft.core;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.Lifecycle;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.IdMap;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.network.chat.ChatType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProviderType;
import net.minecraft.util.valueproviders.IntProviderType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.decoration.PaintingVariants;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.BiomeSources;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGenerators;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.providers.nbt.LootNbtProviderType;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProviders;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.level.storage.loot.providers.score.LootScoreProviderType;
import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProviders;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public abstract class Registry<T>
implements Keyable,
IdMap<T> {
    private static final Logger f_122894_ = LogUtils.getLogger();
    private static final Map<ResourceLocation, Supplier<?>> f_122834_ = Maps.newLinkedHashMap();
    public static final ResourceLocation f_122895_ = new ResourceLocation("root");
    protected static final WritableRegistry<WritableRegistry<?>> f_122896_ = new MappedRegistry(Registry.m_122978_("root"), Lifecycle.experimental(), null);
    public static final Registry<? extends Registry<?>> f_122897_ = f_122896_;
    public static final ResourceKey<Registry<SoundEvent>> f_122898_ = Registry.m_122978_("sound_event");
    public static final ResourceKey<Registry<Fluid>> f_122899_ = Registry.m_122978_("fluid");
    public static final ResourceKey<Registry<MobEffect>> f_122900_ = Registry.m_122978_("mob_effect");
    public static final ResourceKey<Registry<Block>> f_122901_ = Registry.m_122978_("block");
    public static final ResourceKey<Registry<Enchantment>> f_122902_ = Registry.m_122978_("enchantment");
    public static final ResourceKey<Registry<EntityType<?>>> f_122903_ = Registry.m_122978_("entity_type");
    public static final ResourceKey<Registry<Item>> f_122904_ = Registry.m_122978_("item");
    public static final ResourceKey<Registry<Potion>> f_122905_ = Registry.m_122978_("potion");
    public static final ResourceKey<Registry<ParticleType<?>>> f_122906_ = Registry.m_122978_("particle_type");
    public static final ResourceKey<Registry<BlockEntityType<?>>> f_122907_ = Registry.m_122978_("block_entity_type");
    public static final ResourceKey<Registry<PaintingVariant>> f_235743_ = Registry.m_122978_("painting_variant");
    public static final ResourceKey<Registry<ResourceLocation>> f_122909_ = Registry.m_122978_("custom_stat");
    public static final ResourceKey<Registry<ChunkStatus>> f_122910_ = Registry.m_122978_("chunk_status");
    public static final ResourceKey<Registry<RuleTestType<?>>> f_122911_ = Registry.m_122978_("rule_test");
    public static final ResourceKey<Registry<PosRuleTestType<?>>> f_122912_ = Registry.m_122978_("pos_rule_test");
    public static final ResourceKey<Registry<MenuType<?>>> f_122913_ = Registry.m_122978_("menu");
    public static final ResourceKey<Registry<RecipeType<?>>> f_122914_ = Registry.m_122978_("recipe_type");
    public static final ResourceKey<Registry<RecipeSerializer<?>>> f_122915_ = Registry.m_122978_("recipe_serializer");
    public static final ResourceKey<Registry<Attribute>> f_122916_ = Registry.m_122978_("attribute");
    public static final ResourceKey<Registry<GameEvent>> f_175423_ = Registry.m_122978_("game_event");
    public static final ResourceKey<Registry<PositionSourceType<?>>> f_175408_ = Registry.m_122978_("position_source_type");
    public static final ResourceKey<Registry<StatType<?>>> f_122917_ = Registry.m_122978_("stat_type");
    public static final ResourceKey<Registry<VillagerType>> f_122808_ = Registry.m_122978_("villager_type");
    public static final ResourceKey<Registry<VillagerProfession>> f_122809_ = Registry.m_122978_("villager_profession");
    public static final ResourceKey<Registry<PoiType>> f_122810_ = Registry.m_122978_("point_of_interest_type");
    public static final ResourceKey<Registry<MemoryModuleType<?>>> f_122811_ = Registry.m_122978_("memory_module_type");
    public static final ResourceKey<Registry<SensorType<?>>> f_122812_ = Registry.m_122978_("sensor_type");
    public static final ResourceKey<Registry<Schedule>> f_122813_ = Registry.m_122978_("schedule");
    public static final ResourceKey<Registry<Activity>> f_122814_ = Registry.m_122978_("activity");
    public static final ResourceKey<Registry<LootPoolEntryType>> f_122815_ = Registry.m_122978_("loot_pool_entry_type");
    public static final ResourceKey<Registry<LootItemFunctionType>> f_122816_ = Registry.m_122978_("loot_function_type");
    public static final ResourceKey<Registry<LootItemConditionType>> f_122817_ = Registry.m_122978_("loot_condition_type");
    public static final ResourceKey<Registry<LootNumberProviderType>> f_175409_ = Registry.m_122978_("loot_number_provider_type");
    public static final ResourceKey<Registry<LootNbtProviderType>> f_175410_ = Registry.m_122978_("loot_nbt_provider_type");
    public static final ResourceKey<Registry<LootScoreProviderType>> f_175411_ = Registry.m_122978_("loot_score_provider_type");
    public static final ResourceKey<Registry<ArgumentTypeInfo<?, ?>>> f_235724_ = Registry.m_122978_("command_argument_type");
    public static final ResourceKey<Registry<DimensionType>> f_122818_ = Registry.m_122978_("dimension_type");
    public static final ResourceKey<Registry<Level>> f_122819_ = Registry.m_122978_("dimension");
    public static final ResourceKey<Registry<LevelStem>> f_122820_ = Registry.m_122978_("dimension");
    public static final DefaultedRegistry<GameEvent> f_175412_ = Registry.m_206031_(f_175423_, "step", GameEvent::m_204530_, p_206044_ -> GameEvent.f_157785_);
    public static final Registry<SoundEvent> f_122821_ = Registry.m_206008_(f_122898_, p_206042_ -> SoundEvents.f_12019_);
    public static final DefaultedRegistry<Fluid> f_122822_ = Registry.m_206031_(f_122899_, "empty", Fluid::m_205069_, p_206040_ -> Fluids.f_76191_);
    public static final Registry<MobEffect> f_122823_ = Registry.m_206008_(f_122900_, p_205982_ -> MobEffects.f_19621_);
    public static final DefaultedRegistry<Block> f_122824_ = Registry.m_206031_(f_122901_, "air", Block::m_204297_, p_205980_ -> Blocks.f_50016_);
    public static final Registry<Enchantment> f_122825_ = Registry.m_206008_(f_122902_, p_205978_ -> Enchantments.f_44987_);
    public static final DefaultedRegistry<EntityType<?>> f_122826_ = Registry.m_206031_(f_122903_, "pig", EntityType::m_204041_, p_205976_ -> EntityType.f_20510_);
    public static final DefaultedRegistry<Item> f_122827_ = Registry.m_206031_(f_122904_, "air", Item::m_204114_, p_205974_ -> Items.f_41852_);
    public static final DefaultedRegistry<Potion> f_122828_ = Registry.m_206027_(f_122905_, "empty", p_205972_ -> Potions.f_43598_);
    public static final Registry<ParticleType<?>> f_122829_ = Registry.m_206008_(f_122906_, p_205970_ -> ParticleTypes.f_123794_);
    public static final Registry<BlockEntityType<?>> f_122830_ = Registry.m_206008_(f_122907_, p_205968_ -> BlockEntityType.f_58917_);
    public static final DefaultedRegistry<PaintingVariant> f_235728_ = Registry.m_206027_(f_235743_, "kebab", PaintingVariants::m_218942_);
    public static final Registry<ResourceLocation> f_122832_ = Registry.m_206008_(f_122909_, p_235777_ -> Stats.f_12926_);
    public static final DefaultedRegistry<ChunkStatus> f_122833_ = Registry.m_206027_(f_122910_, "empty", p_235775_ -> ChunkStatus.f_62314_);
    public static final Registry<RuleTestType<?>> f_122861_ = Registry.m_206008_(f_122911_, p_235773_ -> RuleTestType.f_74312_);
    public static final Registry<PosRuleTestType<?>> f_122862_ = Registry.m_206008_(f_122912_, p_235771_ -> PosRuleTestType.f_74205_);
    public static final Registry<MenuType<?>> f_122863_ = Registry.m_206008_(f_122913_, p_235769_ -> MenuType.f_39964_);
    public static final Registry<RecipeType<?>> f_122864_ = Registry.m_206008_(f_122914_, p_235767_ -> RecipeType.f_44107_);
    public static final Registry<RecipeSerializer<?>> f_122865_ = Registry.m_206008_(f_122915_, p_235765_ -> RecipeSerializer.f_44077_);
    public static final Registry<Attribute> f_122866_ = Registry.m_206008_(f_122916_, p_235763_ -> Attributes.f_22286_);
    public static final Registry<PositionSourceType<?>> f_175420_ = Registry.m_206008_(f_175408_, p_235761_ -> PositionSourceType.f_157871_);
    public static final Registry<ArgumentTypeInfo<?, ?>> f_235729_ = Registry.m_206008_(f_235724_, ArgumentTypeInfos::m_235384_);
    public static final Registry<StatType<?>> f_122867_ = Registry.m_206008_(f_122917_, p_235759_ -> Stats.f_12982_);
    public static final DefaultedRegistry<VillagerType> f_122868_ = Registry.m_206027_(f_122808_, "plains", p_235757_ -> VillagerType.f_35821_);
    public static final DefaultedRegistry<VillagerProfession> f_122869_ = Registry.m_206027_(f_122809_, "none", p_235755_ -> VillagerProfession.f_35585_);
    public static final Registry<PoiType> f_122870_ = Registry.m_206008_(f_122810_, PoiTypes::m_218082_);
    public static final DefaultedRegistry<MemoryModuleType<?>> f_122871_ = Registry.m_206027_(f_122811_, "dummy", p_235753_ -> MemoryModuleType.f_26349_);
    public static final DefaultedRegistry<SensorType<?>> f_122872_ = Registry.m_206027_(f_122812_, "dummy", p_235751_ -> SensorType.f_26809_);
    public static final Registry<Schedule> f_122873_ = Registry.m_206008_(f_122813_, p_235749_ -> Schedule.f_38012_);
    public static final Registry<Activity> f_122874_ = Registry.m_206008_(f_122814_, p_235747_ -> Activity.f_37979_);
    public static final Registry<LootPoolEntryType> f_122875_ = Registry.m_206008_(f_122815_, p_235745_ -> LootPoolEntries.f_79619_);
    public static final Registry<LootItemFunctionType> f_122876_ = Registry.m_206008_(f_122816_, p_235860_ -> LootItemFunctions.f_80736_);
    public static final Registry<LootItemConditionType> f_122877_ = Registry.m_206008_(f_122817_, p_235858_ -> LootItemConditions.f_81811_);
    public static final Registry<LootNumberProviderType> f_175421_ = Registry.m_206008_(f_175409_, p_235856_ -> NumberProviders.f_165731_);
    public static final Registry<LootNbtProviderType> f_175422_ = Registry.m_206008_(f_175410_, p_235854_ -> NbtProviders.f_165624_);
    public static final Registry<LootScoreProviderType> f_175413_ = Registry.m_206008_(f_175411_, p_235852_ -> ScoreboardNameProviders.f_165869_);
    public static final ResourceKey<Registry<FloatProviderType<?>>> f_175414_ = Registry.m_122978_("float_provider_type");
    public static final Registry<FloatProviderType<?>> f_175415_ = Registry.m_206008_(f_175414_, p_235850_ -> FloatProviderType.f_146519_);
    public static final ResourceKey<Registry<IntProviderType<?>>> f_175416_ = Registry.m_122978_("int_provider_type");
    public static final Registry<IntProviderType<?>> f_175417_ = Registry.m_206008_(f_175416_, p_235848_ -> IntProviderType.f_146550_);
    public static final ResourceKey<Registry<HeightProviderType<?>>> f_175418_ = Registry.m_122978_("height_provider_type");
    public static final Registry<HeightProviderType<?>> f_175419_ = Registry.m_206008_(f_175418_, p_235846_ -> HeightProviderType.f_161981_);
    public static final ResourceKey<Registry<BlockPredicateType<?>>> f_194565_ = Registry.m_122978_("block_predicate_type");
    public static final Registry<BlockPredicateType<?>> f_194566_ = Registry.m_206008_(f_194565_, p_235844_ -> BlockPredicateType.f_190444_);
    public static final ResourceKey<Registry<NoiseGeneratorSettings>> f_122878_ = Registry.m_122978_("worldgen/noise_settings");
    public static final ResourceKey<Registry<ConfiguredWorldCarver<?>>> f_122880_ = Registry.m_122978_("worldgen/configured_carver");
    public static final ResourceKey<Registry<ConfiguredFeature<?, ?>>> f_122881_ = Registry.m_122978_("worldgen/configured_feature");
    public static final ResourceKey<Registry<PlacedFeature>> f_194567_ = Registry.m_122978_("worldgen/placed_feature");
    public static final ResourceKey<Registry<Structure>> f_235725_ = Registry.m_122978_("worldgen/structure");
    public static final ResourceKey<Registry<StructureSet>> f_211073_ = Registry.m_122978_("worldgen/structure_set");
    public static final ResourceKey<Registry<StructureProcessorList>> f_122883_ = Registry.m_122978_("worldgen/processor_list");
    public static final ResourceKey<Registry<StructureTemplatePool>> f_122884_ = Registry.m_122978_("worldgen/template_pool");
    public static final ResourceKey<Registry<Biome>> f_122885_ = Registry.m_122978_("worldgen/biome");
    public static final ResourceKey<Registry<NormalNoise.NoiseParameters>> f_194568_ = Registry.m_122978_("worldgen/noise");
    public static final ResourceKey<Registry<DensityFunction>> f_211074_ = Registry.m_122978_("worldgen/density_function");
    public static final ResourceKey<Registry<WorldPreset>> f_235726_ = Registry.m_122978_("worldgen/world_preset");
    public static final ResourceKey<Registry<FlatLevelGeneratorPreset>> f_235727_ = Registry.m_122978_("worldgen/flat_level_generator_preset");
    public static final ResourceKey<Registry<WorldCarver<?>>> f_122836_ = Registry.m_122978_("worldgen/carver");
    public static final Registry<WorldCarver<?>> f_122837_ = Registry.m_206008_(f_122836_, p_235842_ -> WorldCarver.f_64974_);
    public static final ResourceKey<Registry<Feature<?>>> f_122838_ = Registry.m_122978_("worldgen/feature");
    public static final Registry<Feature<?>> f_122839_ = Registry.m_206008_(f_122838_, p_235840_ -> Feature.f_65731_);
    public static final ResourceKey<Registry<StructurePlacementType<?>>> f_205929_ = Registry.m_122978_("worldgen/structure_placement");
    public static final Registry<StructurePlacementType<?>> f_205930_ = Registry.m_206008_(f_205929_, p_235838_ -> StructurePlacementType.f_205041_);
    public static final ResourceKey<Registry<StructurePieceType>> f_122842_ = Registry.m_122978_("worldgen/structure_piece");
    public static final Registry<StructurePieceType> f_122843_ = Registry.m_206008_(f_122842_, p_235836_ -> StructurePieceType.f_210127_);
    public static final ResourceKey<Registry<StructureType<?>>> f_235739_ = Registry.m_122978_("worldgen/structure_type");
    public static final Registry<StructureType<?>> f_235740_ = Registry.m_206008_(f_235739_, p_235834_ -> StructureType.f_226867_);
    public static final ResourceKey<Registry<PlacementModifierType<?>>> f_194569_ = Registry.m_122978_("worldgen/placement_modifier_type");
    public static final Registry<PlacementModifierType<?>> f_194570_ = Registry.m_206008_(f_194569_, p_235832_ -> PlacementModifierType.f_191853_);
    public static final ResourceKey<Registry<BlockStateProviderType<?>>> f_122846_ = Registry.m_122978_("worldgen/block_state_provider_type");
    public static final ResourceKey<Registry<FoliagePlacerType<?>>> f_122848_ = Registry.m_122978_("worldgen/foliage_placer_type");
    public static final ResourceKey<Registry<TrunkPlacerType<?>>> f_122849_ = Registry.m_122978_("worldgen/trunk_placer_type");
    public static final ResourceKey<Registry<TreeDecoratorType<?>>> f_122850_ = Registry.m_122978_("worldgen/tree_decorator_type");
    public static final ResourceKey<Registry<RootPlacerType<?>>> f_235741_ = Registry.m_122978_("worldgen/root_placer_type");
    public static final ResourceKey<Registry<FeatureSizeType<?>>> f_122851_ = Registry.m_122978_("worldgen/feature_size_type");
    public static final ResourceKey<Registry<Codec<? extends BiomeSource>>> f_122852_ = Registry.m_122978_("worldgen/biome_source");
    public static final ResourceKey<Registry<Codec<? extends ChunkGenerator>>> f_122853_ = Registry.m_122978_("worldgen/chunk_generator");
    public static final ResourceKey<Registry<Codec<? extends SurfaceRules.ConditionSource>>> f_194571_ = Registry.m_122978_("worldgen/material_condition");
    public static final ResourceKey<Registry<Codec<? extends SurfaceRules.RuleSource>>> f_194572_ = Registry.m_122978_("worldgen/material_rule");
    public static final ResourceKey<Registry<Codec<? extends DensityFunction>>> f_211075_ = Registry.m_122978_("worldgen/density_function_type");
    public static final ResourceKey<Registry<StructureProcessorType<?>>> f_122854_ = Registry.m_122978_("worldgen/structure_processor");
    public static final ResourceKey<Registry<StructurePoolElementType<?>>> f_122855_ = Registry.m_122978_("worldgen/structure_pool_element");
    public static final Registry<BlockStateProviderType<?>> f_122856_ = Registry.m_206008_(f_122846_, p_235830_ -> BlockStateProviderType.f_68752_);
    public static final Registry<FoliagePlacerType<?>> f_122858_ = Registry.m_206008_(f_122848_, p_235828_ -> FoliagePlacerType.f_68591_);
    public static final Registry<TrunkPlacerType<?>> f_122859_ = Registry.m_206008_(f_122849_, p_235822_ -> TrunkPlacerType.f_70315_);
    public static final Registry<RootPlacerType<?>> f_235742_ = Registry.m_206008_(f_235741_, p_235818_ -> RootPlacerType.f_225898_);
    public static final Registry<TreeDecoratorType<?>> f_122860_ = Registry.m_206008_(f_122850_, p_235814_ -> TreeDecoratorType.f_70043_);
    public static final Registry<FeatureSizeType<?>> f_122888_ = Registry.m_206008_(f_122851_, p_235808_ -> FeatureSizeType.f_68296_);
    public static final Registry<Codec<? extends BiomeSource>> f_122889_ = Registry.m_205999_(f_122852_, Lifecycle.stable(), BiomeSources::m_220586_);
    public static final Registry<Codec<? extends ChunkGenerator>> f_122890_ = Registry.m_205999_(f_122853_, Lifecycle.stable(), ChunkGenerators::m_223242_);
    public static final Registry<Codec<? extends SurfaceRules.ConditionSource>> f_194573_ = Registry.m_206008_(f_194571_, SurfaceRules.ConditionSource::m_204624_);
    public static final Registry<Codec<? extends SurfaceRules.RuleSource>> f_194574_ = Registry.m_206008_(f_194572_, SurfaceRules.RuleSource::m_204630_);
    public static final Registry<Codec<? extends DensityFunction>> f_211076_ = Registry.m_206008_(f_211075_, DensityFunctions::m_208342_);
    public static final Registry<StructureProcessorType<?>> f_122891_ = Registry.m_206008_(f_122854_, p_235806_ -> StructureProcessorType.f_74456_);
    public static final Registry<StructurePoolElementType<?>> f_122892_ = Registry.m_206008_(f_122855_, p_235802_ -> StructurePoolElementType.f_210545_);
    public static final ResourceKey<Registry<ChatType>> f_235730_ = Registry.m_122978_("chat_type");
    public static final ResourceKey<Registry<CatVariant>> f_235731_ = Registry.m_122978_("cat_variant");
    public static final Registry<CatVariant> f_235732_ = Registry.m_206008_(f_235731_, p_235797_ -> CatVariant.f_218141_);
    public static final ResourceKey<Registry<FrogVariant>> f_235733_ = Registry.m_122978_("frog_variant");
    public static final Registry<FrogVariant> f_235734_ = Registry.m_206008_(f_235733_, p_235794_ -> FrogVariant.f_218185_);
    public static final ResourceKey<Registry<BannerPattern>> f_235735_ = Registry.m_122978_("banner_pattern");
    public static final Registry<BannerPattern> f_235736_ = Registry.m_206008_(f_235735_, BannerPatterns::m_222754_);
    public static final ResourceKey<Registry<Instrument>> f_235737_ = Registry.m_122978_("instrument");
    public static final Registry<Instrument> f_235738_ = Registry.m_206008_(f_235737_, Instruments::m_220148_);
    private final ResourceKey<? extends Registry<T>> f_122887_;
    private final Lifecycle f_122893_;

    private static <T> ResourceKey<Registry<T>> m_122978_(String p_122979_) {
        return ResourceKey.m_135788_(new ResourceLocation(p_122979_));
    }

    public static <T extends Registry<?>> void m_205992_(Registry<T> p_205993_) {
        p_205993_.forEach(p_235790_ -> {
            if (p_235790_.m_6566_().isEmpty()) {
                Util.m_143785_("Registry '" + p_205993_.m_7981_(p_235790_) + "' was empty after loading");
            }
            if (p_235790_ instanceof DefaultedRegistry) {
                ResourceLocation $$2 = ((DefaultedRegistry)p_235790_).m_122315_();
                Validate.notNull(p_235790_.m_7745_($$2), (String)("Missing default of DefaultedMappedRegistry: " + $$2), (Object[])new Object[0]);
            }
        });
    }

    private static <T> Registry<T> m_206008_(ResourceKey<? extends Registry<T>> p_206009_, RegistryBootstrap<T> p_206010_) {
        return Registry.m_205999_(p_206009_, Lifecycle.experimental(), p_206010_);
    }

    private static <T> DefaultedRegistry<T> m_206027_(ResourceKey<? extends Registry<T>> p_206028_, String p_206029_, RegistryBootstrap<T> p_206030_) {
        return Registry.m_206016_(p_206028_, p_206029_, Lifecycle.experimental(), p_206030_);
    }

    private static <T> DefaultedRegistry<T> m_206031_(ResourceKey<? extends Registry<T>> p_206032_, String p_206033_, Function<T, Holder.Reference<T>> p_206034_, RegistryBootstrap<T> p_206035_) {
        return Registry.m_206021_(p_206032_, p_206033_, Lifecycle.experimental(), p_206034_, p_206035_);
    }

    private static <T> Registry<T> m_205999_(ResourceKey<? extends Registry<T>> p_206000_, Lifecycle p_206001_, RegistryBootstrap<T> p_206002_) {
        return Registry.m_206011_(p_206000_, new MappedRegistry(p_206000_, p_206001_, null), p_206002_, p_206001_);
    }

    private static <T> Registry<T> m_206003_(ResourceKey<? extends Registry<T>> p_206004_, Lifecycle p_206005_, Function<T, Holder.Reference<T>> p_206006_, RegistryBootstrap<T> p_206007_) {
        return Registry.m_206011_(p_206004_, new MappedRegistry<T>(p_206004_, p_206005_, p_206006_), p_206007_, p_206005_);
    }

    private static <T> DefaultedRegistry<T> m_206016_(ResourceKey<? extends Registry<T>> p_206017_, String p_206018_, Lifecycle p_206019_, RegistryBootstrap<T> p_206020_) {
        return Registry.m_206011_(p_206017_, new DefaultedRegistry(p_206018_, p_206017_, p_206019_, null), p_206020_, p_206019_);
    }

    private static <T> DefaultedRegistry<T> m_206021_(ResourceKey<? extends Registry<T>> p_206022_, String p_206023_, Lifecycle p_206024_, Function<T, Holder.Reference<T>> p_206025_, RegistryBootstrap<T> p_206026_) {
        return Registry.m_206011_(p_206022_, new DefaultedRegistry<T>(p_206023_, p_206022_, p_206024_, p_206025_), p_206026_, p_206024_);
    }

    private static <T, R extends WritableRegistry<T>> R m_206011_(ResourceKey<? extends Registry<T>> p_206012_, R p_206013_, RegistryBootstrap<T> p_206014_, Lifecycle p_206015_) {
        ResourceLocation $$4 = p_206012_.m_135782_();
        f_122834_.put($$4, () -> p_206014_.m_206149_(p_206013_));
        f_122896_.m_203505_(p_206012_, p_206013_, p_206015_);
        return p_206013_;
    }

    protected Registry(ResourceKey<? extends Registry<T>> p_122920_, Lifecycle p_122921_) {
        Bootstrap.m_179912_(() -> "registry " + p_122920_);
        this.f_122887_ = p_122920_;
        this.f_122893_ = p_122921_;
    }

    public static void m_206101_() {
        for (Registry registry : f_122897_) {
            registry.m_203521_();
        }
    }

    public ResourceKey<? extends Registry<T>> m_123023_() {
        return this.f_122887_;
    }

    public Lifecycle m_194602_() {
        return this.f_122893_;
    }

    public String toString() {
        return "Registry[" + this.f_122887_ + " (" + this.f_122893_ + ")]";
    }

    public Codec<T> m_194605_() {
        Codec $$0 = ResourceLocation.f_135803_.flatXmap(p_206084_ -> Optional.ofNullable(this.m_7745_((ResourceLocation)p_206084_)).map(DataResult::success).orElseGet(() -> DataResult.error((String)("Unknown registry key in " + this.f_122887_ + ": " + p_206084_))), p_206094_ -> this.m_7854_(p_206094_).map(ResourceKey::m_135782_).map(DataResult::success).orElseGet(() -> DataResult.error((String)("Unknown registry element in " + this.f_122887_ + ":" + p_206094_))));
        Codec<Object> $$1 = ExtraCodecs.m_184421_(p_235816_ -> this.m_7854_(p_235816_).isPresent() ? this.m_7447_(p_235816_) : -1, this::m_7942_, -1);
        return ExtraCodecs.m_184368_(ExtraCodecs.m_184425_($$0, $$1), this::m_6228_, p_235810_ -> this.f_122893_);
    }

    public Codec<Holder<T>> m_206110_() {
        Codec $$0 = ResourceLocation.f_135803_.flatXmap(p_206070_ -> this.m_203636_(ResourceKey.m_135785_(this.f_122887_, p_206070_)).map(DataResult::success).orElseGet(() -> DataResult.error((String)("Unknown registry key in " + this.f_122887_ + ": " + p_206070_))), p_206061_ -> p_206061_.m_203543_().map(ResourceKey::m_135782_).map(DataResult::success).orElseGet(() -> DataResult.error((String)("Unknown registry element in " + this.f_122887_ + ":" + p_206061_))));
        return ExtraCodecs.m_184368_($$0, p_235792_ -> this.m_6228_(p_235792_.m_203334_()), p_206047_ -> this.f_122893_);
    }

    public <U> Stream<U> keys(DynamicOps<U> p_123030_) {
        return this.m_6566_().stream().map(p_235784_ -> p_123030_.createString(p_235784_.toString()));
    }

    @Nullable
    public abstract ResourceLocation m_7981_(T var1);

    public abstract Optional<ResourceKey<T>> m_7854_(T var1);

    @Override
    public abstract int m_7447_(@Nullable T var1);

    @Nullable
    public abstract T m_6246_(@Nullable ResourceKey<T> var1);

    @Nullable
    public abstract T m_7745_(@Nullable ResourceLocation var1);

    public abstract Lifecycle m_6228_(T var1);

    public abstract Lifecycle m_7837_();

    public Optional<T> m_6612_(@Nullable ResourceLocation p_123007_) {
        return Optional.ofNullable(this.m_7745_(p_123007_));
    }

    public Optional<T> m_123009_(@Nullable ResourceKey<T> p_123010_) {
        return Optional.ofNullable(this.m_6246_(p_123010_));
    }

    public T m_123013_(ResourceKey<T> p_123014_) {
        T $$1 = this.m_6246_(p_123014_);
        if ($$1 == null) {
            throw new IllegalStateException("Missing key in " + this.f_122887_ + ": " + p_123014_);
        }
        return $$1;
    }

    public abstract Set<ResourceLocation> m_6566_();

    public abstract Set<Map.Entry<ResourceKey<T>, T>> m_6579_();

    public abstract Set<ResourceKey<T>> m_214010_();

    public abstract Optional<Holder<T>> m_213642_(RandomSource var1);

    public Stream<T> m_123024_() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public abstract boolean m_7804_(ResourceLocation var1);

    public abstract boolean m_142003_(ResourceKey<T> var1);

    public static <T> T m_122961_(Registry<? super T> p_122962_, String p_122963_, T p_122964_) {
        return Registry.m_122965_(p_122962_, new ResourceLocation(p_122963_), p_122964_);
    }

    public static <V, T extends V> T m_122965_(Registry<V> p_122966_, ResourceLocation p_122967_, T p_122968_) {
        return Registry.m_194579_(p_122966_, ResourceKey.m_135785_(p_122966_.f_122887_, p_122967_), p_122968_);
    }

    public static <V, T extends V> T m_194579_(Registry<V> p_194580_, ResourceKey<V> p_194581_, T p_194582_) {
        ((WritableRegistry)p_194580_).m_203505_(p_194581_, p_194582_, Lifecycle.stable());
        return p_194582_;
    }

    public static <V, T extends V> T m_122956_(Registry<V> p_122957_, int p_122958_, String p_122959_, T p_122960_) {
        ((WritableRegistry)p_122957_).m_203704_(p_122958_, ResourceKey.m_135785_(p_122957_.f_122887_, new ResourceLocation(p_122959_)), p_122960_, Lifecycle.stable());
        return p_122960_;
    }

    public abstract Registry<T> m_203521_();

    public abstract Holder<T> m_214121_(ResourceKey<T> var1);

    public abstract DataResult<Holder<T>> m_214185_(ResourceKey<T> var1);

    public abstract Holder.Reference<T> m_203693_(T var1);

    public abstract Optional<Holder<T>> m_203300_(int var1);

    public abstract Optional<Holder<T>> m_203636_(ResourceKey<T> var1);

    public Holder<T> m_206081_(ResourceKey<T> p_206082_) {
        return this.m_203636_(p_206082_).orElseThrow(() -> new IllegalStateException("Missing key in " + this.f_122887_ + ": " + p_206082_));
    }

    public abstract Stream<Holder.Reference<T>> m_203611_();

    public abstract Optional<HolderSet.Named<T>> m_203431_(TagKey<T> var1);

    public Iterable<Holder<T>> m_206058_(TagKey<T> p_206059_) {
        return (Iterable)DataFixUtils.orElse(this.m_203431_(p_206059_), List.of());
    }

    public abstract HolderSet.Named<T> m_203561_(TagKey<T> var1);

    public abstract Stream<Pair<TagKey<T>, HolderSet.Named<T>>> m_203612_();

    public abstract Stream<TagKey<T>> m_203613_();

    public abstract boolean m_203658_(TagKey<T> var1);

    public abstract void m_203635_();

    public abstract void m_203652_(Map<TagKey<T>, List<Holder<T>>> var1);

    public IdMap<Holder<T>> m_206115_() {
        return new IdMap<Holder<T>>(){

            @Override
            public int m_7447_(Holder<T> p_206142_) {
                return Registry.this.m_7447_(p_206142_.m_203334_());
            }

            @Override
            @Nullable
            public Holder<T> m_7942_(int p_206147_) {
                return Registry.this.m_203300_(p_206147_).orElse(null);
            }

            @Override
            public int m_13562_() {
                return Registry.this.m_13562_();
            }

            @Override
            public Iterator<Holder<T>> iterator() {
                return Registry.this.m_203611_().map(p_206140_ -> p_206140_).iterator();
            }

            @Override
            @Nullable
            public /* synthetic */ Object m_7942_(int n) {
                return this.m_7942_(n);
            }
        };
    }

    static {
        BuiltinRegistries.m_123870_();
        f_122834_.forEach((? super K p_235779_, ? super V p_235780_) -> {
            if (p_235780_.get() == null) {
                f_122894_.error("Unable to bootstrap registry '{}'", p_235779_);
            }
        });
        Registry.m_205992_(f_122896_);
    }

    @FunctionalInterface
    static interface RegistryBootstrap<T> {
        public T m_206149_(Registry<T> var1);
    }
}

