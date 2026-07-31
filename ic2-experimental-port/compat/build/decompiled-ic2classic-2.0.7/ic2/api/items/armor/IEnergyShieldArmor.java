/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IEnergyShieldArmor {
    public boolean addsShieldEffect(EquipmentSlot var1, LivingEntity var2, ItemStack var3);

    public boolean isEffectAlwaysOn(EquipmentSlot var1, LivingEntity var2, ItemStack var3);

    public static boolean addsEnergyShieldEffect(LivingEntity living) {
        if (living == null) {
            return false;
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack;
            if (slot.m_20743_() != EquipmentSlot.Type.ARMOR || !((stack = living.m_6844_(slot)).m_41720_() instanceof IEnergyShieldArmor) || !((IEnergyShieldArmor)stack.m_41720_()).addsShieldEffect(slot, living, stack)) continue;
            return true;
        }
        return false;
    }

    public static boolean shouldAlwaysShowEffect(LivingEntity living) {
        if (living == null) {
            return false;
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            IEnergyShieldArmor shield;
            ItemStack stack;
            if (slot.m_20743_() != EquipmentSlot.Type.ARMOR || !((stack = living.m_6844_(slot)).m_41720_() instanceof IEnergyShieldArmor) || !((IEnergyShieldArmor)stack.m_41720_()).addsShieldEffect(slot, living, stack) || !(shield = (IEnergyShieldArmor)stack.m_41720_()).addsShieldEffect(slot, living, stack) || !shield.isEffectAlwaysOn(slot, living, stack)) continue;
            return true;
        }
        return false;
    }
}

