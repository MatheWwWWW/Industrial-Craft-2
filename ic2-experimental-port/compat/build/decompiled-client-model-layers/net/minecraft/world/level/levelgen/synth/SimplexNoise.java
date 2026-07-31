/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.synth;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class SimplexNoise {
    protected static final int[][] f_75453_ = new int[][]{{1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}, {1, 1, 0}, {0, -1, 1}, {-1, 1, 0}, {0, -1, -1}};
    private static final double f_75457_ = Math.sqrt(3.0);
    private static final double f_75458_ = 0.5 * (f_75457_ - 1.0);
    private static final double f_75459_ = (3.0 - f_75457_) / 6.0;
    private final int[] f_75460_ = new int[512];
    public final double f_75454_;
    public final double f_75455_;
    public final double f_75456_;

    public SimplexNoise(RandomSource p_230549_) {
        this.f_75454_ = p_230549_.m_188500_() * 256.0;
        this.f_75455_ = p_230549_.m_188500_() * 256.0;
        this.f_75456_ = p_230549_.m_188500_() * 256.0;
        for (int $$1 = 0; $$1 < 256; ++$$1) {
            this.f_75460_[$$1] = $$1;
        }
        for (int $$2 = 0; $$2 < 256; ++$$2) {
            int $$3 = p_230549_.m_188503_(256 - $$2);
            int $$4 = this.f_75460_[$$2];
            this.f_75460_[$$2] = this.f_75460_[$$3 + $$2];
            this.f_75460_[$$3 + $$2] = $$4;
        }
    }

    private int m_75471_(int p_75472_) {
        return this.f_75460_[p_75472_ & 0xFF];
    }

    protected static double m_75479_(int[] p_75480_, double p_75481_, double p_75482_, double p_75483_) {
        return (double)p_75480_[0] * p_75481_ + (double)p_75480_[1] * p_75482_ + (double)p_75480_[2] * p_75483_;
    }

    private double m_75473_(int p_75474_, double p_75475_, double p_75476_, double p_75477_, double p_75478_) {
        double $$7;
        double $$5 = p_75478_ - p_75475_ * p_75475_ - p_75476_ * p_75476_ - p_75477_ * p_75477_;
        if ($$5 < 0.0) {
            double $$6 = 0.0;
        } else {
            $$5 *= $$5;
            $$7 = $$5 * $$5 * SimplexNoise.m_75479_(f_75453_[p_75474_], p_75475_, p_75476_, p_75477_);
        }
        return $$7;
    }

    public double m_75464_(double p_75465_, double p_75466_) {
        int $$13;
        int $$12;
        double $$7;
        double $$9;
        int $$4;
        double $$5;
        double $$2 = (p_75465_ + p_75466_) * f_75458_;
        int $$3 = Mth.m_14107_(p_75465_ + $$2);
        double $$6 = (double)$$3 - ($$5 = (double)($$3 + ($$4 = Mth.m_14107_(p_75466_ + $$2))) * f_75459_);
        double $$8 = p_75465_ - $$6;
        if ($$8 > ($$9 = p_75466_ - ($$7 = (double)$$4 - $$5))) {
            boolean $$10 = true;
            boolean $$11 = false;
        } else {
            $$12 = 0;
            $$13 = 1;
        }
        double $$14 = $$8 - (double)$$12 + f_75459_;
        double $$15 = $$9 - (double)$$13 + f_75459_;
        double $$16 = $$8 - 1.0 + 2.0 * f_75459_;
        double $$17 = $$9 - 1.0 + 2.0 * f_75459_;
        int $$18 = $$3 & 0xFF;
        int $$19 = $$4 & 0xFF;
        int $$20 = this.m_75471_($$18 + this.m_75471_($$19)) % 12;
        int $$21 = this.m_75471_($$18 + $$12 + this.m_75471_($$19 + $$13)) % 12;
        int $$22 = this.m_75471_($$18 + 1 + this.m_75471_($$19 + 1)) % 12;
        double $$23 = this.m_75473_($$20, $$8, $$9, 0.0, 0.5);
        double $$24 = this.m_75473_($$21, $$14, $$15, 0.0, 0.5);
        double $$25 = this.m_75473_($$22, $$16, $$17, 0.0, 0.5);
        return 70.0 * ($$23 + $$24 + $$25);
    }

    public double m_75467_(double p_75468_, double p_75469_, double p_75470_) {
        int $$51;
        int $$50;
        int $$49;
        int $$48;
        int $$47;
        int $$46;
        double $$3 = 0.3333333333333333;
        double $$4 = (p_75468_ + p_75469_ + p_75470_) * 0.3333333333333333;
        int $$5 = Mth.m_14107_(p_75468_ + $$4);
        int $$6 = Mth.m_14107_(p_75469_ + $$4);
        int $$7 = Mth.m_14107_(p_75470_ + $$4);
        double $$8 = 0.16666666666666666;
        double $$9 = (double)($$5 + $$6 + $$7) * 0.16666666666666666;
        double $$10 = (double)$$5 - $$9;
        double $$11 = (double)$$6 - $$9;
        double $$12 = (double)$$7 - $$9;
        double $$13 = p_75468_ - $$10;
        double $$14 = p_75469_ - $$11;
        double $$15 = p_75470_ - $$12;
        if ($$13 >= $$14) {
            if ($$14 >= $$15) {
                boolean $$16 = true;
                boolean $$17 = false;
                boolean $$18 = false;
                boolean $$19 = true;
                boolean $$20 = true;
                boolean $$21 = false;
            } else if ($$13 >= $$15) {
                boolean $$22 = true;
                boolean $$23 = false;
                boolean $$24 = false;
                boolean $$25 = true;
                boolean $$26 = false;
                boolean $$27 = true;
            } else {
                boolean $$28 = false;
                boolean $$29 = false;
                boolean $$30 = true;
                boolean $$31 = true;
                boolean $$32 = false;
                boolean $$33 = true;
            }
        } else if ($$14 < $$15) {
            boolean $$34 = false;
            boolean $$35 = false;
            boolean $$36 = true;
            boolean $$37 = false;
            boolean $$38 = true;
            boolean $$39 = true;
        } else if ($$13 < $$15) {
            boolean $$40 = false;
            boolean $$41 = true;
            boolean $$42 = false;
            boolean $$43 = false;
            boolean $$44 = true;
            boolean $$45 = true;
        } else {
            $$46 = 0;
            $$47 = 1;
            $$48 = 0;
            $$49 = 1;
            $$50 = 1;
            $$51 = 0;
        }
        double $$52 = $$13 - (double)$$46 + 0.16666666666666666;
        double $$53 = $$14 - (double)$$47 + 0.16666666666666666;
        double $$54 = $$15 - (double)$$48 + 0.16666666666666666;
        double $$55 = $$13 - (double)$$49 + 0.3333333333333333;
        double $$56 = $$14 - (double)$$50 + 0.3333333333333333;
        double $$57 = $$15 - (double)$$51 + 0.3333333333333333;
        double $$58 = $$13 - 1.0 + 0.5;
        double $$59 = $$14 - 1.0 + 0.5;
        double $$60 = $$15 - 1.0 + 0.5;
        int $$61 = $$5 & 0xFF;
        int $$62 = $$6 & 0xFF;
        int $$63 = $$7 & 0xFF;
        int $$64 = this.m_75471_($$61 + this.m_75471_($$62 + this.m_75471_($$63))) % 12;
        int $$65 = this.m_75471_($$61 + $$46 + this.m_75471_($$62 + $$47 + this.m_75471_($$63 + $$48))) % 12;
        int $$66 = this.m_75471_($$61 + $$49 + this.m_75471_($$62 + $$50 + this.m_75471_($$63 + $$51))) % 12;
        int $$67 = this.m_75471_($$61 + 1 + this.m_75471_($$62 + 1 + this.m_75471_($$63 + 1))) % 12;
        double $$68 = this.m_75473_($$64, $$13, $$14, $$15, 0.6);
        double $$69 = this.m_75473_($$65, $$52, $$53, $$54, 0.6);
        double $$70 = this.m_75473_($$66, $$55, $$56, $$57, 0.6);
        double $$71 = this.m_75473_($$67, $$58, $$59, $$60, 0.6);
        return 32.0 * ($$68 + $$69 + $$70 + $$71);
    }
}

