/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class DigDurabilityEnchantment
extends Enchantment {
    protected DigDurabilityEnchantment(Enchantment.Rarity p_44648_, EquipmentSlot ... p_44649_) {
        super(p_44648_, EnchantmentCategory.BREAKABLE, p_44649_);
    }

    @Override
    public int m_6183_(int p_44652_) {
        return 5 + (p_44652_ - 1) * 8;
    }

    @Override
    public int m_6175_(int p_44660_) {
        return super.m_6183_(p_44660_) + 50;
    }

    @Override
    public int m_6586_() {
        return 3;
    }

    @Override
    public boolean m_6081_(ItemStack p_44654_) {
        if (p_44654_.m_41763_()) {
            return true;
        }
        return super.m_6081_(p_44654_);
    }

    public static boolean m_220282_(ItemStack p_220283_, int p_220284_, RandomSource p_220285_) {
        if (p_220283_.m_41720_() instanceof ArmorItem && p_220285_.m_188501_() < 0.6f) {
            return false;
        }
        return p_220285_.m_188503_(p_220284_ + 1) > 0;
    }
}

