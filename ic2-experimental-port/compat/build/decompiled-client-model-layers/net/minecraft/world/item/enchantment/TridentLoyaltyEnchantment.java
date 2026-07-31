/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class TridentLoyaltyEnchantment
extends Enchantment {
    public TridentLoyaltyEnchantment(Enchantment.Rarity p_45240_, EquipmentSlot ... p_45241_) {
        super(p_45240_, EnchantmentCategory.TRIDENT, p_45241_);
    }

    @Override
    public int m_6183_(int p_45244_) {
        return 5 + p_45244_ * 7;
    }

    @Override
    public int m_6175_(int p_45248_) {
        return 50;
    }

    @Override
    public int m_6586_() {
        return 3;
    }
}

