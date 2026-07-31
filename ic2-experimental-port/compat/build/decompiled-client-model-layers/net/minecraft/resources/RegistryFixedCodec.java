/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Lifecycle
 */
package net.minecraft.resources;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class RegistryFixedCodec<E>
implements Codec<Holder<E>> {
    private final ResourceKey<? extends Registry<E>> f_206721_;

    public static <E> RegistryFixedCodec<E> m_206740_(ResourceKey<? extends Registry<E>> p_206741_) {
        return new RegistryFixedCodec<E>(p_206741_);
    }

    private RegistryFixedCodec(ResourceKey<? extends Registry<E>> p_206723_) {
        this.f_206721_ = p_206723_;
    }

    public <T> DataResult<T> encode(Holder<E> p_206729_, DynamicOps<T> p_206730_, T p_206731_) {
        RegistryOps $$3;
        Optional $$4;
        if (p_206730_ instanceof RegistryOps && ($$4 = ($$3 = (RegistryOps)p_206730_).m_206826_(this.f_206721_)).isPresent()) {
            if (!p_206729_.m_203401_($$4.get())) {
                return DataResult.error((String)("Element " + p_206729_ + " is not valid in current registry set"));
            }
            return (DataResult)p_206729_.m_203439_().map(p_206727_ -> ResourceLocation.f_135803_.encode((Object)p_206727_.m_135782_(), p_206730_, p_206731_), p_206733_ -> DataResult.error((String)("Elements from registry " + this.f_206721_ + " can't be serialized to a value")));
        }
        return DataResult.error((String)("Can't access registry " + this.f_206721_));
    }

    public <T> DataResult<Pair<Holder<E>, T>> decode(DynamicOps<T> p_206743_, T p_206744_) {
        RegistryOps $$2;
        Optional $$3;
        if (p_206743_ instanceof RegistryOps && ($$3 = ($$2 = (RegistryOps)p_206743_).m_206826_(this.f_206721_)).isPresent()) {
            return ResourceLocation.f_135803_.decode(p_206743_, p_206744_).flatMap(p_214221_ -> {
                ResourceLocation $$2 = (ResourceLocation)p_214221_.getFirst();
                DataResult $$3 = ((Registry)$$3.get()).m_214185_(ResourceKey.m_135785_(this.f_206721_, $$2));
                return $$3.map(p_214218_ -> Pair.of((Object)p_214218_, (Object)p_214221_.getSecond())).setLifecycle(Lifecycle.stable());
            });
        }
        return DataResult.error((String)("Can't access registry " + this.f_206721_));
    }

    public String toString() {
        return "RegistryFixedCodec[" + this.f_206721_ + "]";
    }

    public /* synthetic */ DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
        return this.encode((Holder)object, dynamicOps, object2);
    }
}

