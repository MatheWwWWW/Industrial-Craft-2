/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class KnockbackEnchantment
extends Enchantment {
    protected KnockbackEnchantment(Enchantment.Rarity p_45079_, EquipmentSlot ... p_45080_) {
        super(p_45079_, EnchantmentCategory.WEAPON, p_45080_);
    }

    @Override
    public int m_6183_(int p_45083_) {
        return 5 + 20 * (p_45083_ - 1);
    }

    @Override
    public int m_6175_(int p_45085_) {
        return super.m_6183_(p_45085_) + 50;
    }

    @Override
    public int m_6586_() {
        return 2;
    }
}

