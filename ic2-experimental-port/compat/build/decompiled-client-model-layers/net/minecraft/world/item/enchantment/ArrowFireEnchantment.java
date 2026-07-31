/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ArrowFireEnchantment
extends Enchantment {
    public ArrowFireEnchantment(Enchantment.Rarity p_44576_, EquipmentSlot ... p_44577_) {
        super(p_44576_, EnchantmentCategory.BOW, p_44577_);
    }

    @Override
    public int m_6183_(int p_44580_) {
        return 20;
    }

    @Override
    public int m_6175_(int p_44582_) {
        return 50;
    }

    @Override
    public int m_6586_() {
        return 1;
    }
}

