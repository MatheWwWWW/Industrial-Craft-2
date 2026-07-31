/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.BambooFeature;
import net.minecraft.world.level.levelgen.feature.BasaltColumnsFeature;
import net.minecraft.world.level.levelgen.feature.BasaltPillarFeature;
import net.minecraft.world.level.levelgen.feature.BlockBlobFeature;
import net.minecraft.world.level.levelgen.feature.BlockColumnFeature;
import net.minecraft.world.level.levelgen.feature.BlockPileFeature;
import net.minecraft.world.level.levelgen.feature.BlueIceFeature;
import net.minecraft.world.level.levelgen.feature.BonusChestFeature;
import net.minecraft.world.level.levelgen.feature.ChorusPlantFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.CoralClawFeature;
import net.minecraft.world.level.levelgen.feature.CoralMushroomFeature;
import net.minecraft.world.level.levelgen.feature.CoralTreeFeature;
import net.minecraft.world.level.levelgen.feature.DeltaFeature;
import net.minecraft.world.level.levelgen.feature.DesertWellFeature;
import net.minecraft.world.level.levelgen.feature.DiskFeature;
import net.minecraft.world.level.levelgen.feature.DripstoneClusterFeature;
import net.minecraft.world.level.levelgen.feature.EndGatewayFeature;
import net.minecraft.world.level.levelgen.feature.EndIslandFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.FillLayerFeature;
import net.minecraft.world.level.levelgen.feature.FossilFeature;
import net.minecraft.world.level.levelgen.feature.FossilFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.GeodeFeature;
import net.minecraft.world.level.levelgen.feature.GlowstoneFeature;
import net.minecraft.world.level.levelgen.feature.HugeBrownMushroomFeature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.levelgen.feature.HugeFungusFeature;
import net.minecraft.world.level.levelgen.feature.HugeRedMushroomFeature;
import net.minecraft.world.level.levelgen.feature.IceSpikeFeature;
import net.minecraft.world.level.levelgen.feature.IcebergFeature;
import net.minecraft.world.level.levelgen.feature.KelpFeature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.LargeDripstoneFeature;
import net.minecraft.world.level.levelgen.feature.MonsterRoomFeature;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.NetherForestVegetationFeature;
import net.minecraft.world.level.levelgen.feature.NoOpFeature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.PointedDripstoneFeature;
import net.minecraft.world.level.levelgen.feature.RandomBooleanSelectorFeature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.RandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.ReplaceBlobsFeature;
import net.minecraft.world.level.levelgen.feature.ReplaceBlockFeature;
import net.minecraft.world.level.levelgen.feature.RootSystemFeature;
import net.minecraft.world.level.levelgen.feature.ScatteredOreFeature;
import net.minecraft.world.level.levelgen.feature.SculkPatchFeature;
import net.minecraft.world.level.levelgen.feature.SeaPickleFeature;
import net.minecraft.world.level.levelgen.feature.SeagrassFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.SimpleRandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.SnowAndFreezeFeature;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import net.minecraft.world.level.levelgen.feature.SpringFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.TwistingVinesFeature;
import net.minecraft.world.level.levelgen.feature.UnderwaterMagmaFeature;
import net.minecraft.world.level.levelgen.feature.VegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.VinesFeature;
import net.minecraft.world.level.levelgen.feature.VoidStartPlatformFeature;
import net.minecraft.world.level.levelgen.feature.WaterloggedVegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.WeepingVinesFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LayerConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TwistingVinesConfig;
import net.minecraft.world.level.levelgen.feature.configurations.UnderwaterMagmaConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

public abstract class Feature<FC extends FeatureConfiguration> {
    public static final Feature<NoneFeatureConfiguration> f_65759_ = Feature.m_65807_("no_op", new NoOpFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<TreeConfiguration> f_65760_ = Feature.m_65807_("tree", new TreeFeature(TreeConfiguration.f_68184_));
    public static final Feature<RandomPatchConfiguration> f_65761_ = Feature.m_65807_("flower", new RandomPatchFeature(RandomPatchConfiguration.f_67902_));
    public static final Feature<RandomPatchConfiguration> f_65762_ = Feature.m_65807_("no_bonemeal_flower", new RandomPatchFeature(RandomPatchConfiguration.f_67902_));
    public static final Feature<RandomPatchConfiguration> f_65763_ = Feature.m_65807_("random_patch", new RandomPatchFeature(RandomPatchConfiguration.f_67902_));
    public static final Feature<BlockPileConfiguration> f_65764_ = Feature.m_65807_("block_pile", new BlockPileFeature(BlockPileConfiguration.f_67539_));
    public static final Feature<SpringConfiguration> f_65765_ = Feature.m_65807_("spring_feature", new SpringFeature(SpringConfiguration.f_68123_));
    public static final Feature<NoneFeatureConfiguration> f_65766_ = Feature.m_65807_("chorus_plant", new ChorusPlantFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<ReplaceBlockConfiguration> f_159732_ = Feature.m_65807_("replace_single_block", new ReplaceBlockFeature(ReplaceBlockConfiguration.f_68023_));
    public static final Feature<NoneFeatureConfiguration> f_65768_ = Feature.m_65807_("void_start_platform", new VoidStartPlatformFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<NoneFeatureConfiguration> f_65769_ = Feature.m_65807_("desert_well", new DesertWellFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<FossilFeatureConfiguration> f_65770_ = Feature.m_65807_("fossil", new FossilFeature(FossilFeatureConfiguration.f_159796_));
    public static final Feature<HugeMushroomFeatureConfiguration> f_65771_ = Feature.m_65807_("huge_red_mushroom", new HugeRedMushroomFeature(HugeMushroomFeatureConfiguration.f_67739_));
    public static final Feature<HugeMushroomFeatureConfiguration> f_65772_ = Feature.m_65807_("huge_brown_mushroom", new HugeBrownMushroomFeature(HugeMushroomFeatureConfiguration.f_67739_));
    public static final Feature<NoneFeatureConfiguration> f_65773_ = Feature.m_65807_("ice_spike", new IceSpikeFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<NoneFeatureConfiguration> f_65774_ = Feature.m_65807_("glowstone_blob", new GlowstoneFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<NoneFeatureConfiguration> f_65775_ = Feature.m_65807_("freeze_top_layer", new SnowAndFreezeFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<NoneFeatureConfiguration> f_65776_ = Feature.m_65807_("vines", new VinesFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<BlockColumnConfiguration> f_190875_ = Feature.m_65807_("block_column", new BlockColumnFeature(BlockColumnConfiguration.f_191206_));
    public static final Feature<VegetationPatchConfiguration> f_159734_ = Feature.m_65807_("vegetation_patch", new VegetationPatchFeature(VegetationPatchConfiguration.f_161280_));
    public static final Feature<VegetationPatchConfiguration> f_159735_ = Feature.m_65807_("waterlogged_vegetation_patch", new WaterloggedVegetationPatchFeature(VegetationPatchConfiguration.f_161280_));
    public static final Feature<RootSystemConfiguration> f_159724_ = Feature.m_65807_("root_system", new RootSystemFeature(RootSystemConfiguration.f_161101_));
    public static final Feature<MultifaceGrowthConfiguration> f_225026_ = Feature.m_65807_("multiface_growth", new MultifaceGrowthFeature(MultifaceGrowthConfiguration.f_225381_));
    public static final Feature<UnderwaterMagmaConfiguration> f_159726_ = Feature.m_65807_("underwater_magma", new UnderwaterMagmaFeature(UnderwaterMagmaConfiguration.f_161263_));
    public static final Feature<NoneFeatureConfiguration> f_65777_ = Feature.m_65807_("monster_room", new MonsterRoomFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<NoneFeatureConfiguration> f_65778_ = Feature.m_65807_("blue_ice", new BlueIceFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<BlockStateConfiguration> f_65779_ = Feature.m_65807_("iceberg", new IcebergFeature(BlockStateConfiguration.f_67546_));
    public static final Feature<BlockStateConfiguration> f_65780_ = Feature.m_65807_("forest_rock", new BlockBlobFeature(BlockStateConfiguration.f_67546_));
    public static final Feature<DiskConfiguration> f_65781_ = Feature.m_65807_("disk", new DiskFeature(DiskConfiguration.f_67618_));
    public static final Feature<LakeFeature.Configuration> f_65783_ = Feature.m_65807_("lake", new LakeFeature(LakeFeature.Configuration.f_190953_));
    public static final Feature<OreConfiguration> f_65731_ = Feature.m_65807_("ore", new OreFeature(OreConfiguration.f_67837_));
    public static final Feature<SpikeConfiguration> f_65732_ = Feature.m_65807_("end_spike", new SpikeFeature(SpikeConfiguration.f_68099_));
    public static final Feature<NoneFeatureConfiguration> f_65733_ = Feature.m_65807_("end_island", new EndIslandFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<EndGatewayConfiguration> f_65734_ = Feature.m_65807_("end_gateway", new EndGatewayFeature(EndGatewayConfiguration.f_67639_));
    public static final SeagrassFeature f_65735_ = Feature.m_65807_("seagrass", new SeagrassFeature(ProbabilityFeatureConfiguration.f_67858_));
    public static final Feature<NoneFeatureConfiguration> f_65736_ = Feature.m_65807_("kelp", new KelpFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<NoneFeatureConfiguration> f_65737_ = Feature.m_65807_("coral_tree", new CoralTreeFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<NoneFeatureConfiguration> f_65738_ = Feature.m_65807_("coral_mushroom", new CoralMushroomFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<NoneFeatureConfiguration> f_65739_ = Feature.m_65807_("coral_claw", new CoralClawFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<CountConfiguration> f_65740_ = Feature.m_65807_("sea_pickle", new SeaPickleFeature(CountConfiguration.f_67568_));
    public static final Feature<SimpleBlockConfiguration> f_65741_ = Feature.m_65807_("simple_block", new SimpleBlockFeature(SimpleBlockConfiguration.f_68068_));
    public static final Feature<ProbabilityFeatureConfiguration> f_65742_ = Feature.m_65807_("bamboo", new BambooFeature(ProbabilityFeatureConfiguration.f_67858_));
    public static final Feature<HugeFungusConfiguration> f_65743_ = Feature.m_65807_("huge_fungus", new HugeFungusFeature(HugeFungusConfiguration.f_65892_));
    public static final Feature<NetherForestVegetationConfig> f_65744_ = Feature.m_65807_("nether_forest_vegetation", new NetherForestVegetationFeature(NetherForestVegetationConfig.f_191258_));
    public static final Feature<NoneFeatureConfiguration> f_65745_ = Feature.m_65807_("weeping_vines", new WeepingVinesFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<TwistingVinesConfig> f_65746_ = Feature.m_65807_("twisting_vines", new TwistingVinesFeature(TwistingVinesConfig.f_191364_));
    public static final Feature<ColumnFeatureConfiguration> f_65747_ = Feature.m_65807_("basalt_columns", new BasaltColumnsFeature(ColumnFeatureConfiguration.f_67553_));
    public static final Feature<DeltaFeatureConfiguration> f_65748_ = Feature.m_65807_("delta_feature", new DeltaFeature(DeltaFeatureConfiguration.f_67593_));
    public static final Feature<ReplaceSphereConfiguration> f_65749_ = Feature.m_65807_("netherrack_replace_blobs", new ReplaceBlobsFeature(ReplaceSphereConfiguration.f_68036_));
    public static final Feature<LayerConfiguration> f_65750_ = Feature.m_65807_("fill_layer", new FillLayerFeature(LayerConfiguration.f_67767_));
    public static final BonusChestFeature f_65751_ = Feature.m_65807_("bonus_chest", new BonusChestFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<NoneFeatureConfiguration> f_65752_ = Feature.m_65807_("basalt_pillar", new BasaltPillarFeature(NoneFeatureConfiguration.f_67815_));
    public static final Feature<OreConfiguration> f_159727_ = Feature.m_65807_("scattered_ore", new ScatteredOreFeature(OreConfiguration.f_67837_));
    public static final Feature<RandomFeatureConfiguration> f_65754_ = Feature.m_65807_("random_selector", new RandomSelectorFeature(RandomFeatureConfiguration.f_67881_));
    public static final Feature<SimpleRandomFeatureConfiguration> f_65755_ = Feature.m_65807_("simple_random_selector", new SimpleRandomSelectorFeature(SimpleRandomFeatureConfiguration.f_68089_));
    public static final Feature<RandomBooleanFeatureConfiguration> f_65756_ = Feature.m_65807_("random_boolean_selector", new RandomBooleanSelectorFeature(RandomBooleanFeatureConfiguration.f_67867_));
    public static final Feature<GeodeConfiguration> f_159728_ = Feature.m_65807_("geode", new GeodeFeature(GeodeConfiguration.f_160812_));
    public static final Feature<DripstoneClusterConfiguration> f_159729_ = Feature.m_65807_("dripstone_cluster", new DripstoneClusterFeature(DripstoneClusterConfiguration.f_160758_));
    public static final Feature<LargeDripstoneConfiguration> f_159730_ = Feature.m_65807_("large_dripstone", new LargeDripstoneFeature(LargeDripstoneConfiguration.f_160944_));
    public static final Feature<PointedDripstoneConfiguration> f_190874_ = Feature.m_65807_("pointed_dripstone", new PointedDripstoneFeature(PointedDripstoneConfiguration.f_191274_));
    public static final Feature<SculkPatchConfiguration> f_225027_ = Feature.m_65807_("sculk_patch", new SculkPatchFeature(SculkPatchConfiguration.f_225425_));
    private final Codec<ConfiguredFeature<FC, Feature<FC>>> f_65757_;

    private static <C extends FeatureConfiguration, F extends Feature<C>> F m_65807_(String p_65808_, F p_65809_) {
        return (F)Registry.m_122961_(Registry.f_122839_, p_65808_, p_65809_);
    }

    public Feature(Codec<FC> p_65786_) {
        this.f_65757_ = p_65786_.fieldOf("config").xmap(p_65806_ -> new ConfiguredFeature<FeatureConfiguration, Feature>(this, (FeatureConfiguration)p_65806_), ConfiguredFeature::f_65378_).codec();
    }

    public Codec<ConfiguredFeature<FC, Feature<FC>>> m_65787_() {
        return this.f_65757_;
    }

    protected void m_5974_(LevelWriter p_65791_, BlockPos p_65792_, BlockState p_65793_) {
        p_65791_.m_7731_(p_65792_, p_65793_, 3);
    }

    public static Predicate<BlockState> m_204735_(TagKey<Block> p_204736_) {
        return p_204739_ -> !p_204739_.m_204336_(p_204736_);
    }

    protected void m_159742_(WorldGenLevel p_159743_, BlockPos p_159744_, BlockState p_159745_, Predicate<BlockState> p_159746_) {
        if (p_159746_.test(p_159743_.m_8055_(p_159744_))) {
            p_159743_.m_7731_(p_159744_, p_159745_, 2);
        }
    }

    public abstract boolean m_142674_(FeaturePlaceContext<FC> var1);

    public boolean m_225028_(FC p_225029_, WorldGenLevel p_225030_, ChunkGenerator p_225031_, RandomSource p_225032_, BlockPos p_225033_) {
        if (p_225030_.m_180807_(p_225033_)) {
            return this.m_142674_(new FeaturePlaceContext<FC>(Optional.empty(), p_225030_, p_225031_, p_225032_, p_225033_, p_225029_));
        }
        return false;
    }

    protected static boolean m_159747_(BlockState p_159748_) {
        return p_159748_.m_204336_(BlockTags.f_13061_);
    }

    public static boolean m_159759_(BlockState p_159760_) {
        return p_159760_.m_204336_(BlockTags.f_144274_);
    }

    public static boolean m_65788_(LevelSimulatedReader p_65789_, BlockPos p_65790_) {
        return p_65789_.m_7433_(p_65790_, Feature::m_159759_);
    }

    public static boolean m_159753_(Function<BlockPos, BlockState> p_159754_, BlockPos p_159755_, Predicate<BlockState> p_159756_) {
        BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
        for (Direction $$4 : Direction.values()) {
            $$3.m_122159_(p_159755_, $$4);
            if (!p_159756_.test(p_159754_.apply($$3))) continue;
            return true;
        }
        return false;
    }

    public static boolean m_159750_(Function<BlockPos, BlockState> p_159751_, BlockPos p_159752_) {
        return Feature.m_159753_(p_159751_, p_159752_, BlockBehaviour.BlockStateBase::m_60795_);
    }

    protected void m_159739_(WorldGenLevel p_159740_, BlockPos p_159741_) {
        BlockPos.MutableBlockPos $$2 = p_159741_.m_122032_();
        for (int $$3 = 0; $$3 < 2; ++$$3) {
            $$2.m_122173_(Direction.UP);
            if (p_159740_.m_8055_($$2).m_60795_()) {
                return;
            }
            p_159740_.m_46865_($$2).m_8113_($$2);
        }
    }
}

