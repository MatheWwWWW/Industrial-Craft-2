/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.WorldgenRandom;

public final class NoiseGeneratorSettings
extends Record {
    private final NoiseSettings f_64439_;
    private final BlockState f_64440_;
    private final BlockState f_64441_;
    private final NoiseRouter f_209353_;
    private final SurfaceRules.RuleSource f_188871_;
    private final List<Climate.ParameterPoint> f_224370_;
    private final int f_64444_;
    private final boolean f_64445_;
    private final boolean f_158533_;
    private final boolean f_158536_;
    private final boolean f_209354_;
    public static final Codec<NoiseGeneratorSettings> f_64430_ = RecordCodecBuilder.create(p_64475_ -> p_64475_.group((App)NoiseSettings.f_64507_.fieldOf("noise").forGetter(NoiseGeneratorSettings::f_64439_), (App)BlockState.f_61039_.fieldOf("default_block").forGetter(NoiseGeneratorSettings::f_64440_), (App)BlockState.f_61039_.fieldOf("default_fluid").forGetter(NoiseGeneratorSettings::f_64441_), (App)NoiseRouter.f_224391_.fieldOf("noise_router").forGetter(NoiseGeneratorSettings::f_209353_), (App)SurfaceRules.RuleSource.f_189682_.fieldOf("surface_rule").forGetter(NoiseGeneratorSettings::f_188871_), (App)Climate.ParameterPoint.f_186862_.listOf().fieldOf("spawn_target").forGetter(NoiseGeneratorSettings::f_224370_), (App)Codec.INT.fieldOf("sea_level").forGetter(NoiseGeneratorSettings::f_64444_), (App)Codec.BOOL.fieldOf("disable_mob_generation").forGetter(NoiseGeneratorSettings::f_64445_), (App)Codec.BOOL.fieldOf("aquifers_enabled").forGetter(NoiseGeneratorSettings::m_158567_), (App)Codec.BOOL.fieldOf("ore_veins_enabled").forGetter(NoiseGeneratorSettings::m_209369_), (App)Codec.BOOL.fieldOf("legacy_random_source").forGetter(NoiseGeneratorSettings::f_209354_)).apply((Applicative)p_64475_, NoiseGeneratorSettings::new));
    public static final Codec<Holder<NoiseGeneratorSettings>> f_64431_ = RegistryFileCodec.m_135589_(Registry.f_122878_, f_64430_);
    public static final ResourceKey<NoiseGeneratorSettings> f_64432_ = ResourceKey.m_135785_(Registry.f_122878_, new ResourceLocation("overworld"));
    public static final ResourceKey<NoiseGeneratorSettings> f_188869_ = ResourceKey.m_135785_(Registry.f_122878_, new ResourceLocation("large_biomes"));
    public static final ResourceKey<NoiseGeneratorSettings> f_64433_ = ResourceKey.m_135785_(Registry.f_122878_, new ResourceLocation("amplified"));
    public static final ResourceKey<NoiseGeneratorSettings> f_64434_ = ResourceKey.m_135785_(Registry.f_122878_, new ResourceLocation("nether"));
    public static final ResourceKey<NoiseGeneratorSettings> f_64435_ = ResourceKey.m_135785_(Registry.f_122878_, new ResourceLocation("end"));
    public static final ResourceKey<NoiseGeneratorSettings> f_64436_ = ResourceKey.m_135785_(Registry.f_122878_, new ResourceLocation("caves"));
    public static final ResourceKey<NoiseGeneratorSettings> f_64437_ = ResourceKey.m_135785_(Registry.f_122878_, new ResourceLocation("floating_islands"));

    public NoiseGeneratorSettings(NoiseSettings f_64439_, BlockState f_64440_, BlockState f_64441_, NoiseRouter f_209353_, SurfaceRules.RuleSource f_188871_, List<Climate.ParameterPoint> f_224370_, int f_64444_, boolean f_64445_, boolean f_158533_, boolean f_158536_, boolean f_209354_) {
        this.f_64439_ = f_64439_;
        this.f_64440_ = f_64440_;
        this.f_64441_ = f_64441_;
        this.f_209353_ = f_209353_;
        this.f_188871_ = f_188871_;
        this.f_224370_ = f_224370_;
        this.f_64444_ = f_64444_;
        this.f_64445_ = f_64445_;
        this.f_158533_ = f_158533_;
        this.f_158536_ = f_158536_;
        this.f_209354_ = f_209354_;
    }

    @Deprecated
    public boolean f_64445_() {
        return this.f_64445_;
    }

    public boolean m_158567_() {
        return this.f_158533_;
    }

    public boolean m_209369_() {
        return this.f_158536_;
    }

    public WorldgenRandom.Algorithm m_188893_() {
        return this.f_209354_ ? WorldgenRandom.Algorithm.LEGACY : WorldgenRandom.Algorithm.XOROSHIRO;
    }

    private static Holder<NoiseGeneratorSettings> m_224385_(Registry<NoiseGeneratorSettings> p_224386_, ResourceKey<NoiseGeneratorSettings> p_224387_, NoiseGeneratorSettings p_224388_) {
        return BuiltinRegistries.m_206388_(p_224386_, p_224387_.m_135782_(), p_224388_);
    }

    public static Holder<NoiseGeneratorSettings> m_224383_(Registry<NoiseGeneratorSettings> p_224384_) {
        NoiseGeneratorSettings.m_224385_(p_224384_, f_64432_, NoiseGeneratorSettings.m_198265_(false, false));
        NoiseGeneratorSettings.m_224385_(p_224384_, f_188869_, NoiseGeneratorSettings.m_198265_(false, true));
        NoiseGeneratorSettings.m_224385_(p_224384_, f_64433_, NoiseGeneratorSettings.m_198265_(true, false));
        NoiseGeneratorSettings.m_224385_(p_224384_, f_64434_, NoiseGeneratorSettings.m_198269_());
        NoiseGeneratorSettings.m_224385_(p_224384_, f_64435_, NoiseGeneratorSettings.m_198268_());
        NoiseGeneratorSettings.m_224385_(p_224384_, f_64436_, NoiseGeneratorSettings.m_198270_());
        return NoiseGeneratorSettings.m_224385_(p_224384_, f_64437_, NoiseGeneratorSettings.m_198271_());
    }

    private static NoiseGeneratorSettings m_198268_() {
        return new NoiseGeneratorSettings(NoiseSettings.f_209631_, Blocks.f_50259_.m_49966_(), Blocks.f_50016_.m_49966_(), NoiseRouterData.m_224511_(BuiltinRegistries.f_211085_), SurfaceRuleData.m_194813_(), List.of(), 0, true, false, false, true);
    }

    private static NoiseGeneratorSettings m_198269_() {
        return new NoiseGeneratorSettings(NoiseSettings.f_209630_, Blocks.f_50134_.m_49966_(), Blocks.f_49991_.m_49966_(), NoiseRouterData.m_224496_(BuiltinRegistries.f_211085_), SurfaceRuleData.m_194812_(), List.of(), 32, false, false, false, true);
    }

    private static NoiseGeneratorSettings m_198265_(boolean p_198266_, boolean p_198267_) {
        return new NoiseGeneratorSettings(NoiseSettings.f_224519_, Blocks.f_50069_.m_49966_(), Blocks.f_49990_.m_49966_(), NoiseRouterData.m_224485_(BuiltinRegistries.f_211085_, p_198267_, p_198266_), SurfaceRuleData.m_194807_(), new OverworldBiomeBuilder().m_187154_(), 63, false, true, true, false);
    }

    private static NoiseGeneratorSettings m_198270_() {
        return new NoiseGeneratorSettings(NoiseSettings.f_209632_, Blocks.f_50069_.m_49966_(), Blocks.f_49990_.m_49966_(), NoiseRouterData.m_224507_(BuiltinRegistries.f_211085_), SurfaceRuleData.m_198380_(false, true, true), List.of(), 32, false, false, false, true);
    }

    private static NoiseGeneratorSettings m_198271_() {
        return new NoiseGeneratorSettings(NoiseSettings.f_209633_, Blocks.f_50069_.m_49966_(), Blocks.f_49990_.m_49966_(), NoiseRouterData.m_224509_(BuiltinRegistries.f_211085_), SurfaceRuleData.m_198380_(false, false, false), List.of(), -64, false, false, false, true);
    }

    public static NoiseGeneratorSettings m_238396_() {
        return new NoiseGeneratorSettings(NoiseSettings.f_224519_, Blocks.f_50069_.m_49966_(), Blocks.f_50016_.m_49966_(), NoiseRouterData.m_238384_(), SurfaceRuleData.m_238362_(), List.of(), 63, true, false, false, false);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{NoiseGeneratorSettings.class, "noiseSettings;defaultBlock;defaultFluid;noiseRouter;surfaceRule;spawnTarget;seaLevel;disableMobGeneration;aquifersEnabled;oreVeinsEnabled;useLegacyRandomSource", "f_64439_", "f_64440_", "f_64441_", "f_209353_", "f_188871_", "f_224370_", "f_64444_", "f_64445_", "f_158533_", "f_158536_", "f_209354_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{NoiseGeneratorSettings.class, "noiseSettings;defaultBlock;defaultFluid;noiseRouter;surfaceRule;spawnTarget;seaLevel;disableMobGeneration;aquifersEnabled;oreVeinsEnabled;useLegacyRandomSource", "f_64439_", "f_64440_", "f_64441_", "f_209353_", "f_188871_", "f_224370_", "f_64444_", "f_64445_", "f_158533_", "f_158536_", "f_209354_"}, this);
    }

    @Override
    public final boolean equals(Object p_209371_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{NoiseGeneratorSettings.class, "noiseSettings;defaultBlock;defaultFluid;noiseRouter;surfaceRule;spawnTarget;seaLevel;disableMobGeneration;aquifersEnabled;oreVeinsEnabled;useLegacyRandomSource", "f_64439_", "f_64440_", "f_64441_", "f_209353_", "f_188871_", "f_224370_", "f_64444_", "f_64445_", "f_158533_", "f_158536_", "f_209354_"}, this, p_209371_);
    }

    public NoiseSettings f_64439_() {
        return this.f_64439_;
    }

    public BlockState f_64440_() {
        return this.f_64440_;
    }

    public BlockState f_64441_() {
        return this.f_64441_;
    }

    public NoiseRouter f_209353_() {
        return this.f_209353_;
    }

    public SurfaceRules.RuleSource f_188871_() {
        return this.f_188871_;
    }

    public List<Climate.ParameterPoint> f_224370_() {
        return this.f_224370_;
    }

    public int f_64444_() {
        return this.f_64444_;
    }

    public boolean f_158533_() {
        return this.f_158533_;
    }

    public boolean f_209354_() {
        return this.f_209354_;
    }
}

