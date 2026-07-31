/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.Hashing
 */
package net.minecraft.world.level.biome;

import com.google.common.hash.Hashing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.util.LinearCongruentialGenerator;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;

public class BiomeManager {
    public static final int f_151750_ = QuartPos.m_175400_(8);
    private static final int f_186673_ = 2;
    private static final int f_186674_ = 4;
    private static final int f_186675_ = 3;
    private final NoiseBiomeSource f_47862_;
    private final long f_47863_;

    public BiomeManager(NoiseBiomeSource p_186677_, long p_186678_) {
        this.f_47862_ = p_186677_;
        this.f_47863_ = p_186678_;
    }

    public static long m_47877_(long p_47878_) {
        return Hashing.sha256().hashLong(p_47878_).asLong();
    }

    public BiomeManager m_186687_(NoiseBiomeSource p_186688_) {
        return new BiomeManager(p_186688_, this.f_47863_);
    }

    public Holder<Biome> m_204214_(BlockPos p_204215_) {
        int $$1 = p_204215_.m_123341_() - 2;
        int $$2 = p_204215_.m_123342_() - 2;
        int $$3 = p_204215_.m_123343_() - 2;
        int $$4 = $$1 >> 2;
        int $$5 = $$2 >> 2;
        int $$6 = $$3 >> 2;
        double $$7 = (double)($$1 & 3) / 4.0;
        double $$8 = (double)($$2 & 3) / 4.0;
        double $$9 = (double)($$3 & 3) / 4.0;
        int $$10 = 0;
        double $$11 = Double.POSITIVE_INFINITY;
        for (int $$12 = 0; $$12 < 8; ++$$12) {
            double $$21;
            double $$20;
            double $$19;
            boolean $$15;
            int $$18;
            boolean $$14;
            int $$17;
            boolean $$13 = ($$12 & 4) == 0;
            int $$16 = $$13 ? $$4 : $$4 + 1;
            double $$22 = BiomeManager.m_186679_(this.f_47863_, $$16, $$17 = ($$14 = ($$12 & 2) == 0) ? $$5 : $$5 + 1, $$18 = ($$15 = ($$12 & 1) == 0) ? $$6 : $$6 + 1, $$19 = $$13 ? $$7 : $$7 - 1.0, $$20 = $$14 ? $$8 : $$8 - 1.0, $$21 = $$15 ? $$9 : $$9 - 1.0);
            if (!($$11 > $$22)) continue;
            $$10 = $$12;
            $$11 = $$22;
        }
        int $$23 = ($$10 & 4) == 0 ? $$4 : $$4 + 1;
        int $$24 = ($$10 & 2) == 0 ? $$5 : $$5 + 1;
        int $$25 = ($$10 & 1) == 0 ? $$6 : $$6 + 1;
        return this.f_47862_.m_203495_($$23, $$24, $$25);
    }

    public Holder<Biome> m_204206_(double p_204207_, double p_204208_, double p_204209_) {
        int $$3 = QuartPos.m_175400_(Mth.m_14107_(p_204207_));
        int $$4 = QuartPos.m_175400_(Mth.m_14107_(p_204208_));
        int $$5 = QuartPos.m_175400_(Mth.m_14107_(p_204209_));
        return this.m_204210_($$3, $$4, $$5);
    }

    public Holder<Biome> m_204216_(BlockPos p_204217_) {
        int $$1 = QuartPos.m_175400_(p_204217_.m_123341_());
        int $$2 = QuartPos.m_175400_(p_204217_.m_123342_());
        int $$3 = QuartPos.m_175400_(p_204217_.m_123343_());
        return this.m_204210_($$1, $$2, $$3);
    }

    public Holder<Biome> m_204210_(int p_204211_, int p_204212_, int p_204213_) {
        return this.f_47862_.m_203495_(p_204211_, p_204212_, p_204213_);
    }

    private static double m_186679_(long p_186680_, int p_186681_, int p_186682_, int p_186683_, double p_186684_, double p_186685_, double p_186686_) {
        long $$7 = p_186680_;
        $$7 = LinearCongruentialGenerator.m_13972_($$7, p_186681_);
        $$7 = LinearCongruentialGenerator.m_13972_($$7, p_186682_);
        $$7 = LinearCongruentialGenerator.m_13972_($$7, p_186683_);
        $$7 = LinearCongruentialGenerator.m_13972_($$7, p_186681_);
        $$7 = LinearCongruentialGenerator.m_13972_($$7, p_186682_);
        $$7 = LinearCongruentialGenerator.m_13972_($$7, p_186683_);
        double $$8 = BiomeManager.m_186689_($$7);
        $$7 = LinearCongruentialGenerator.m_13972_($$7, p_186680_);
        double $$9 = BiomeManager.m_186689_($$7);
        $$7 = LinearCongruentialGenerator.m_13972_($$7, p_186680_);
        double $$10 = BiomeManager.m_186689_($$7);
        return Mth.m_144952_(p_186686_ + $$10) + Mth.m_144952_(p_186685_ + $$9) + Mth.m_144952_(p_186684_ + $$8);
    }

    private static double m_186689_(long p_186690_) {
        double $$1 = (double)Math.floorMod(p_186690_ >> 24, 1024) / 1024.0;
        return ($$1 - 0.5) * 0.9;
    }

    public static interface NoiseBiomeSource {
        public Holder<Biome> m_203495_(int var1, int var2, int var3);
    }
}

