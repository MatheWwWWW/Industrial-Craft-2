/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SweepingEdgeEnchantment
extends Enchantment {
    public SweepingEdgeEnchantment(Enchantment.Rarity p_45186_, EquipmentSlot ... p_45187_) {
        super(p_45186_, EnchantmentCategory.WEAPON, p_45187_);
    }

    @Override
    public int m_6183_(int p_45190_) {
        return 5 + (p_45190_ - 1) * 9;
    }

    @Override
    public int m_6175_(int p_45192_) {
        return this.m_6183_(p_45192_) + 15;
    }

    @Override
    public int m_6586_() {
        return 3;
    }

    public static float m_45193_(int p_45194_) {
        return 1.0f - 1.0f / (float)(p_45194_ + 1);
    }
}

