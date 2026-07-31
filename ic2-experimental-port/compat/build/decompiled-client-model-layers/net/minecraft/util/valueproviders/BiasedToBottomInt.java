/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.util.valueproviders;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviderType;

public class BiasedToBottomInt
extends IntProvider {
    public static final Codec<BiasedToBottomInt> f_146359_ = RecordCodecBuilder.create(p_146373_ -> p_146373_.group((App)Codec.INT.fieldOf("min_inclusive").forGetter(p_146381_ -> p_146381_.f_146360_), (App)Codec.INT.fieldOf("max_inclusive").forGetter(p_146378_ -> p_146378_.f_146361_)).apply((Applicative)p_146373_, BiasedToBottomInt::new)).comapFlatMap(p_146371_ -> {
        if (p_146371_.f_146361_ < p_146371_.f_146360_) {
            return DataResult.error((String)("Max must be at least min, min_inclusive: " + p_146371_.f_146360_ + ", max_inclusive: " + p_146371_.f_146361_));
        }
        return DataResult.success((Object)p_146371_);
    }, Function.identity());
    private final int f_146360_;
    private final int f_146361_;

    private BiasedToBottomInt(int p_146364_, int p_146365_) {
        this.f_146360_ = p_146364_;
        this.f_146361_ = p_146365_;
    }

    public static BiasedToBottomInt m_146367_(int p_146368_, int p_146369_) {
        return new BiasedToBottomInt(p_146368_, p_146369_);
    }

    @Override
    public int m_214085_(RandomSource p_216832_) {
        return this.f_146360_ + p_216832_.m_188503_(p_216832_.m_188503_(this.f_146361_ - this.f_146360_ + 1) + 1);
    }

    @Override
    public int m_142739_() {
        return this.f_146360_;
    }

    @Override
    public int m_142737_() {
        return this.f_146361_;
    }

    @Override
    public IntProviderType<?> m_141948_() {
        return IntProviderType.f_146552_;
    }

    public String toString() {
        return "[" + this.f_146360_ + "-" + this.f_146361_ + "]";
    }
}

