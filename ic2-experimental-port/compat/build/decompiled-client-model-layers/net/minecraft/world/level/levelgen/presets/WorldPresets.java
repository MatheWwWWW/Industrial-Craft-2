/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.presets;

import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class WorldPresets {
    public static final ResourceKey<WorldPreset> f_226437_ = WorldPresets.m_226459_("normal");
    public static final ResourceKey<WorldPreset> f_226438_ = WorldPresets.m_226459_("flat");
    public static final ResourceKey<WorldPreset> f_226439_ = WorldPresets.m_226459_("large_biomes");
    public static final ResourceKey<WorldPreset> f_226440_ = WorldPresets.m_226459_("amplified");
    public static final ResourceKey<WorldPreset> f_226441_ = WorldPresets.m_226459_("single_biome_surface");
    public static final ResourceKey<WorldPreset> f_226442_ = WorldPresets.m_226459_("debug_all_block_states");

    public static Holder<WorldPreset> m_226447_(Registry<WorldPreset> p_226448_) {
        return new Bootstrap(p_226448_).m_226480_();
    }

    private static ResourceKey<WorldPreset> m_226459_(String p_226460_) {
        return ResourceKey.m_135785_(Registry.f_235726_, new ResourceLocation(p_226460_));
    }

    public static Optional<ResourceKey<WorldPreset>> m_226445_(WorldGenSettings p_226446_) {
        ChunkGenerator $$1 = p_226446_.m_64666_();
        if ($$1 instanceof FlatLevelSource) {
            return Optional.of(f_226438_);
        }
        if ($$1 instanceof DebugLevelSource) {
            return Optional.of(f_226442_);
        }
        return Optional.empty();
    }

    public static WorldGenSettings m_226454_(RegistryAccess p_226455_, long p_226456_, boolean p_226457_, boolean p_226458_) {
        return p_226455_.m_175515_(Registry.f_235726_).m_206081_(f_226437_).m_203334_().m_226421_(p_226456_, p_226457_, p_226458_);
    }

    public static WorldGenSettings m_226451_(RegistryAccess p_226452_, long p_226453_) {
        return WorldPresets.m_226454_(p_226452_, p_226453_, true, false);
    }

    public static WorldGenSettings m_226449_(RegistryAccess p_226450_) {
        return WorldPresets.m_226451_(p_226450_, RandomSource.m_216327_().m_188505_());
    }

    public static WorldGenSettings m_226461_(RegistryAccess p_226462_) {
        return WorldPresets.m_226454_(p_226462_, "North Carolina".hashCode(), true, true);
    }

    public static LevelStem m_226463_(RegistryAccess p_226464_) {
        return p_226464_.m_175515_(Registry.f_235726_).m_206081_(f_226437_).m_203334_().m_226434_();
    }

    static class Bootstrap {
        private final Registry<WorldPreset> f_226465_;
        private final Registry<DimensionType> f_226466_ = BuiltinRegistries.f_235987_;
        private final Registry<Biome> f_226467_ = BuiltinRegistries.f_123865_;
        private final Registry<StructureSet> f_226468_ = BuiltinRegistries.f_211084_;
        private final Registry<NoiseGeneratorSettings> f_226469_ = BuiltinRegistries.f_123866_;
        private final Registry<NormalNoise.NoiseParameters> f_226470_ = BuiltinRegistries.f_194654_;
        private final Holder<DimensionType> f_226471_ = this.f_226466_.m_214121_(BuiltinDimensionTypes.f_223538_);
        private final Holder<DimensionType> f_226472_ = this.f_226466_.m_214121_(BuiltinDimensionTypes.f_223539_);
        private final Holder<NoiseGeneratorSettings> f_226473_ = this.f_226469_.m_214121_(NoiseGeneratorSettings.f_64434_);
        private final LevelStem f_226474_ = new LevelStem(this.f_226472_, new NoiseBasedChunkGenerator(this.f_226468_, this.f_226470_, (BiomeSource)MultiNoiseBiomeSource.Preset.f_48512_.m_187099_(this.f_226467_), this.f_226473_));
        private final Holder<DimensionType> f_226475_ = this.f_226466_.m_214121_(BuiltinDimensionTypes.f_223540_);
        private final Holder<NoiseGeneratorSettings> f_226476_ = this.f_226469_.m_214121_(NoiseGeneratorSettings.f_64435_);
        private final LevelStem f_226477_ = new LevelStem(this.f_226475_, new NoiseBasedChunkGenerator(this.f_226468_, this.f_226470_, (BiomeSource)new TheEndBiomeSource(this.f_226467_), this.f_226476_));

        Bootstrap(Registry<WorldPreset> p_226479_) {
            this.f_226465_ = p_226479_;
        }

        private LevelStem m_226487_(ChunkGenerator p_226488_) {
            return new LevelStem(this.f_226471_, p_226488_);
        }

        private LevelStem m_226484_(BiomeSource p_226485_, Holder<NoiseGeneratorSettings> p_226486_) {
            return this.m_226487_(new NoiseBasedChunkGenerator(this.f_226468_, this.f_226470_, p_226485_, p_226486_));
        }

        private WorldPreset m_226489_(LevelStem p_226490_) {
            return new WorldPreset(Map.of(LevelStem.f_63971_, p_226490_, LevelStem.f_63972_, this.f_226474_, LevelStem.f_63973_, this.f_226477_));
        }

        private Holder<WorldPreset> m_226481_(ResourceKey<WorldPreset> p_226482_, LevelStem p_226483_) {
            return BuiltinRegistries.m_206384_(this.f_226465_, p_226482_, this.m_226489_(p_226483_));
        }

        public Holder<WorldPreset> m_226480_() {
            MultiNoiseBiomeSource $$0 = MultiNoiseBiomeSource.Preset.f_187087_.m_187099_(this.f_226467_);
            Holder<NoiseGeneratorSettings> $$1 = this.f_226469_.m_214121_(NoiseGeneratorSettings.f_64432_);
            this.m_226481_(f_226437_, this.m_226484_($$0, $$1));
            Holder<NoiseGeneratorSettings> $$2 = this.f_226469_.m_214121_(NoiseGeneratorSettings.f_188869_);
            this.m_226481_(f_226439_, this.m_226484_($$0, $$2));
            Holder<NoiseGeneratorSettings> $$3 = this.f_226469_.m_214121_(NoiseGeneratorSettings.f_64433_);
            this.m_226481_(f_226440_, this.m_226484_($$0, $$3));
            this.m_226481_(f_226441_, this.m_226484_(new FixedBiomeSource(this.f_226467_.m_214121_(Biomes.f_48202_)), $$1));
            this.m_226481_(f_226438_, this.m_226487_(new FlatLevelSource(this.f_226468_, FlatLevelGeneratorSettings.m_211734_(this.f_226467_, this.f_226468_))));
            return this.m_226481_(f_226442_, this.m_226487_(new DebugLevelSource(this.f_226468_, this.f_226467_)));
        }
    }
}

