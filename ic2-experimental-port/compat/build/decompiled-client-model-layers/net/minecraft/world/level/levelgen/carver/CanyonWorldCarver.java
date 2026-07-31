/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

public class CanyonWorldCarver
extends WorldCarver<CanyonCarverConfiguration> {
    public CanyonWorldCarver(Codec<CanyonCarverConfiguration> p_64711_) {
        super(p_64711_);
    }

    @Override
    public boolean m_214133_(CanyonCarverConfiguration p_224797_, RandomSource p_224798_) {
        return p_224798_.m_188501_() <= p_224797_.f_67859_;
    }

    @Override
    public boolean m_213788_(CarvingContext p_224813_, CanyonCarverConfiguration p_224814_, ChunkAccess p_224815_, Function<BlockPos, Holder<Biome>> p_224816_, RandomSource p_224817_, Aquifer p_224818_, ChunkPos p_224819_, CarvingMask p_224820_) {
        int $$8 = (this.m_65073_() * 2 - 1) * 16;
        double $$9 = p_224819_.m_151382_(p_224817_.m_188503_(16));
        int $$10 = p_224814_.f_159088_.m_213859_(p_224817_, p_224813_);
        double $$11 = p_224819_.m_151391_(p_224817_.m_188503_(16));
        float $$12 = p_224817_.m_188501_() * ((float)Math.PI * 2);
        float $$13 = p_224814_.f_158967_.m_214084_(p_224817_);
        double $$14 = p_224814_.f_159089_.m_214084_(p_224817_);
        float $$15 = p_224814_.f_158968_.f_158993_.m_214084_(p_224817_);
        int $$16 = (int)((float)$$8 * p_224814_.f_158968_.f_158992_.m_214084_(p_224817_));
        boolean $$17 = false;
        this.m_190593_(p_224813_, p_224814_, p_224815_, p_224816_, p_224817_.m_188505_(), p_224818_, $$9, $$10, $$11, $$15, $$12, $$13, 0, $$16, $$14, p_224820_);
        return true;
    }

    private void m_190593_(CarvingContext p_190594_, CanyonCarverConfiguration p_190595_, ChunkAccess p_190596_, Function<BlockPos, Holder<Biome>> p_190597_, long p_190598_, Aquifer p_190599_, double p_190600_, double p_190601_, double p_190602_, float p_190603_, float p_190604_, float p_190605_, int p_190606_, int p_190607_, double p_190608_, CarvingMask p_190609_) {
        RandomSource $$16 = RandomSource.m_216335_(p_190598_);
        float[] $$17 = this.m_224808_(p_190594_, p_190595_, $$16);
        float $$18 = 0.0f;
        float $$19 = 0.0f;
        for (int $$20 = p_190606_; $$20 < p_190607_; ++$$20) {
            double $$21 = 1.5 + (double)(Mth.m_14031_((float)$$20 * (float)Math.PI / (float)p_190607_) * p_190603_);
            double $$22 = $$21 * p_190608_;
            $$21 *= (double)p_190595_.f_158968_.f_158995_.m_214084_($$16);
            $$22 = this.m_224799_(p_190595_, $$16, $$22, p_190607_, $$20);
            float $$23 = Mth.m_14089_(p_190605_);
            float $$24 = Mth.m_14031_(p_190605_);
            p_190600_ += (double)(Mth.m_14089_(p_190604_) * $$23);
            p_190601_ += (double)$$24;
            p_190602_ += (double)(Mth.m_14031_(p_190604_) * $$23);
            p_190605_ *= 0.7f;
            p_190605_ += $$19 * 0.05f;
            p_190604_ += $$18 * 0.05f;
            $$19 *= 0.8f;
            $$18 *= 0.5f;
            $$19 += ($$16.m_188501_() - $$16.m_188501_()) * $$16.m_188501_() * 2.0f;
            $$18 += ($$16.m_188501_() - $$16.m_188501_()) * $$16.m_188501_() * 4.0f;
            if ($$16.m_188503_(4) == 0) continue;
            if (!CanyonWorldCarver.m_159367_(p_190596_.m_7697_(), p_190600_, p_190602_, $$20, p_190607_, p_190603_)) {
                return;
            }
            this.m_190753_(p_190594_, p_190595_, p_190596_, p_190597_, p_190599_, p_190600_, p_190601_, p_190602_, $$21, $$22, p_190609_, (p_159082_, p_159083_, p_159084_, p_159085_, p_159086_) -> this.m_159073_(p_159082_, $$17, p_159083_, p_159084_, p_159085_, p_159086_));
        }
    }

    private float[] m_224808_(CarvingContext p_224809_, CanyonCarverConfiguration p_224810_, RandomSource p_224811_) {
        int $$3 = p_224809_.m_142208_();
        float[] $$4 = new float[$$3];
        float $$5 = 1.0f;
        for (int $$6 = 0; $$6 < $$3; ++$$6) {
            if ($$6 == 0 || p_224811_.m_188503_(p_224810_.f_158968_.f_158994_) == 0) {
                $$5 = 1.0f + p_224811_.m_188501_() * p_224811_.m_188501_();
            }
            $$4[$$6] = $$5 * $$5;
        }
        return $$4;
    }

    private double m_224799_(CanyonCarverConfiguration p_224800_, RandomSource p_224801_, double p_224802_, float p_224803_, float p_224804_) {
        float $$5 = 1.0f - Mth.m_14154_(0.5f - p_224804_ / p_224803_) * 2.0f;
        float $$6 = p_224800_.f_158968_.f_158996_ + p_224800_.f_158968_.f_158997_ * $$5;
        return (double)$$6 * p_224802_ * (double)Mth.m_216283_(p_224801_, 0.75f, 1.0f);
    }

    private boolean m_159073_(CarvingContext p_159074_, float[] p_159075_, double p_159076_, double p_159077_, double p_159078_, int p_159079_) {
        int $$6 = p_159079_ - p_159074_.m_142201_();
        return (p_159076_ * p_159076_ + p_159078_ * p_159078_) * (double)p_159075_[$$6 - 1] + p_159077_ * p_159077_ / 6.0 >= 1.0;
    }
}

