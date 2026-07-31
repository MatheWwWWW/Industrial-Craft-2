/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.FluidState;

public class GeodeFeature
extends Feature<GeodeConfiguration> {
    private static final Direction[] f_159831_ = Direction.values();

    public GeodeFeature(Codec<GeodeConfiguration> p_159834_) {
        super(p_159834_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<GeodeConfiguration> p_159836_) {
        GeodeConfiguration $$1 = p_159836_.m_159778_();
        RandomSource $$2 = p_159836_.m_225041_();
        BlockPos $$3 = p_159836_.m_159777_();
        WorldGenLevel $$4 = p_159836_.m_159774_();
        int $$5 = $$1.f_160822_;
        int $$6 = $$1.f_160823_;
        LinkedList $$7 = Lists.newLinkedList();
        int $$8 = $$1.f_160820_.m_214085_($$2);
        WorldgenRandom $$9 = new WorldgenRandom(new LegacyRandomSource($$4.m_7328_()));
        NormalNoise $$10 = NormalNoise.m_230504_($$9, -4, 1.0);
        LinkedList $$11 = Lists.newLinkedList();
        double $$12 = (double)$$8 / (double)$$1.f_160819_.m_142737_();
        GeodeLayerSettings $$13 = $$1.f_160814_;
        GeodeBlockSettings $$14 = $$1.f_160813_;
        GeodeCrackSettings $$15 = $$1.f_160815_;
        double $$16 = 1.0 / Math.sqrt($$13.f_158342_);
        double $$17 = 1.0 / Math.sqrt($$13.f_158343_ + $$12);
        double $$18 = 1.0 / Math.sqrt($$13.f_158344_ + $$12);
        double $$19 = 1.0 / Math.sqrt($$13.f_158345_ + $$12);
        double $$20 = 1.0 / Math.sqrt($$15.f_158326_ + $$2.m_188500_() / 2.0 + ($$8 > 3 ? $$12 : 0.0));
        boolean $$21 = (double)$$2.m_188501_() < $$15.f_158325_;
        int $$22 = 0;
        for (int $$23 = 0; $$23 < $$8; ++$$23) {
            int $$26;
            int $$25;
            int $$24 = $$1.f_160819_.m_214085_($$2);
            BlockPos $$27 = $$3.m_7918_($$24, $$25 = $$1.f_160819_.m_214085_($$2), $$26 = $$1.f_160819_.m_214085_($$2));
            BlockState $$28 = $$4.m_8055_($$27);
            if (($$28.m_60795_() || $$28.m_204336_(BlockTags.f_144289_)) && ++$$22 > $$1.f_160825_) {
                return false;
            }
            $$7.add(Pair.of((Object)$$27, (Object)$$1.f_160821_.m_214085_($$2)));
        }
        if ($$21) {
            int $$29 = $$2.m_188503_(4);
            int $$30 = $$8 * 2 + 1;
            if ($$29 == 0) {
                $$11.add($$3.m_7918_($$30, 7, 0));
                $$11.add($$3.m_7918_($$30, 5, 0));
                $$11.add($$3.m_7918_($$30, 1, 0));
            } else if ($$29 == 1) {
                $$11.add($$3.m_7918_(0, 7, $$30));
                $$11.add($$3.m_7918_(0, 5, $$30));
                $$11.add($$3.m_7918_(0, 1, $$30));
            } else if ($$29 == 2) {
                $$11.add($$3.m_7918_($$30, 7, $$30));
                $$11.add($$3.m_7918_($$30, 5, $$30));
                $$11.add($$3.m_7918_($$30, 1, $$30));
            } else {
                $$11.add($$3.m_7918_(0, 7, 0));
                $$11.add($$3.m_7918_(0, 5, 0));
                $$11.add($$3.m_7918_(0, 1, 0));
            }
        }
        ArrayList $$31 = Lists.newArrayList();
        Predicate<BlockState> $$32 = GeodeFeature.m_204735_($$1.f_160813_.f_158293_);
        for (BlockPos $$33 : BlockPos.m_121940_($$3.m_7918_($$5, $$5, $$5), $$3.m_7918_($$6, $$6, $$6))) {
            double $$34 = $$10.m_75380_($$33.m_123341_(), $$33.m_123342_(), $$33.m_123343_()) * $$1.f_160824_;
            double $$35 = 0.0;
            double $$36 = 0.0;
            for (Pair $$37 : $$7) {
                $$35 += Mth.m_14193_($$33.m_123331_((Vec3i)$$37.getFirst()) + (double)((Integer)$$37.getSecond()).intValue()) + $$34;
            }
            for (BlockPos $$38 : $$11) {
                $$36 += Mth.m_14193_($$33.m_123331_($$38) + (double)$$15.f_158327_) + $$34;
            }
            if ($$35 < $$19) continue;
            if ($$21 && $$36 >= $$20 && $$35 < $$16) {
                this.m_159742_($$4, $$33, Blocks.f_50016_.m_49966_(), $$32);
                for (Direction $$39 : f_159831_) {
                    BlockPos $$40 = $$33.m_121945_($$39);
                    FluidState $$41 = $$4.m_6425_($$40);
                    if ($$41.m_76178_()) continue;
                    $$4.m_186469_($$40, $$41.m_76152_(), 0);
                }
                continue;
            }
            if ($$35 >= $$16) {
                this.m_159742_($$4, $$33, $$14.f_158287_.m_213972_($$2, $$33), $$32);
                continue;
            }
            if ($$35 >= $$17) {
                boolean $$42;
                boolean bl = $$42 = (double)$$2.m_188501_() < $$1.f_160817_;
                if ($$42) {
                    this.m_159742_($$4, $$33, $$14.f_158289_.m_213972_($$2, $$33), $$32);
                } else {
                    this.m_159742_($$4, $$33, $$14.f_158288_.m_213972_($$2, $$33), $$32);
                }
                if ($$1.f_160818_ && !$$42 || !((double)$$2.m_188501_() < $$1.f_160816_)) continue;
                $$31.add($$33.m_7949_());
                continue;
            }
            if ($$35 >= $$18) {
                this.m_159742_($$4, $$33, $$14.f_158290_.m_213972_($$2, $$33), $$32);
                continue;
            }
            if (!($$35 >= $$19)) continue;
            this.m_159742_($$4, $$33, $$14.f_158291_.m_213972_($$2, $$33), $$32);
        }
        List<BlockState> $$43 = $$14.f_158292_;
        block5: for (BlockPos $$44 : $$31) {
            BlockState $$45 = Util.m_214621_($$43, $$2);
            for (Direction $$46 : f_159831_) {
                if ($$45.m_61138_(BlockStateProperties.f_61372_)) {
                    $$45 = (BlockState)$$45.m_61124_(BlockStateProperties.f_61372_, $$46);
                }
                BlockPos $$47 = $$44.m_121945_($$46);
                BlockState $$48 = $$4.m_8055_($$47);
                if ($$45.m_61138_(BlockStateProperties.f_61362_)) {
                    $$45 = (BlockState)$$45.m_61124_(BlockStateProperties.f_61362_, $$48.m_60819_().m_76170_());
                }
                if (!BuddingAmethystBlock.m_152734_($$48)) continue;
                this.m_159742_($$4, $$47, $$45, $$32);
                continue block5;
            }
        }
        return true;
    }
}

