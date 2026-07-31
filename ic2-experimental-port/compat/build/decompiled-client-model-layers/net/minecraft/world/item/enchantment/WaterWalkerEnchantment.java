/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class WaterWalkerEnchantment
extends Enchantment {
    public WaterWalkerEnchantment(Enchantment.Rarity p_45280_, EquipmentSlot ... p_45281_) {
        super(p_45280_, EnchantmentCategory.ARMOR_FEET, p_45281_);
    }

    @Override
    public int m_6183_(int p_45284_) {
        return p_45284_ * 10;
    }

    @Override
    public int m_6175_(int p_45288_) {
        return this.m_6183_(p_45288_) + 15;
    }

    @Override
    public int m_6586_() {
        return 3;
    }

    @Override
    public boolean m_5975_(Enchantment p_45286_) {
        return super.m_5975_(p_45286_) && p_45286_ != Enchantments.f_44974_;
    }
}

