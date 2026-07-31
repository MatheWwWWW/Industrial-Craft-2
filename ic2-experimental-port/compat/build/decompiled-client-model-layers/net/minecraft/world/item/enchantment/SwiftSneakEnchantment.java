/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SwiftSneakEnchantment
extends Enchantment {
    public SwiftSneakEnchantment(Enchantment.Rarity p_220306_, EquipmentSlot ... p_220307_) {
        super(p_220306_, EnchantmentCategory.ARMOR_LEGS, p_220307_);
    }

    @Override
    public int m_6183_(int p_220310_) {
        return p_220310_ * 25;
    }

    @Override
    public int m_6175_(int p_220313_) {
        return this.m_6183_(p_220313_) + 50;
    }

    @Override
    public boolean m_6591_() {
        return true;
    }

    @Override
    public boolean m_6594_() {
        return false;
    }

    @Override
    public boolean m_6592_() {
        return false;
    }

    @Override
    public int m_6586_() {
        return 3;
    }
}

