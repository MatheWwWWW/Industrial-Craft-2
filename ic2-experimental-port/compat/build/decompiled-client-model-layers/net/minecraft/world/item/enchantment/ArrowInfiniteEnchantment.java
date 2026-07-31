/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MendingEnchantment;

public class ArrowInfiniteEnchantment
extends Enchantment {
    public ArrowInfiniteEnchantment(Enchantment.Rarity p_44584_, EquipmentSlot ... p_44585_) {
        super(p_44584_, EnchantmentCategory.BOW, p_44585_);
    }

    @Override
    public int m_6183_(int p_44588_) {
        return 20;
    }

    @Override
    public int m_6175_(int p_44592_) {
        return 50;
    }

    @Override
    public int m_6586_() {
        return 1;
    }

    @Override
    public boolean m_5975_(Enchantment p_44590_) {
        if (p_44590_ instanceof MendingEnchantment) {
            return false;
        }
        return super.m_5975_(p_44590_);
    }
}

