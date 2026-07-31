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

public class ClampedNormalFloat
extends FloatProvider {
    public static final Codec<ClampedNormalFloat> f_146411_ = RecordCodecBuilder.create(p_146431_ -> p_146431_.group((App)Codec.FLOAT.fieldOf("mean").forGetter(p_146449_ -> Float.valueOf(p_146449_.f_146412_)), (App)Codec.FLOAT.fieldOf("deviation").forGetter(p_146447_ -> Float.valueOf(p_146447_.f_146413_)), (App)Codec.FLOAT.fieldOf("min").forGetter(p_146445_ -> Float.valueOf(p_146445_.f_146414_)), (App)Codec.FLOAT.fieldOf("max").forGetter(p_146442_ -> Float.valueOf(p_146442_.f_146415_))).apply((Applicative)p_146431_, ClampedNormalFloat::new)).comapFlatMap(p_146429_ -> {
        if (p_146429_.f_146415_ < p_146429_.f_146414_) {
            return DataResult.error((String)("Max must be larger than min: [" + p_146429_.f_146414_ + ", " + p_146429_.f_146415_ + "]"));
        }
        return DataResult.success((Object)p_146429_);
    }, Function.identity());
    private float f_146412_;
    private float f_146413_;
    private float f_146414_;
    private float f_146415_;

    public static ClampedNormalFloat m_146423_(float p_146424_, float p_146425_, float p_146426_, float p_146427_) {
        return new ClampedNormalFloat(p_146424_, p_146425_, p_146426_, p_146427_);
    }

    private ClampedNormalFloat(float p_146418_, float p_146419_, float p_146420_, float p_146421_) {
        this.f_146412_ = p_146418_;
        this.f_146413_ = p_146419_;
        this.f_146414_ = p_146420_;
        this.f_146415_ = p_146421_;
    }

    @Override
    public float m_214084_(RandomSource p_216836_) {
        return ClampedNormalFloat.m_216837_(p_216836_, this.f_146412_, this.f_146413_, this.f_146414_, this.f_146415_);
    }

    public static float m_216837_(RandomSource p_216838_, float p_216839_, float p_216840_, float p_216841_, float p_216842_) {
        return Mth.m_14036_(Mth.m_216291_(p_216838_, p_216839_, p_216840_), p_216841_, p_216842_);
    }

    @Override
    public float m_142735_() {
        return this.f_146414_;
    }

    @Override
    public float m_142734_() {
        return this.f_146415_;
    }

    @Override
    public FloatProviderType<?> m_141961_() {
        return FloatProviderType.f_146521_;
    }

    public String toString() {
        return "normal(" + this.f_146412_ + ", " + this.f_146413_ + ") in [" + this.f_146414_ + "-" + this.f_146415_ + "]";
    }
}

