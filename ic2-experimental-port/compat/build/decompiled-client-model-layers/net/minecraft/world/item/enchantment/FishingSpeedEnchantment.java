/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FishingSpeedEnchantment
extends Enchantment {
    protected FishingSpeedEnchantment(Enchantment.Rarity p_45004_, EnchantmentCategory p_45005_, EquipmentSlot ... p_45006_) {
        super(p_45004_, p_45005_, p_45006_);
    }

    @Override
    public int m_6183_(int p_45009_) {
        return 15 + (p_45009_ - 1) * 9;
    }

    @Override
    public int m_6175_(int p_45011_) {
        return super.m_6183_(p_45011_) + 50;
    }

    @Override
    public int m_6586_() {
        return 3;
    }
}

