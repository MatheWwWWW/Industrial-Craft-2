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
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviderType;

public class TrapezoidFloat
extends FloatProvider {
    public static final Codec<TrapezoidFloat> f_146561_ = RecordCodecBuilder.create(p_146578_ -> p_146578_.group((App)Codec.FLOAT.fieldOf("min").forGetter(p_146588_ -> Float.valueOf(p_146588_.f_146562_)), (App)Codec.FLOAT.fieldOf("max").forGetter(p_146586_ -> Float.valueOf(p_146586_.f_146563_)), (App)Codec.FLOAT.fieldOf("plateau").forGetter(p_146583_ -> Float.valueOf(p_146583_.f_146564_))).apply((Applicative)p_146578_, TrapezoidFloat::new)).comapFlatMap(p_146576_ -> {
        if (p_146576_.f_146563_ < p_146576_.f_146562_) {
            return DataResult.error((String)("Max must be larger than min: [" + p_146576_.f_146562_ + ", " + p_146576_.f_146563_ + "]"));
        }
        if (p_146576_.f_146564_ > p_146576_.f_146563_ - p_146576_.f_146562_) {
            return DataResult.error((String)("Plateau can at most be the full span: [" + p_146576_.f_146562_ + ", " + p_146576_.f_146563_ + "]"));
        }
        return DataResult.success((Object)p_146576_);
    }, Function.identity());
    private final float f_146562_;
    private final float f_146563_;
    private final float f_146564_;

    public static TrapezoidFloat m_146571_(float p_146572_, float p_146573_, float p_146574_) {
        return new TrapezoidFloat(p_146572_, p_146573_, p_146574_);
    }

    private TrapezoidFloat(float p_146567_, float p_146568_, float p_146569_) {
        this.f_146562_ = p_146567_;
        this.f_146563_ = p_146568_;
        this.f_146564_ = p_146569_;
    }

    @Override
    public float m_214084_(RandomSource p_216864_) {
        float $$1 = this.f_146563_ - this.f_146562_;
        float $$2 = ($$1 - this.f_146564_) / 2.0f;
        float $$3 = $$1 - $$2;
        return this.f_146562_ + p_216864_.m_188501_() * $$3 + p_216864_.m_188501_() * $$2;
    }

    @Override
    public float m_142735_() {
        return this.f_146562_;
    }

    @Override
    public float m_142734_() {
        return this.f_146563_;
    }

    @Override
    public FloatProviderType<?> m_141961_() {
        return FloatProviderType.f_146522_;
    }

    public String toString() {
        return "trapezoid(" + this.f_146564_ + ") in [" + this.f_146562_ + "-" + this.f_146563_ + "]";
    }
}

