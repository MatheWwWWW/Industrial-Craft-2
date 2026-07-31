/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntRBTreeSet
 *  it.unimi.dsi.fastutil.ints.IntSortedSet
 */
package net.minecraft.world.level.levelgen.synth;

import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

public class PerlinSimplexNoise {
    private final SimplexNoise[] f_75432_;
    private final double f_75433_;
    private final double f_75434_;

    public PerlinSimplexNoise(RandomSource p_230546_, List<Integer> p_230547_) {
        this(p_230546_, (IntSortedSet)new IntRBTreeSet(p_230547_));
    }

    private PerlinSimplexNoise(RandomSource p_230543_, IntSortedSet p_230544_) {
        int $$3;
        if (p_230544_.isEmpty()) {
            throw new IllegalArgumentException("Need some octaves!");
        }
        int $$2 = -p_230544_.firstInt();
        int $$4 = $$2 + ($$3 = p_230544_.lastInt()) + 1;
        if ($$4 < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
        }
        SimplexNoise $$5 = new SimplexNoise(p_230543_);
        int $$6 = $$3;
        this.f_75432_ = new SimplexNoise[$$4];
        if ($$6 >= 0 && $$6 < $$4 && p_230544_.contains(0)) {
            this.f_75432_[$$6] = $$5;
        }
        for (int $$7 = $$6 + 1; $$7 < $$4; ++$$7) {
            if ($$7 >= 0 && p_230544_.contains($$6 - $$7)) {
                this.f_75432_[$$7] = new SimplexNoise(p_230543_);
                continue;
            }
            p_230543_.m_190110_(262);
        }
        if ($$3 > 0) {
            long $$8 = (long)($$5.m_75467_($$5.f_75454_, $$5.f_75455_, $$5.f_75456_) * 9.223372036854776E18);
            WorldgenRandom $$9 = new WorldgenRandom(new LegacyRandomSource($$8));
            for (int $$10 = $$6 - 1; $$10 >= 0; --$$10) {
                if ($$10 < $$4 && p_230544_.contains($$6 - $$10)) {
                    this.f_75432_[$$10] = new SimplexNoise($$9);
                    continue;
                }
                $$9.m_190110_(262);
            }
        }
        this.f_75434_ = Math.pow(2.0, $$3);
        this.f_75433_ = 1.0 / (Math.pow(2.0, $$4) - 1.0);
    }

    public double m_75449_(double p_75450_, double p_75451_, boolean p_75452_) {
        double $$3 = 0.0;
        double $$4 = this.f_75434_;
        double $$5 = this.f_75433_;
        for (SimplexNoise $$6 : this.f_75432_) {
            if ($$6 != null) {
                $$3 += $$6.m_75464_(p_75450_ * $$4 + (p_75452_ ? $$6.f_75454_ : 0.0), p_75451_ * $$4 + (p_75452_ ? $$6.f_75455_ : 0.0)) * $$5;
            }
            $$4 /= 2.0;
            $$5 *= 2.0;
        }
        return $$3;
    }
}

