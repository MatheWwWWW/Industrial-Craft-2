/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableDouble
 */
package net.minecraft.world.level.levelgen;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import org.apache.commons.lang3.mutable.MutableDouble;

public interface Aquifer {
    public static Aquifer m_223880_(NoiseChunk p_223881_, ChunkPos p_223882_, NoiseRouter p_223883_, PositionalRandomFactory p_223884_, int p_223885_, int p_223886_, FluidPicker p_223887_) {
        return new NoiseBasedAquifer(p_223881_, p_223882_, p_223883_, p_223884_, p_223885_, p_223886_, p_223887_);
    }

    public static Aquifer m_188374_(final FluidPicker p_188375_) {
        return new Aquifer(){

            @Override
            @Nullable
            public BlockState m_207104_(DensityFunction.FunctionContext p_208172_, double p_208173_) {
                if (p_208173_ > 0.0) {
                    return null;
                }
                return p_188375_.m_183538_(p_208172_.m_207115_(), p_208172_.m_207114_(), p_208172_.m_207113_()).m_188405_(p_208172_.m_207114_());
            }

            @Override
            public boolean m_142203_() {
                return false;
            }
        };
    }

    @Nullable
    public BlockState m_207104_(DensityFunction.FunctionContext var1, double var2);

    public boolean m_142203_();

    public static class NoiseBasedAquifer
    implements Aquifer {
        private static final int f_157985_ = 10;
        private static final int f_157986_ = 9;
        private static final int f_157987_ = 10;
        private static final int f_157988_ = 6;
        private static final int f_157989_ = 3;
        private static final int f_157990_ = 6;
        private static final int f_157991_ = 16;
        private static final int f_157992_ = 12;
        private static final int f_157993_ = 16;
        private static final int f_196978_ = 11;
        private static final double f_196979_ = NoiseBasedAquifer.m_158024_(Mth.m_144944_(10), Mth.m_144944_(12));
        private final NoiseChunk f_188407_;
        private final DensityFunction f_157994_;
        private final DensityFunction f_188408_;
        private final DensityFunction f_188409_;
        private final DensityFunction f_157996_;
        private final PositionalRandomFactory f_188410_;
        private final FluidStatus[] f_157998_;
        private final long[] f_157999_;
        private final FluidPicker f_188411_;
        private final DensityFunction f_223888_;
        private final DensityFunction f_223889_;
        private boolean f_158000_;
        private final int f_158002_;
        private final int f_158003_;
        private final int f_158004_;
        private final int f_158005_;
        private final int f_158006_;
        private static final int[][] f_188412_ = new int[][]{{-2, -1}, {-1, -1}, {0, -1}, {1, -1}, {-3, 0}, {-2, 0}, {-1, 0}, {0, 0}, {1, 0}, {-2, 1}, {-1, 1}, {0, 1}, {1, 1}};

        NoiseBasedAquifer(NoiseChunk p_223891_, ChunkPos p_223892_, NoiseRouter p_223893_, PositionalRandomFactory p_223894_, int p_223895_, int p_223896_, FluidPicker p_223897_) {
            this.f_188407_ = p_223891_;
            this.f_157994_ = p_223893_.f_209378_();
            this.f_188408_ = p_223893_.f_209379_();
            this.f_188409_ = p_223893_.f_209380_();
            this.f_157996_ = p_223893_.f_209381_();
            this.f_223888_ = p_223893_.f_209387_();
            this.f_223889_ = p_223893_.f_209388_();
            this.f_188410_ = p_223894_;
            this.f_158002_ = this.m_158039_(p_223892_.m_45604_()) - 1;
            this.f_188411_ = p_223897_;
            int $$7 = this.m_158039_(p_223892_.m_45608_()) + 1;
            this.f_158005_ = $$7 - this.f_158002_ + 1;
            this.f_158003_ = this.m_158045_(p_223895_) - 1;
            int $$8 = this.m_158045_(p_223895_ + p_223896_) + 1;
            int $$9 = $$8 - this.f_158003_ + 1;
            this.f_158004_ = this.m_158047_(p_223892_.m_45605_()) - 1;
            int $$10 = this.m_158047_(p_223892_.m_45609_()) + 1;
            this.f_158006_ = $$10 - this.f_158004_ + 1;
            int $$11 = this.f_158005_ * $$9 * this.f_158006_;
            this.f_157998_ = new FluidStatus[$$11];
            this.f_157999_ = new long[$$11];
            Arrays.fill(this.f_157999_, Long.MAX_VALUE);
        }

        private int m_158027_(int p_158028_, int p_158029_, int p_158030_) {
            int $$3 = p_158028_ - this.f_158002_;
            int $$4 = p_158029_ - this.f_158003_;
            int $$5 = p_158030_ - this.f_158004_;
            return ($$4 * this.f_158006_ + $$5) * this.f_158005_ + $$3;
        }

        @Override
        @Nullable
        public BlockState m_207104_(DensityFunction.FunctionContext p_208186_, double p_208187_) {
            double $$41;
            double $$39;
            BlockState $$32;
            int $$2 = p_208186_.m_207115_();
            int $$3 = p_208186_.m_207114_();
            int $$4 = p_208186_.m_207113_();
            if (p_208187_ > 0.0) {
                this.f_158000_ = false;
                return null;
            }
            FluidStatus $$5 = this.f_188411_.m_183538_($$2, $$3, $$4);
            if ($$5.m_188405_($$3).m_60713_(Blocks.f_49991_)) {
                this.f_158000_ = false;
                return Blocks.f_49991_.m_49966_();
            }
            int $$6 = Math.floorDiv($$2 - 5, 16);
            int $$7 = Math.floorDiv($$3 + 1, 12);
            int $$8 = Math.floorDiv($$4 - 5, 16);
            int $$9 = Integer.MAX_VALUE;
            int $$10 = Integer.MAX_VALUE;
            int $$11 = Integer.MAX_VALUE;
            long $$12 = 0L;
            long $$13 = 0L;
            long $$14 = 0L;
            for (int $$15 = 0; $$15 <= 1; ++$$15) {
                for (int $$16 = -1; $$16 <= 1; ++$$16) {
                    for (int $$17 = 0; $$17 <= 1; ++$$17) {
                        long $$25;
                        int $$18 = $$6 + $$15;
                        int $$19 = $$7 + $$16;
                        int $$20 = $$8 + $$17;
                        int $$21 = this.m_158027_($$18, $$19, $$20);
                        long $$22 = this.f_157999_[$$21];
                        if ($$22 != Long.MAX_VALUE) {
                            long $$23 = $$22;
                        } else {
                            RandomSource $$24 = this.f_188410_.m_213715_($$18, $$19, $$20);
                            this.f_157999_[$$21] = $$25 = BlockPos.m_121882_($$18 * 16 + $$24.m_188503_(10), $$19 * 12 + $$24.m_188503_(9), $$20 * 16 + $$24.m_188503_(10));
                        }
                        int $$26 = BlockPos.m_121983_($$25) - $$2;
                        int $$27 = BlockPos.m_122008_($$25) - $$3;
                        int $$28 = BlockPos.m_122015_($$25) - $$4;
                        int $$29 = $$26 * $$26 + $$27 * $$27 + $$28 * $$28;
                        if ($$9 >= $$29) {
                            $$14 = $$13;
                            $$13 = $$12;
                            $$12 = $$25;
                            $$11 = $$10;
                            $$10 = $$9;
                            $$9 = $$29;
                            continue;
                        }
                        if ($$10 >= $$29) {
                            $$14 = $$13;
                            $$13 = $$25;
                            $$11 = $$10;
                            $$10 = $$29;
                            continue;
                        }
                        if ($$11 < $$29) continue;
                        $$14 = $$25;
                        $$11 = $$29;
                    }
                }
            }
            FluidStatus $$30 = this.m_188445_($$12);
            double $$31 = NoiseBasedAquifer.m_158024_($$9, $$10);
            BlockState $$33 = $$32 = $$30.m_188405_($$3);
            if ($$31 <= 0.0) {
                this.f_158000_ = $$31 >= f_196979_;
                return $$33;
            }
            if ($$32.m_60713_(Blocks.f_49990_) && this.f_188411_.m_183538_($$2, $$3 - 1, $$4).m_188405_($$3 - 1).m_60713_(Blocks.f_49991_)) {
                this.f_158000_ = true;
                return $$33;
            }
            MutableDouble $$34 = new MutableDouble(Double.NaN);
            FluidStatus $$35 = this.m_188445_($$13);
            double $$36 = $$31 * this.m_208188_(p_208186_, $$34, $$30, $$35);
            if (p_208187_ + $$36 > 0.0) {
                this.f_158000_ = false;
                return null;
            }
            FluidStatus $$37 = this.m_188445_($$14);
            double $$38 = NoiseBasedAquifer.m_158024_($$9, $$11);
            if ($$38 > 0.0 && p_208187_ + ($$39 = $$31 * $$38 * this.m_208188_(p_208186_, $$34, $$30, $$37)) > 0.0) {
                this.f_158000_ = false;
                return null;
            }
            double $$40 = NoiseBasedAquifer.m_158024_($$10, $$11);
            if ($$40 > 0.0 && p_208187_ + ($$41 = $$31 * $$40 * this.m_208188_(p_208186_, $$34, $$35, $$37)) > 0.0) {
                this.f_158000_ = false;
                return null;
            }
            this.f_158000_ = true;
            return $$33;
        }

        @Override
        public boolean m_142203_() {
            return this.f_158000_;
        }

        private static double m_158024_(int p_158025_, int p_158026_) {
            double $$2 = 25.0;
            return 1.0 - (double)Math.abs(p_158026_ - p_158025_) / 25.0;
        }

        private double m_208188_(DensityFunction.FunctionContext p_208189_, MutableDouble p_208190_, FluidStatus p_208191_, FluidStatus p_208192_) {
            double $$29;
            double $$23;
            int $$4 = p_208189_.m_207114_();
            BlockState $$5 = p_208191_.m_188405_($$4);
            BlockState $$6 = p_208192_.m_188405_($$4);
            if ($$5.m_60713_(Blocks.f_49991_) && $$6.m_60713_(Blocks.f_49990_) || $$5.m_60713_(Blocks.f_49990_) && $$6.m_60713_(Blocks.f_49991_)) {
                return 2.0;
            }
            int $$7 = Math.abs(p_208191_.f_188400_ - p_208192_.f_188400_);
            if ($$7 == 0) {
                return 0.0;
            }
            double $$8 = 0.5 * (double)(p_208191_.f_188400_ + p_208192_.f_188400_);
            double $$9 = (double)$$4 + 0.5 - $$8;
            double $$10 = (double)$$7 / 2.0;
            double $$11 = 0.0;
            double $$12 = 2.5;
            double $$13 = 1.5;
            double $$14 = 3.0;
            double $$15 = 10.0;
            double $$16 = 3.0;
            double $$17 = $$10 - Math.abs($$9);
            if ($$9 > 0.0) {
                double $$18 = 0.0 + $$17;
                if ($$18 > 0.0) {
                    double $$19 = $$18 / 1.5;
                } else {
                    double $$20 = $$18 / 2.5;
                }
            } else {
                double $$21 = 3.0 + $$17;
                if ($$21 > 0.0) {
                    double $$22 = $$21 / 3.0;
                } else {
                    $$23 = $$21 / 10.0;
                }
            }
            double $$24 = 2.0;
            if ($$23 < -2.0 || $$23 > 2.0) {
                double $$25 = 0.0;
            } else {
                double $$26 = p_208190_.getValue();
                if (Double.isNaN($$26)) {
                    double $$27 = this.f_157994_.m_207386_(p_208189_);
                    p_208190_.setValue($$27);
                    double $$28 = $$27;
                } else {
                    $$29 = $$26;
                }
            }
            return 2.0 * ($$29 + $$23);
        }

        private int m_158039_(int p_158040_) {
            return Math.floorDiv(p_158040_, 16);
        }

        private int m_158045_(int p_158046_) {
            return Math.floorDiv(p_158046_, 12);
        }

        private int m_158047_(int p_158048_) {
            return Math.floorDiv(p_158048_, 16);
        }

        private FluidStatus m_188445_(long p_188446_) {
            FluidStatus $$9;
            int $$6;
            int $$5;
            int $$1 = BlockPos.m_121983_(p_188446_);
            int $$2 = BlockPos.m_122008_(p_188446_);
            int $$3 = BlockPos.m_122015_(p_188446_);
            int $$4 = this.m_158039_($$1);
            int $$7 = this.m_158027_($$4, $$5 = this.m_158045_($$2), $$6 = this.m_158047_($$3));
            FluidStatus $$8 = this.f_157998_[$$7];
            if ($$8 != null) {
                return $$8;
            }
            this.f_157998_[$$7] = $$9 = this.m_188447_($$1, $$2, $$3);
            return $$9;
        }

        private FluidStatus m_188447_(int p_188448_, int p_188449_, int p_188450_) {
            FluidStatus $$3 = this.f_188411_.m_183538_(p_188448_, p_188449_, p_188450_);
            int $$4 = Integer.MAX_VALUE;
            int $$5 = p_188449_ + 12;
            int $$6 = p_188449_ - 12;
            boolean $$7 = false;
            for (int[] $$8 : f_188412_) {
                FluidStatus $$15;
                boolean $$14;
                boolean $$13;
                int $$9 = p_188448_ + SectionPos.m_123223_($$8[0]);
                int $$10 = p_188450_ + SectionPos.m_123223_($$8[1]);
                int $$11 = this.f_188407_.m_198256_($$9, $$10);
                int $$12 = $$11 + 8;
                boolean bl = $$13 = $$8[0] == 0 && $$8[1] == 0;
                if ($$13 && $$6 > $$12) {
                    return $$3;
                }
                boolean bl2 = $$14 = $$5 > $$12;
                if (($$14 || $$13) && !($$15 = this.f_188411_.m_183538_($$9, $$12, $$10)).m_188405_($$12).m_60795_()) {
                    if ($$13) {
                        $$7 = true;
                    }
                    if ($$14) {
                        return $$15;
                    }
                }
                $$4 = Math.min($$4, $$11);
            }
            int $$16 = this.m_223909_(p_188448_, p_188449_, p_188450_, $$3, $$4, $$7);
            return new FluidStatus($$16, this.m_223903_(p_188448_, p_188449_, p_188450_, $$3, $$16));
        }

        private int m_223909_(int p_223910_, int p_223911_, int p_223912_, FluidStatus p_223913_, int p_223914_, boolean p_223915_) {
            int $$19;
            double $$16;
            double $$15;
            DensityFunction.SinglePointContext $$6 = new DensityFunction.SinglePointContext(p_223910_, p_223911_, p_223912_);
            if (OverworldBiomeBuilder.m_220665_(this.f_223888_.m_207386_($$6), this.f_223889_.m_207386_($$6))) {
                double $$7 = -1.0;
                double $$8 = -1.0;
            } else {
                int $$9 = p_223914_ + 8 - p_223911_;
                int $$10 = 64;
                double $$11 = p_223915_ ? Mth.m_144851_($$9, 0.0, 64.0, 1.0, 0.0) : 0.0;
                double $$12 = Mth.m_14008_(this.f_188408_.m_207386_($$6), -1.0, 1.0);
                double $$13 = Mth.m_144914_($$11, 1.0, 0.0, -0.3, 0.8);
                double $$14 = Mth.m_144914_($$11, 1.0, 0.0, -0.8, 0.4);
                $$15 = $$12 - $$14;
                $$16 = $$12 - $$13;
            }
            if ($$16 > 0.0) {
                int $$17 = p_223913_.f_188400_;
            } else if ($$15 > 0.0) {
                int $$18 = this.m_223898_(p_223910_, p_223911_, p_223912_, p_223914_);
            } else {
                $$19 = DimensionType.f_188294_;
            }
            return $$19;
        }

        private int m_223898_(int p_223899_, int p_223900_, int p_223901_, int p_223902_) {
            int $$4 = 16;
            int $$5 = 40;
            int $$6 = Math.floorDiv(p_223899_, 16);
            int $$7 = Math.floorDiv(p_223900_, 40);
            int $$8 = Math.floorDiv(p_223901_, 16);
            int $$9 = $$7 * 40 + 20;
            int $$10 = 10;
            double $$11 = this.f_188409_.m_207386_(new DensityFunction.SinglePointContext($$6, $$7, $$8)) * 10.0;
            int $$12 = Mth.m_184628_($$11, 3);
            int $$13 = $$9 + $$12;
            return Math.min(p_223902_, $$13);
        }

        private BlockState m_223903_(int p_223904_, int p_223905_, int p_223906_, FluidStatus p_223907_, int p_223908_) {
            BlockState $$5 = p_223907_.f_188401_;
            if (p_223908_ <= -10 && p_223908_ != DimensionType.f_188294_ && p_223907_.f_188401_ != Blocks.f_49991_.m_49966_()) {
                int $$10;
                int $$9;
                int $$6 = 64;
                int $$7 = 40;
                int $$8 = Math.floorDiv(p_223904_, 64);
                double $$11 = this.f_157996_.m_207386_(new DensityFunction.SinglePointContext($$8, $$9 = Math.floorDiv(p_223905_, 40), $$10 = Math.floorDiv(p_223906_, 64)));
                if (Math.abs($$11) > 0.3) {
                    $$5 = Blocks.f_49991_.m_49966_();
                }
            }
            return $$5;
        }
    }

    public static interface FluidPicker {
        public FluidStatus m_183538_(int var1, int var2, int var3);
    }

    public static final class FluidStatus {
        final int f_188400_;
        final BlockState f_188401_;

        public FluidStatus(int p_188403_, BlockState p_188404_) {
            this.f_188400_ = p_188403_;
            this.f_188401_ = p_188404_;
        }

        public BlockState m_188405_(int p_188406_) {
            return p_188406_ < this.f_188400_ ? this.f_188401_ : Blocks.f_50016_.m_49966_();
        }
    }
}

