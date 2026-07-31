/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class TridentRiptideEnchantment
extends Enchantment {
    public TridentRiptideEnchantment(Enchantment.Rarity p_45250_, EquipmentSlot ... p_45251_) {
        super(p_45250_, EnchantmentCategory.TRIDENT, p_45251_);
    }

    @Override
    public int m_6183_(int p_45254_) {
        return 10 + p_45254_ * 7;
    }

    @Override
    public int m_6175_(int p_45258_) {
        return 50;
    }

    @Override
    public int m_6586_() {
        return 3;
    }

    @Override
    public boolean m_5975_(Enchantment p_45256_) {
        return super.m_5975_(p_45256_) && p_45256_ != Enchantments.f_44955_ && p_45256_ != Enchantments.f_44958_;
    }
}

