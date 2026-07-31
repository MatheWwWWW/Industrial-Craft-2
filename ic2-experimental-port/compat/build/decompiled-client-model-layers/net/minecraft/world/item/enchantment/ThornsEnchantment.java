/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import java.util.Map;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class ThornsEnchantment
extends Enchantment {
    private static final float f_151302_ = 0.15f;

    public ThornsEnchantment(Enchantment.Rarity p_45196_, EquipmentSlot ... p_45197_) {
        super(p_45196_, EnchantmentCategory.ARMOR_CHEST, p_45197_);
    }

    @Override
    public int m_6183_(int p_45200_) {
        return 10 + 20 * (p_45200_ - 1);
    }

    @Override
    public int m_6175_(int p_45210_) {
        return super.m_6183_(p_45210_) + 50;
    }

    @Override
    public int m_6586_() {
        return 3;
    }

    @Override
    public boolean m_6081_(ItemStack p_45205_) {
        if (p_45205_.m_41720_() instanceof ArmorItem) {
            return true;
        }
        return super.m_6081_(p_45205_);
    }

    @Override
    public void m_7675_(LivingEntity p_45215_, Entity p_45216_, int p_45217_) {
        RandomSource $$3 = p_45215_.m_217043_();
        Map.Entry<EquipmentSlot, ItemStack> $$4 = EnchantmentHelper.m_44906_(Enchantments.f_44972_, p_45215_);
        if (ThornsEnchantment.m_220316_(p_45217_, $$3)) {
            if (p_45216_ != null) {
                p_45216_.m_6469_(DamageSource.m_19335_(p_45215_), ThornsEnchantment.m_220319_(p_45217_, $$3));
            }
            if ($$4 != null) {
                $$4.getValue().m_41622_(2, p_45215_, p_45208_ -> p_45208_.m_21166_((EquipmentSlot)((Object)((Object)$$4.getKey()))));
            }
        }
    }

    public static boolean m_220316_(int p_220317_, RandomSource p_220318_) {
        if (p_220317_ <= 0) {
            return false;
        }
        return p_220318_.m_188501_() < 0.15f * (float)p_220317_;
    }

    public static int m_220319_(int p_220320_, RandomSource p_220321_) {
        if (p_220320_ > 10) {
            return p_220320_ - 10;
        }
        return 1 + p_220321_.m_188503_(4);
    }
}

