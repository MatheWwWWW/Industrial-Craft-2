/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonElement
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Decoder
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Lifecycle
 */
package net.minecraft.resources;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.RegistryResourceAccess;
import net.minecraft.resources.ResourceKey;

public class RegistryLoader {
    private final RegistryResourceAccess f_206750_;
    private final Map<ResourceKey<? extends Registry<?>>, ReadCache<?>> f_206751_ = new IdentityHashMap();

    RegistryLoader(RegistryResourceAccess p_206753_) {
        this.f_206750_ = p_206753_;
    }

    public <E> DataResult<? extends Registry<E>> m_206762_(WritableRegistry<E> p_206763_, ResourceKey<? extends Registry<E>> p_206764_, Codec<E> p_206765_, DynamicOps<JsonElement> p_206766_) {
        Map $$4 = this.f_206750_.m_214030_(p_206764_);
        DataResult $$5 = DataResult.success(p_206763_, (Lifecycle)Lifecycle.stable());
        for (Map.Entry $$6 : $$4.entrySet()) {
            $$5 = $$5.flatMap(p_214227_ -> this.m_214228_((WritableRegistry)p_214227_, p_206764_, p_206765_, (ResourceKey)$$6.getKey(), Optional.of((RegistryResourceAccess.EntryThunk)$$6.getValue()), p_206766_).map(p_206761_ -> p_214227_));
        }
        return $$5.setPartial(p_206763_);
    }

    <E> DataResult<Holder<E>> m_206767_(WritableRegistry<E> p_206768_, ResourceKey<? extends Registry<E>> p_206769_, Codec<E> p_206770_, ResourceKey<E> p_206771_, DynamicOps<JsonElement> p_206772_) {
        Optional<RegistryResourceAccess.EntryThunk<E>> $$5 = this.f_206750_.m_213852_(p_206771_);
        return this.m_214228_(p_206768_, p_206769_, p_206770_, p_206771_, $$5, p_206772_);
    }

    private <E> DataResult<Holder<E>> m_214228_(WritableRegistry<E> p_214229_, ResourceKey<? extends Registry<E>> p_214230_, Codec<E> p_214231_, ResourceKey<E> p_214232_, Optional<RegistryResourceAccess.EntryThunk<E>> p_214233_, DynamicOps<JsonElement> p_214234_) {
        DataResult $$14;
        ReadCache<E> $$6 = this.m_206773_(p_214230_);
        DataResult $$7 = $$6.f_206803_.get(p_214232_);
        if ($$7 != null) {
            return $$7;
        }
        Holder $$8 = p_214229_.m_214121_(p_214232_);
        $$6.f_206803_.put(p_214232_, DataResult.success($$8));
        if (p_214233_.isEmpty()) {
            if (p_214229_.m_142003_(p_214232_)) {
                DataResult $$9 = DataResult.success($$8, (Lifecycle)Lifecycle.stable());
            } else {
                DataResult $$10 = DataResult.error((String)("Missing referenced custom/removed registry entry for registry " + p_214230_ + " named " + p_214232_.m_135782_()));
            }
        } else {
            DataResult<RegistryResourceAccess.ParsedEntry<E>> $$11 = p_214233_.get().m_214270_(p_214234_, (Decoder<E>)p_214231_);
            Optional $$12 = $$11.result();
            if ($$12.isPresent()) {
                RegistryResourceAccess.ParsedEntry $$13 = (RegistryResourceAccess.ParsedEntry)$$12.get();
                p_214229_.m_203384_($$13.f_195951_(), p_214232_, $$13.f_195950_(), $$11.lifecycle());
            }
            $$14 = $$11.map(p_206756_ -> $$8);
        }
        $$6.f_206803_.put(p_214232_, $$14);
        return $$14;
    }

    private <E> ReadCache<E> m_206773_(ResourceKey<? extends Registry<E>> p_206774_) {
        return this.f_206751_.computeIfAbsent(p_206774_, p_206782_ -> new ReadCache());
    }

    public Bound m_206757_(RegistryAccess.Writable p_206758_) {
        return new Bound(p_206758_, this);
    }

    static final class ReadCache<E> {
        final Map<ResourceKey<E>, DataResult<Holder<E>>> f_206803_ = Maps.newIdentityHashMap();

        ReadCache() {
        }
    }

    public record Bound(RegistryAccess.Writable f_206783_, RegistryLoader f_206784_) {
        public <E> DataResult<? extends Registry<E>> m_206789_(ResourceKey<? extends Registry<E>> p_206790_, Codec<E> p_206791_, DynamicOps<JsonElement> p_206792_) {
            WritableRegistry $$3 = this.f_206783_.m_206253_(p_206790_);
            return this.f_206784_.m_206762_($$3, p_206790_, p_206791_, p_206792_);
        }

        public <E> DataResult<Holder<E>> m_206793_(ResourceKey<? extends Registry<E>> p_206794_, Codec<E> p_206795_, ResourceKey<E> p_206796_, DynamicOps<JsonElement> p_206797_) {
            WritableRegistry $$4 = this.f_206783_.m_206253_(p_206794_);
            return this.f_206784_.m_206767_($$4, p_206794_, p_206795_, p_206796_, p_206797_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Bound.class, "access;loader", "f_206783_", "f_206784_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Bound.class, "access;loader", "f_206783_", "f_206784_"}, this);
        }

        @Override
        public final boolean equals(Object p_206800_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Bound.class, "access;loader", "f_206783_", "f_206784_"}, this, p_206800_);
        }
    }
}

