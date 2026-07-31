/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class ArrowPiercingEnchantment
extends Enchantment {
    public ArrowPiercingEnchantment(Enchantment.Rarity p_44602_, EquipmentSlot ... p_44603_) {
        super(p_44602_, EnchantmentCategory.CROSSBOW, p_44603_);
    }

    @Override
    public int m_6183_(int p_44606_) {
        return 1 + (p_44606_ - 1) * 10;
    }

    @Override
    public int m_6175_(int p_44610_) {
        return 50;
    }

    @Override
    public int m_6586_() {
        return 4;
    }

    @Override
    public boolean m_5975_(Enchantment p_44608_) {
        return super.m_5975_(p_44608_) && p_44608_ != Enchantments.f_44959_;
    }
}

