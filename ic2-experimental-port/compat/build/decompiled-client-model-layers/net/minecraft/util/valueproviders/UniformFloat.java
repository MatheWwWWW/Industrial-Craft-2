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
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviderType;

public class UniformFloat
extends FloatProvider {
    public static final Codec<UniformFloat> f_146590_ = RecordCodecBuilder.create(p_146601_ -> p_146601_.group((App)Codec.FLOAT.fieldOf("min_inclusive").forGetter(p_146612_ -> Float.valueOf(p_146612_.f_146591_)), (App)Codec.FLOAT.fieldOf("max_exclusive").forGetter(p_146609_ -> Float.valueOf(p_146609_.f_146592_))).apply((Applicative)p_146601_, UniformFloat::new)).comapFlatMap(p_146599_ -> {
        if (p_146599_.f_146592_ <= p_146599_.f_146591_) {
            return DataResult.error((String)("Max must be larger than min, min_inclusive: " + p_146599_.f_146591_ + ", max_exclusive: " + p_146599_.f_146592_));
        }
        return DataResult.success((Object)p_146599_);
    }, Function.identity());
    private final float f_146591_;
    private final float f_146592_;

    private UniformFloat(float p_146595_, float p_146596_) {
        this.f_146591_ = p_146595_;
        this.f_146592_ = p_146596_;
    }

    public static UniformFloat m_146605_(float p_146606_, float p_146607_) {
        if (p_146607_ <= p_146606_) {
            throw new IllegalArgumentException("Max must exceed min");
        }
        return new UniformFloat(p_146606_, p_146607_);
    }

    @Override
    public float m_214084_(RandomSource p_216866_) {
        return Mth.m_216283_(p_216866_, this.f_146591_, this.f_146592_);
    }

    @Override
    public float m_142735_() {
        return this.f_146591_;
    }

    @Override
    public float m_142734_() {
        return this.f_146592_;
    }

    @Override
    public FloatProviderType<?> m_141961_() {
        return FloatProviderType.f_146520_;
    }

    public String toString() {
        return "[" + this.f_146591_ + "-" + this.f_146592_ + "]";
    }
}

