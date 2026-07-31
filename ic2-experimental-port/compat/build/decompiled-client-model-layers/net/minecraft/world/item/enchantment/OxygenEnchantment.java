/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class OxygenEnchantment
extends Enchantment {
    public OxygenEnchantment(Enchantment.Rarity p_45117_, EquipmentSlot ... p_45118_) {
        super(p_45117_, EnchantmentCategory.ARMOR_HEAD, p_45118_);
    }

    @Override
    public int m_6183_(int p_45121_) {
        return 10 * p_45121_;
    }

    @Override
    public int m_6175_(int p_45123_) {
        return this.m_6183_(p_45123_) + 30;
    }

    @Override
    public int m_6586_() {
        return 3;
    }
}

