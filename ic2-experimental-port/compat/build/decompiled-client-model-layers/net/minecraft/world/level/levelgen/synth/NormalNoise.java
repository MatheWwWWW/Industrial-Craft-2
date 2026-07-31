/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.doubles.DoubleArrayList
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 *  it.unimi.dsi.fastutil.doubles.DoubleListIterator
 */
package net.minecraft.world.level.levelgen.synth;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

public class NormalNoise {
    private static final double f_164344_ = 1.0181268882175227;
    private static final double f_164345_ = 0.3333333333333333;
    private final double f_75373_;
    private final PerlinNoise f_75374_;
    private final PerlinNoise f_75375_;
    private final double f_210624_;
    private final NoiseParameters f_210625_;

    @Deprecated
    public static NormalNoise m_230508_(RandomSource p_230509_, NoiseParameters p_230510_) {
        return new NormalNoise(p_230509_, p_230510_, false);
    }

    public static NormalNoise m_230504_(RandomSource p_230505_, int p_230506_, double ... p_230507_) {
        return NormalNoise.m_230511_(p_230505_, new NoiseParameters(p_230506_, (DoubleList)new DoubleArrayList(p_230507_)));
    }

    public static NormalNoise m_230511_(RandomSource p_230512_, NoiseParameters p_230513_) {
        return new NormalNoise(p_230512_, p_230513_, true);
    }

    private NormalNoise(RandomSource p_230501_, NoiseParameters p_230502_, boolean p_230503_) {
        int $$3 = p_230502_.f_192853_;
        DoubleList $$4 = p_230502_.f_192854_;
        this.f_210625_ = p_230502_;
        if (p_230503_) {
            this.f_75374_ = PerlinNoise.m_230535_(p_230501_, $$3, $$4);
            this.f_75375_ = PerlinNoise.m_230535_(p_230501_, $$3, $$4);
        } else {
            this.f_75374_ = PerlinNoise.m_230525_(p_230501_, $$3, $$4);
            this.f_75375_ = PerlinNoise.m_230525_(p_230501_, $$3, $$4);
        }
        int $$5 = Integer.MAX_VALUE;
        int $$6 = Integer.MIN_VALUE;
        DoubleListIterator $$7 = $$4.iterator();
        while ($$7.hasNext()) {
            int $$8 = $$7.nextIndex();
            double $$9 = $$7.nextDouble();
            if ($$9 == 0.0) continue;
            $$5 = Math.min($$5, $$8);
            $$6 = Math.max($$6, $$8);
        }
        this.f_75373_ = 0.16666666666666666 / NormalNoise.m_75384_($$6 - $$5);
        this.f_210624_ = (this.f_75374_.m_210642_() + this.f_75375_.m_210642_()) * this.f_75373_;
    }

    public double m_210630_() {
        return this.f_210624_;
    }

    private static double m_75384_(int p_75385_) {
        return 0.1 * (1.0 + 1.0 / (double)(p_75385_ + 1));
    }

    public double m_75380_(double p_75381_, double p_75382_, double p_75383_) {
        double $$3 = p_75381_ * 1.0181268882175227;
        double $$4 = p_75382_ * 1.0181268882175227;
        double $$5 = p_75383_ * 1.0181268882175227;
        return (this.f_75374_.m_75408_(p_75381_, p_75382_, p_75383_) + this.f_75375_.m_75408_($$3, $$4, $$5)) * this.f_75373_;
    }

    public NoiseParameters m_192842_() {
        return this.f_210625_;
    }

    @VisibleForTesting
    public void m_192846_(StringBuilder p_192847_) {
        p_192847_.append("NormalNoise {");
        p_192847_.append("first: ");
        this.f_75374_.m_192890_(p_192847_);
        p_192847_.append(", second: ");
        this.f_75375_.m_192890_(p_192847_);
        p_192847_.append("}");
    }

    public record NoiseParameters(int f_192853_, DoubleList f_192854_) {
        public static final Codec<NoiseParameters> f_192851_ = RecordCodecBuilder.create(p_192865_ -> p_192865_.group((App)Codec.INT.fieldOf("firstOctave").forGetter(NoiseParameters::f_192853_), (App)Codec.DOUBLE.listOf().fieldOf("amplitudes").forGetter(NoiseParameters::f_192854_)).apply((Applicative)p_192865_, NoiseParameters::new));
        public static final Codec<Holder<NoiseParameters>> f_192852_ = RegistryFileCodec.m_135589_(Registry.f_194568_, f_192851_);

        public NoiseParameters(int p_192861_, List<Double> p_192862_) {
            this(p_192861_, (DoubleList)new DoubleArrayList(p_192862_));
        }

        public NoiseParameters(int p_192857_, double p_192858_, double ... p_192859_) {
            this(p_192857_, (DoubleList)Util.m_137469_(new DoubleArrayList(p_192859_), p_210636_ -> p_210636_.add(0, p_192858_)));
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{NoiseParameters.class, "firstOctave;amplitudes", "f_192853_", "f_192854_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{NoiseParameters.class, "firstOctave;amplitudes", "f_192853_", "f_192854_"}, this);
        }

        @Override
        public final boolean equals(Object p_210638_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{NoiseParameters.class, "firstOctave;amplitudes", "f_192853_", "f_192854_"}, this, p_210638_);
        }
    }
}

