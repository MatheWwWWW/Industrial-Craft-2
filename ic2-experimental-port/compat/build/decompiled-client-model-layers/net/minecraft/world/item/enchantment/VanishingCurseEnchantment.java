/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class VanishingCurseEnchantment
extends Enchantment {
    public VanishingCurseEnchantment(Enchantment.Rarity p_45270_, EquipmentSlot ... p_45271_) {
        super(p_45270_, EnchantmentCategory.VANISHABLE, p_45271_);
    }

    @Override
    public int m_6183_(int p_45274_) {
        return 25;
    }

    @Override
    public int m_6175_(int p_45277_) {
        return 50;
    }

    @Override
    public int m_6586_() {
        return 1;
    }

    @Override
    public boolean m_6591_() {
        return true;
    }

    @Override
    public boolean m_6589_() {
        return true;
    }
}

