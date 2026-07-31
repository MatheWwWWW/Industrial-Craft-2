/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class OreFeature
extends Feature<OreConfiguration> {
    public OreFeature(Codec<OreConfiguration> p_66531_) {
        super(p_66531_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<OreConfiguration> p_160177_) {
        RandomSource $$1 = p_160177_.m_225041_();
        BlockPos $$2 = p_160177_.m_159777_();
        WorldGenLevel $$3 = p_160177_.m_159774_();
        OreConfiguration $$4 = p_160177_.m_159778_();
        float $$5 = $$1.m_188501_() * (float)Math.PI;
        float $$6 = (float)$$4.f_67839_ / 8.0f;
        int $$7 = Mth.m_14167_(((float)$$4.f_67839_ / 16.0f * 2.0f + 1.0f) / 2.0f);
        double $$8 = (double)$$2.m_123341_() + Math.sin($$5) * (double)$$6;
        double $$9 = (double)$$2.m_123341_() - Math.sin($$5) * (double)$$6;
        double $$10 = (double)$$2.m_123343_() + Math.cos($$5) * (double)$$6;
        double $$11 = (double)$$2.m_123343_() - Math.cos($$5) * (double)$$6;
        int $$12 = 2;
        double $$13 = $$2.m_123342_() + $$1.m_188503_(3) - 2;
        double $$14 = $$2.m_123342_() + $$1.m_188503_(3) - 2;
        int $$15 = $$2.m_123341_() - Mth.m_14167_($$6) - $$7;
        int $$16 = $$2.m_123342_() - 2 - $$7;
        int $$17 = $$2.m_123343_() - Mth.m_14167_($$6) - $$7;
        int $$18 = 2 * (Mth.m_14167_($$6) + $$7);
        int $$19 = 2 * (2 + $$7);
        for (int $$20 = $$15; $$20 <= $$15 + $$18; ++$$20) {
            for (int $$21 = $$17; $$21 <= $$17 + $$18; ++$$21) {
                if ($$16 > $$3.m_6924_(Heightmap.Types.OCEAN_FLOOR_WG, $$20, $$21)) continue;
                return this.m_225171_($$3, $$1, $$4, $$8, $$9, $$10, $$11, $$13, $$14, $$15, $$16, $$17, $$18, $$19);
            }
        }
        return false;
    }

    protected boolean m_225171_(WorldGenLevel p_225172_, RandomSource p_225173_, OreConfiguration p_225174_, double p_225175_, double p_225176_, double p_225177_, double p_225178_, double p_225179_, double p_225180_, int p_225181_, int p_225182_, int p_225183_, int p_225184_, int p_225185_) {
        int $$14 = 0;
        BitSet $$15 = new BitSet(p_225184_ * p_225185_ * p_225184_);
        BlockPos.MutableBlockPos $$16 = new BlockPos.MutableBlockPos();
        int $$17 = p_225174_.f_67839_;
        double[] $$18 = new double[$$17 * 4];
        for (int $$19 = 0; $$19 < $$17; ++$$19) {
            float $$20 = (float)$$19 / (float)$$17;
            double $$21 = Mth.m_14139_($$20, p_225175_, p_225176_);
            double $$22 = Mth.m_14139_($$20, p_225179_, p_225180_);
            double $$23 = Mth.m_14139_($$20, p_225177_, p_225178_);
            double $$24 = p_225173_.m_188500_() * (double)$$17 / 16.0;
            double $$25 = ((double)(Mth.m_14031_((float)Math.PI * $$20) + 1.0f) * $$24 + 1.0) / 2.0;
            $$18[$$19 * 4 + 0] = $$21;
            $$18[$$19 * 4 + 1] = $$22;
            $$18[$$19 * 4 + 2] = $$23;
            $$18[$$19 * 4 + 3] = $$25;
        }
        for (int $$26 = 0; $$26 < $$17 - 1; ++$$26) {
            if ($$18[$$26 * 4 + 3] <= 0.0) continue;
            for (int $$27 = $$26 + 1; $$27 < $$17; ++$$27) {
                double $$30;
                double $$29;
                double $$28;
                double $$31;
                if ($$18[$$27 * 4 + 3] <= 0.0 || !(($$31 = $$18[$$26 * 4 + 3] - $$18[$$27 * 4 + 3]) * $$31 > ($$28 = $$18[$$26 * 4 + 0] - $$18[$$27 * 4 + 0]) * $$28 + ($$29 = $$18[$$26 * 4 + 1] - $$18[$$27 * 4 + 1]) * $$29 + ($$30 = $$18[$$26 * 4 + 2] - $$18[$$27 * 4 + 2]) * $$30)) continue;
                if ($$31 > 0.0) {
                    $$18[$$27 * 4 + 3] = -1.0;
                    continue;
                }
                $$18[$$26 * 4 + 3] = -1.0;
            }
        }
        try (BulkSectionAccess $$32 = new BulkSectionAccess(p_225172_);){
            for (int $$33 = 0; $$33 < $$17; ++$$33) {
                double $$34 = $$18[$$33 * 4 + 3];
                if ($$34 < 0.0) continue;
                double $$35 = $$18[$$33 * 4 + 0];
                double $$36 = $$18[$$33 * 4 + 1];
                double $$37 = $$18[$$33 * 4 + 2];
                int $$38 = Math.max(Mth.m_14107_($$35 - $$34), p_225181_);
                int $$39 = Math.max(Mth.m_14107_($$36 - $$34), p_225182_);
                int $$40 = Math.max(Mth.m_14107_($$37 - $$34), p_225183_);
                int $$41 = Math.max(Mth.m_14107_($$35 + $$34), $$38);
                int $$42 = Math.max(Mth.m_14107_($$36 + $$34), $$39);
                int $$43 = Math.max(Mth.m_14107_($$37 + $$34), $$40);
                for (int $$44 = $$38; $$44 <= $$41; ++$$44) {
                    double $$45 = ((double)$$44 + 0.5 - $$35) / $$34;
                    if (!($$45 * $$45 < 1.0)) continue;
                    for (int $$46 = $$39; $$46 <= $$42; ++$$46) {
                        double $$47 = ((double)$$46 + 0.5 - $$36) / $$34;
                        if (!($$45 * $$45 + $$47 * $$47 < 1.0)) continue;
                        block11: for (int $$48 = $$40; $$48 <= $$43; ++$$48) {
                            LevelChunkSection $$51;
                            int $$50;
                            double $$49 = ((double)$$48 + 0.5 - $$37) / $$34;
                            if (!($$45 * $$45 + $$47 * $$47 + $$49 * $$49 < 1.0) || p_225172_.m_151562_($$46) || $$15.get($$50 = $$44 - p_225181_ + ($$46 - p_225182_) * p_225184_ + ($$48 - p_225183_) * p_225184_ * p_225185_)) continue;
                            $$15.set($$50);
                            $$16.m_122178_($$44, $$46, $$48);
                            if (!p_225172_.m_180807_($$16) || ($$51 = $$32.m_156104_($$16)) == null) continue;
                            int $$52 = SectionPos.m_123207_($$44);
                            int $$53 = SectionPos.m_123207_($$46);
                            int $$54 = SectionPos.m_123207_($$48);
                            BlockState $$55 = $$51.m_62982_($$52, $$53, $$54);
                            for (OreConfiguration.TargetBlockState $$56 : p_225174_.f_161005_) {
                                if (!OreFeature.m_225186_($$55, $$32::m_156110_, p_225173_, p_225174_, $$56, $$16)) continue;
                                $$51.m_62991_($$52, $$53, $$54, $$56.f_161033_, false);
                                ++$$14;
                                continue block11;
                            }
                        }
                    }
                }
            }
        }
        return $$14 > 0;
    }

    public static boolean m_225186_(BlockState p_225187_, Function<BlockPos, BlockState> p_225188_, RandomSource p_225189_, OreConfiguration p_225190_, OreConfiguration.TargetBlockState p_225191_, BlockPos.MutableBlockPos p_225192_) {
        if (!p_225191_.f_161032_.m_213865_(p_225187_, p_225189_)) {
            return false;
        }
        if (OreFeature.m_225168_(p_225189_, p_225190_.f_161006_)) {
            return true;
        }
        return !OreFeature.m_159750_(p_225188_, p_225192_);
    }

    protected static boolean m_225168_(RandomSource p_225169_, float p_225170_) {
        if (p_225170_ <= 0.0f) {
            return true;
        }
        if (p_225170_ >= 1.0f) {
            return false;
        }
        return p_225169_.m_188501_() >= p_225170_;
    }
}

