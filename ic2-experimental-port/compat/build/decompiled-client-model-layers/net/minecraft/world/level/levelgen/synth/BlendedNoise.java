/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.synth;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.stream.IntStream;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

public class BlendedNoise
implements DensityFunction.SimpleFunction {
    private static final Codec<Double> f_230454_ = Codec.doubleRange((double)0.001, (double)1000.0);
    private static final MapCodec<BlendedNoise> f_230455_ = RecordCodecBuilder.mapCodec(p_230486_ -> p_230486_.group((App)f_230454_.fieldOf("xz_scale").forGetter(p_230497_ -> p_230497_.f_192799_), (App)f_230454_.fieldOf("y_scale").forGetter(p_230495_ -> p_230495_.f_192800_), (App)f_230454_.fieldOf("xz_factor").forGetter(p_230493_ -> p_230493_.f_230458_), (App)f_230454_.fieldOf("y_factor").forGetter(p_230490_ -> p_230490_.f_230459_), (App)Codec.doubleRange((double)1.0, (double)8.0).fieldOf("smear_scale_multiplier").forGetter(p_230488_ -> p_230488_.f_230460_)).apply((Applicative)p_230486_, BlendedNoise::m_230477_));
    public static final KeyDispatchDataCodec<BlendedNoise> f_210616_ = KeyDispatchDataCodec.m_216238_(f_230455_);
    private final PerlinNoise f_164288_;
    private final PerlinNoise f_164289_;
    private final PerlinNoise f_164290_;
    private final double f_230456_;
    private final double f_230457_;
    private final double f_230458_;
    private final double f_230459_;
    private final double f_230460_;
    private final double f_210617_;
    private final double f_192799_;
    private final double f_192800_;

    public static BlendedNoise m_230477_(double p_230478_, double p_230479_, double p_230480_, double p_230481_, double p_230482_) {
        return new BlendedNoise(new XoroshiroRandomSource(0L), p_230478_, p_230479_, p_230480_, p_230481_, p_230482_);
    }

    private BlendedNoise(PerlinNoise p_230469_, PerlinNoise p_230470_, PerlinNoise p_230471_, double p_230472_, double p_230473_, double p_230474_, double p_230475_, double p_230476_) {
        this.f_164288_ = p_230469_;
        this.f_164289_ = p_230470_;
        this.f_164290_ = p_230471_;
        this.f_192799_ = p_230472_;
        this.f_192800_ = p_230473_;
        this.f_230458_ = p_230474_;
        this.f_230459_ = p_230475_;
        this.f_230460_ = p_230476_;
        this.f_230456_ = 684.412 * this.f_192799_;
        this.f_230457_ = 684.412 * this.f_192800_;
        this.f_210617_ = p_230469_.m_210643_(this.f_230457_);
    }

    @VisibleForTesting
    public BlendedNoise(RandomSource p_230462_, double p_230463_, double p_230464_, double p_230465_, double p_230466_, double p_230467_) {
        this(PerlinNoise.m_230532_(p_230462_, IntStream.rangeClosed(-15, 0)), PerlinNoise.m_230532_(p_230462_, IntStream.rangeClosed(-15, 0)), PerlinNoise.m_230532_(p_230462_, IntStream.rangeClosed(-7, 0)), p_230463_, p_230464_, p_230465_, p_230466_, p_230467_);
    }

    public BlendedNoise m_230483_(RandomSource p_230484_) {
        return new BlendedNoise(p_230484_, this.f_192799_, this.f_192800_, this.f_230458_, this.f_230459_, this.f_230460_);
    }

    @Override
    public double m_207386_(DensityFunction.FunctionContext p_210621_) {
        double $$1 = (double)p_210621_.m_207115_() * this.f_230456_;
        double $$2 = (double)p_210621_.m_207114_() * this.f_230457_;
        double $$3 = (double)p_210621_.m_207113_() * this.f_230456_;
        double $$4 = $$1 / this.f_230458_;
        double $$5 = $$2 / this.f_230459_;
        double $$6 = $$3 / this.f_230458_;
        double $$7 = this.f_230457_ * this.f_230460_;
        double $$8 = $$7 / this.f_230459_;
        double $$9 = 0.0;
        double $$10 = 0.0;
        double $$11 = 0.0;
        boolean $$12 = true;
        double $$13 = 1.0;
        for (int $$14 = 0; $$14 < 8; ++$$14) {
            ImprovedNoise $$15 = this.f_164290_.m_75424_($$14);
            if ($$15 != null) {
                $$11 += $$15.m_75327_(PerlinNoise.m_75406_($$4 * $$13), PerlinNoise.m_75406_($$5 * $$13), PerlinNoise.m_75406_($$6 * $$13), $$8 * $$13, $$5 * $$13) / $$13;
            }
            $$13 /= 2.0;
        }
        double $$16 = ($$11 / 10.0 + 1.0) / 2.0;
        boolean $$17 = $$16 >= 1.0;
        boolean $$18 = $$16 <= 0.0;
        $$13 = 1.0;
        for (int $$19 = 0; $$19 < 16; ++$$19) {
            ImprovedNoise $$25;
            ImprovedNoise $$24;
            double $$20 = PerlinNoise.m_75406_($$1 * $$13);
            double $$21 = PerlinNoise.m_75406_($$2 * $$13);
            double $$22 = PerlinNoise.m_75406_($$3 * $$13);
            double $$23 = $$7 * $$13;
            if (!$$17 && ($$24 = this.f_164288_.m_75424_($$19)) != null) {
                $$9 += $$24.m_75327_($$20, $$21, $$22, $$23, $$2 * $$13) / $$13;
            }
            if (!$$18 && ($$25 = this.f_164289_.m_75424_($$19)) != null) {
                $$10 += $$25.m_75327_($$20, $$21, $$22, $$23, $$2 * $$13) / $$13;
            }
            $$13 /= 2.0;
        }
        return Mth.m_14085_($$9 / 512.0, $$10 / 512.0, $$16) / 128.0;
    }

    @Override
    public double m_207402_() {
        return -this.m_207401_();
    }

    @Override
    public double m_207401_() {
        return this.f_210617_;
    }

    @VisibleForTesting
    public void m_192817_(StringBuilder p_192818_) {
        p_192818_.append("BlendedNoise{minLimitNoise=");
        this.f_164288_.m_192890_(p_192818_);
        p_192818_.append(", maxLimitNoise=");
        this.f_164289_.m_192890_(p_192818_);
        p_192818_.append(", mainNoise=");
        this.f_164290_.m_192890_(p_192818_);
        p_192818_.append(String.format(Locale.ROOT, ", xzScale=%.3f, yScale=%.3f, xzMainScale=%.3f, yMainScale=%.3f, cellWidth=4, cellHeight=8", 684.412, 684.412, 8.555150000000001, 4.277575000000001)).append('}');
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
        return f_210616_;
    }
}

