/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class HugeBrownMushroomFeature
extends AbstractHugeMushroomFeature {
    public HugeBrownMushroomFeature(Codec<HugeMushroomFeatureConfiguration> p_65879_) {
        super(p_65879_);
    }

    @Override
    protected void m_213950_(LevelAccessor p_225043_, RandomSource p_225044_, BlockPos p_225045_, int p_225046_, BlockPos.MutableBlockPos p_225047_, HugeMushroomFeatureConfiguration p_225048_) {
        int $$6 = p_225048_.f_67742_;
        for (int $$7 = -$$6; $$7 <= $$6; ++$$7) {
            for (int $$8 = -$$6; $$8 <= $$6; ++$$8) {
                boolean $$14;
                boolean $$9 = $$7 == -$$6;
                boolean $$10 = $$7 == $$6;
                boolean $$11 = $$8 == -$$6;
                boolean $$12 = $$8 == $$6;
                boolean $$13 = $$9 || $$10;
                boolean bl = $$14 = $$11 || $$12;
                if ($$13 && $$14) continue;
                p_225047_.m_122154_(p_225045_, $$7, p_225046_, $$8);
                if (p_225043_.m_8055_(p_225047_).m_60804_(p_225043_, p_225047_)) continue;
                boolean $$15 = $$9 || $$14 && $$7 == 1 - $$6;
                boolean $$16 = $$10 || $$14 && $$7 == $$6 - 1;
                boolean $$17 = $$11 || $$13 && $$8 == 1 - $$6;
                boolean $$18 = $$12 || $$13 && $$8 == $$6 - 1;
                BlockState $$19 = p_225048_.f_67740_.m_213972_(p_225044_, p_225045_);
                if ($$19.m_61138_(HugeMushroomBlock.f_54130_) && $$19.m_61138_(HugeMushroomBlock.f_54128_) && $$19.m_61138_(HugeMushroomBlock.f_54127_) && $$19.m_61138_(HugeMushroomBlock.f_54129_)) {
                    $$19 = (BlockState)((BlockState)((BlockState)((BlockState)$$19.m_61124_(HugeMushroomBlock.f_54130_, $$15)).m_61124_(HugeMushroomBlock.f_54128_, $$16)).m_61124_(HugeMushroomBlock.f_54127_, $$17)).m_61124_(HugeMushroomBlock.f_54129_, $$18);
                }
                this.m_5974_(p_225043_, p_225047_, $$19);
            }
        }
    }

    @Override
    protected int m_6794_(int p_65881_, int p_65882_, int p_65883_, int p_65884_) {
        return p_65884_ <= 3 ? 0 : p_65883_;
    }
}

