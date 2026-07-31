/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.AncientCityStructurePieces;
import net.minecraft.data.worldgen.BastionPieces;
import net.minecraft.data.worldgen.DesertVillagePools;
import net.minecraft.data.worldgen.PillagerOutpostPools;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.data.worldgen.SavannaVillagePools;
import net.minecraft.data.worldgen.SnowyVillagePools;
import net.minecraft.data.worldgen.TaigaVillagePools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.structures.BuriedTreasureStructure;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidStructure;
import net.minecraft.world.level.levelgen.structure.structures.EndCityStructure;
import net.minecraft.world.level.levelgen.structure.structures.IglooStructure;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.structures.JungleTempleStructure;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinStructure;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckStructure;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdStructure;
import net.minecraft.world.level.levelgen.structure.structures.SwampHutStructure;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionStructure;

public class Structures {
    public static final Holder<Structure> f_236505_ = Structures.m_236533_(BuiltinStructures.f_209845_, new JigsawStructure(Structures.m_236545_(BiomeTags.f_207622_, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.m_146330_((WeightedEntry[])new MobSpawnSettings.SpawnerData[]{new MobSpawnSettings.SpawnerData(EntityType.f_20513_, 1, 1, 1)}))), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN), PillagerOutpostPools.f_127180_, 7, ConstantHeight.m_161956_(VerticalAnchor.m_158922_(0)), true, Heightmap.Types.WORLD_SURFACE_WG));
    public static final Holder<Structure> f_236506_ = Structures.m_236533_(BuiltinStructures.f_209846_, new MineshaftStructure(Structures.m_236538_(BiomeTags.f_207617_, GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE), MineshaftStructure.Type.NORMAL));
    public static final Holder<Structure> f_236507_ = Structures.m_236533_(BuiltinStructures.f_209847_, new MineshaftStructure(Structures.m_236538_(BiomeTags.f_207618_, GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE), MineshaftStructure.Type.MESA));
    public static final Holder<Structure> f_236508_ = Structures.m_236533_(BuiltinStructures.f_209848_, new WoodlandMansionStructure(Structures.m_236542_(BiomeTags.f_207595_, TerrainAdjustment.NONE)));
    public static final Holder<Structure> f_236509_ = Structures.m_236533_(BuiltinStructures.f_209849_, new JungleTempleStructure(Structures.m_236542_(BiomeTags.f_207616_, TerrainAdjustment.NONE)));
    public static final Holder<Structure> f_236510_ = Structures.m_236533_(BuiltinStructures.f_209850_, new DesertPyramidStructure(Structures.m_236542_(BiomeTags.f_207614_, TerrainAdjustment.NONE)));
    public static final Holder<Structure> f_236511_ = Structures.m_236533_(BuiltinStructures.f_209851_, new IglooStructure(Structures.m_236542_(BiomeTags.f_207615_, TerrainAdjustment.NONE)));
    public static final Holder<Structure> f_236512_ = Structures.m_236533_(BuiltinStructures.f_209852_, new ShipwreckStructure(Structures.m_236542_(BiomeTags.f_207588_, TerrainAdjustment.NONE), false));
    public static final Holder<Structure> f_236513_ = Structures.m_236533_(BuiltinStructures.f_209853_, new ShipwreckStructure(Structures.m_236542_(BiomeTags.f_207587_, TerrainAdjustment.NONE), true));
    public static final Holder<Structure> f_236514_ = Structures.m_236533_(BuiltinStructures.f_209854_, new SwampHutStructure(Structures.m_236545_(BiomeTags.f_207589_, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.m_146330_((WeightedEntry[])new MobSpawnSettings.SpawnerData[]{new MobSpawnSettings.SpawnerData(EntityType.f_20495_, 1, 1, 1)})), MobCategory.CREATURE, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.m_146330_((WeightedEntry[])new MobSpawnSettings.SpawnerData[]{new MobSpawnSettings.SpawnerData(EntityType.f_20553_, 1, 1, 1)}))), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
    public static final Holder<Structure> f_236515_ = Structures.m_236533_(BuiltinStructures.f_209855_, new StrongholdStructure(Structures.m_236542_(BiomeTags.f_207596_, TerrainAdjustment.BURY)));
    public static final Holder<Structure> f_236516_ = Structures.m_236533_(BuiltinStructures.f_209856_, new OceanMonumentStructure(Structures.m_236545_(BiomeTags.f_207619_, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.m_146330_((WeightedEntry[])new MobSpawnSettings.SpawnerData[]{new MobSpawnSettings.SpawnerData(EntityType.f_20455_, 1, 2, 4)})), MobCategory.UNDERGROUND_WATER_CREATURE, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, MobSpawnSettings.f_151796_), MobCategory.AXOLOTLS, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, MobSpawnSettings.f_151796_)), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
    public static final Holder<Structure> f_236517_ = Structures.m_236533_(BuiltinStructures.f_209857_, new OceanRuinStructure(Structures.m_236542_(BiomeTags.f_207620_, TerrainAdjustment.NONE), OceanRuinStructure.Type.COLD, 0.3f, 0.9f));
    public static final Holder<Structure> f_236518_ = Structures.m_236533_(BuiltinStructures.f_209858_, new OceanRuinStructure(Structures.m_236542_(BiomeTags.f_207621_, TerrainAdjustment.NONE), OceanRuinStructure.Type.WARM, 0.3f, 0.9f));
    public static final Holder<Structure> f_236519_ = Structures.m_236533_(BuiltinStructures.f_209859_, new NetherFortressStructure(Structures.m_236545_(BiomeTags.f_207597_, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, NetherFortressStructure.f_228517_)), GenerationStep.Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.NONE)));
    public static final Holder<Structure> f_236520_ = Structures.m_236533_(BuiltinStructures.f_209860_, new NetherFossilStructure(Structures.m_236538_(BiomeTags.f_207598_, GenerationStep.Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.BEARD_THIN), UniformHeight.m_162034_(VerticalAnchor.m_158922_(32), VerticalAnchor.m_158935_(2))));
    public static final Holder<Structure> f_236521_ = Structures.m_236533_(BuiltinStructures.f_209861_, new EndCityStructure(Structures.m_236542_(BiomeTags.f_207601_, TerrainAdjustment.NONE)));
    public static final Holder<Structure> f_236522_ = Structures.m_236533_(BuiltinStructures.f_209862_, new BuriedTreasureStructure(Structures.m_236538_(BiomeTags.f_207613_, GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE)));
    public static final Holder<Structure> f_236523_ = Structures.m_236533_(BuiltinStructures.f_209863_, new JigsawStructure(Structures.m_236542_(BiomeTags.f_207599_, TerrainAdjustment.NONE), BastionPieces.f_126673_, 6, ConstantHeight.m_161956_(VerticalAnchor.m_158922_(33)), false));
    public static final Holder<Structure> f_236524_ = Structures.m_236533_(BuiltinStructures.f_209864_, new JigsawStructure(Structures.m_236542_(BiomeTags.f_207591_, TerrainAdjustment.BEARD_THIN), PlainVillagePools.f_127183_, 6, ConstantHeight.m_161956_(VerticalAnchor.m_158922_(0)), true, Heightmap.Types.WORLD_SURFACE_WG));
    public static final Holder<Structure> f_236525_ = Structures.m_236533_(BuiltinStructures.f_209865_, new JigsawStructure(Structures.m_236542_(BiomeTags.f_207590_, TerrainAdjustment.BEARD_THIN), DesertVillagePools.f_126858_, 6, ConstantHeight.m_161956_(VerticalAnchor.m_158922_(0)), true, Heightmap.Types.WORLD_SURFACE_WG));
    public static final Holder<Structure> f_236526_ = Structures.m_236533_(BuiltinStructures.f_209866_, new JigsawStructure(Structures.m_236542_(BiomeTags.f_207592_, TerrainAdjustment.BEARD_THIN), SavannaVillagePools.f_127228_, 6, ConstantHeight.m_161956_(VerticalAnchor.m_158922_(0)), true, Heightmap.Types.WORLD_SURFACE_WG));
    public static final Holder<Structure> f_236527_ = Structures.m_236533_(BuiltinStructures.f_209867_, new JigsawStructure(Structures.m_236542_(BiomeTags.f_207593_, TerrainAdjustment.BEARD_THIN), SnowyVillagePools.f_127231_, 6, ConstantHeight.m_161956_(VerticalAnchor.m_158922_(0)), true, Heightmap.Types.WORLD_SURFACE_WG));
    public static final Holder<Structure> f_236528_ = Structures.m_236533_(BuiltinStructures.f_209868_, new JigsawStructure(Structures.m_236542_(BiomeTags.f_207594_, TerrainAdjustment.BEARD_THIN), TaigaVillagePools.f_127303_, 6, ConstantHeight.m_161956_(VerticalAnchor.m_158922_(0)), true, Heightmap.Types.WORLD_SURFACE_WG));
    public static final Holder<Structure> f_236529_ = Structures.m_236533_(BuiltinStructures.f_209869_, new RuinedPortalStructure(Structures.m_236542_(BiomeTags.f_207586_, TerrainAdjustment.NONE), List.of(new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.UNDERGROUND, 1.0f, 0.2f, false, false, true, false, 0.5f), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE, 0.5f, 0.2f, false, false, true, false, 0.5f))));
    public static final Holder<Structure> f_236530_ = Structures.m_236533_(BuiltinStructures.f_209870_, new RuinedPortalStructure(Structures.m_236542_(BiomeTags.f_207623_, TerrainAdjustment.NONE), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.PARTLY_BURIED, 0.0f, 0.0f, false, false, false, false, 1.0f)));
    public static final Holder<Structure> f_236499_ = Structures.m_236533_(BuiltinStructures.f_209840_, new RuinedPortalStructure(Structures.m_236542_(BiomeTags.f_207624_, TerrainAdjustment.NONE), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE, 0.5f, 0.8f, true, true, false, false, 1.0f)));
    public static final Holder<Structure> f_236500_ = Structures.m_236533_(BuiltinStructures.f_209841_, new RuinedPortalStructure(Structures.m_236542_(BiomeTags.f_207626_, TerrainAdjustment.NONE), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR, 0.0f, 0.5f, false, true, false, false, 1.0f)));
    public static final Holder<Structure> f_236501_ = Structures.m_236533_(BuiltinStructures.f_209842_, new RuinedPortalStructure(Structures.m_236542_(BiomeTags.f_207627_, TerrainAdjustment.NONE), List.of(new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.IN_MOUNTAIN, 1.0f, 0.2f, false, false, true, false, 0.5f), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE, 0.5f, 0.2f, false, false, true, false, 0.5f))));
    public static final Holder<Structure> f_236502_ = Structures.m_236533_(BuiltinStructures.f_209843_, new RuinedPortalStructure(Structures.m_236542_(BiomeTags.f_207625_, TerrainAdjustment.NONE), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR, 0.0f, 0.8f, false, false, true, false, 1.0f)));
    public static final Holder<Structure> f_236503_ = Structures.m_236533_(BuiltinStructures.f_209844_, new RuinedPortalStructure(Structures.m_236542_(BiomeTags.f_207600_, TerrainAdjustment.NONE), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.IN_NETHER, 0.5f, 0.0f, false, false, false, true, 1.0f)));
    public static final Holder<Structure> f_236504_ = Structures.m_236533_(BuiltinStructures.f_226492_, new JigsawStructure(Structures.m_236545_(BiomeTags.f_215799_, Arrays.stream(MobCategory.values()).collect(Collectors.toMap(p_236555_ -> p_236555_, p_236551_ -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.m_146332_()))), GenerationStep.Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.BEARD_BOX), AncientCityStructurePieces.f_236459_, Optional.of(new ResourceLocation("city_anchor")), 7, ConstantHeight.m_161956_(VerticalAnchor.m_158922_(-27)), false, Optional.empty(), 116));

    public static Holder<? extends Structure> m_236552_(Registry<Structure> p_236553_) {
        return f_236506_;
    }

    private static Structure.StructureSettings m_236545_(TagKey<Biome> p_236546_, Map<MobCategory, StructureSpawnOverride> p_236547_, GenerationStep.Decoration p_236548_, TerrainAdjustment p_236549_) {
        return new Structure.StructureSettings(Structures.m_236536_(p_236546_), p_236547_, p_236548_, p_236549_);
    }

    private static Structure.StructureSettings m_236538_(TagKey<Biome> p_236539_, GenerationStep.Decoration p_236540_, TerrainAdjustment p_236541_) {
        return Structures.m_236545_(p_236539_, Map.of(), p_236540_, p_236541_);
    }

    private static Structure.StructureSettings m_236542_(TagKey<Biome> p_236543_, TerrainAdjustment p_236544_) {
        return Structures.m_236545_(p_236543_, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, p_236544_);
    }

    private static Holder<Structure> m_236533_(ResourceKey<Structure> p_236534_, Structure p_236535_) {
        return BuiltinRegistries.m_206384_(BuiltinRegistries.f_235988_, p_236534_, p_236535_);
    }

    private static HolderSet<Biome> m_236536_(TagKey<Biome> p_236537_) {
        return BuiltinRegistries.f_123865_.m_203561_(p_236537_);
    }
}

