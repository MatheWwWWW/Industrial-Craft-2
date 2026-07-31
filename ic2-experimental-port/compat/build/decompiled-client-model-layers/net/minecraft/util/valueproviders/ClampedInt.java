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

public class ClampedInt
extends IntProvider {
    public static final Codec<ClampedInt> f_146383_ = RecordCodecBuilder.create(p_146400_ -> p_146400_.group((App)IntProvider.f_146531_.fieldOf("source").forGetter(p_146410_ -> p_146410_.f_146384_), (App)Codec.INT.fieldOf("min_inclusive").forGetter(p_146408_ -> p_146408_.f_146385_), (App)Codec.INT.fieldOf("max_inclusive").forGetter(p_146405_ -> p_146405_.f_146386_)).apply((Applicative)p_146400_, ClampedInt::new)).comapFlatMap(p_146394_ -> {
        if (p_146394_.f_146386_ < p_146394_.f_146385_) {
            return DataResult.error((String)("Max must be at least min, min_inclusive: " + p_146394_.f_146385_ + ", max_inclusive: " + p_146394_.f_146386_));
        }
        return DataResult.success((Object)p_146394_);
    }, Function.identity());
    private final IntProvider f_146384_;
    private int f_146385_;
    private int f_146386_;

    public static ClampedInt m_146395_(IntProvider p_146396_, int p_146397_, int p_146398_) {
        return new ClampedInt(p_146396_, p_146397_, p_146398_);
    }

    public ClampedInt(IntProvider p_146389_, int p_146390_, int p_146391_) {
        this.f_146384_ = p_146389_;
        this.f_146385_ = p_146390_;
        this.f_146386_ = p_146391_;
    }

    @Override
    public int m_214085_(RandomSource p_216834_) {
        return Mth.m_14045_(this.f_146384_.m_214085_(p_216834_), this.f_146385_, this.f_146386_);
    }

    @Override
    public int m_142739_() {
        return Math.max(this.f_146385_, this.f_146384_.m_142739_());
    }

    @Override
    public int m_142737_() {
        return Math.min(this.f_146386_, this.f_146384_.m_142737_());
    }

    @Override
    public IntProviderType<?> m_141948_() {
        return IntProviderType.f_146553_;
    }
}

