/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.phys.Vec3;

public class LargeDripstoneFeature
extends Feature<LargeDripstoneConfiguration> {
    public LargeDripstoneFeature(Codec<LargeDripstoneConfiguration> p_159960_) {
        super(p_159960_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<LargeDripstoneConfiguration> p_159967_) {
        WindOffsetter $$13;
        WorldGenLevel $$1 = p_159967_.m_159774_();
        BlockPos $$2 = p_159967_.m_159777_();
        LargeDripstoneConfiguration $$3 = p_159967_.m_159778_();
        RandomSource $$4 = p_159967_.m_225041_();
        if (!DripstoneUtils.m_159628_($$1, $$2)) {
            return false;
        }
        Optional<Column> $$5 = Column.m_158175_($$1, $$2, $$3.f_160945_, DripstoneUtils::m_159664_, DripstoneUtils::m_159649_);
        if (!$$5.isPresent() || !($$5.get() instanceof Column.Range)) {
            return false;
        }
        Column.Range $$6 = (Column.Range)$$5.get();
        if ($$6.m_158214_() < 4) {
            return false;
        }
        int $$7 = (int)((float)$$6.m_158214_() * $$3.f_160948_);
        int $$8 = Mth.m_14045_($$7, $$3.f_160946_.m_142739_(), $$3.f_160946_.m_142737_());
        int $$9 = Mth.m_216287_($$4, $$3.f_160946_.m_142739_(), $$8);
        LargeDripstone $$10 = LargeDripstoneFeature.m_225138_($$2.m_175288_($$6.m_158212_() - 1), false, $$4, $$9, $$3.f_160949_, $$3.f_160947_);
        LargeDripstone $$11 = LargeDripstoneFeature.m_225138_($$2.m_175288_($$6.m_158213_() + 1), true, $$4, $$9, $$3.f_160950_, $$3.f_160947_);
        if ($$10.m_159996_($$3) && $$11.m_159996_($$3)) {
            WindOffsetter $$12 = new WindOffsetter($$2.m_123342_(), $$4, $$3.f_160951_);
        } else {
            $$13 = WindOffsetter.m_160007_();
        }
        boolean $$14 = $$10.m_159989_($$1, $$13);
        boolean $$15 = $$11.m_159989_($$1, $$13);
        if ($$14) {
            $$10.m_225145_($$1, $$4, $$13);
        }
        if ($$15) {
            $$11.m_225145_($$1, $$4, $$13);
        }
        return true;
    }

    private static LargeDripstone m_225138_(BlockPos p_225139_, boolean p_225140_, RandomSource p_225141_, int p_225142_, FloatProvider p_225143_, FloatProvider p_225144_) {
        return new LargeDripstone(p_225139_, p_225140_, p_225142_, p_225143_.m_214084_(p_225141_), p_225144_.m_214084_(p_225141_));
    }

    private void m_159961_(WorldGenLevel p_159962_, BlockPos p_159963_, Column.Range p_159964_, WindOffsetter p_159965_) {
        p_159962_.m_7731_(p_159965_.m_160008_(p_159963_.m_175288_(p_159964_.m_158212_() - 1)), Blocks.f_50090_.m_49966_(), 2);
        p_159962_.m_7731_(p_159965_.m_160008_(p_159963_.m_175288_(p_159964_.m_158213_() + 1)), Blocks.f_50074_.m_49966_(), 2);
        BlockPos.MutableBlockPos $$4 = p_159963_.m_175288_(p_159964_.m_158213_() + 2).m_122032_();
        while ($$4.m_123342_() < p_159964_.m_158212_() - 1) {
            BlockPos $$5 = p_159965_.m_160008_($$4);
            if (DripstoneUtils.m_159628_(p_159962_, $$5) || p_159962_.m_8055_($$5).m_60713_(Blocks.f_152537_)) {
                p_159962_.m_7731_($$5, Blocks.f_50318_.m_49966_(), 2);
            }
            $$4.m_122173_(Direction.UP);
        }
    }

    static final class LargeDripstone {
        private BlockPos f_159975_;
        private final boolean f_159976_;
        private int f_159977_;
        private final double f_159978_;
        private final double f_159979_;

        LargeDripstone(BlockPos p_197116_, boolean p_197117_, int p_197118_, double p_197119_, double p_197120_) {
            this.f_159975_ = p_197116_;
            this.f_159976_ = p_197117_;
            this.f_159977_ = p_197118_;
            this.f_159978_ = p_197119_;
            this.f_159979_ = p_197120_;
        }

        private int m_159986_() {
            return this.m_159987_(0.0f);
        }

        private int m_159998_() {
            if (this.f_159976_) {
                return this.f_159975_.m_123342_();
            }
            return this.f_159975_.m_123342_() - this.m_159986_();
        }

        private int m_159999_() {
            if (!this.f_159976_) {
                return this.f_159975_.m_123342_();
            }
            return this.f_159975_.m_123342_() + this.m_159986_();
        }

        boolean m_159989_(WorldGenLevel p_159990_, WindOffsetter p_159991_) {
            while (this.f_159977_ > 1) {
                BlockPos.MutableBlockPos $$2 = this.f_159975_.m_122032_();
                int $$3 = Math.min(10, this.m_159986_());
                for (int $$4 = 0; $$4 < $$3; ++$$4) {
                    if (p_159990_.m_8055_($$2).m_60713_(Blocks.f_49991_)) {
                        return false;
                    }
                    if (DripstoneUtils.m_159639_(p_159990_, p_159991_.m_160008_($$2), this.f_159977_)) {
                        this.f_159975_ = $$2;
                        return true;
                    }
                    $$2.m_122173_(this.f_159976_ ? Direction.DOWN : Direction.UP);
                }
                this.f_159977_ /= 2;
            }
            return false;
        }

        private int m_159987_(float p_159988_) {
            return (int)DripstoneUtils.m_159623_(p_159988_, this.f_159977_, this.f_159979_, this.f_159978_);
        }

        void m_225145_(WorldGenLevel p_225146_, RandomSource p_225147_, WindOffsetter p_225148_) {
            for (int $$3 = -this.f_159977_; $$3 <= this.f_159977_; ++$$3) {
                block1: for (int $$4 = -this.f_159977_; $$4 <= this.f_159977_; ++$$4) {
                    int $$6;
                    float $$5 = Mth.m_14116_($$3 * $$3 + $$4 * $$4);
                    if ($$5 > (float)this.f_159977_ || ($$6 = this.m_159987_($$5)) <= 0) continue;
                    if ((double)p_225147_.m_188501_() < 0.2) {
                        $$6 = (int)((float)$$6 * Mth.m_216283_(p_225147_, 0.8f, 1.0f));
                    }
                    BlockPos.MutableBlockPos $$7 = this.f_159975_.m_7918_($$3, 0, $$4).m_122032_();
                    boolean $$8 = false;
                    int $$9 = this.f_159976_ ? p_225146_.m_6924_(Heightmap.Types.WORLD_SURFACE_WG, $$7.m_123341_(), $$7.m_123343_()) : Integer.MAX_VALUE;
                    for (int $$10 = 0; $$10 < $$6 && $$7.m_123342_() < $$9; ++$$10) {
                        BlockPos $$11 = p_225148_.m_160008_($$7);
                        if (DripstoneUtils.m_159659_(p_225146_, $$11)) {
                            $$8 = true;
                            Block $$12 = Blocks.f_152537_;
                            p_225146_.m_7731_($$11, $$12.m_49966_(), 2);
                        } else if ($$8 && p_225146_.m_8055_($$11).m_204336_(BlockTags.f_13061_)) continue block1;
                        $$7.m_122173_(this.f_159976_ ? Direction.UP : Direction.DOWN);
                    }
                }
            }
        }

        boolean m_159996_(LargeDripstoneConfiguration p_159997_) {
            return this.f_159977_ >= p_159997_.f_160952_ && this.f_159978_ >= (double)p_159997_.f_160953_;
        }
    }

    static final class WindOffsetter {
        private final int f_160000_;
        @Nullable
        private final Vec3 f_160001_;

        WindOffsetter(int p_225150_, RandomSource p_225151_, FloatProvider p_225152_) {
            this.f_160000_ = p_225150_;
            float $$3 = p_225152_.m_214084_(p_225151_);
            float $$4 = Mth.m_216283_(p_225151_, 0.0f, (float)Math.PI);
            this.f_160001_ = new Vec3(Mth.m_14089_($$4) * $$3, 0.0, Mth.m_14031_($$4) * $$3);
        }

        private WindOffsetter() {
            this.f_160000_ = 0;
            this.f_160001_ = null;
        }

        static WindOffsetter m_160007_() {
            return new WindOffsetter();
        }

        BlockPos m_160008_(BlockPos p_160009_) {
            if (this.f_160001_ == null) {
                return p_160009_;
            }
            int $$1 = this.f_160000_ - p_160009_.m_123342_();
            Vec3 $$2 = this.f_160001_.m_82490_($$1);
            return p_160009_.m_7637_($$2.f_82479_, 0.0, $$2.f_82481_);
        }
    }
}

