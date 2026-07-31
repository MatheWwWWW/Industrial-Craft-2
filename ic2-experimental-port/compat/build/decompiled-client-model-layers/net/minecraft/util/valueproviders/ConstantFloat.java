/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.util.valueproviders;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviderType;

public class ConstantFloat
extends FloatProvider {
    public static final ConstantFloat f_146451_ = new ConstantFloat(0.0f);
    public static final Codec<ConstantFloat> f_146452_ = Codec.either((Codec)Codec.FLOAT, (Codec)RecordCodecBuilder.create(p_146465_ -> p_146465_.group((App)Codec.FLOAT.fieldOf("value").forGetter(p_146473_ -> Float.valueOf(p_146473_.f_146453_))).apply((Applicative)p_146465_, ConstantFloat::new))).xmap(p_146463_ -> (ConstantFloat)p_146463_.map(ConstantFloat::m_146458_, p_146470_ -> p_146470_), p_146461_ -> Either.left((Object)Float.valueOf(p_146461_.f_146453_)));
    private final float f_146453_;

    public static ConstantFloat m_146458_(float p_146459_) {
        if (p_146459_ == 0.0f) {
            return f_146451_;
        }
        return new ConstantFloat(p_146459_);
    }

    private ConstantFloat(float p_146456_) {
        this.f_146453_ = p_146456_;
    }

    public float m_146474_() {
        return this.f_146453_;
    }

    @Override
    public float m_214084_(RandomSource p_216852_) {
        return this.f_146453_;
    }

    @Override
    public float m_142735_() {
        return this.f_146453_;
    }

    @Override
    public float m_142734_() {
        return this.f_146453_ + 1.0f;
    }

    @Override
    public FloatProviderType<?> m_141961_() {
        return FloatProviderType.f_146519_;
    }

    public String toString() {
        return Float.toString(this.f_146453_);
    }
}

