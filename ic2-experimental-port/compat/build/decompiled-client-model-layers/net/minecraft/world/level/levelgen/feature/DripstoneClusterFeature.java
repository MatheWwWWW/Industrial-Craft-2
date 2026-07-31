/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;

public class DripstoneClusterFeature
extends Feature<DripstoneClusterConfiguration> {
    public DripstoneClusterFeature(Codec<DripstoneClusterConfiguration> p_159575_) {
        super(p_159575_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<DripstoneClusterConfiguration> p_159605_) {
        WorldGenLevel $$1 = p_159605_.m_159774_();
        BlockPos $$2 = p_159605_.m_159777_();
        DripstoneClusterConfiguration $$3 = p_159605_.m_159778_();
        RandomSource $$4 = p_159605_.m_225041_();
        if (!DripstoneUtils.m_159628_($$1, $$2)) {
            return false;
        }
        int $$5 = $$3.f_160760_.m_214085_($$4);
        float $$6 = $$3.f_160766_.m_214084_($$4);
        float $$7 = $$3.f_160765_.m_214084_($$4);
        int $$8 = $$3.f_160761_.m_214085_($$4);
        int $$9 = $$3.f_160761_.m_214085_($$4);
        for (int $$10 = -$$8; $$10 <= $$8; ++$$10) {
            for (int $$11 = -$$9; $$11 <= $$9; ++$$11) {
                double $$12 = this.m_159576_($$8, $$9, $$10, $$11, $$3);
                BlockPos $$13 = $$2.m_7918_($$10, 0, $$11);
                this.m_225015_($$1, $$4, $$13, $$10, $$11, $$6, $$12, $$5, $$7, $$3);
            }
        }
        return true;
    }

    private void m_225015_(WorldGenLevel p_225016_, RandomSource p_225017_, BlockPos p_225018_, int p_225019_, int p_225020_, float p_225021_, double p_225022_, int p_225023_, float p_225024_, DripstoneClusterConfiguration p_225025_) {
        boolean $$39;
        int $$38;
        int $$37;
        int $$28;
        boolean $$24;
        int $$23;
        boolean $$18;
        Column $$16;
        boolean $$13;
        Optional<Column> $$10 = Column.m_158175_(p_225016_, p_225018_, p_225025_.f_160759_, DripstoneUtils::m_159664_, DripstoneUtils::m_203130_);
        if (!$$10.isPresent()) {
            return;
        }
        OptionalInt $$11 = $$10.get().m_142011_();
        OptionalInt $$12 = $$10.get().m_142009_();
        if (!$$11.isPresent() && !$$12.isPresent()) {
            return;
        }
        boolean bl = $$13 = p_225017_.m_188501_() < p_225021_;
        if ($$13 && $$12.isPresent() && this.m_159619_(p_225016_, p_225018_.m_175288_($$12.getAsInt()))) {
            int $$14 = $$12.getAsInt();
            Column $$15 = $$10.get().m_158181_(OptionalInt.of($$14 - 1));
            p_225016_.m_7731_(p_225018_.m_175288_($$14), Blocks.f_49990_.m_49966_(), 2);
        } else {
            $$16 = $$10.get();
        }
        OptionalInt $$17 = $$16.m_142009_();
        boolean bl2 = $$18 = p_225017_.m_188500_() < p_225022_;
        if ($$11.isPresent() && $$18 && !this.m_159585_(p_225016_, p_225018_.m_175288_($$11.getAsInt()))) {
            int $$21;
            int $$19 = p_225025_.f_160764_.m_214085_(p_225017_);
            this.m_159588_(p_225016_, p_225018_.m_175288_($$11.getAsInt()), $$19, Direction.UP);
            if ($$17.isPresent()) {
                int $$20 = Math.min(p_225023_, $$11.getAsInt() - $$17.getAsInt());
            } else {
                $$21 = p_225023_;
            }
            int $$22 = this.m_225008_(p_225017_, p_225019_, p_225020_, p_225024_, $$21, p_225025_);
        } else {
            $$23 = 0;
        }
        boolean bl3 = $$24 = p_225017_.m_188500_() < p_225022_;
        if ($$17.isPresent() && $$24 && !this.m_159585_(p_225016_, p_225018_.m_175288_($$17.getAsInt()))) {
            int $$25 = p_225025_.f_160764_.m_214085_(p_225017_);
            this.m_159588_(p_225016_, p_225018_.m_175288_($$17.getAsInt()), $$25, Direction.DOWN);
            if ($$11.isPresent()) {
                int $$26 = Math.max(0, $$23 + Mth.m_216287_(p_225017_, -p_225025_.f_160762_, p_225025_.f_160762_));
            } else {
                int $$27 = this.m_225008_(p_225017_, p_225019_, p_225020_, p_225024_, p_225023_, p_225025_);
            }
        } else {
            $$28 = 0;
        }
        if ($$11.isPresent() && $$17.isPresent() && $$11.getAsInt() - $$23 <= $$17.getAsInt() + $$28) {
            int $$29 = $$17.getAsInt();
            int $$30 = $$11.getAsInt();
            int $$31 = Math.max($$30 - $$23, $$29 + 1);
            int $$32 = Math.min($$29 + $$28, $$30 - 1);
            int $$33 = Mth.m_216287_(p_225017_, $$31, $$32 + 1);
            int $$34 = $$33 - 1;
            int $$35 = $$30 - $$33;
            int $$36 = $$34 - $$29;
        } else {
            $$37 = $$23;
            $$38 = $$28;
        }
        boolean bl4 = $$39 = p_225017_.m_188499_() && $$37 > 0 && $$38 > 0 && $$16.m_142030_().isPresent() && $$37 + $$38 == $$16.m_142030_().getAsInt();
        if ($$11.isPresent()) {
            DripstoneUtils.m_190847_(p_225016_, p_225018_.m_175288_($$11.getAsInt() - 1), Direction.DOWN, $$37, $$39);
        }
        if ($$17.isPresent()) {
            DripstoneUtils.m_190847_(p_225016_, p_225018_.m_175288_($$17.getAsInt() + 1), Direction.UP, $$38, $$39);
        }
    }

    private boolean m_159585_(LevelReader p_159586_, BlockPos p_159587_) {
        return p_159586_.m_8055_(p_159587_).m_60713_(Blocks.f_49991_);
    }

    private int m_225008_(RandomSource p_225009_, int p_225010_, int p_225011_, float p_225012_, int p_225013_, DripstoneClusterConfiguration p_225014_) {
        if (p_225009_.m_188501_() > p_225012_) {
            return 0;
        }
        int $$6 = Math.abs(p_225010_) + Math.abs(p_225011_);
        float $$7 = (float)Mth.m_144851_($$6, 0.0, p_225014_.f_160769_, (double)p_225013_ / 2.0, 0.0);
        return (int)DripstoneClusterFeature.m_225002_(p_225009_, 0.0f, p_225013_, $$7, p_225014_.f_160763_);
    }

    private boolean m_159619_(WorldGenLevel p_159620_, BlockPos p_159621_) {
        BlockState $$2 = p_159620_.m_8055_(p_159621_);
        if ($$2.m_60713_(Blocks.f_49990_) || $$2.m_60713_(Blocks.f_152537_) || $$2.m_60713_(Blocks.f_152588_)) {
            return false;
        }
        if (p_159620_.m_8055_(p_159621_.m_7494_()).m_60819_().m_205070_(FluidTags.f_13131_)) {
            return false;
        }
        for (Direction $$3 : Direction.Plane.HORIZONTAL) {
            if (this.m_159582_(p_159620_, p_159621_.m_121945_($$3))) continue;
            return false;
        }
        return this.m_159582_(p_159620_, p_159621_.m_7495_());
    }

    private boolean m_159582_(LevelAccessor p_159583_, BlockPos p_159584_) {
        BlockState $$2 = p_159583_.m_8055_(p_159584_);
        return $$2.m_204336_(BlockTags.f_13061_) || $$2.m_60819_().m_205070_(FluidTags.f_13131_);
    }

    private void m_159588_(WorldGenLevel p_159589_, BlockPos p_159590_, int p_159591_, Direction p_159592_) {
        BlockPos.MutableBlockPos $$4 = p_159590_.m_122032_();
        for (int $$5 = 0; $$5 < p_159591_; ++$$5) {
            if (!DripstoneUtils.m_190853_(p_159589_, $$4)) {
                return;
            }
            $$4.m_122173_(p_159592_);
        }
    }

    private double m_159576_(int p_159577_, int p_159578_, int p_159579_, int p_159580_, DripstoneClusterConfiguration p_159581_) {
        int $$5 = p_159577_ - Math.abs(p_159579_);
        int $$6 = p_159578_ - Math.abs(p_159580_);
        int $$7 = Math.min($$5, $$6);
        return Mth.m_184631_($$7, 0.0f, p_159581_.f_160768_, p_159581_.f_160767_, 1.0f);
    }

    private static float m_225002_(RandomSource p_225003_, float p_225004_, float p_225005_, float p_225006_, float p_225007_) {
        return ClampedNormalFloat.m_216837_(p_225003_, p_225006_, p_225007_, p_225004_, p_225005_);
    }
}

