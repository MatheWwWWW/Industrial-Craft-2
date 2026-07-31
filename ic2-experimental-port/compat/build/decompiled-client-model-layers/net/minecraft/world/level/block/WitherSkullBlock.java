/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockMaterialPredicate;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.material.Material;

public class WitherSkullBlock
extends SkullBlock {
    @Nullable
    private static BlockPattern f_58251_;
    @Nullable
    private static BlockPattern f_58252_;

    protected WitherSkullBlock(BlockBehaviour.Properties p_58254_) {
        super(SkullBlock.Types.WITHER_SKELETON, p_58254_);
    }

    @Override
    public void m_6402_(Level p_58260_, BlockPos p_58261_, BlockState p_58262_, @Nullable LivingEntity p_58263_, ItemStack p_58264_) {
        super.m_6402_(p_58260_, p_58261_, p_58262_, p_58263_, p_58264_);
        BlockEntity $$5 = p_58260_.m_7702_(p_58261_);
        if ($$5 instanceof SkullBlockEntity) {
            WitherSkullBlock.m_58255_(p_58260_, p_58261_, (SkullBlockEntity)$$5);
        }
    }

    public static void m_58255_(Level p_58256_, BlockPos p_58257_, SkullBlockEntity p_58258_) {
        boolean $$4;
        if (p_58256_.f_46443_) {
            return;
        }
        BlockState $$3 = p_58258_.m_58900_();
        boolean bl = $$4 = $$3.m_60713_(Blocks.f_50312_) || $$3.m_60713_(Blocks.f_50313_);
        if (!$$4 || p_58257_.m_123342_() < p_58256_.m_141937_() || p_58256_.m_46791_() == Difficulty.PEACEFUL) {
            return;
        }
        BlockPattern $$5 = WitherSkullBlock.m_58273_();
        BlockPattern.BlockPatternMatch $$6 = $$5.m_61184_(p_58256_, p_58257_);
        if ($$6 == null) {
            return;
        }
        for (int $$7 = 0; $$7 < $$5.m_61203_(); ++$$7) {
            for (int $$8 = 0; $$8 < $$5.m_61202_(); ++$$8) {
                BlockInWorld $$9 = $$6.m_61229_($$7, $$8, 0);
                p_58256_.m_7731_($$9.m_61176_(), Blocks.f_50016_.m_49966_(), 2);
                p_58256_.m_46796_(2001, $$9.m_61176_(), Block.m_49956_($$9.m_61168_()));
            }
        }
        WitherBoss $$10 = EntityType.f_20496_.m_20615_(p_58256_);
        BlockPos $$11 = $$6.m_61229_(1, 2, 0).m_61176_();
        $$10.m_7678_((double)$$11.m_123341_() + 0.5, (double)$$11.m_123342_() + 0.55, (double)$$11.m_123343_() + 0.5, $$6.m_61233_().m_122434_() == Direction.Axis.X ? 0.0f : 90.0f, 0.0f);
        $$10.f_20883_ = $$6.m_61233_().m_122434_() == Direction.Axis.X ? 0.0f : 90.0f;
        $$10.m_31506_();
        for (ServerPlayer $$12 : p_58256_.m_45976_(ServerPlayer.class, $$10.m_20191_().m_82400_(50.0))) {
            CriteriaTriggers.f_10580_.m_68256_($$12, $$10);
        }
        p_58256_.m_7967_($$10);
        for (int $$13 = 0; $$13 < $$5.m_61203_(); ++$$13) {
            for (int $$14 = 0; $$14 < $$5.m_61202_(); ++$$14) {
                p_58256_.m_6289_($$6.m_61229_($$13, $$14, 0).m_61176_(), Blocks.f_50016_);
            }
        }
    }

    public static boolean m_58267_(Level p_58268_, BlockPos p_58269_, ItemStack p_58270_) {
        if (p_58270_.m_150930_(Items.f_42679_) && p_58269_.m_123342_() >= p_58268_.m_141937_() + 2 && p_58268_.m_46791_() != Difficulty.PEACEFUL && !p_58268_.f_46443_) {
            return WitherSkullBlock.m_58274_().m_61184_(p_58268_, p_58269_) != null;
        }
        return false;
    }

    private static BlockPattern m_58273_() {
        if (f_58251_ == null) {
            f_58251_ = BlockPatternBuilder.m_61243_().m_61247_("^^^", "###", "~#~").m_61244_('#', p_58272_ -> p_58272_.m_61168_().m_204336_(BlockTags.f_13071_)).m_61244_('^', BlockInWorld.m_61169_(BlockStatePredicate.m_61287_(Blocks.f_50312_).or(BlockStatePredicate.m_61287_(Blocks.f_50313_)))).m_61244_('~', BlockInWorld.m_61169_(BlockMaterialPredicate.m_61262_(Material.f_76296_))).m_61249_();
        }
        return f_58251_;
    }

    private static BlockPattern m_58274_() {
        if (f_58252_ == null) {
            f_58252_ = BlockPatternBuilder.m_61243_().m_61247_("   ", "###", "~#~").m_61244_('#', p_58266_ -> p_58266_.m_61168_().m_204336_(BlockTags.f_13071_)).m_61244_('~', BlockInWorld.m_61169_(BlockMaterialPredicate.m_61262_(Material.f_76296_))).m_61249_();
        }
        return f_58252_;
    }
}

