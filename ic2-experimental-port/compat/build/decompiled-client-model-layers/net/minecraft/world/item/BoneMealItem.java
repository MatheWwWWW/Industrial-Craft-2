/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BoneMealItem
extends Item {
    public static final int f_150701_ = 3;
    public static final int f_150702_ = 1;
    public static final int f_150703_ = 3;

    public BoneMealItem(Item.Properties p_40626_) {
        super(p_40626_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_40637_) {
        Level $$1 = p_40637_.m_43725_();
        BlockPos $$2 = p_40637_.m_8083_();
        BlockPos $$3 = $$2.m_121945_(p_40637_.m_43719_());
        if (BoneMealItem.m_40627_(p_40637_.m_43722_(), $$1, $$2)) {
            if (!$$1.f_46443_) {
                $$1.m_46796_(1505, $$2, 0);
            }
            return InteractionResult.m_19078_($$1.f_46443_);
        }
        BlockState $$4 = $$1.m_8055_($$2);
        boolean $$5 = $$4.m_60783_($$1, $$2, p_40637_.m_43719_());
        if ($$5 && BoneMealItem.m_40631_(p_40637_.m_43722_(), $$1, $$3, p_40637_.m_43719_())) {
            if (!$$1.f_46443_) {
                $$1.m_46796_(1505, $$3, 0);
            }
            return InteractionResult.m_19078_($$1.f_46443_);
        }
        return InteractionResult.PASS;
    }

    public static boolean m_40627_(ItemStack p_40628_, Level p_40629_, BlockPos p_40630_) {
        BonemealableBlock $$4;
        BlockState $$3 = p_40629_.m_8055_(p_40630_);
        if ($$3.m_60734_() instanceof BonemealableBlock && ($$4 = (BonemealableBlock)((Object)$$3.m_60734_())).m_7370_(p_40629_, p_40630_, $$3, p_40629_.f_46443_)) {
            if (p_40629_ instanceof ServerLevel) {
                if ($$4.m_214167_(p_40629_, p_40629_.f_46441_, p_40630_, $$3)) {
                    $$4.m_214148_((ServerLevel)p_40629_, p_40629_.f_46441_, p_40630_, $$3);
                }
                p_40628_.m_41774_(1);
            }
            return true;
        }
        return false;
    }

    public static boolean m_40631_(ItemStack p_40632_, Level p_40633_, BlockPos p_40634_, @Nullable Direction p_40635_) {
        if (!p_40633_.m_8055_(p_40634_).m_60713_(Blocks.f_49990_) || p_40633_.m_6425_(p_40634_).m_76186_() != 8) {
            return false;
        }
        if (!(p_40633_ instanceof ServerLevel)) {
            return true;
        }
        RandomSource $$4 = p_40633_.m_213780_();
        block0: for (int $$5 = 0; $$5 < 128; ++$$5) {
            BlockPos $$6 = p_40634_;
            BlockState $$7 = Blocks.f_50037_.m_49966_();
            for (int $$8 = 0; $$8 < $$5 / 16; ++$$8) {
                if (p_40633_.m_8055_($$6 = $$6.m_7918_($$4.m_188503_(3) - 1, ($$4.m_188503_(3) - 1) * $$4.m_188503_(3) / 2, $$4.m_188503_(3) - 1)).m_60838_(p_40633_, $$6)) continue block0;
            }
            Holder<Biome> $$9 = p_40633_.m_204166_($$6);
            if ($$9.m_203656_(BiomeTags.f_215804_)) {
                if ($$5 == 0 && p_40635_ != null && p_40635_.m_122434_().m_122479_()) {
                    $$7 = Registry.f_122824_.m_203431_(BlockTags.f_13052_).flatMap(p_204098_ -> p_204098_.m_213653_(p_204097_.f_46441_)).map(p_204100_ -> ((Block)p_204100_.m_203334_()).m_49966_()).orElse($$7);
                    if ($$7.m_61138_(BaseCoralWallFanBlock.f_49192_)) {
                        $$7 = (BlockState)$$7.m_61124_(BaseCoralWallFanBlock.f_49192_, p_40635_);
                    }
                } else if ($$4.m_188503_(4) == 0) {
                    $$7 = Registry.f_122824_.m_203431_(BlockTags.f_13050_).flatMap(p_204091_ -> p_204091_.m_213653_(p_204090_.f_46441_)).map(p_204095_ -> ((Block)p_204095_.m_203334_()).m_49966_()).orElse($$7);
                }
            }
            if ($$7.m_204338_(BlockTags.f_13052_, p_204093_ -> p_204093_.m_61138_(BaseCoralWallFanBlock.f_49192_))) {
                for (int $$10 = 0; !$$7.m_60710_(p_40633_, $$6) && $$10 < 4; ++$$10) {
                    $$7 = (BlockState)$$7.m_61124_(BaseCoralWallFanBlock.f_49192_, Direction.Plane.HORIZONTAL.m_235690_($$4));
                }
            }
            if (!$$7.m_60710_(p_40633_, $$6)) continue;
            BlockState $$11 = p_40633_.m_8055_($$6);
            if ($$11.m_60713_(Blocks.f_49990_) && p_40633_.m_6425_($$6).m_76186_() == 8) {
                p_40633_.m_7731_($$6, $$7, 3);
                continue;
            }
            if (!$$11.m_60713_(Blocks.f_50037_) || $$4.m_188503_(10) != 0) continue;
            ((BonemealableBlock)((Object)Blocks.f_50037_)).m_214148_((ServerLevel)p_40633_, $$4, $$6, $$11);
        }
        p_40632_.m_41774_(1);
        return true;
    }

    public static void m_40638_(LevelAccessor p_40639_, BlockPos p_40640_, int p_40641_) {
        double $$7;
        BlockState $$3;
        if (p_40641_ == 0) {
            p_40641_ = 15;
        }
        if (($$3 = p_40639_.m_8055_(p_40640_)).m_60795_()) {
            return;
        }
        double $$4 = 0.5;
        if ($$3.m_60713_(Blocks.f_49990_)) {
            p_40641_ *= 3;
            double $$5 = 1.0;
            $$4 = 3.0;
        } else if ($$3.m_60804_(p_40639_, p_40640_)) {
            p_40640_ = p_40640_.m_7494_();
            p_40641_ *= 3;
            $$4 = 3.0;
            double $$6 = 1.0;
        } else {
            $$7 = $$3.m_60808_(p_40639_, p_40640_).m_83297_(Direction.Axis.Y);
        }
        p_40639_.m_7106_(ParticleTypes.f_123748_, (double)p_40640_.m_123341_() + 0.5, (double)p_40640_.m_123342_() + 0.5, (double)p_40640_.m_123343_() + 0.5, 0.0, 0.0, 0.0);
        RandomSource $$8 = p_40639_.m_213780_();
        for (int $$9 = 0; $$9 < p_40641_; ++$$9) {
            double $$16;
            double $$15;
            double $$10 = $$8.m_188583_() * 0.02;
            double $$11 = $$8.m_188583_() * 0.02;
            double $$12 = $$8.m_188583_() * 0.02;
            double $$13 = 0.5 - $$4;
            double $$14 = (double)p_40640_.m_123341_() + $$13 + $$8.m_188500_() * $$4 * 2.0;
            if (p_40639_.m_8055_(new BlockPos($$14, $$15 = (double)p_40640_.m_123342_() + $$8.m_188500_() * $$7, $$16 = (double)p_40640_.m_123343_() + $$13 + $$8.m_188500_() * $$4 * 2.0).m_7495_()).m_60795_()) continue;
            p_40639_.m_7106_(ParticleTypes.f_123748_, $$14, $$15, $$16, $$10, $$11, $$12);
        }
    }
}

