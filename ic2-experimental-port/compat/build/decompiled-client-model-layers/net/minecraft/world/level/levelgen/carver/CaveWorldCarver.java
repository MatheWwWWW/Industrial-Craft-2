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
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

public class CaveWorldCarver
extends WorldCarver<CaveCarverConfiguration> {
    public CaveWorldCarver(Codec<CaveCarverConfiguration> p_159194_) {
        super(p_159194_);
    }

    @Override
    public boolean m_214133_(CaveCarverConfiguration p_224894_, RandomSource p_224895_) {
        return p_224895_.m_188501_() <= p_224894_.f_67859_;
    }

    @Override
    public boolean m_213788_(CarvingContext p_224885_, CaveCarverConfiguration p_224886_, ChunkAccess p_224887_, Function<BlockPos, Holder<Biome>> p_224888_, RandomSource p_224889_, Aquifer p_224890_, ChunkPos p_224891_, CarvingMask p_224892_) {
        int $$8 = SectionPos.m_123223_(this.m_65073_() * 2 - 1);
        int $$9 = p_224889_.m_188503_(p_224889_.m_188503_(p_224889_.m_188503_(this.m_6208_()) + 1) + 1);
        for (int $$10 = 0; $$10 < $$9; ++$$10) {
            double $$11 = p_224891_.m_151382_(p_224889_.m_188503_(16));
            double $$12 = p_224886_.f_159088_.m_213859_(p_224889_, p_224885_);
            double $$13 = p_224891_.m_151391_(p_224889_.m_188503_(16));
            double $$14 = p_224886_.f_159155_.m_214084_(p_224889_);
            double $$15 = p_224886_.f_159156_.m_214084_(p_224889_);
            double $$16 = p_224886_.f_159157_.m_214084_(p_224889_);
            WorldCarver.CarveSkipChecker $$17 = (p_159202_, p_159203_, p_159204_, p_159205_, p_159206_) -> CaveWorldCarver.m_159195_(p_159203_, p_159204_, p_159205_, $$16);
            int $$18 = 1;
            if (p_224889_.m_188503_(4) == 0) {
                double $$19 = p_224886_.f_159089_.m_214084_(p_224889_);
                float $$20 = 1.0f + p_224889_.m_188501_() * 6.0f;
                this.m_190690_(p_224885_, p_224886_, p_224887_, p_224888_, p_224890_, $$11, $$12, $$13, $$20, $$19, p_224892_, $$17);
                $$18 += p_224889_.m_188503_(4);
            }
            for (int $$21 = 0; $$21 < $$18; ++$$21) {
                float $$22 = p_224889_.m_188501_() * ((float)Math.PI * 2);
                float $$23 = (p_224889_.m_188501_() - 0.5f) / 4.0f;
                float $$24 = this.m_213592_(p_224889_);
                int $$25 = $$8 - p_224889_.m_188503_($$8 / 4);
                boolean $$26 = false;
                this.m_190670_(p_224885_, p_224886_, p_224887_, p_224888_, p_224889_.m_188505_(), p_224890_, $$11, $$12, $$13, $$14, $$15, $$24, $$22, $$23, 0, $$25, this.m_6203_(), p_224892_, $$17);
            }
        }
        return true;
    }

    protected int m_6208_() {
        return 15;
    }

    protected float m_213592_(RandomSource p_224871_) {
        float $$1 = p_224871_.m_188501_() * 2.0f + p_224871_.m_188501_();
        if (p_224871_.m_188503_(10) == 0) {
            $$1 *= p_224871_.m_188501_() * p_224871_.m_188501_() * 3.0f + 1.0f;
        }
        return $$1;
    }

    protected double m_6203_() {
        return 1.0;
    }

    protected void m_190690_(CarvingContext p_190691_, CaveCarverConfiguration p_190692_, ChunkAccess p_190693_, Function<BlockPos, Holder<Biome>> p_190694_, Aquifer p_190695_, double p_190696_, double p_190697_, double p_190698_, float p_190699_, double p_190700_, CarvingMask p_190701_, WorldCarver.CarveSkipChecker p_190702_) {
        double $$12 = 1.5 + (double)(Mth.m_14031_(1.5707964f) * p_190699_);
        double $$13 = $$12 * p_190700_;
        this.m_190753_(p_190691_, p_190692_, p_190693_, p_190694_, p_190695_, p_190696_ + 1.0, p_190697_, p_190698_, $$12, $$13, p_190701_, p_190702_);
    }

    protected void m_190670_(CarvingContext p_190671_, CaveCarverConfiguration p_190672_, ChunkAccess p_190673_, Function<BlockPos, Holder<Biome>> p_190674_, long p_190675_, Aquifer p_190676_, double p_190677_, double p_190678_, double p_190679_, double p_190680_, double p_190681_, float p_190682_, float p_190683_, float p_190684_, int p_190685_, int p_190686_, double p_190687_, CarvingMask p_190688_, WorldCarver.CarveSkipChecker p_190689_) {
        RandomSource $$19 = RandomSource.m_216335_(p_190675_);
        int $$20 = $$19.m_188503_(p_190686_ / 2) + p_190686_ / 4;
        boolean $$21 = $$19.m_188503_(6) == 0;
        float $$22 = 0.0f;
        float $$23 = 0.0f;
        for (int $$24 = p_190685_; $$24 < p_190686_; ++$$24) {
            double $$25 = 1.5 + (double)(Mth.m_14031_((float)Math.PI * (float)$$24 / (float)p_190686_) * p_190682_);
            double $$26 = $$25 * p_190687_;
            float $$27 = Mth.m_14089_(p_190684_);
            p_190677_ += (double)(Mth.m_14089_(p_190683_) * $$27);
            p_190678_ += (double)Mth.m_14031_(p_190684_);
            p_190679_ += (double)(Mth.m_14031_(p_190683_) * $$27);
            p_190684_ *= $$21 ? 0.92f : 0.7f;
            p_190684_ += $$23 * 0.1f;
            p_190683_ += $$22 * 0.1f;
            $$23 *= 0.9f;
            $$22 *= 0.75f;
            $$23 += ($$19.m_188501_() - $$19.m_188501_()) * $$19.m_188501_() * 2.0f;
            $$22 += ($$19.m_188501_() - $$19.m_188501_()) * $$19.m_188501_() * 4.0f;
            if ($$24 == $$20 && p_190682_ > 1.0f) {
                this.m_190670_(p_190671_, p_190672_, p_190673_, p_190674_, $$19.m_188505_(), p_190676_, p_190677_, p_190678_, p_190679_, p_190680_, p_190681_, $$19.m_188501_() * 0.5f + 0.5f, p_190683_ - 1.5707964f, p_190684_ / 3.0f, $$24, p_190686_, 1.0, p_190688_, p_190689_);
                this.m_190670_(p_190671_, p_190672_, p_190673_, p_190674_, $$19.m_188505_(), p_190676_, p_190677_, p_190678_, p_190679_, p_190680_, p_190681_, $$19.m_188501_() * 0.5f + 0.5f, p_190683_ + 1.5707964f, p_190684_ / 3.0f, $$24, p_190686_, 1.0, p_190688_, p_190689_);
                return;
            }
            if ($$19.m_188503_(4) == 0) continue;
            if (!CaveWorldCarver.m_159367_(p_190673_.m_7697_(), p_190677_, p_190679_, $$24, p_190686_, p_190682_)) {
                return;
            }
            this.m_190753_(p_190671_, p_190672_, p_190673_, p_190674_, p_190676_, p_190677_, p_190678_, p_190679_, $$25 * p_190680_, $$26 * p_190681_, p_190688_, p_190689_);
        }
    }

    private static boolean m_159195_(double p_159196_, double p_159197_, double p_159198_, double p_159199_) {
        if (p_159197_ <= p_159199_) {
            return true;
        }
        return p_159196_ * p_159196_ + p_159197_ * p_159197_ + p_159198_ * p_159198_ >= 1.0;
    }
}

