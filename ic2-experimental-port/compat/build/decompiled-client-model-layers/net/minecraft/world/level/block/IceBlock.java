/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;

public class IceBlock
extends HalfTransparentBlock {
    public IceBlock(BlockBehaviour.Properties p_54155_) {
        super(p_54155_);
    }

    @Override
    public void m_6240_(Level p_54157_, Player p_54158_, BlockPos p_54159_, BlockState p_54160_, @Nullable BlockEntity p_54161_, ItemStack p_54162_) {
        super.m_6240_(p_54157_, p_54158_, p_54159_, p_54160_, p_54161_, p_54162_);
        if (EnchantmentHelper.m_44843_(Enchantments.f_44985_, p_54162_) == 0) {
            if (p_54157_.m_6042_().f_63857_()) {
                p_54157_.m_7471_(p_54159_, false);
                return;
            }
            Material $$6 = p_54157_.m_8055_(p_54159_.m_7495_()).m_60767_();
            if ($$6.m_76334_() || $$6.m_76332_()) {
                p_54157_.m_46597_(p_54159_, Blocks.f_49990_.m_49966_());
            }
        }
    }

    @Override
    public void m_213898_(BlockState p_221355_, ServerLevel p_221356_, BlockPos p_221357_, RandomSource p_221358_) {
        if (p_221356_.m_45517_(LightLayer.BLOCK, p_221357_) > 11 - p_221355_.m_60739_(p_221356_, p_221357_)) {
            this.m_54168_(p_221355_, p_221356_, p_221357_);
        }
    }

    protected void m_54168_(BlockState p_54169_, Level p_54170_, BlockPos p_54171_) {
        if (p_54170_.m_6042_().f_63857_()) {
            p_54170_.m_7471_(p_54171_, false);
            return;
        }
        p_54170_.m_46597_(p_54171_, Blocks.f_49990_.m_49966_());
        p_54170_.m_46586_(p_54171_, Blocks.f_49990_, p_54171_);
    }

    @Override
    public PushReaction m_5537_(BlockState p_54173_) {
        return PushReaction.NORMAL;
    }
}

