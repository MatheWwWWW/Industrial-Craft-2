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
import net.minecraft.resources.RegistryLoader;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class RegistryFileCodec<E>
implements Codec<Holder<E>> {
    private final ResourceKey<? extends Registry<E>> f_135570_;
    private final Codec<E> f_135571_;
    private final boolean f_135572_;

    public static <E> RegistryFileCodec<E> m_135589_(ResourceKey<? extends Registry<E>> p_135590_, Codec<E> p_135591_) {
        return RegistryFileCodec.m_135592_(p_135590_, p_135591_, true);
    }

    public static <E> RegistryFileCodec<E> m_135592_(ResourceKey<? extends Registry<E>> p_135593_, Codec<E> p_135594_, boolean p_135595_) {
        return new RegistryFileCodec<E>(p_135593_, p_135594_, p_135595_);
    }

    private RegistryFileCodec(ResourceKey<? extends Registry<E>> p_135574_, Codec<E> p_135575_, boolean p_135576_) {
        this.f_135570_ = p_135574_;
        this.f_135571_ = p_135575_;
        this.f_135572_ = p_135576_;
    }

    public <T> DataResult<T> encode(Holder<E> p_206716_, DynamicOps<T> p_206717_, T p_206718_) {
        RegistryOps $$3;
        Optional $$4;
        if (p_206717_ instanceof RegistryOps && ($$4 = ($$3 = (RegistryOps)p_206717_).m_206826_(this.f_135570_)).isPresent()) {
            if (!p_206716_.m_203401_($$4.get())) {
                return DataResult.error((String)("Element " + p_206716_ + " is not valid in current registry set"));
            }
            return (DataResult)p_206716_.m_203439_().map(p_206714_ -> ResourceLocation.f_135803_.encode((Object)p_206714_.m_135782_(), p_206717_, p_206718_), p_206710_ -> this.f_135571_.encode(p_206710_, p_206717_, p_206718_));
        }
        return this.f_135571_.encode(p_206716_.m_203334_(), p_206717_, p_206718_);
    }

    public <T> DataResult<Pair<Holder<E>, T>> decode(DynamicOps<T> p_135608_, T p_135609_) {
        if (p_135608_ instanceof RegistryOps) {
            RegistryOps $$2 = (RegistryOps)p_135608_;
            Optional $$3 = $$2.m_206826_(this.f_135570_);
            if ($$3.isEmpty()) {
                return DataResult.error((String)("Registry does not exist: " + this.f_135570_));
            }
            Registry $$4 = $$3.get();
            DataResult $$5 = ResourceLocation.f_135803_.decode(p_135608_, p_135609_);
            if ($$5.result().isEmpty()) {
                if (!this.f_135572_) {
                    return DataResult.error((String)"Inline definitions not allowed here");
                }
                return this.f_135571_.decode(p_135608_, p_135609_).map(p_206720_ -> p_206720_.mapFirst(Holder::m_205709_));
            }
            Pair $$6 = (Pair)$$5.result().get();
            ResourceKey $$7 = ResourceKey.m_135785_(this.f_135570_, (ResourceLocation)$$6.getFirst());
            Optional<RegistryLoader.Bound> $$8 = $$2.m_206812_();
            if ($$8.isPresent()) {
                return $$8.get().m_206793_(this.f_135570_, this.f_135571_, $$7, $$2.m_206831_()).map(p_206706_ -> Pair.of((Object)p_206706_, (Object)$$6.getSecond()));
            }
            DataResult $$9 = $$4.m_214185_($$7);
            return $$9.map(p_214215_ -> Pair.of((Object)p_214215_, (Object)$$6.getSecond())).setLifecycle(Lifecycle.stable());
        }
        return this.f_135571_.decode(p_135608_, p_135609_).map(p_214212_ -> p_214212_.mapFirst(Holder::m_205709_));
    }

    public String toString() {
        return "RegistryFileCodec[" + this.f_135570_ + " " + this.f_135571_ + "]";
    }

    public /* synthetic */ DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
        return this.encode((Holder)object, dynamicOps, object2);
    }
}

