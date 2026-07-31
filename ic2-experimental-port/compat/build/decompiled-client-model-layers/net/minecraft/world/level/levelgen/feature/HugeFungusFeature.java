/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.levelgen.feature.WeepingVinesFeature;
import net.minecraft.world.level.material.Material;

public class HugeFungusFeature
extends Feature<HugeFungusConfiguration> {
    private static final float f_159876_ = 0.06f;

    public HugeFungusFeature(Codec<HugeFungusConfiguration> p_65922_) {
        super(p_65922_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<HugeFungusConfiguration> p_159878_) {
        WorldGenLevel $$1 = p_159878_.m_159774_();
        BlockPos $$2 = p_159878_.m_159777_();
        RandomSource $$3 = p_159878_.m_225041_();
        ChunkGenerator $$4 = p_159878_.m_159775_();
        HugeFungusConfiguration $$5 = p_159878_.m_159778_();
        Block $$6 = $$5.f_65897_.m_60734_();
        BlockPos $$7 = null;
        BlockState $$8 = $$1.m_8055_($$2.m_7495_());
        if ($$8.m_60713_($$6)) {
            $$7 = $$2;
        }
        if ($$7 == null) {
            return false;
        }
        int $$9 = Mth.m_216271_($$3, 4, 13);
        if ($$3.m_188503_(12) == 0) {
            $$9 *= 2;
        }
        if (!$$5.f_65901_) {
            int $$10 = $$4.m_6331_();
            if ($$7.m_123342_() + $$9 + 1 >= $$10) {
                return false;
            }
        }
        boolean $$11 = !$$5.f_65901_ && $$3.m_188501_() < 0.06f;
        $$1.m_7731_($$2, Blocks.f_50016_.m_49966_(), 4);
        this.m_225057_($$1, $$3, $$5, $$7, $$9, $$11);
        this.m_225074_($$1, $$3, $$5, $$7, $$9, $$11);
        return true;
    }

    private static boolean m_65923_(LevelAccessor p_65924_, BlockPos p_65925_, boolean p_65926_) {
        return p_65924_.m_7433_(p_65925_, p_65966_ -> {
            Material $$2 = p_65966_.m_60767_();
            return p_65966_.m_60767_().m_76336_() || p_65926_ && $$2 == Material.f_76300_;
        });
    }

    private void m_225057_(LevelAccessor p_225058_, RandomSource p_225059_, HugeFungusConfiguration p_225060_, BlockPos p_225061_, int p_225062_, boolean p_225063_) {
        BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
        BlockState $$7 = p_225060_.f_65898_;
        int $$8 = p_225063_ ? 1 : 0;
        for (int $$9 = -$$8; $$9 <= $$8; ++$$9) {
            for (int $$10 = -$$8; $$10 <= $$8; ++$$10) {
                boolean $$11 = p_225063_ && Mth.m_14040_($$9) == $$8 && Mth.m_14040_($$10) == $$8;
                for (int $$12 = 0; $$12 < p_225062_; ++$$12) {
                    $$6.m_122154_(p_225061_, $$9, $$12, $$10);
                    if (!HugeFungusFeature.m_65923_(p_225058_, $$6, true)) continue;
                    if (p_225060_.f_65901_) {
                        if (!p_225058_.m_8055_((BlockPos)$$6.m_7495_()).m_60795_()) {
                            p_225058_.m_46961_($$6, true);
                        }
                        p_225058_.m_7731_($$6, $$7, 3);
                        continue;
                    }
                    if ($$11) {
                        if (!(p_225059_.m_188501_() < 0.1f)) continue;
                        this.m_5974_(p_225058_, $$6, $$7);
                        continue;
                    }
                    this.m_5974_(p_225058_, $$6, $$7);
                }
            }
        }
    }

    private void m_225074_(LevelAccessor p_225075_, RandomSource p_225076_, HugeFungusConfiguration p_225077_, BlockPos p_225078_, int p_225079_, boolean p_225080_) {
        int $$9;
        BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
        boolean $$7 = p_225077_.f_65899_.m_60713_(Blocks.f_50451_);
        int $$8 = Math.min(p_225076_.m_188503_(1 + p_225079_ / 3) + 5, p_225079_);
        for (int $$10 = $$9 = p_225079_ - $$8; $$10 <= p_225079_; ++$$10) {
            int $$11;
            int n = $$11 = $$10 < p_225079_ - p_225076_.m_188503_(3) ? 2 : 1;
            if ($$8 > 8 && $$10 < $$9 + 4) {
                $$11 = 3;
            }
            if (p_225080_) {
                ++$$11;
            }
            for (int $$12 = -$$11; $$12 <= $$11; ++$$12) {
                for (int $$13 = -$$11; $$13 <= $$11; ++$$13) {
                    boolean $$14 = $$12 == -$$11 || $$12 == $$11;
                    boolean $$15 = $$13 == -$$11 || $$13 == $$11;
                    boolean $$16 = !$$14 && !$$15 && $$10 != p_225079_;
                    boolean $$17 = $$14 && $$15;
                    boolean $$18 = $$10 < $$9 + 3;
                    $$6.m_122154_(p_225078_, $$12, $$10, $$13);
                    if (!HugeFungusFeature.m_65923_(p_225075_, $$6, false)) continue;
                    if (p_225077_.f_65901_ && !p_225075_.m_8055_((BlockPos)$$6.m_7495_()).m_60795_()) {
                        p_225075_.m_46961_($$6, true);
                    }
                    if ($$18) {
                        if ($$16) continue;
                        this.m_225064_(p_225075_, p_225076_, $$6, p_225077_.f_65899_, $$7);
                        continue;
                    }
                    if ($$16) {
                        this.m_225049_(p_225075_, p_225076_, p_225077_, $$6, 0.1f, 0.2f, $$7 ? 0.1f : 0.0f);
                        continue;
                    }
                    if ($$17) {
                        this.m_225049_(p_225075_, p_225076_, p_225077_, $$6, 0.01f, 0.7f, $$7 ? 0.083f : 0.0f);
                        continue;
                    }
                    this.m_225049_(p_225075_, p_225076_, p_225077_, $$6, 5.0E-4f, 0.98f, $$7 ? 0.07f : 0.0f);
                }
            }
        }
    }

    private void m_225049_(LevelAccessor p_225050_, RandomSource p_225051_, HugeFungusConfiguration p_225052_, BlockPos.MutableBlockPos p_225053_, float p_225054_, float p_225055_, float p_225056_) {
        if (p_225051_.m_188501_() < p_225054_) {
            this.m_5974_(p_225050_, p_225053_, p_225052_.f_65900_);
        } else if (p_225051_.m_188501_() < p_225055_) {
            this.m_5974_(p_225050_, p_225053_, p_225052_.f_65899_);
            if (p_225051_.m_188501_() < p_225056_) {
                HugeFungusFeature.m_225070_(p_225053_, p_225050_, p_225051_);
            }
        }
    }

    private void m_225064_(LevelAccessor p_225065_, RandomSource p_225066_, BlockPos p_225067_, BlockState p_225068_, boolean p_225069_) {
        if (p_225065_.m_8055_(p_225067_.m_7495_()).m_60713_(p_225068_.m_60734_())) {
            this.m_5974_(p_225065_, p_225067_, p_225068_);
        } else if ((double)p_225066_.m_188501_() < 0.15) {
            this.m_5974_(p_225065_, p_225067_, p_225068_);
            if (p_225069_ && p_225066_.m_188503_(11) == 0) {
                HugeFungusFeature.m_225070_(p_225067_, p_225065_, p_225066_);
            }
        }
    }

    private static void m_225070_(BlockPos p_225071_, LevelAccessor p_225072_, RandomSource p_225073_) {
        BlockPos.MutableBlockPos $$3 = p_225071_.m_122032_().m_122173_(Direction.DOWN);
        if (!p_225072_.m_46859_($$3)) {
            return;
        }
        int $$4 = Mth.m_216271_(p_225073_, 1, 5);
        if (p_225073_.m_188503_(7) == 0) {
            $$4 *= 2;
        }
        int $$5 = 23;
        int $$6 = 25;
        WeepingVinesFeature.m_225352_(p_225072_, p_225073_, $$3, $$4, 23, 25);
    }
}

