/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Keyable
 *  javax.annotation.Nullable
 */
package net.minecraft.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.ExtraCodecs;

public interface StringRepresentable {
    public static final int f_216433_ = 16;

    public String m_7912_();

    public static <E extends Enum<E>> EnumCodec<E> m_216439_(Supplier<E[]> p_216440_) {
        Enum[] $$1 = (Enum[])p_216440_.get();
        if ($$1.length > 16) {
            Map<String, Enum> $$2 = Arrays.stream($$1).collect(Collectors.toMap(p_184753_ -> ((StringRepresentable)p_184753_).m_7912_(), p_216435_ -> p_216435_));
            return new EnumCodec($$1, p_216438_ -> p_216438_ == null ? null : (Enum)$$2.get(p_216438_));
        }
        return new EnumCodec($$1, p_216443_ -> {
            for (Enum $$2 : $$1) {
                if (!((StringRepresentable)((Object)$$2)).m_7912_().equals(p_216443_)) continue;
                return $$2;
            }
            return null;
        });
    }

    public static Keyable m_14357_(final StringRepresentable[] p_14358_) {
        return new Keyable(){

            public <T> Stream<T> keys(DynamicOps<T> p_184758_) {
                return Arrays.stream(p_14358_).map(StringRepresentable::m_7912_).map(arg_0 -> p_184758_.createString(arg_0));
            }
        };
    }

    @Deprecated
    public static class EnumCodec<E extends Enum<E>>
    implements Codec<E> {
        private Codec<E> f_216444_;
        private Function<String, E> f_216445_;

        public EnumCodec(E[] p_216447_, Function<String, E> p_216448_) {
            this.f_216444_ = ExtraCodecs.m_184425_(ExtraCodecs.m_184405_(p_216461_ -> ((StringRepresentable)p_216461_).m_7912_(), p_216448_), ExtraCodecs.m_184421_(p_216454_ -> ((Enum)p_216454_).ordinal(), p_216459_ -> p_216459_ >= 0 && p_216459_ < p_216447_.length ? p_216447_[p_216459_] : null, -1));
            this.f_216445_ = p_216448_;
        }

        public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> p_216463_, T p_216464_) {
            return this.f_216444_.decode(p_216463_, p_216464_);
        }

        public <T> DataResult<T> encode(E p_216450_, DynamicOps<T> p_216451_, T p_216452_) {
            return this.f_216444_.encode(p_216450_, p_216451_, p_216452_);
        }

        @Nullable
        public E m_216455_(@Nullable String p_216456_) {
            return (E)((Enum)this.f_216445_.apply(p_216456_));
        }

        public /* synthetic */ DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
            return this.encode((E)((Enum)object), (DynamicOps<T>)dynamicOps, (T)object2);
        }
    }
}

