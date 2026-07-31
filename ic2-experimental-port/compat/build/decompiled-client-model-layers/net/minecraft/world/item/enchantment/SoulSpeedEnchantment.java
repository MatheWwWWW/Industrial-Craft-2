/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SoulSpeedEnchantment
extends Enchantment {
    public SoulSpeedEnchantment(Enchantment.Rarity p_45175_, EquipmentSlot ... p_45176_) {
        super(p_45175_, EnchantmentCategory.ARMOR_FEET, p_45176_);
    }

    @Override
    public int m_6183_(int p_45179_) {
        return p_45179_ * 10;
    }

    @Override
    public int m_6175_(int p_45182_) {
        return this.m_6183_(p_45182_) + 15;
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

