/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;

public class FrostWalkerEnchantment
extends Enchantment {
    public FrostWalkerEnchantment(Enchantment.Rarity p_45013_, EquipmentSlot ... p_45014_) {
        super(p_45013_, EnchantmentCategory.ARMOR_FEET, p_45014_);
    }

    @Override
    public int m_6183_(int p_45017_) {
        return p_45017_ * 10;
    }

    @Override
    public int m_6175_(int p_45027_) {
        return this.m_6183_(p_45027_) + 15;
    }

    @Override
    public boolean m_6591_() {
        return true;
    }

    @Override
    public int m_6586_() {
        return 2;
    }

    public static void m_45018_(LivingEntity p_45019_, Level p_45020_, BlockPos p_45021_, int p_45022_) {
        if (!p_45019_.m_20096_()) {
            return;
        }
        BlockState $$4 = Blocks.f_50449_.m_49966_();
        float $$5 = Math.min(16, 2 + p_45022_);
        BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
        for (BlockPos $$7 : BlockPos.m_121940_(p_45021_.m_7637_(-$$5, -1.0, -$$5), p_45021_.m_7637_($$5, -1.0, $$5))) {
            BlockState $$9;
            if (!$$7.m_203195_(p_45019_.m_20182_(), $$5)) continue;
            $$6.m_122178_($$7.m_123341_(), $$7.m_123342_() + 1, $$7.m_123343_());
            BlockState $$8 = p_45020_.m_8055_($$6);
            if (!$$8.m_60795_() || ($$9 = p_45020_.m_8055_($$7)).m_60767_() != Material.f_76305_ || $$9.m_61143_(LiquidBlock.f_54688_) != 0 || !$$4.m_60710_(p_45020_, $$7) || !p_45020_.m_45752_($$4, $$7, CollisionContext.m_82749_())) continue;
            p_45020_.m_46597_($$7, $$4);
            p_45020_.m_186460_($$7, Blocks.f_50449_, Mth.m_216271_(p_45019_.m_217043_(), 60, 120));
        }
    }

    @Override
    public boolean m_5975_(Enchantment p_45024_) {
        return super.m_5975_(p_45024_) && p_45024_ != Enchantments.f_44973_;
    }
}

