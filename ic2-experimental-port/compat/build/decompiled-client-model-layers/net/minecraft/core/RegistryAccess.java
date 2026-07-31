/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Suppliers
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  com.mojang.serialization.Lifecycle
 *  com.mojang.serialization.codecs.UnboundedMapCodec
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.core;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.network.chat.ChatType;
import net.minecraft.resources.RegistryLoader;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.RegistryResourceAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.slf4j.Logger;

public interface RegistryAccess {
    public static final Logger f_123047_ = LogUtils.getLogger();
    public static final Map<ResourceKey<? extends Registry<?>>, RegistryData<?>> f_123048_ = (Map)Util.m_137537_(() -> {
        ImmutableMap.Builder $$0 = ImmutableMap.builder();
        RegistryAccess.m_123057_($$0, Registry.f_122818_, DimensionType.f_63843_, DimensionType.f_63843_);
        RegistryAccess.m_123057_($$0, Registry.f_122885_, Biome.f_47429_, Biome.f_47430_);
        RegistryAccess.m_123053_($$0, Registry.f_122880_, ConfiguredWorldCarver.f_64846_);
        RegistryAccess.m_123053_($$0, Registry.f_122881_, ConfiguredFeature.f_65373_);
        RegistryAccess.m_123053_($$0, Registry.f_194567_, PlacedFeature.f_191772_);
        RegistryAccess.m_123053_($$0, Registry.f_235725_, Structure.f_226553_);
        RegistryAccess.m_123053_($$0, Registry.f_211073_, StructureSet.f_210001_);
        RegistryAccess.m_123053_($$0, Registry.f_122883_, StructureProcessorType.f_74467_);
        RegistryAccess.m_123053_($$0, Registry.f_122884_, StructureTemplatePool.f_210554_);
        RegistryAccess.m_123053_($$0, Registry.f_122878_, NoiseGeneratorSettings.f_64430_);
        RegistryAccess.m_123053_($$0, Registry.f_194568_, NormalNoise.NoiseParameters.f_192851_);
        RegistryAccess.m_123053_($$0, Registry.f_211074_, DensityFunction.f_208216_);
        RegistryAccess.m_123057_($$0, Registry.f_235730_, ChatType.f_237005_, ChatType.f_237005_);
        RegistryAccess.m_123053_($$0, Registry.f_235726_, WorldPreset.f_226414_);
        RegistryAccess.m_123053_($$0, Registry.f_235727_, FlatLevelGeneratorPreset.f_226243_);
        return $$0.build();
    });
    public static final Codec<RegistryAccess> f_206151_ = RegistryAccess.m_206205_();
    public static final Supplier<Frozen> f_123049_ = Suppliers.memoize(() -> RegistryAccess.m_206197_().m_203557_());

    public <E> Optional<Registry<E>> m_142664_(ResourceKey<? extends Registry<? extends E>> var1);

    default public <E> Registry<E> m_206191_(ResourceKey<? extends Registry<? extends E>> p_206192_) {
        return this.m_142664_(p_206192_).orElseThrow(() -> new IllegalStateException("Missing registry: " + p_206192_));
    }

    default public <E> Optional<? extends Registry<E>> m_6632_(ResourceKey<? extends Registry<? extends E>> p_123085_) {
        Optional<Registry<E>> $$1 = this.m_142664_(p_123085_);
        if ($$1.isPresent()) {
            return $$1;
        }
        return Registry.f_122897_.m_6612_(p_123085_.m_135782_());
    }

    default public <E> Registry<E> m_175515_(ResourceKey<? extends Registry<? extends E>> p_175516_) {
        return this.m_6632_(p_175516_).orElseThrow(() -> new IllegalStateException("Missing registry: " + p_175516_));
    }

    private static <E> void m_123053_(ImmutableMap.Builder<ResourceKey<? extends Registry<?>>, RegistryData<?>> p_123054_, ResourceKey<? extends Registry<E>> p_123055_, Codec<E> p_123056_) {
        p_123054_.put(p_123055_, new RegistryData<E>(p_123055_, p_123056_, null));
    }

    private static <E> void m_123057_(ImmutableMap.Builder<ResourceKey<? extends Registry<?>>, RegistryData<?>> p_123058_, ResourceKey<? extends Registry<E>> p_123059_, Codec<E> p_123060_, Codec<E> p_123061_) {
        p_123058_.put(p_123059_, new RegistryData<E>(p_123059_, p_123060_, p_123061_));
    }

    public static Iterable<RegistryData<?>> m_194613_() {
        return f_123048_.values();
    }

    public Stream<RegistryEntry<?>> m_203610_();

    private static Stream<RegistryEntry<Object>> m_206202_() {
        return Registry.f_122897_.m_203611_().map(RegistryEntry::m_206239_);
    }

    default public Stream<RegistryEntry<?>> m_206193_() {
        return Stream.concat(this.m_203610_(), RegistryAccess.m_206202_());
    }

    default public Stream<RegistryEntry<?>> m_206196_() {
        return Stream.concat(this.m_206206_(), RegistryAccess.m_206202_());
    }

    private static <E> Codec<RegistryAccess> m_206205_() {
        Codec $$0 = ResourceLocation.f_135803_.xmap(ResourceKey::m_135788_, ResourceKey::m_135782_);
        Codec $$1 = $$0.partialDispatch("type", p_206188_ -> DataResult.success(p_206188_.m_123023_()), p_206214_ -> RegistryAccess.m_206203_(p_206214_).map(p_206183_ -> RegistryCodecs.m_206291_(p_206214_, Lifecycle.experimental(), p_206183_)));
        UnboundedMapCodec $$2 = Codec.unboundedMap((Codec)$$0, (Codec)$$1);
        return RegistryAccess.m_206163_($$2);
    }

    private static <K extends ResourceKey<? extends Registry<?>>, V extends Registry<?>> Codec<RegistryAccess> m_206163_(UnboundedMapCodec<K, V> p_206164_) {
        return p_206164_.xmap(ImmutableRegistryAccess::new, p_206180_ -> (Map)p_206180_.m_206206_().collect(ImmutableMap.toImmutableMap(p_206195_ -> p_206195_.f_206233_(), p_206190_ -> p_206190_.f_206234_())));
    }

    private Stream<RegistryEntry<?>> m_206206_() {
        return this.m_203610_().filter(p_206170_ -> f_123048_.get(p_206170_.f_206233_).m_123111_());
    }

    private static <E> DataResult<? extends Codec<E>> m_206203_(ResourceKey<? extends Registry<E>> p_206204_) {
        return Optional.ofNullable(f_123048_.get(p_206204_)).map(p_206168_ -> p_206168_.f_123103_()).map(DataResult::success).orElseGet(() -> DataResult.error((String)("Unknown or not serializable registry: " + p_206204_)));
    }

    private static Map<ResourceKey<? extends Registry<?>>, ? extends WritableRegistry<?>> m_206209_() {
        return f_123048_.keySet().stream().collect(Collectors.toMap(Function.identity(), RegistryAccess::m_206200_));
    }

    private static Writable m_206212_() {
        return new WritableRegistryAccess(RegistryAccess.m_206209_());
    }

    public static Frozen m_206165_(final Registry<? extends Registry<?>> p_206166_) {
        return new Frozen(){

            public <T> Optional<Registry<T>> m_142664_(ResourceKey<? extends Registry<? extends T>> p_206220_) {
                Registry $$1 = p_206166_;
                return $$1.m_123009_(p_206220_);
            }

            @Override
            public Stream<RegistryEntry<?>> m_203610_() {
                return p_206166_.m_6579_().stream().map(RegistryEntry::m_206241_);
            }
        };
    }

    public static Writable m_206197_() {
        Writable $$0 = RegistryAccess.m_206212_();
        RegistryResourceAccess.InMemoryStorage $$1 = new RegistryResourceAccess.InMemoryStorage();
        for (Map.Entry<ResourceKey<Registry<?>>, RegistryData<?>> $$2 : f_123048_.entrySet()) {
            RegistryAccess.m_211081_($$1, $$2.getValue());
        }
        RegistryOps.m_206817_(JsonOps.INSTANCE, $$0, $$1);
        return $$0;
    }

    private static <E> void m_211081_(RegistryResourceAccess.InMemoryStorage p_211082_, RegistryData<E> p_211083_) {
        ResourceKey<Registry<E>> $$2 = p_211083_.f_123101_();
        Registry<E> $$3 = BuiltinRegistries.f_206379_.m_175515_($$2);
        for (Map.Entry<ResourceKey<E>, E> $$4 : $$3.m_6579_()) {
            ResourceKey<E> $$5 = $$4.getKey();
            E $$6 = $$4.getValue();
            p_211082_.m_206836_(BuiltinRegistries.f_206379_, $$5, p_211083_.f_123102_(), $$3.m_7447_($$6), $$6, $$3.m_6228_($$6));
        }
    }

    public static void m_206171_(Writable p_206172_, DynamicOps<JsonElement> p_206173_, RegistryLoader p_206174_) {
        RegistryLoader.Bound $$3 = p_206174_.m_206757_(p_206172_);
        for (RegistryData<?> $$4 : f_123048_.values()) {
            RegistryAccess.m_206159_(p_206173_, $$3, $$4);
        }
    }

    private static <E> void m_206159_(DynamicOps<JsonElement> p_206160_, RegistryLoader.Bound p_206161_, RegistryData<E> p_206162_) {
        DataResult<Registry<E>> $$3 = p_206161_.m_206789_(p_206162_.f_123101_(), p_206162_.f_123102_(), p_206160_);
        $$3.error().ifPresent(p_206153_ -> {
            throw new JsonParseException("Error loading registry data: " + p_206153_.message());
        });
    }

    public static RegistryAccess m_206154_(Dynamic<?> p_206155_) {
        return new ImmutableRegistryAccess(f_123048_.keySet().stream().collect(Collectors.toMap(Function.identity(), p_206158_ -> RegistryAccess.m_206184_(p_206158_, p_206155_))));
    }

    public static <E> Registry<E> m_206184_(ResourceKey<? extends Registry<? extends E>> p_206185_, Dynamic<?> p_206186_) {
        return (Registry)RegistryOps.m_206832_(p_206185_).codec().parse(p_206186_).resultOrPartial(Util.m_137489_(p_206185_ + " registry: ", arg_0 -> ((Logger)f_123047_).error(arg_0))).orElseThrow(() -> new IllegalStateException("Failed to get " + p_206185_ + " registry"));
    }

    public static <E> WritableRegistry<?> m_206200_(ResourceKey<? extends Registry<?>> p_206201_) {
        return new MappedRegistry(p_206201_, Lifecycle.stable(), null);
    }

    default public Frozen m_203557_() {
        return new ImmutableRegistryAccess(this.m_203610_().map(RegistryEntry::m_206247_));
    }

    default public Lifecycle m_211816_() {
        return this.m_203610_().map(p_211815_ -> p_211815_.f_206234_.m_7837_()).reduce(Lifecycle.stable(), Lifecycle::add);
    }

    public record RegistryData<E>(ResourceKey<? extends Registry<E>> f_123101_, Codec<E> f_123102_, @Nullable Codec<E> f_123103_) {
        public boolean m_123111_() {
            return this.f_123103_ != null;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{RegistryData.class, "key;codec;networkCodec", "f_123101_", "f_123102_", "f_123103_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{RegistryData.class, "key;codec;networkCodec", "f_123101_", "f_123102_", "f_123103_"}, this);
        }

        @Override
        public final boolean equals(Object p_194619_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{RegistryData.class, "key;codec;networkCodec", "f_123101_", "f_123102_", "f_123103_"}, this, p_194619_);
        }
    }

    public static final class WritableRegistryAccess
    implements Writable {
        private final Map<? extends ResourceKey<? extends Registry<?>>, ? extends WritableRegistry<?>> f_206257_;

        WritableRegistryAccess(Map<? extends ResourceKey<? extends Registry<?>>, ? extends WritableRegistry<?>> p_206259_) {
            this.f_206257_ = p_206259_;
        }

        @Override
        public <E> Optional<Registry<E>> m_142664_(ResourceKey<? extends Registry<? extends E>> p_206263_) {
            return Optional.ofNullable(this.f_206257_.get(p_206263_)).map(p_206266_ -> p_206266_);
        }

        @Override
        public <E> Optional<WritableRegistry<E>> m_203275_(ResourceKey<? extends Registry<? extends E>> p_206268_) {
            return Optional.ofNullable(this.f_206257_.get(p_206268_)).map(p_206261_ -> p_206261_);
        }

        @Override
        public Stream<RegistryEntry<?>> m_203610_() {
            return this.f_206257_.entrySet().stream().map(RegistryEntry::m_206241_);
        }
    }

    public static interface Writable
    extends RegistryAccess {
        public <E> Optional<WritableRegistry<E>> m_203275_(ResourceKey<? extends Registry<? extends E>> var1);

        default public <E> WritableRegistry<E> m_206253_(ResourceKey<? extends Registry<? extends E>> p_206254_) {
            return this.m_203275_(p_206254_).orElseThrow(() -> new IllegalStateException("Missing registry: " + p_206254_));
        }
    }

    public static final class ImmutableRegistryAccess
    implements Frozen {
        private final Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> f_206223_;

        public ImmutableRegistryAccess(Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> p_206225_) {
            this.f_206223_ = Map.copyOf(p_206225_);
        }

        ImmutableRegistryAccess(Stream<RegistryEntry<?>> p_206227_) {
            this.f_206223_ = (Map)p_206227_.collect(ImmutableMap.toImmutableMap(RegistryEntry::f_206233_, RegistryEntry::f_206234_));
        }

        @Override
        public <E> Optional<Registry<E>> m_142664_(ResourceKey<? extends Registry<? extends E>> p_206229_) {
            return Optional.ofNullable(this.f_206223_.get(p_206229_)).map(p_206232_ -> p_206232_);
        }

        @Override
        public Stream<RegistryEntry<?>> m_203610_() {
            return this.f_206223_.entrySet().stream().map(RegistryEntry::m_206241_);
        }
    }

    public record RegistryEntry<T>(ResourceKey<? extends Registry<T>> f_206233_, Registry<T> f_206234_) {
        private static <T, R extends Registry<? extends T>> RegistryEntry<T> m_206241_(Map.Entry<? extends ResourceKey<? extends Registry<?>>, R> p_206242_) {
            return RegistryEntry.m_206243_(p_206242_.getKey(), (Registry)p_206242_.getValue());
        }

        private static <T> RegistryEntry<T> m_206239_(Holder.Reference<? extends Registry<? extends T>> p_206240_) {
            return RegistryEntry.m_206243_(p_206240_.m_205785_(), p_206240_.m_203334_());
        }

        private static <T> RegistryEntry<T> m_206243_(ResourceKey<? extends Registry<?>> p_206244_, Registry<?> p_206245_) {
            return new RegistryEntry(p_206244_, p_206245_);
        }

        private RegistryEntry<T> m_206247_() {
            return new RegistryEntry<T>(this.f_206233_, this.f_206234_.m_203521_());
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{RegistryEntry.class, "key;value", "f_206233_", "f_206234_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{RegistryEntry.class, "key;value", "f_206233_", "f_206234_"}, this);
        }

        @Override
        public final boolean equals(Object p_206249_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{RegistryEntry.class, "key;value", "f_206233_", "f_206234_"}, this, p_206249_);
        }
    }

    public static interface Frozen
    extends RegistryAccess {
        @Override
        default public Frozen m_203557_() {
            return this;
        }
    }
}

