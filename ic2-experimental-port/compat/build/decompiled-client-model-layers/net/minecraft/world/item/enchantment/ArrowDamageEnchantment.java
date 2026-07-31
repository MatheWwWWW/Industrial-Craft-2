/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ArrowDamageEnchantment
extends Enchantment {
    public ArrowDamageEnchantment(Enchantment.Rarity p_44568_, EquipmentSlot ... p_44569_) {
        super(p_44568_, EnchantmentCategory.BOW, p_44569_);
    }

    @Override
    public int m_6183_(int p_44572_) {
        return 1 + (p_44572_ - 1) * 10;
    }

    @Override
    public int m_6175_(int p_44574_) {
        return this.m_6183_(p_44574_) + 15;
    }

    @Override
    public int m_6586_() {
        return 5;
    }
}

