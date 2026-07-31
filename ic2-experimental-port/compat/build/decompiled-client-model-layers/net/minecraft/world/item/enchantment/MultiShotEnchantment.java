/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class MultiShotEnchantment
extends Enchantment {
    public MultiShotEnchantment(Enchantment.Rarity p_45107_, EquipmentSlot ... p_45108_) {
        super(p_45107_, EnchantmentCategory.CROSSBOW, p_45108_);
    }

    @Override
    public int m_6183_(int p_45111_) {
        return 20;
    }

    @Override
    public int m_6175_(int p_45115_) {
        return 50;
    }

    @Override
    public int m_6586_() {
        return 1;
    }

    @Override
    public boolean m_5975_(Enchantment p_45113_) {
        return super.m_5975_(p_45113_) && p_45113_ != Enchantments.f_44961_;
    }
}

