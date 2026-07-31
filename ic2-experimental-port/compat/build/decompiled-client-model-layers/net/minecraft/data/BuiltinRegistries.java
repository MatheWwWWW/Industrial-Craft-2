/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Lifecycle
 *  org.slf4j.Logger
 */
package net.minecraft.data;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.network.chat.ChatType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPresets;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.slf4j.Logger;

public class BuiltinRegistries {
    private static final Logger f_123857_ = LogUtils.getLogger();
    private static final Map<ResourceLocation, Supplier<? extends Holder<?>>> f_123867_ = Maps.newLinkedHashMap();
    private static final WritableRegistry<WritableRegistry<?>> f_123868_ = new MappedRegistry(ResourceKey.m_135788_(new ResourceLocation("root")), Lifecycle.experimental(), null);
    public static final Registry<? extends Registry<?>> f_123858_ = f_123868_;
    public static final Registry<DimensionType> f_235987_ = BuiltinRegistries.m_236001_(Registry.f_122818_, DimensionTypes::m_236473_);
    public static final Registry<ConfiguredWorldCarver<?>> f_123860_ = BuiltinRegistries.m_236001_(Registry.f_122880_, p_236013_ -> Carvers.f_126848_);
    public static final Registry<ConfiguredFeature<?, ?>> f_123861_ = BuiltinRegistries.m_236001_(Registry.f_122881_, FeatureUtils::m_236677_);
    public static final Registry<PlacedFeature> f_194653_ = BuiltinRegistries.m_236001_(Registry.f_194567_, PlacementUtils::m_236769_);
    public static final Registry<Structure> f_235988_ = BuiltinRegistries.m_236001_(Registry.f_235725_, Structures::m_236552_);
    public static final Registry<StructureSet> f_211084_ = BuiltinRegistries.m_236001_(Registry.f_211073_, StructureSets::m_236497_);
    public static final Registry<StructureProcessorList> f_123863_ = BuiltinRegistries.m_236001_(Registry.f_122883_, p_236008_ -> ProcessorLists.f_127199_);
    public static final Registry<StructureTemplatePool> f_123864_ = BuiltinRegistries.m_236001_(Registry.f_122884_, Pools::m_236491_);
    public static final Registry<Biome> f_123865_ = BuiltinRegistries.m_236001_(Registry.f_122885_, Biomes::m_236652_);
    public static final Registry<NormalNoise.NoiseParameters> f_194654_ = BuiltinRegistries.m_236001_(Registry.f_194568_, NoiseData::m_236475_);
    public static final Registry<DensityFunction> f_211085_ = BuiltinRegistries.m_236001_(Registry.f_211074_, NoiseRouterData::m_224458_);
    public static final Registry<NoiseGeneratorSettings> f_123866_ = BuiltinRegistries.m_236001_(Registry.f_122878_, NoiseGeneratorSettings::m_224383_);
    public static final Registry<WorldPreset> f_235989_ = BuiltinRegistries.m_236001_(Registry.f_235726_, WorldPresets::m_226447_);
    public static final Registry<FlatLevelGeneratorPreset> f_235990_ = BuiltinRegistries.m_236001_(Registry.f_235727_, FlatLevelGeneratorPresets::m_226274_);
    public static final Registry<ChatType> f_235991_ = BuiltinRegistries.m_236001_(Registry.f_235730_, ChatType::m_237021_);
    public static final RegistryAccess f_206379_;

    private static <T> Registry<T> m_236001_(ResourceKey<? extends Registry<T>> p_236002_, RegistryBootstrap<T> p_236003_) {
        return BuiltinRegistries.m_235992_(p_236002_, Lifecycle.stable(), p_236003_);
    }

    private static <T> Registry<T> m_235992_(ResourceKey<? extends Registry<T>> p_235993_, Lifecycle p_235994_, RegistryBootstrap<T> p_235995_) {
        return BuiltinRegistries.m_235996_(p_235993_, new MappedRegistry(p_235993_, p_235994_, null), p_235995_, p_235994_);
    }

    private static <T, R extends WritableRegistry<T>> R m_235996_(ResourceKey<? extends Registry<T>> p_235997_, R p_235998_, RegistryBootstrap<T> p_235999_, Lifecycle p_236000_) {
        ResourceLocation $$4 = p_235997_.m_135782_();
        f_123867_.put($$4, () -> p_235999_.m_236014_(p_235998_));
        f_123868_.m_203505_(p_235997_, p_235998_, p_236000_);
        return p_235998_;
    }

    public static <V extends T, T> Holder<V> m_206380_(Registry<T> p_206381_, String p_206382_, V p_206383_) {
        Holder<T> $$3 = BuiltinRegistries.m_206388_(p_206381_, new ResourceLocation(p_206382_), p_206383_);
        return $$3;
    }

    public static <T> Holder<T> m_206396_(Registry<T> p_206397_, String p_206398_, T p_206399_) {
        return BuiltinRegistries.m_206388_(p_206397_, new ResourceLocation(p_206398_), p_206399_);
    }

    public static <T> Holder<T> m_206388_(Registry<T> p_206389_, ResourceLocation p_206390_, T p_206391_) {
        return BuiltinRegistries.m_206384_(p_206389_, ResourceKey.m_135785_(p_206389_.m_123023_(), p_206390_), p_206391_);
    }

    public static <T> Holder<T> m_206384_(Registry<T> p_206385_, ResourceKey<T> p_206386_, T p_206387_) {
        return ((WritableRegistry)p_206385_).m_203505_(p_206386_, p_206387_, Lifecycle.stable());
    }

    public static void m_123870_() {
    }

    static {
        f_123867_.forEach((p_236005_, p_236006_) -> {
            if (!((Holder)p_236006_.get()).m_203633_()) {
                f_123857_.error("Unable to bootstrap registry '{}'", p_236005_);
            }
        });
        Registry.m_205992_(f_123868_);
        f_206379_ = RegistryAccess.m_206165_(f_123858_);
    }

    @FunctionalInterface
    static interface RegistryBootstrap<T> {
        public Holder<? extends T> m_236014_(Registry<T> var1);
    }
}

