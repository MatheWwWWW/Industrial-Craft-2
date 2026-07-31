/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 */
package net.minecraft.resources;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;

public class HolderSetCodec<E>
implements Codec<HolderSet<E>> {
    private final ResourceKey<? extends Registry<E>> f_206655_;
    private final Codec<Holder<E>> f_206656_;
    private final Codec<List<Holder<E>>> f_206657_;
    private final Codec<Either<TagKey<E>, List<Holder<E>>>> f_206658_;

    private static <E> Codec<List<Holder<E>>> m_206667_(Codec<Holder<E>> p_206668_, boolean p_206669_) {
        Function $$2 = ExtraCodecs.m_203984_(Holder::m_203376_);
        Codec $$3 = p_206668_.listOf().flatXmap($$2, $$2);
        if (p_206669_) {
            return $$3;
        }
        return Codec.either((Codec)$$3, p_206668_).xmap(p_206664_ -> (List)p_206664_.map(p_206694_ -> p_206694_, List::of), p_206684_ -> p_206684_.size() == 1 ? Either.right((Object)((Holder)p_206684_.get(0))) : Either.left((Object)p_206684_));
    }

    public static <E> Codec<HolderSet<E>> m_206685_(ResourceKey<? extends Registry<E>> p_206686_, Codec<Holder<E>> p_206687_, boolean p_206688_) {
        return new HolderSetCodec<E>(p_206686_, p_206687_, p_206688_);
    }

    private HolderSetCodec(ResourceKey<? extends Registry<E>> p_206660_, Codec<Holder<E>> p_206661_, boolean p_206662_) {
        this.f_206655_ = p_206660_;
        this.f_206656_ = p_206661_;
        this.f_206657_ = HolderSetCodec.m_206667_(p_206661_, p_206662_);
        this.f_206658_ = Codec.either(TagKey.m_203886_(p_206660_), this.f_206657_);
    }

    public <T> DataResult<Pair<HolderSet<E>, T>> decode(DynamicOps<T> p_206696_, T p_206697_) {
        RegistryOps $$2;
        Optional $$3;
        if (p_206696_ instanceof RegistryOps && ($$3 = ($$2 = (RegistryOps)p_206696_).m_206826_(this.f_206655_)).isPresent()) {
            Registry $$4 = $$3.get();
            return this.f_206658_.decode(p_206696_, p_206697_).map(p_206682_ -> p_206682_.mapFirst(p_206679_ -> (HolderSet)p_206679_.map($$4::m_203561_, HolderSet::m_205800_)));
        }
        return this.m_206670_(p_206696_, p_206697_);
    }

    public <T> DataResult<T> encode(HolderSet<E> p_206674_, DynamicOps<T> p_206675_, T p_206676_) {
        RegistryOps $$3;
        Optional $$4;
        if (p_206675_ instanceof RegistryOps && ($$4 = ($$3 = (RegistryOps)p_206675_).m_206826_(this.f_206655_)).isPresent()) {
            if (!p_206674_.m_207277_($$4.get())) {
                return DataResult.error((String)("HolderSet " + p_206674_ + " is not valid in current registry set"));
            }
            return this.f_206658_.encode((Object)p_206674_.m_203440_().mapRight(List::copyOf), p_206675_, p_206676_);
        }
        return this.m_206689_(p_206674_, p_206675_, p_206676_);
    }

    private <T> DataResult<Pair<HolderSet<E>, T>> m_206670_(DynamicOps<T> p_206671_, T p_206672_) {
        return this.f_206656_.listOf().decode(p_206671_, p_206672_).flatMap(p_206666_ -> {
            ArrayList<Holder.Direct> $$1 = new ArrayList<Holder.Direct>();
            for (Holder $$2 : (List)p_206666_.getFirst()) {
                if ($$2 instanceof Holder.Direct) {
                    Holder.Direct $$3 = (Holder.Direct)$$2;
                    $$1.add($$3);
                    continue;
                }
                return DataResult.error((String)("Can't decode element " + $$2 + " without registry"));
            }
            return DataResult.success((Object)new Pair(HolderSet.m_205800_($$1), p_206666_.getSecond()));
        });
    }

    private <T> DataResult<T> m_206689_(HolderSet<E> p_206690_, DynamicOps<T> p_206691_, T p_206692_) {
        return this.f_206657_.encode(p_206690_.m_203614_().toList(), p_206691_, p_206692_);
    }

    public /* synthetic */ DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
        return this.encode((HolderSet)object, dynamicOps, object2);
    }
}

