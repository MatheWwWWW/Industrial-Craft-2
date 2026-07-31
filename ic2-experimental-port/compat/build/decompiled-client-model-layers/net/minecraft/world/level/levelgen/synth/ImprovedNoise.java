/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 */
package net.minecraft.world.level.levelgen.synth;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.synth.NoiseUtils;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

public final class ImprovedNoise {
    private static final float f_164305_ = 1.0E-7f;
    private final byte[] f_75324_;
    public final double f_75321_;
    public final double f_75322_;
    public final double f_75323_;

    public ImprovedNoise(RandomSource p_230499_) {
        this.f_75321_ = p_230499_.m_188500_() * 256.0;
        this.f_75322_ = p_230499_.m_188500_() * 256.0;
        this.f_75323_ = p_230499_.m_188500_() * 256.0;
        this.f_75324_ = new byte[256];
        for (int $$1 = 0; $$1 < 256; ++$$1) {
            this.f_75324_[$$1] = (byte)$$1;
        }
        for (int $$2 = 0; $$2 < 256; ++$$2) {
            int $$3 = p_230499_.m_188503_(256 - $$2);
            byte $$4 = this.f_75324_[$$2];
            this.f_75324_[$$2] = this.f_75324_[$$2 + $$3];
            this.f_75324_[$$2 + $$3] = $$4;
        }
    }

    public double m_164308_(double p_164309_, double p_164310_, double p_164311_) {
        return this.m_75327_(p_164309_, p_164310_, p_164311_, 0.0, 0.0);
    }

    @Deprecated
    public double m_75327_(double p_75328_, double p_75329_, double p_75330_, double p_75331_, double p_75332_) {
        double $$17;
        double $$5 = p_75328_ + this.f_75321_;
        double $$6 = p_75329_ + this.f_75322_;
        double $$7 = p_75330_ + this.f_75323_;
        int $$8 = Mth.m_14107_($$5);
        int $$9 = Mth.m_14107_($$6);
        int $$10 = Mth.m_14107_($$7);
        double $$11 = $$5 - (double)$$8;
        double $$12 = $$6 - (double)$$9;
        double $$13 = $$7 - (double)$$10;
        if (p_75331_ != 0.0) {
            double $$15;
            if (p_75332_ >= 0.0 && p_75332_ < $$12) {
                double $$14 = p_75332_;
            } else {
                $$15 = $$12;
            }
            double $$16 = (double)Mth.m_14107_($$15 / p_75331_ + (double)1.0E-7f) * p_75331_;
        } else {
            $$17 = 0.0;
        }
        return this.m_164317_($$8, $$9, $$10, $$11, $$12 - $$17, $$13, $$12);
    }

    public double m_164312_(double p_164313_, double p_164314_, double p_164315_, double[] p_164316_) {
        double $$4 = p_164313_ + this.f_75321_;
        double $$5 = p_164314_ + this.f_75322_;
        double $$6 = p_164315_ + this.f_75323_;
        int $$7 = Mth.m_14107_($$4);
        int $$8 = Mth.m_14107_($$5);
        int $$9 = Mth.m_14107_($$6);
        double $$10 = $$4 - (double)$$7;
        double $$11 = $$5 - (double)$$8;
        double $$12 = $$6 - (double)$$9;
        return this.m_164325_($$7, $$8, $$9, $$10, $$11, $$12, p_164316_);
    }

    private static double m_75335_(int p_75336_, double p_75337_, double p_75338_, double p_75339_) {
        return SimplexNoise.m_75479_(SimplexNoise.f_75453_[p_75336_ & 0xF], p_75337_, p_75338_, p_75339_);
    }

    private int m_75333_(int p_75334_) {
        return this.f_75324_[p_75334_ & 0xFF] & 0xFF;
    }

    private double m_164317_(int p_164318_, int p_164319_, int p_164320_, double p_164321_, double p_164322_, double p_164323_, double p_164324_) {
        int $$7 = this.m_75333_(p_164318_);
        int $$8 = this.m_75333_(p_164318_ + 1);
        int $$9 = this.m_75333_($$7 + p_164319_);
        int $$10 = this.m_75333_($$7 + p_164319_ + 1);
        int $$11 = this.m_75333_($$8 + p_164319_);
        int $$12 = this.m_75333_($$8 + p_164319_ + 1);
        double $$13 = ImprovedNoise.m_75335_(this.m_75333_($$9 + p_164320_), p_164321_, p_164322_, p_164323_);
        double $$14 = ImprovedNoise.m_75335_(this.m_75333_($$11 + p_164320_), p_164321_ - 1.0, p_164322_, p_164323_);
        double $$15 = ImprovedNoise.m_75335_(this.m_75333_($$10 + p_164320_), p_164321_, p_164322_ - 1.0, p_164323_);
        double $$16 = ImprovedNoise.m_75335_(this.m_75333_($$12 + p_164320_), p_164321_ - 1.0, p_164322_ - 1.0, p_164323_);
        double $$17 = ImprovedNoise.m_75335_(this.m_75333_($$9 + p_164320_ + 1), p_164321_, p_164322_, p_164323_ - 1.0);
        double $$18 = ImprovedNoise.m_75335_(this.m_75333_($$11 + p_164320_ + 1), p_164321_ - 1.0, p_164322_, p_164323_ - 1.0);
        double $$19 = ImprovedNoise.m_75335_(this.m_75333_($$10 + p_164320_ + 1), p_164321_, p_164322_ - 1.0, p_164323_ - 1.0);
        double $$20 = ImprovedNoise.m_75335_(this.m_75333_($$12 + p_164320_ + 1), p_164321_ - 1.0, p_164322_ - 1.0, p_164323_ - 1.0);
        double $$21 = Mth.m_14197_(p_164321_);
        double $$22 = Mth.m_14197_(p_164324_);
        double $$23 = Mth.m_14197_(p_164323_);
        return Mth.m_14019_($$21, $$22, $$23, $$13, $$14, $$15, $$16, $$17, $$18, $$19, $$20);
    }

    private double m_164325_(int p_164326_, int p_164327_, int p_164328_, double p_164329_, double p_164330_, double p_164331_, double[] p_164332_) {
        int $$7 = this.m_75333_(p_164326_);
        int $$8 = this.m_75333_(p_164326_ + 1);
        int $$9 = this.m_75333_($$7 + p_164327_);
        int $$10 = this.m_75333_($$7 + p_164327_ + 1);
        int $$11 = this.m_75333_($$8 + p_164327_);
        int $$12 = this.m_75333_($$8 + p_164327_ + 1);
        int $$13 = this.m_75333_($$9 + p_164328_);
        int $$14 = this.m_75333_($$11 + p_164328_);
        int $$15 = this.m_75333_($$10 + p_164328_);
        int $$16 = this.m_75333_($$12 + p_164328_);
        int $$17 = this.m_75333_($$9 + p_164328_ + 1);
        int $$18 = this.m_75333_($$11 + p_164328_ + 1);
        int $$19 = this.m_75333_($$10 + p_164328_ + 1);
        int $$20 = this.m_75333_($$12 + p_164328_ + 1);
        int[] $$21 = SimplexNoise.f_75453_[$$13 & 0xF];
        int[] $$22 = SimplexNoise.f_75453_[$$14 & 0xF];
        int[] $$23 = SimplexNoise.f_75453_[$$15 & 0xF];
        int[] $$24 = SimplexNoise.f_75453_[$$16 & 0xF];
        int[] $$25 = SimplexNoise.f_75453_[$$17 & 0xF];
        int[] $$26 = SimplexNoise.f_75453_[$$18 & 0xF];
        int[] $$27 = SimplexNoise.f_75453_[$$19 & 0xF];
        int[] $$28 = SimplexNoise.f_75453_[$$20 & 0xF];
        double $$29 = SimplexNoise.m_75479_($$21, p_164329_, p_164330_, p_164331_);
        double $$30 = SimplexNoise.m_75479_($$22, p_164329_ - 1.0, p_164330_, p_164331_);
        double $$31 = SimplexNoise.m_75479_($$23, p_164329_, p_164330_ - 1.0, p_164331_);
        double $$32 = SimplexNoise.m_75479_($$24, p_164329_ - 1.0, p_164330_ - 1.0, p_164331_);
        double $$33 = SimplexNoise.m_75479_($$25, p_164329_, p_164330_, p_164331_ - 1.0);
        double $$34 = SimplexNoise.m_75479_($$26, p_164329_ - 1.0, p_164330_, p_164331_ - 1.0);
        double $$35 = SimplexNoise.m_75479_($$27, p_164329_, p_164330_ - 1.0, p_164331_ - 1.0);
        double $$36 = SimplexNoise.m_75479_($$28, p_164329_ - 1.0, p_164330_ - 1.0, p_164331_ - 1.0);
        double $$37 = Mth.m_14197_(p_164329_);
        double $$38 = Mth.m_14197_(p_164330_);
        double $$39 = Mth.m_14197_(p_164331_);
        double $$40 = Mth.m_14019_($$37, $$38, $$39, $$21[0], $$22[0], $$23[0], $$24[0], $$25[0], $$26[0], $$27[0], $$28[0]);
        double $$41 = Mth.m_14019_($$37, $$38, $$39, $$21[1], $$22[1], $$23[1], $$24[1], $$25[1], $$26[1], $$27[1], $$28[1]);
        double $$42 = Mth.m_14019_($$37, $$38, $$39, $$21[2], $$22[2], $$23[2], $$24[2], $$25[2], $$26[2], $$27[2], $$28[2]);
        double $$43 = Mth.m_14012_($$38, $$39, $$30 - $$29, $$32 - $$31, $$34 - $$33, $$36 - $$35);
        double $$44 = Mth.m_14012_($$39, $$37, $$31 - $$29, $$35 - $$33, $$32 - $$30, $$36 - $$34);
        double $$45 = Mth.m_14012_($$37, $$38, $$33 - $$29, $$34 - $$30, $$35 - $$31, $$36 - $$32);
        double $$46 = Mth.m_144946_(p_164329_);
        double $$47 = Mth.m_144946_(p_164330_);
        double $$48 = Mth.m_144946_(p_164331_);
        double $$49 = $$40 + $$46 * $$43;
        double $$50 = $$41 + $$47 * $$44;
        double $$51 = $$42 + $$48 * $$45;
        p_164332_[0] = p_164332_[0] + $$49;
        p_164332_[1] = p_164332_[1] + $$50;
        p_164332_[2] = p_164332_[2] + $$51;
        return Mth.m_14019_($$37, $$38, $$39, $$29, $$30, $$31, $$32, $$33, $$34, $$35, $$36);
    }

    @VisibleForTesting
    public void m_192823_(StringBuilder p_192824_) {
        NoiseUtils.m_192825_(p_192824_, this.f_75321_, this.f_75322_, this.f_75323_, this.f_75324_);
    }
}

