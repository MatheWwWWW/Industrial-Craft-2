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

public class HugeRedMushroomFeature
extends AbstractHugeMushroomFeature {
    public HugeRedMushroomFeature(Codec<HugeMushroomFeatureConfiguration> p_65975_) {
        super(p_65975_);
    }

    @Override
    protected void m_213950_(LevelAccessor p_225082_, RandomSource p_225083_, BlockPos p_225084_, int p_225085_, BlockPos.MutableBlockPos p_225086_, HugeMushroomFeatureConfiguration p_225087_) {
        for (int $$6 = p_225085_ - 3; $$6 <= p_225085_; ++$$6) {
            int $$7 = $$6 < p_225085_ ? p_225087_.f_67742_ : p_225087_.f_67742_ - 1;
            int $$8 = p_225087_.f_67742_ - 2;
            for (int $$9 = -$$7; $$9 <= $$7; ++$$9) {
                for (int $$10 = -$$7; $$10 <= $$7; ++$$10) {
                    boolean $$16;
                    boolean $$11 = $$9 == -$$7;
                    boolean $$12 = $$9 == $$7;
                    boolean $$13 = $$10 == -$$7;
                    boolean $$14 = $$10 == $$7;
                    boolean $$15 = $$11 || $$12;
                    boolean bl = $$16 = $$13 || $$14;
                    if ($$6 < p_225085_ && $$15 == $$16) continue;
                    p_225086_.m_122154_(p_225084_, $$9, $$6, $$10);
                    if (p_225082_.m_8055_(p_225086_).m_60804_(p_225082_, p_225086_)) continue;
                    BlockState $$17 = p_225087_.f_67740_.m_213972_(p_225083_, p_225084_);
                    if ($$17.m_61138_(HugeMushroomBlock.f_54130_) && $$17.m_61138_(HugeMushroomBlock.f_54128_) && $$17.m_61138_(HugeMushroomBlock.f_54127_) && $$17.m_61138_(HugeMushroomBlock.f_54129_) && $$17.m_61138_(HugeMushroomBlock.f_54131_)) {
                        $$17 = (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)$$17.m_61124_(HugeMushroomBlock.f_54131_, $$6 >= p_225085_ - 1)).m_61124_(HugeMushroomBlock.f_54130_, $$9 < -$$8)).m_61124_(HugeMushroomBlock.f_54128_, $$9 > $$8)).m_61124_(HugeMushroomBlock.f_54127_, $$10 < -$$8)).m_61124_(HugeMushroomBlock.f_54129_, $$10 > $$8);
                    }
                    this.m_5974_(p_225082_, p_225086_, $$17);
                }
            }
        }
    }

    @Override
    protected int m_6794_(int p_65977_, int p_65978_, int p_65979_, int p_65980_) {
        int $$4 = 0;
        if (p_65980_ < p_65978_ && p_65980_ >= p_65978_ - 3) {
            $$4 = p_65979_;
        } else if (p_65980_ == p_65978_) {
            $$4 = p_65979_;
        }
        return $$4;
    }
}

