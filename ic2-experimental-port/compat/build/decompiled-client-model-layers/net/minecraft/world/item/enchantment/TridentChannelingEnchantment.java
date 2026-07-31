/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class TridentChannelingEnchantment
extends Enchantment {
    public TridentChannelingEnchantment(Enchantment.Rarity p_45219_, EquipmentSlot ... p_45220_) {
        super(p_45219_, EnchantmentCategory.TRIDENT, p_45220_);
    }

    @Override
    public int m_6183_(int p_45223_) {
        return 25;
    }

    @Override
    public int m_6175_(int p_45227_) {
        return 50;
    }

    @Override
    public int m_6586_() {
        return 1;
    }
}

