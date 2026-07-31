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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviderType;

public class UniformInt
extends IntProvider {
    public static final Codec<UniformInt> f_146614_ = RecordCodecBuilder.create(p_146628_ -> p_146628_.group((App)Codec.INT.fieldOf("min_inclusive").forGetter(p_146636_ -> p_146636_.f_146615_), (App)Codec.INT.fieldOf("max_inclusive").forGetter(p_146633_ -> p_146633_.f_146616_)).apply((Applicative)p_146628_, UniformInt::new)).comapFlatMap(p_146626_ -> {
        if (p_146626_.f_146616_ < p_146626_.f_146615_) {
            return DataResult.error((String)("Max must be at least min, min_inclusive: " + p_146626_.f_146615_ + ", max_inclusive: " + p_146626_.f_146616_));
        }
        return DataResult.success((Object)p_146626_);
    }, Function.identity());
    private final int f_146615_;
    private final int f_146616_;

    private UniformInt(int p_146619_, int p_146620_) {
        this.f_146615_ = p_146619_;
        this.f_146616_ = p_146620_;
    }

    public static UniformInt m_146622_(int p_146623_, int p_146624_) {
        return new UniformInt(p_146623_, p_146624_);
    }

    @Override
    public int m_214085_(RandomSource p_216868_) {
        return Mth.m_216287_(p_216868_, this.f_146615_, this.f_146616_);
    }

    @Override
    public int m_142739_() {
        return this.f_146615_;
    }

    @Override
    public int m_142737_() {
        return this.f_146616_;
    }

    @Override
    public IntProviderType<?> m_141948_() {
        return IntProviderType.f_146551_;
    }

    public String toString() {
        return "[" + this.f_146615_ + "-" + this.f_146616_ + "]";
    }
}

