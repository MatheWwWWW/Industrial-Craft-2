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
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviderType;

public class ConstantInt
extends IntProvider {
    public static final ConstantInt f_146476_ = new ConstantInt(0);
    public static final Codec<ConstantInt> f_146477_ = Codec.either((Codec)Codec.INT, (Codec)RecordCodecBuilder.create(p_146490_ -> p_146490_.group((App)Codec.INT.fieldOf("value").forGetter(p_146498_ -> p_146498_.f_146478_)).apply((Applicative)p_146490_, ConstantInt::new))).xmap(p_146488_ -> (ConstantInt)p_146488_.map(ConstantInt::m_146483_, p_146495_ -> p_146495_), p_146486_ -> Either.left((Object)p_146486_.f_146478_));
    private final int f_146478_;

    public static ConstantInt m_146483_(int p_146484_) {
        if (p_146484_ == 0) {
            return f_146476_;
        }
        return new ConstantInt(p_146484_);
    }

    private ConstantInt(int p_146481_) {
        this.f_146478_ = p_146481_;
    }

    public int m_146499_() {
        return this.f_146478_;
    }

    @Override
    public int m_214085_(RandomSource p_216854_) {
        return this.f_146478_;
    }

    @Override
    public int m_142739_() {
        return this.f_146478_;
    }

    @Override
    public int m_142737_() {
        return this.f_146478_;
    }

    @Override
    public IntProviderType<?> m_141948_() {
        return IntProviderType.f_146550_;
    }

    public String toString() {
        return Integer.toString(this.f_146478_);
    }
}

