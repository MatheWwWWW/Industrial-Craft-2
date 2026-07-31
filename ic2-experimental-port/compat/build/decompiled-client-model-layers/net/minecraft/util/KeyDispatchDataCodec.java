/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.MapCodec
 */
package net.minecraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;

public record KeyDispatchDataCodec<A>(Codec<A> f_216232_) {
    public static <A> KeyDispatchDataCodec<A> m_216236_(Codec<A> p_216237_) {
        return new KeyDispatchDataCodec<A>(p_216237_);
    }

    public static <A> KeyDispatchDataCodec<A> m_216238_(MapCodec<A> p_216239_) {
        return new KeyDispatchDataCodec<A>(p_216239_.codec());
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{KeyDispatchDataCodec.class, "codec", "f_216232_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{KeyDispatchDataCodec.class, "codec", "f_216232_"}, this);
    }

    @Override
    public final boolean equals(Object p_216241_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{KeyDispatchDataCodec.class, "codec", "f_216232_"}, this, p_216241_);
    }
}

