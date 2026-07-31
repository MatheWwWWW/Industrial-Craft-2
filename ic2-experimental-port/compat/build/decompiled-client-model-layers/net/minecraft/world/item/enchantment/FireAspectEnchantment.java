/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FireAspectEnchantment
extends Enchantment {
    protected FireAspectEnchantment(Enchantment.Rarity p_44996_, EquipmentSlot ... p_44997_) {
        super(p_44996_, EnchantmentCategory.WEAPON, p_44997_);
    }

    @Override
    public int m_6183_(int p_45000_) {
        return 10 + 20 * (p_45000_ - 1);
    }

    @Override
    public int m_6175_(int p_45002_) {
        return super.m_6183_(p_45002_) + 50;
    }

    @Override
    public int m_6586_() {
        return 2;
    }
}

