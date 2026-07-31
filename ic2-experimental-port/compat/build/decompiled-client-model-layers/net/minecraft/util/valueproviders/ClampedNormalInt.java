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

public class ClampedNormalInt
extends IntProvider {
    public static final Codec<ClampedNormalInt> f_185867_ = RecordCodecBuilder.create(p_185887_ -> p_185887_.group((App)Codec.FLOAT.fieldOf("mean").forGetter(p_185905_ -> Float.valueOf(p_185905_.f_185868_)), (App)Codec.FLOAT.fieldOf("deviation").forGetter(p_185903_ -> Float.valueOf(p_185903_.f_185869_)), (App)Codec.INT.fieldOf("min_inclusive").forGetter(p_185901_ -> p_185901_.f_185870_), (App)Codec.INT.fieldOf("max_inclusive").forGetter(p_185898_ -> p_185898_.f_185871_)).apply((Applicative)p_185887_, ClampedNormalInt::new)).comapFlatMap(p_185885_ -> {
        if (p_185885_.f_185871_ < p_185885_.f_185870_) {
            return DataResult.error((String)("Max must be larger than min: [" + p_185885_.f_185870_ + ", " + p_185885_.f_185871_ + "]"));
        }
        return DataResult.success((Object)p_185885_);
    }, Function.identity());
    private float f_185868_;
    private float f_185869_;
    private int f_185870_;
    private int f_185871_;

    public static ClampedNormalInt m_185879_(float p_185880_, float p_185881_, int p_185882_, int p_185883_) {
        return new ClampedNormalInt(p_185880_, p_185881_, p_185882_, p_185883_);
    }

    private ClampedNormalInt(float p_185874_, float p_185875_, int p_185876_, int p_185877_) {
        this.f_185868_ = p_185874_;
        this.f_185869_ = p_185875_;
        this.f_185870_ = p_185876_;
        this.f_185871_ = p_185877_;
    }

    @Override
    public int m_214085_(RandomSource p_216844_) {
        return ClampedNormalInt.m_216845_(p_216844_, this.f_185868_, this.f_185869_, this.f_185870_, this.f_185871_);
    }

    public static int m_216845_(RandomSource p_216846_, float p_216847_, float p_216848_, float p_216849_, float p_216850_) {
        return (int)Mth.m_14036_(Mth.m_216291_(p_216846_, p_216847_, p_216848_), p_216849_, p_216850_);
    }

    @Override
    public int m_142739_() {
        return this.f_185870_;
    }

    @Override
    public int m_142737_() {
        return this.f_185871_;
    }

    @Override
    public IntProviderType<?> m_141948_() {
        return IntProviderType.f_185908_;
    }

    public String toString() {
        return "normal(" + this.f_185868_ + ", " + this.f_185869_ + ") in [" + this.f_185870_ + "-" + this.f_185871_ + "]";
    }
}

