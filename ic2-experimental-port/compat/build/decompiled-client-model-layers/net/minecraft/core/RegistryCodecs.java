/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Decoder
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Encoder
 *  com.mojang.serialization.Lifecycle
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import net.minecraft.core.HolderSet;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.RegistryLoader;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;

public class RegistryCodecs {
    private static <T> MapCodec<RegistryEntry<T>> m_206303_(ResourceKey<? extends Registry<T>> p_206304_, MapCodec<T> p_206305_) {
        return RecordCodecBuilder.mapCodec(p_206309_ -> p_206309_.group((App)ResourceKey.m_195966_(p_206304_).fieldOf("name").forGetter(RegistryEntry::f_206354_), (App)Codec.INT.fieldOf("id").forGetter(RegistryEntry::f_206355_), (App)p_206305_.forGetter(RegistryEntry::f_206356_)).apply((Applicative)p_206309_, RegistryEntry::new));
    }

    public static <T> Codec<Registry<T>> m_206291_(ResourceKey<? extends Registry<T>> p_206292_, Lifecycle p_206293_, Codec<T> p_206294_) {
        return RegistryCodecs.m_206303_(p_206292_, p_206294_.fieldOf("element")).codec().listOf().xmap(p_206298_ -> {
            MappedRegistry $$3 = new MappedRegistry(p_206292_, p_206293_, null);
            for (RegistryEntry $$4 : p_206298_) {
                ((WritableRegistry)$$3).m_203704_($$4.f_206355_(), $$4.f_206354_(), $$4.f_206356_(), p_206293_);
            }
            return $$3;
        }, p_206314_ -> {
            ImmutableList.Builder $$1 = ImmutableList.builder();
            for (Object $$2 : p_206314_) {
                $$1.add(new RegistryEntry(p_206314_.m_7854_($$2).get(), p_206314_.m_7447_($$2), $$2));
            }
            return $$1.build();
        });
    }

    public static <E> Codec<Registry<E>> m_206318_(ResourceKey<? extends Registry<E>> p_206319_, Lifecycle p_206320_, Codec<E> p_206321_) {
        Codec<Map<ResourceKey<E>, E>> $$3 = RegistryCodecs.m_206315_(p_206319_, p_206321_);
        Encoder $$4 = $$3.comap(p_206271_ -> ImmutableMap.copyOf(p_206271_.m_6579_()));
        return Codec.of((Encoder)$$4, RegistryCodecs.m_206282_(p_206319_, p_206321_, $$3, p_206320_), (String)("DataPackRegistryCodec for " + p_206319_));
    }

    private static <E> Decoder<Registry<E>> m_206282_(final ResourceKey<? extends Registry<E>> p_206283_, final Codec<E> p_206284_, Decoder<Map<ResourceKey<E>, E>> p_206285_, Lifecycle p_206286_) {
        final Decoder $$4 = p_206285_.map(p_206302_ -> {
            MappedRegistry $$3 = new MappedRegistry(p_206283_, p_206286_, null);
            p_206302_.forEach((p_206275_, p_206276_) -> $$3.m_203505_(p_206275_, p_206276_, p_206286_));
            return $$3;
        });
        return new Decoder<Registry<E>>(){

            public <T> DataResult<Pair<Registry<E>, T>> decode(DynamicOps<T> p_206352_, T p_206353_) {
                DataResult $$2 = $$4.decode(p_206352_, p_206353_);
                if (p_206352_ instanceof RegistryOps) {
                    RegistryOps $$3 = (RegistryOps)p_206352_;
                    return $$3.m_206812_().map(p_206338_ -> this.m_206339_($$2, $$3, p_206338_.f_206784_())).orElseGet(() -> DataResult.error((String)"Can't load registry with this ops"));
                }
                return $$2.map(p_206331_ -> p_206331_.mapFirst(p_206344_ -> p_206344_));
            }

            private <T> DataResult<Pair<Registry<E>, T>> m_206339_(DataResult<Pair<WritableRegistry<E>, T>> p_206340_, RegistryOps<?> p_206341_, RegistryLoader p_206342_) {
                return p_206340_.flatMap(p_206350_ -> p_206342_.m_206762_((WritableRegistry)p_206350_.getFirst(), p_206283_, p_206284_, p_206341_.m_206831_()).map(p_206334_ -> Pair.of((Object)p_206334_, (Object)p_206350_.getSecond())));
            }
        };
    }

    private static <T> Codec<Map<ResourceKey<T>, T>> m_206315_(ResourceKey<? extends Registry<T>> p_206316_, Codec<T> p_206317_) {
        return Codec.unboundedMap(ResourceKey.m_195966_(p_206316_), p_206317_);
    }

    public static <E> Codec<HolderSet<E>> m_206279_(ResourceKey<? extends Registry<E>> p_206280_, Codec<E> p_206281_) {
        return RegistryCodecs.m_206287_(p_206280_, p_206281_, false);
    }

    public static <E> Codec<HolderSet<E>> m_206287_(ResourceKey<? extends Registry<E>> p_206288_, Codec<E> p_206289_, boolean p_206290_) {
        return HolderSetCodec.m_206685_(p_206288_, RegistryFileCodec.m_135589_(p_206288_, p_206289_), p_206290_);
    }

    public static <E> Codec<HolderSet<E>> m_206277_(ResourceKey<? extends Registry<E>> p_206278_) {
        return RegistryCodecs.m_206310_(p_206278_, false);
    }

    public static <E> Codec<HolderSet<E>> m_206310_(ResourceKey<? extends Registry<E>> p_206311_, boolean p_206312_) {
        return HolderSetCodec.m_206685_(p_206311_, RegistryFixedCodec.m_206740_(p_206311_), p_206312_);
    }

    record RegistryEntry<T>(ResourceKey<T> f_206354_, int f_206355_, T f_206356_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{RegistryEntry.class, "key;id;value", "f_206354_", "f_206355_", "f_206356_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{RegistryEntry.class, "key;id;value", "f_206354_", "f_206355_", "f_206356_"}, this);
        }

        @Override
        public final boolean equals(Object p_206365_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{RegistryEntry.class, "key;id;value", "f_206354_", "f_206355_", "f_206356_"}, this, p_206365_);
        }
    }
}

