/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class TridentImpalerEnchantment
extends Enchantment {
    public TridentImpalerEnchantment(Enchantment.Rarity p_45229_, EquipmentSlot ... p_45230_) {
        super(p_45229_, EnchantmentCategory.TRIDENT, p_45230_);
    }

    @Override
    public int m_6183_(int p_45233_) {
        return 1 + (p_45233_ - 1) * 8;
    }

    @Override
    public int m_6175_(int p_45238_) {
        return this.m_6183_(p_45238_) + 20;
    }

    @Override
    public int m_6586_() {
        return 5;
    }

    @Override
    public float m_7335_(int p_45235_, MobType p_45236_) {
        if (p_45236_ == MobType.f_21644_) {
            return (float)p_45235_ * 2.5f;
        }
        return 0.0f;
    }
}

