/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.doubles.DoubleArrayList
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 *  it.unimi.dsi.fastutil.ints.IntBidirectionalIterator
 *  it.unimi.dsi.fastutil.ints.IntRBTreeSet
 *  it.unimi.dsi.fastutil.ints.IntSortedSet
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.synth;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;

public class PerlinNoise {
    private static final int f_164358_ = 0x2000000;
    private final ImprovedNoise[] f_75390_;
    private final int f_192867_;
    private final DoubleList f_75391_;
    private final double f_75392_;
    private final double f_75393_;
    private final double f_210641_;

    @Deprecated
    public static PerlinNoise m_230532_(RandomSource p_230533_, IntStream p_230534_) {
        return new PerlinNoise(p_230533_, PerlinNoise.m_75430_((IntSortedSet)new IntRBTreeSet((Collection)p_230534_.boxed().collect(ImmutableList.toImmutableList()))), false);
    }

    @Deprecated
    public static PerlinNoise m_230525_(RandomSource p_230526_, int p_230527_, DoubleList p_230528_) {
        return new PerlinNoise(p_230526_, (Pair<Integer, DoubleList>)Pair.of((Object)p_230527_, (Object)p_230528_), false);
    }

    public static PerlinNoise m_230539_(RandomSource p_230540_, IntStream p_230541_) {
        return PerlinNoise.m_230529_(p_230540_, (List)p_230541_.boxed().collect(ImmutableList.toImmutableList()));
    }

    public static PerlinNoise m_230529_(RandomSource p_230530_, List<Integer> p_230531_) {
        return new PerlinNoise(p_230530_, PerlinNoise.m_75430_((IntSortedSet)new IntRBTreeSet(p_230531_)), true);
    }

    public static PerlinNoise m_230520_(RandomSource p_230521_, int p_230522_, double p_230523_, double ... p_230524_) {
        DoubleArrayList $$4 = new DoubleArrayList(p_230524_);
        $$4.add(0, p_230523_);
        return new PerlinNoise(p_230521_, (Pair<Integer, DoubleList>)Pair.of((Object)p_230522_, (Object)$$4), true);
    }

    public static PerlinNoise m_230535_(RandomSource p_230536_, int p_230537_, DoubleList p_230538_) {
        return new PerlinNoise(p_230536_, (Pair<Integer, DoubleList>)Pair.of((Object)p_230537_, (Object)p_230538_), true);
    }

    private static Pair<Integer, DoubleList> m_75430_(IntSortedSet p_75431_) {
        int $$2;
        if (p_75431_.isEmpty()) {
            throw new IllegalArgumentException("Need some octaves!");
        }
        int $$1 = -p_75431_.firstInt();
        int $$3 = $$1 + ($$2 = p_75431_.lastInt()) + 1;
        if ($$3 < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
        }
        DoubleArrayList $$4 = new DoubleArrayList(new double[$$3]);
        IntBidirectionalIterator $$5 = p_75431_.iterator();
        while ($$5.hasNext()) {
            int $$6 = $$5.nextInt();
            $$4.set($$6 + $$1, 1.0);
        }
        return Pair.of((Object)(-$$1), (Object)$$4);
    }

    protected PerlinNoise(RandomSource p_230515_, Pair<Integer, DoubleList> p_230516_, boolean p_230517_) {
        this.f_192867_ = (Integer)p_230516_.getFirst();
        this.f_75391_ = (DoubleList)p_230516_.getSecond();
        int $$3 = this.f_75391_.size();
        int $$4 = -this.f_192867_;
        this.f_75390_ = new ImprovedNoise[$$3];
        if (p_230517_) {
            PositionalRandomFactory $$5 = p_230515_.m_188582_();
            for (int $$6 = 0; $$6 < $$3; ++$$6) {
                if (this.f_75391_.getDouble($$6) == 0.0) continue;
                int $$7 = this.f_192867_ + $$6;
                this.f_75390_[$$6] = new ImprovedNoise($$5.m_214111_("octave_" + $$7));
            }
        } else {
            double $$9;
            ImprovedNoise $$8 = new ImprovedNoise(p_230515_);
            if ($$4 >= 0 && $$4 < $$3 && ($$9 = this.f_75391_.getDouble($$4)) != 0.0) {
                this.f_75390_[$$4] = $$8;
            }
            for (int $$10 = $$4 - 1; $$10 >= 0; --$$10) {
                if ($$10 < $$3) {
                    double $$11 = this.f_75391_.getDouble($$10);
                    if ($$11 != 0.0) {
                        this.f_75390_[$$10] = new ImprovedNoise(p_230515_);
                        continue;
                    }
                    PerlinNoise.m_230518_(p_230515_);
                    continue;
                }
                PerlinNoise.m_230518_(p_230515_);
            }
            if (Arrays.stream(this.f_75390_).filter(Objects::nonNull).count() != this.f_75391_.stream().filter(p_192897_ -> p_192897_ != 0.0).count()) {
                throw new IllegalStateException("Failed to create correct number of noise levels for given non-zero amplitudes");
            }
            if ($$4 < $$3 - 1) {
                throw new IllegalArgumentException("Positive octaves are temporarily disabled");
            }
        }
        this.f_75393_ = Math.pow(2.0, -$$4);
        this.f_75392_ = Math.pow(2.0, $$3 - 1) / (Math.pow(2.0, $$3) - 1.0);
        this.f_210641_ = this.m_210649_(2.0);
    }

    protected double m_210642_() {
        return this.f_210641_;
    }

    private static void m_230518_(RandomSource p_230519_) {
        p_230519_.m_190110_(262);
    }

    public double m_75408_(double p_75409_, double p_75410_, double p_75411_) {
        return this.m_75417_(p_75409_, p_75410_, p_75411_, 0.0, 0.0, false);
    }

    @Deprecated
    public double m_75417_(double p_75418_, double p_75419_, double p_75420_, double p_75421_, double p_75422_, boolean p_75423_) {
        double $$6 = 0.0;
        double $$7 = this.f_75393_;
        double $$8 = this.f_75392_;
        for (int $$9 = 0; $$9 < this.f_75390_.length; ++$$9) {
            ImprovedNoise $$10 = this.f_75390_[$$9];
            if ($$10 != null) {
                double $$11 = $$10.m_75327_(PerlinNoise.m_75406_(p_75418_ * $$7), p_75423_ ? -$$10.f_75322_ : PerlinNoise.m_75406_(p_75419_ * $$7), PerlinNoise.m_75406_(p_75420_ * $$7), p_75421_ * $$7, p_75422_ * $$7);
                $$6 += this.f_75391_.getDouble($$9) * $$11 * $$8;
            }
            $$7 *= 2.0;
            $$8 /= 2.0;
        }
        return $$6;
    }

    public double m_210643_(double p_210644_) {
        return this.m_210649_(p_210644_ + 2.0);
    }

    private double m_210649_(double p_210650_) {
        double $$1 = 0.0;
        double $$2 = this.f_75392_;
        for (int $$3 = 0; $$3 < this.f_75390_.length; ++$$3) {
            ImprovedNoise $$4 = this.f_75390_[$$3];
            if ($$4 != null) {
                $$1 += this.f_75391_.getDouble($$3) * p_210650_ * $$2;
            }
            $$2 /= 2.0;
        }
        return $$1;
    }

    @Nullable
    public ImprovedNoise m_75424_(int p_75425_) {
        return this.f_75390_[this.f_75390_.length - 1 - p_75425_];
    }

    public static double m_75406_(double p_75407_) {
        return p_75407_ - (double)Mth.m_14134_(p_75407_ / 3.3554432E7 + 0.5) * 3.3554432E7;
    }

    protected int m_192872_() {
        return this.f_192867_;
    }

    protected DoubleList m_192892_() {
        return this.f_75391_;
    }

    @VisibleForTesting
    public void m_192890_(StringBuilder p_192891_) {
        p_192891_.append("PerlinNoise{");
        List<String> $$1 = this.f_75391_.stream().map(p_192889_ -> String.format(Locale.ROOT, "%.2f", p_192889_)).toList();
        p_192891_.append("first octave: ").append(this.f_192867_).append(", amplitudes: ").append($$1).append(", noise levels: [");
        for (int $$2 = 0; $$2 < this.f_75390_.length; ++$$2) {
            p_192891_.append($$2).append(": ");
            ImprovedNoise $$3 = this.f_75390_[$$2];
            if ($$3 == null) {
                p_192891_.append("null");
            } else {
                $$3.m_192823_(p_192891_);
            }
            p_192891_.append(", ");
        }
        p_192891_.append("]");
        p_192891_.append("}");
    }
}

