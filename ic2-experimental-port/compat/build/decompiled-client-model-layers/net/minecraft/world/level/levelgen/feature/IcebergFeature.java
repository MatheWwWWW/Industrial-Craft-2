/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.material.Material;

public class IcebergFeature
extends Feature<BlockStateConfiguration> {
    public IcebergFeature(Codec<BlockStateConfiguration> p_66017_) {
        super(p_66017_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<BlockStateConfiguration> p_159884_) {
        boolean $$24;
        int $$11;
        BlockPos $$1 = p_159884_.m_159777_();
        WorldGenLevel $$2 = p_159884_.m_159774_();
        $$1 = new BlockPos($$1.m_123341_(), p_159884_.m_159775_().m_6337_(), $$1.m_123343_());
        RandomSource $$3 = p_159884_.m_225041_();
        boolean $$4 = $$3.m_188500_() > 0.7;
        BlockState $$5 = p_159884_.m_159778_().f_67547_;
        double $$6 = $$3.m_188500_() * 2.0 * Math.PI;
        int $$7 = 11 - $$3.m_188503_(5);
        int $$8 = 3 + $$3.m_188503_(3);
        boolean $$9 = $$3.m_188500_() > 0.7;
        int $$10 = 11;
        int n = $$11 = $$9 ? $$3.m_188503_(6) + 6 : $$3.m_188503_(15) + 3;
        if (!$$9 && $$3.m_188500_() > 0.9) {
            $$11 += $$3.m_188503_(19) + 7;
        }
        int $$12 = Math.min($$11 + $$3.m_188503_(11), 18);
        int $$13 = Math.min($$11 + $$3.m_188503_(7) - $$3.m_188503_(5), 11);
        int $$14 = $$9 ? $$7 : 11;
        for (int $$15 = -$$14; $$15 < $$14; ++$$15) {
            for (int $$16 = -$$14; $$16 < $$14; ++$$16) {
                for (int $$17 = 0; $$17 < $$11; ++$$17) {
                    int $$18;
                    int n2 = $$18 = $$9 ? this.m_66109_($$17, $$11, $$13) : this.m_225094_($$3, $$17, $$11, $$13);
                    if (!$$9 && $$15 >= $$18) continue;
                    this.m_225109_($$2, $$3, $$1, $$11, $$15, $$17, $$16, $$18, $$14, $$9, $$8, $$6, $$4, $$5);
                }
            }
        }
        this.m_66051_($$2, $$1, $$13, $$11, $$9, $$7);
        for (int $$19 = -$$14; $$19 < $$14; ++$$19) {
            for (int $$20 = -$$14; $$20 < $$14; ++$$20) {
                for (int $$21 = -1; $$21 > -$$12; --$$21) {
                    int $$22 = $$9 ? Mth.m_14167_((float)$$14 * (1.0f - (float)Math.pow($$21, 2.0) / ((float)$$12 * 8.0f))) : $$14;
                    int $$23 = this.m_225133_($$3, -$$21, $$12, $$13);
                    if ($$19 >= $$23) continue;
                    this.m_225109_($$2, $$3, $$1, $$12, $$19, $$21, $$20, $$23, $$22, $$9, $$8, $$6, $$4, $$5);
                }
            }
        }
        boolean bl = $$9 ? $$3.m_188500_() > 0.1 : ($$24 = $$3.m_188500_() > 0.7);
        if ($$24) {
            this.m_225099_($$3, $$2, $$13, $$11, $$1, $$9, $$7, $$6, $$8);
        }
        return true;
    }

    private void m_225099_(RandomSource p_225100_, LevelAccessor p_225101_, int p_225102_, int p_225103_, BlockPos p_225104_, boolean p_225105_, int p_225106_, double p_225107_, int p_225108_) {
        int $$9 = p_225100_.m_188499_() ? -1 : 1;
        int $$10 = p_225100_.m_188499_() ? -1 : 1;
        int $$11 = p_225100_.m_188503_(Math.max(p_225102_ / 2 - 2, 1));
        if (p_225100_.m_188499_()) {
            $$11 = p_225102_ / 2 + 1 - p_225100_.m_188503_(Math.max(p_225102_ - p_225102_ / 2 - 1, 1));
        }
        int $$12 = p_225100_.m_188503_(Math.max(p_225102_ / 2 - 2, 1));
        if (p_225100_.m_188499_()) {
            $$12 = p_225102_ / 2 + 1 - p_225100_.m_188503_(Math.max(p_225102_ - p_225102_ / 2 - 1, 1));
        }
        if (p_225105_) {
            $$11 = $$12 = p_225100_.m_188503_(Math.max(p_225106_ - 5, 1));
        }
        BlockPos $$13 = new BlockPos($$9 * $$11, 0, $$10 * $$12);
        double $$14 = p_225105_ ? p_225107_ + 1.5707963267948966 : p_225100_.m_188500_() * 2.0 * Math.PI;
        for (int $$15 = 0; $$15 < p_225103_ - 3; ++$$15) {
            int $$16 = this.m_225094_(p_225100_, $$15, p_225103_, p_225102_);
            this.m_66035_($$16, $$15, p_225104_, p_225101_, false, $$14, $$13, p_225106_, p_225108_);
        }
        for (int $$17 = -1; $$17 > -p_225103_ + p_225100_.m_188503_(5); --$$17) {
            int $$18 = this.m_225133_(p_225100_, -$$17, p_225103_, p_225102_);
            this.m_66035_($$18, $$17, p_225104_, p_225101_, true, $$14, $$13, p_225106_, p_225108_);
        }
    }

    private void m_66035_(int p_66036_, int p_66037_, BlockPos p_66038_, LevelAccessor p_66039_, boolean p_66040_, double p_66041_, BlockPos p_66042_, int p_66043_, int p_66044_) {
        int $$9 = p_66036_ + 1 + p_66043_ / 3;
        int $$10 = Math.min(p_66036_ - 3, 3) + p_66044_ / 2 - 1;
        for (int $$11 = -$$9; $$11 < $$9; ++$$11) {
            for (int $$12 = -$$9; $$12 < $$9; ++$$12) {
                BlockPos $$14;
                BlockState $$15;
                double $$13 = this.m_66022_($$11, $$12, p_66042_, $$9, $$10, p_66041_);
                if (!($$13 < 0.0) || !IcebergFeature.m_159885_($$15 = p_66039_.m_8055_($$14 = p_66038_.m_7918_($$11, p_66037_, $$12))) && !$$15.m_60713_(Blocks.f_50127_)) continue;
                if (p_66040_) {
                    this.m_5974_(p_66039_, $$14, Blocks.f_49990_.m_49966_());
                    continue;
                }
                this.m_5974_(p_66039_, $$14, Blocks.f_50016_.m_49966_());
                this.m_66048_(p_66039_, $$14);
            }
        }
    }

    private void m_66048_(LevelAccessor p_66049_, BlockPos p_66050_) {
        if (p_66049_.m_8055_(p_66050_.m_7494_()).m_60713_(Blocks.f_50125_)) {
            this.m_5974_(p_66049_, p_66050_.m_7494_(), Blocks.f_50016_.m_49966_());
        }
    }

    private void m_225109_(LevelAccessor p_225110_, RandomSource p_225111_, BlockPos p_225112_, int p_225113_, int p_225114_, int p_225115_, int p_225116_, int p_225117_, int p_225118_, boolean p_225119_, int p_225120_, double p_225121_, boolean p_225122_, BlockState p_225123_) {
        double $$14;
        double d = $$14 = p_225119_ ? this.m_66022_(p_225114_, p_225116_, BlockPos.f_121853_, p_225118_, this.m_66018_(p_225115_, p_225113_, p_225120_), p_225121_) : this.m_225088_(p_225114_, p_225116_, BlockPos.f_121853_, p_225117_, p_225111_);
        if ($$14 < 0.0) {
            double $$16;
            BlockPos $$15 = p_225112_.m_7918_(p_225114_, p_225115_, p_225116_);
            double d2 = $$16 = p_225119_ ? -0.5 : (double)(-6 - p_225111_.m_188503_(3));
            if ($$14 > $$16 && p_225111_.m_188500_() > 0.9) {
                return;
            }
            this.m_225124_($$15, p_225110_, p_225111_, p_225113_ - p_225115_, p_225113_, p_225119_, p_225122_, p_225123_);
        }
    }

    private void m_225124_(BlockPos p_225125_, LevelAccessor p_225126_, RandomSource p_225127_, int p_225128_, int p_225129_, boolean p_225130_, boolean p_225131_, BlockState p_225132_) {
        BlockState $$8 = p_225126_.m_8055_(p_225125_);
        if ($$8.m_60767_() == Material.f_76296_ || $$8.m_60713_(Blocks.f_50127_) || $$8.m_60713_(Blocks.f_50126_) || $$8.m_60713_(Blocks.f_49990_)) {
            int $$10;
            boolean $$9 = !p_225130_ || p_225127_.m_188500_() > 0.05;
            int n = $$10 = p_225130_ ? 3 : 2;
            if (p_225131_ && !$$8.m_60713_(Blocks.f_49990_) && (double)p_225128_ <= (double)p_225127_.m_188503_(Math.max(1, p_225129_ / $$10)) + (double)p_225129_ * 0.6 && $$9) {
                this.m_5974_(p_225126_, p_225125_, Blocks.f_50127_.m_49966_());
            } else {
                this.m_5974_(p_225126_, p_225125_, p_225132_);
            }
        }
    }

    private int m_66018_(int p_66019_, int p_66020_, int p_66021_) {
        int $$3 = p_66021_;
        if (p_66019_ > 0 && p_66020_ - p_66019_ <= 3) {
            $$3 -= 4 - (p_66020_ - p_66019_);
        }
        return $$3;
    }

    private double m_225088_(int p_225089_, int p_225090_, BlockPos p_225091_, int p_225092_, RandomSource p_225093_) {
        float $$5 = 10.0f * Mth.m_14036_(p_225093_.m_188501_(), 0.2f, 0.8f) / (float)p_225092_;
        return (double)$$5 + Math.pow(p_225089_ - p_225091_.m_123341_(), 2.0) + Math.pow(p_225090_ - p_225091_.m_123343_(), 2.0) - Math.pow(p_225092_, 2.0);
    }

    private double m_66022_(int p_66023_, int p_66024_, BlockPos p_66025_, int p_66026_, int p_66027_, double p_66028_) {
        return Math.pow(((double)(p_66023_ - p_66025_.m_123341_()) * Math.cos(p_66028_) - (double)(p_66024_ - p_66025_.m_123343_()) * Math.sin(p_66028_)) / (double)p_66026_, 2.0) + Math.pow(((double)(p_66023_ - p_66025_.m_123341_()) * Math.sin(p_66028_) + (double)(p_66024_ - p_66025_.m_123343_()) * Math.cos(p_66028_)) / (double)p_66027_, 2.0) - 1.0;
    }

    private int m_225094_(RandomSource p_225095_, int p_225096_, int p_225097_, int p_225098_) {
        float $$4 = 3.5f - p_225095_.m_188501_();
        float $$5 = (1.0f - (float)Math.pow(p_225096_, 2.0) / ((float)p_225097_ * $$4)) * (float)p_225098_;
        if (p_225097_ > 15 + p_225095_.m_188503_(5)) {
            int $$6 = p_225096_ < 3 + p_225095_.m_188503_(6) ? p_225096_ / 2 : p_225096_;
            $$5 = (1.0f - (float)$$6 / ((float)p_225097_ * $$4 * 0.4f)) * (float)p_225098_;
        }
        return Mth.m_14167_($$5 / 2.0f);
    }

    private int m_66109_(int p_66110_, int p_66111_, int p_66112_) {
        float $$3 = 1.0f;
        float $$4 = (1.0f - (float)Math.pow(p_66110_, 2.0) / ((float)p_66111_ * 1.0f)) * (float)p_66112_;
        return Mth.m_14167_($$4 / 2.0f);
    }

    private int m_225133_(RandomSource p_225134_, int p_225135_, int p_225136_, int p_225137_) {
        float $$4 = 1.0f + p_225134_.m_188501_() / 2.0f;
        float $$5 = (1.0f - (float)p_225135_ / ((float)p_225136_ * $$4)) * (float)p_225137_;
        return Mth.m_14167_($$5 / 2.0f);
    }

    private static boolean m_159885_(BlockState p_159886_) {
        return p_159886_.m_60713_(Blocks.f_50354_) || p_159886_.m_60713_(Blocks.f_50127_) || p_159886_.m_60713_(Blocks.f_50568_);
    }

    private boolean m_66045_(BlockGetter p_66046_, BlockPos p_66047_) {
        return p_66046_.m_8055_(p_66047_.m_7495_()).m_60767_() == Material.f_76296_;
    }

    private void m_66051_(LevelAccessor p_66052_, BlockPos p_66053_, int p_66054_, int p_66055_, boolean p_66056_, int p_66057_) {
        int $$6 = p_66056_ ? p_66057_ : p_66054_ / 2;
        for (int $$7 = -$$6; $$7 <= $$6; ++$$7) {
            for (int $$8 = -$$6; $$8 <= $$6; ++$$8) {
                for (int $$9 = 0; $$9 <= p_66055_; ++$$9) {
                    BlockPos $$10 = p_66053_.m_7918_($$7, $$9, $$8);
                    BlockState $$11 = p_66052_.m_8055_($$10);
                    if (!IcebergFeature.m_159885_($$11) && !$$11.m_60713_(Blocks.f_50125_)) continue;
                    if (this.m_66045_(p_66052_, $$10)) {
                        this.m_5974_(p_66052_, $$10, Blocks.f_50016_.m_49966_());
                        this.m_5974_(p_66052_, $$10.m_7494_(), Blocks.f_50016_.m_49966_());
                        continue;
                    }
                    if (!IcebergFeature.m_159885_($$11)) continue;
                    BlockState[] $$12 = new BlockState[]{p_66052_.m_8055_($$10.m_122024_()), p_66052_.m_8055_($$10.m_122029_()), p_66052_.m_8055_($$10.m_122012_()), p_66052_.m_8055_($$10.m_122019_())};
                    int $$13 = 0;
                    for (BlockState $$14 : $$12) {
                        if (IcebergFeature.m_159885_($$14)) continue;
                        ++$$13;
                    }
                    if ($$13 < 3) continue;
                    this.m_5974_(p_66052_, $$10, Blocks.f_50016_.m_49966_());
                }
            }
        }
    }
}

